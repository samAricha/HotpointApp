package teka.mobile.hotpointappv1.viewTier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import teka.mobile.hotpointappv1.R
import teka.mobile.hotpointappv1.modelTier.models.MovieModelItem

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.MyViewHolder>(){
    private var moviesList: List<MovieModelItem>? = null

    fun setMoviesList(moviesList: List<MovieModelItem>){
        this.moviesList = moviesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.movie_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MyViewHolder, position: Int) {
        holder.bind(moviesList?.get(position)!!)
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + moviesList!!.get(position).poster_path)
            .into((holder as MyViewHolder).movieImage)
    }

    override fun getItemCount(): Int {
        if(moviesList ==null)return 0
        else return moviesList?.size!!
    }

    /*
    * VIEWHOLDER CLASS
    * */
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        //val movieId = view.findViewById<TextView>(R.id.movieId)
        //val movieTitle = view.findViewById<TextView>(R.id.movieTitle)
        val movieImage = view.findViewById<ImageView>(R.id.image_movie)

        fun bind(data: MovieModelItem){
            //movieId.text = data.id.toString()
            //movieTitle.text = data.name
        }

    }

}