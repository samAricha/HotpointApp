package teka.mobile.hotpointappv1

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import teka.mobile.hotpointappv1.accessoriesModule.accessories.AccessoriesActivity
import teka.mobile.hotpointappv1.accessoriesModule.accessories.AccessoriesFragment
import teka.mobile.hotpointappv1.databinding.ActivityMainBinding
import teka.mobile.hotpointappv1.loginModule.LoginActivity
import teka.mobile.hotpointappv1.modelTier.data.remote.api.RetroInstance
import teka.mobile.hotpointappv1.modelTier.data.remote.api.RetroServiceInterface
import teka.mobile.hotpointappv1.modelTier.models.MovieModel
import teka.mobile.hotpointappv1.modelTier.models.MovieModelItem
import teka.mobile.hotpointappv1.utils.Credentials
import teka.mobile.hotpointappv1.viewModelTier.MovieActivityVewModel
import teka.mobile.hotpointappv1.viewTier.DiscoverMoviesFragment
import teka.mobile.hotpointappv1.viewTier.MovieListInterface


class MainActivity : AppCompatActivity(), MovieListInterface {

    lateinit var binding: ActivityMainBinding
    lateinit var fragmentContainer: FragmentContainerView
    lateinit var fab:FloatingActionButton
    lateinit var moviesList: List<MovieModelItem>
    lateinit var movieModel: MovieModel
    lateinit var movieActivityVewModel: MovieActivityVewModel
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fragmentContainer = binding.fragmentContainer
        fab = binding.fab

        mAuth = FirebaseAuth.getInstance()


        movieActivityVewModel = ViewModelProvider(this)[MovieActivityVewModel::class.java]

        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, AccessoriesActivity::class.java)
            startActivity(intent)
        })

        if (savedInstanceState == null) {
            //iniViewModel()
            getData()
        }

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                    getData()
                }R.id.profile ->{
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }R.id.settings ->{
                    setupAccessoriesFragment()
                }else ->{
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search ->{
                Toast.makeText(this, "you clicked Search!", Toast.LENGTH_LONG).show()
                return true
            }R.id.logout ->{
                mAuth.signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                return true
            }R.id.share ->{
                Toast.makeText(this, "you clicked Share!", Toast.LENGTH_LONG).show()
                return true
            }R.id.whatsapp ->{
                Toast.makeText(this, "you clicked Whatsapp!", Toast.LENGTH_LONG).show()
                return true
            }R.id.instagram ->{
                Toast.makeText(this, "you clicked Instagram!", Toast.LENGTH_LONG).show()
                return true
            }else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun iniViewModel(){
        movieActivityVewModel.getMovieResponse()
        //setupViewFragment(movieModel)
    }

    private fun getData() {
        //THIS SHOULD BE DONE OUTSIDE UI e.g INSIDE VIEWMODEL
        val retroInstance: RetroServiceInterface = RetroInstance.getRetrofit()
        val retrofitData = retroInstance.getMovieData(Credentials.API_KEY)
        d("MainActivity-Log:", "inGetData: $retrofitData")
        retrofitData.enqueue(object : Callback<MovieModel?> {
            override fun onResponse(call: Call<MovieModel?>, response: Response<MovieModel?>) {
                d("MainActivity-Log:", "inGetData: "+response.body())
                setupViewFragment(response.body()!!)

            }

            override fun onFailure(call: Call<MovieModel?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun setupViewFragment(retroResponse: MovieModel) {
        moviesList = retroResponse.results
        val discoverMoviesFragment = DiscoverMoviesFragment()
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, discoverMoviesFragment)
            .commit()
    }

    private fun setupAccessoriesFragment(){
        val accessoriesFragment = AccessoriesFragment(applicationContext)
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, accessoriesFragment)
            .commit()
    }

    override fun onMoviesListAdded(): List<MovieModelItem> {
        return moviesList;
    }


}