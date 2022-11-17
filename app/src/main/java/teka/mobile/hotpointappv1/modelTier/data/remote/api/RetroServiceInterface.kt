package teka.mobile.hotpointappv1.modelTier.data.remote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import teka.mobile.hotpointappv1.modelTier.models.MovieModel

//https://api.themoviedb.org/3/trending/all/day?api_key=<<api-key>>
// Service interface aimed at completing the url
interface RetroServiceInterface {
    @GET("trending/all/day")
    fun getMovieData(@Query("api_key") key:String): Call<MovieModel>
}