package teka.mobile.hotpointappv1.accessoriesModule.accessories;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import teka.mobile.hotpointappv1.R;
import teka.mobile.hotpointappv1.accessoriesModule.accessories.models.Upload;
import teka.mobile.hotpointappv1.viewTier.MovieListInterface;

public class AccessoriesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;

    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private Context ctx;

    private ProgressBar mProgressCircle;

    public AccessoriesFragment() {
        // Required empty public constructor
    }
    public AccessoriesFragment(Context context) {
        this.ctx = context;
    }

    public static AccessoriesFragment newInstance() {
        AccessoriesFragment fragment = new AccessoriesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_accessories, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mProgressCircle = rootView.findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);




        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Upload upld = postSnapshot.getValue(Upload.class);
                    mUploads.add(upld);
                }
                mImageAdapter = new ImageAdapter(context, mUploads);
                mRecyclerView.setAdapter(mImageAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);

            }
        });


    }
}