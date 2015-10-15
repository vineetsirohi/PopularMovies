package in.vasudev.popularmovies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import in.vasudev.popularmovies.model.TheMovieDbUtils;
import in.vasudev.popularmovies.model.movie_detail.MovieDetail;
import in.vasudev.popularmovies.volley.GsonRequest;
import in.vasudev.popularmovies.volley.VolleySingleton;

public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private String movieId;
    private MovieDetail movieDetail;

    private TextView releaseDate;
    private TextView runtime;
    private TextView voteAverage;

    private ImageView imageView;
    private Button buttonMarkFavourite;

    private TextView plotSynopsis;

    private ProgressBar progressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            movieId = getArguments().getString(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        releaseDate = (TextView) rootView.findViewById(R.id.textViewReleaseDate);
        runtime = (TextView) rootView.findViewById(R.id.textViewRuntime);
        voteAverage = (TextView) rootView.findViewById(R.id.textViewVoteAverage);

        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        buttonMarkFavourite = (Button) rootView.findViewById(R.id.buttonFavourite);

        plotSynopsis = (TextView) rootView.findViewById(R.id.textViewPlot);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //        Request movie list
        GsonRequest<MovieDetail> movieListGsonRequest = new GsonRequest<>(
                TheMovieDbUtils.movieDetailUrl(movieId)
                , MovieDetail.class
                , null
                , new Response.Listener<MovieDetail>() {
            @Override
            public void onResponse(MovieDetail response) {
                movieDetail = response;

                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(movieDetail.getTitle());
                }

                releaseDate.setText(movieDetail.getReleaseDate().substring(0, 4));
                runtime.setText(movieDetail.getRuntime() + getString(R.string.min));
                voteAverage.setText(movieDetail.getVoteAverage() + "/10");

                plotSynopsis.setText(movieDetail.getOverview());

                buttonMarkFavourite.setVisibility(View.VISIBLE);

                Picasso.with(getActivity()).cancelRequest(imageView);
                Picasso.with(getActivity())
                        .load(TheMovieDbUtils.MOVIE_IMAGE_PATH_URL + movieDetail.getPosterPath())
                        .into(imageView);

                progressBar.setVisibility(View.INVISIBLE);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PopMovies", "ItemDetailFragment.onErrorResponse - error: " + error.toString());

            }
        });

        VolleySingleton.getInstance(getActivity().getApplication()).addToRequestQueue(movieListGsonRequest);

    }
}
