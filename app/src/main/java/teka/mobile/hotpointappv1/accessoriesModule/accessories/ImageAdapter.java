package teka.mobile.hotpointappv1.accessoriesModule.accessories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import teka.mobile.hotpointappv1.R;
import teka.mobile.hotpointappv1.accessoriesModule.accessories.models.Upload;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads){
        this.mContext = context;
        this.mUploads = uploads;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_accessory, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getmName());
        //Log.i("-----------name-------", uploadCurrent.getProductPhoto());

        Glide.with(mContext)
                .load(uploadCurrent.getmImgUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_shopping_cart_24)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    //View holder class
    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textViewName;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            textViewName = itemView.findViewById(R.id.product_name);
        }
    }
}
