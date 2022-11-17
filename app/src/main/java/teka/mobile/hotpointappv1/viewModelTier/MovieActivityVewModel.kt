package teka.mobile.hotpointappv1.viewModelTier

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import teka.mobile.hotpointappv1.modelTier.data.remote.api.RetroInstance
import teka.mobile.hotpointappv1.modelTier.data.remote.api.RetroServiceInterface
import teka.mobile.hotpointappv1.modelTier.models.MovieModel
import teka.mobile.hotpointappv1.utils.Credentials

class MovieActivityVewModel():ViewModel(){

    lateinit var movieModel:MovieModel

    init {

    }

    fun getMovieResponse(): MovieModel {
        //THIS SHOULD BE DONE OUTSIDE UI e.g INSIDE VIEWMODEL
        val retroInstance: RetroServiceInterface = RetroInstance.getRetrofit()
        val retrofitData = retroInstance.getMovieData(Credentials.API_KEY)
        Log.d("MainActivity-Log:", "inGetData: $retrofitData")
        retrofitData.enqueue(object : Callback<MovieModel?> {
            override fun onResponse(call: Call<MovieModel?>, response: Response<MovieModel?>) {
                Log.d("MainActivity-Log:", "inGetData: " + response.body())
                movieModel = response.body()!!

            }

            override fun onFailure(call: Call<MovieModel?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return movieModel
    }
}