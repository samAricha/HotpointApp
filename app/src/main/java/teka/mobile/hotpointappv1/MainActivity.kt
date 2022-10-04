package teka.mobile.hotpointappv1

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import teka.mobile.hotpointappv1.accessoriesModule.accessories.AccessoriesActivity
import teka.mobile.hotpointappv1.databinding.ActivityMainBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fragmentContainer = binding.fragmentContainer
        fab = binding.fab

        movieActivityVewModel = ViewModelProvider(this)[MovieActivityVewModel::class.java]

        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, AccessoriesActivity::class.java)
            startActivity(intent)
        })

        if (savedInstanceState == null) {

            //iniViewModel()
            getData()
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
        var discoverMoviesFragment: DiscoverMoviesFragment = DiscoverMoviesFragment()
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, discoverMoviesFragment)
            .commit()
    }

    override fun onMoviesListAdded(): List<MovieModelItem> {
        return moviesList;
    }


}