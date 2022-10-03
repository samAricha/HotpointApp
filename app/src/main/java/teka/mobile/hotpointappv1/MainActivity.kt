package teka.mobile.hotpointappv1

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import teka.mobile.hotpointappv1.databinding.ActivityMainBinding
import teka.mobile.hotpointappv1.modelTier.data.remote.api.RetroInstance
import teka.mobile.hotpointappv1.modelTier.data.remote.api.RetroServiceInterface
import teka.mobile.hotpointappv1.modelTier.models.MovieModel
import teka.mobile.hotpointappv1.modelTier.models.MovieModelItem
import teka.mobile.hotpointappv1.utils.Credentials
import teka.mobile.hotpointappv1.viewTier.DiscoverMoviesFragment
import teka.mobile.hotpointappv1.viewTier.MovieListInterface


class MainActivity : AppCompatActivity(), MovieListInterface {

    lateinit var binding: ActivityMainBinding
    lateinit var fragmentContainer: FragmentContainerView
    lateinit var moviesList: List<MovieModelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fragmentContainer = binding.fragmentContainer

        if (savedInstanceState == null) {
            getData()
        }

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