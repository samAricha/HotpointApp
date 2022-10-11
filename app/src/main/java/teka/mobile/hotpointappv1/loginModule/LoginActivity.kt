package teka.mobile.hotpointappv1.loginModule

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //initialization
        phone = binding.registerPhoneNumberInput
        otp = binding.otp
        btnGenOTP = binding.genOtp
        btnVerifyOTP = binding.verityOtpBtn

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        btnGenOTP.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(phone.text.toString())){
                Toast.makeText(this@LoginActivity, "Enter Valid Phone No.", Toast.LENGTH_LONG).show()
            }else{
                val number = phone.text.toString()
                sendVerificationCode(number)

            }
        })

        btnVerifyOTP.setOnClickListener(View.OnClickListener {

            if (TextUtils.isEmpty(otp.text.toString())){
                Toast.makeText(this@LoginActivity, "Code invalid", Toast.LENGTH_LONG).show()
            }else{
                var inputCode = otp.text.toString()
                sendVerificationCode(inputCode)

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
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+254$phoneNumber")       // Phone number to verify
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
            val code = credential.smsCode
            if(code != null){
                signInWithPhoneAuthCredential(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)

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

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            //resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(code: String) {
            verifyCode(code)
    }
    private fun verifyCode(code:String) {
        //code entered by user cross-referenced with that sent by firebase
        var credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
        signinbyCredentials(credential)
    }

    private fun signinbyCredentials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this@LoginActivity, "Login Successfull", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            })
    }
}