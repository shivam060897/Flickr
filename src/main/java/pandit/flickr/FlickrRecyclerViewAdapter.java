package pandit.flickr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrViewHolder> {
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photoList) {
        mContext = context;
        mPhotoList = photoList;
    }

    @Override
    public FlickrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Called by layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrViewHolder holder, int position) {

        Photo photoItem = mPhotoList.get(position);
        Picasso.get().load(photoItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
        holder.title.setText(photoItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return ((mPhotoList != null) && (mPhotoList.size() != 0) ? mPhotoList.size() : 0);
    }

    void loadNewData(List<Photo> newPhoto) {
        mPhotoList = newPhoto;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int Position) {
        return ((mPhotoList != null) && (mPhotoList.size() != 0) ? mPhotoList.get(Position) : null);
    }

    static class FlickrViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;

        public FlickrViewHolder(View itemView) {
            super(itemView);
            this.thumbnail = itemView.findViewById(R.id.thumbnail);
            this.title = itemView.findViewById(R.id.title);
        }
    }
}
