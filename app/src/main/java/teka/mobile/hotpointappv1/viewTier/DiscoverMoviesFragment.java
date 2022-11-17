package teka.mobile.hotpointappv1.viewTier;

import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import teka.mobile.hotpointappv1.R;
import teka.mobile.hotpointappv1.modelTier.models.MovieModel;
import teka.mobile.hotpointappv1.modelTier.models.MovieModelItem;
import teka.mobile.hotpointappv1.utils.ItemOffsetDecoration;


public class DiscoverMoviesFragment extends Fragment {

    TextView txtView;
    MovieModel movieModel;
    List<MovieModelItem> moviesList;
    int pages;
    MovieListInterface movieListInterface;
    RecyclerView recyclerView;


    public static DiscoverMoviesFragment newInstance() {
        return new DiscoverMoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View rootView = inflater.inflate(R.layout.fragment_discover_movies, container, false);
        recyclerView= rootView.findViewById(R.id.rv_movies_list);
        //txtView = rootView.findViewById(R.id.fragmentView);
//        txtView.setText(moviesList.get(0).toString());
        setupListAdapter(moviesList);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        movieListInterface = (MovieListInterface) context;
        moviesList= movieListInterface.onMoviesListAdded();

    }

    private void setupListAdapter(List<MovieModelItem> moviesList ) {
        final MovieAdapter movieAdapter = new MovieAdapter();
        movieAdapter.setMoviesList(moviesList);
        final GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count));

        // setup recyclerView
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

    }
}
