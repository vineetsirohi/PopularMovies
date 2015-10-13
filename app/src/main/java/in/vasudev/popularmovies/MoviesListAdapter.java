package in.vasudev.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.vasudev.popularmovies.model.MovieInfo;
import in.vasudev.popularmovies.model.TheMovieDbUtils;
import in.vasudev.popularmovies.utils.PicassoUtils;

/**
 * Created by vineet on 11/09/2015.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<MovieInfo> movieInfos;

    private View.OnClickListener mOnItemClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MoviesListAdapter(Context context, List<MovieInfo> myDataset,
                             View.OnClickListener onItemClickListener) {
        mContext = context;
        movieInfos = myDataset;
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout, parent, false);
        v.setOnClickListener(mOnItemClickListener);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final MyViewHolder holder = (MyViewHolder) viewHolder;

        final MovieInfo movieInfo = movieInfos.get(position);

        Picasso.with(mContext)
                .load(TheMovieDbUtils.MOVIE_IMAGE_PATH_URL + movieInfo.getPosterPath())
                .transform(PicassoUtils.getFitWidthTransformation(holder.imageView.getWidth()))
                .into(holder.imageView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movieInfos == null ? 0 : movieInfos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView = null;

        public MyViewHolder(ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }

    }



}
