package teka.mobile.hotpointappv1.loginModule

import android.R
import android.app.ActivityOptions
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import teka.mobile.hotpointappv1.MainActivity
import teka.mobile.hotpointappv1.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity: AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    lateinit var phone: EditText
    lateinit var otp: EditText
    lateinit var btnGenOTP: Button
    lateinit var btnVerifyOTP: Button
    lateinit var storedVerificationId:String
    lateinit var progressBar1:ProgressBar
    lateinit var usableNum:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        phone = binding.editTextPhone
        btnGenOTP = binding.loginButton
        progressBar1 = binding.probar1

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        btnGenOTP.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(phone.text.toString())){
                Toast.makeText(this@LoginActivity, "Enter Valid Phone No.", Toast.LENGTH_LONG).show()
            }else{
                val number = phone.text.toString().substring(1)
                sendVerificationCode(number)

            }
        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun sendVerificationCode(phoneNumber:String) {
        progressBar1.visibility = View.VISIBLE
        btnGenOTP.visibility = View.INVISIBLE
        usableNum = "+254$phoneNumber"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(usableNum)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            progressBar1.visibility = View.GONE
            btnGenOTP.visibility = View.VISIBLE
            /*val code = credential.smsCode
            if(code != null){
                signInWithPhoneAuthCredential(code)
            }*/
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)
            Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")
            Toast.makeText(this@LoginActivity, "code sent", Toast.LENGTH_SHORT).show()

            progressBar1.visibility = View.GONE
            btnGenOTP.visibility = View.VISIBLE

            val intent = Intent(applicationContext, VerificationActivity::class.java)
           intent.putExtra("mobile", phone.text.toString().substring(1))
            intent.putExtra("backendotp", verificationId)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        }
    }


}