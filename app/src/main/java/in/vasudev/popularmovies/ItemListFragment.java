package in.vasudev.popularmovies;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.vasudev.popularmovies.model.TheMovieDbUtils;
import in.vasudev.popularmovies.model.movie_list.MovieInfo;
import in.vasudev.popularmovies.model.movie_list.MovieList;
import in.vasudev.popularmovies.provider.FavouritesProvider;
import in.vasudev.popularmovies.provider.FavouritesUtils;
import in.vasudev.popularmovies.volley.GsonRequest;
import in.vasudev.popularmovies.volley.VolleySingleton;

public class ItemListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String STATE_SORT_ORDER = "sort_order";
    private static final int FAVOURITES = 2;

    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);

    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }

    };

    private RecyclerView mRecyclerView;

    private List<MovieInfo> mMovieInfos;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView.setHasFixedSize(true);

        final int columnCount = getResources().getInteger(R.integer.column_count);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return mRecyclerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int sortOrder = TheMovieDbUtils.SORT_ORDER_MOST_POPULAR;
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
            sortOrder = savedInstanceState.getInt(STATE_SORT_ORDER, TheMovieDbUtils.SORT_ORDER_MOST_POPULAR);
        }

        //        Request movie list
        requestMovieListFromApi(sortOrder);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void sortMovies(int sortOrder) {
        if (sortOrder == FAVOURITES) {
            showFavourites();
        } else {
            requestMovieListFromApi(sortOrder);
        }
    }

    private void showFavourites() {
//        Toast.makeText(getActivity(), "Favourites selected", Toast.LENGTH_LONG).show();
        getLoaderManager().initLoader(0, null, this);
    }

    private void requestMovieListFromApi(int sortOrder) {
//        cancel all pending requests
        VolleySingleton.getInstance(getActivity().getApplication()).getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });

//        prepare request
        GsonRequest<MovieList> movieListGsonRequest = new GsonRequest<>(
                TheMovieDbUtils.moviesListUrl(sortOrder)
                , MovieList.class
                , null
                , new Response.Listener<MovieList>() {
            @Override
            public void onResponse(MovieList response) {
                mMovieInfos = response.getMovieInfos();

                mRecyclerView.setAdapter(new MoviesListAdapter(getActivity(),
                        mMovieInfos,
                        new View.OnClickListener() { /* onItemClickListener */
                            @Override
                            public void onClick(View v) {
                                int position = mRecyclerView.getChildAdapterPosition(v);

                                mCallbacks.onItemSelected(mMovieInfos.get(position).getId());
                            }
                        }));
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PopMovies", "ItemListFragment.onErrorResponse - error: " + error.toString());

            }
        });

//        add request to volley queue
        VolleySingleton.getInstance(getActivity().getApplication()).addToRequestQueue(
                movieListGsonRequest);
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
//         When setting CHOICE_MODE_SINGLE, ListView will automatically
//         give items the 'activated' state when touched.

//        mRecyclerView.setChoiceMode(activateOnItemClick
//                ? ListView.CHOICE_MODE_SINGLE
//                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
//        if (position == ListView.INVALID_POSITION) {
//            mRecyclerView.setItemChecked(mActivatedPosition, false);
//        } else {
//            mRecyclerView.setItemChecked(position, true);
//        }

        mActivatedPosition = position;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), FavouritesUtils.getFavouritesUri(getActivity()), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        DatabaseUtils.dumpCursor(cursor);
        mMovieInfos = new ArrayList<>();
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    MovieInfo movieInfo = new MovieInfo();
                    movieInfo.setId(cursor.getString(cursor.getColumnIndex(FavouritesProvider.FavouriteMovie.KEY_ID)));
                    movieInfo.setTitle(cursor.getString(cursor.getColumnIndex(FavouritesProvider.FavouriteMovie.KEY_TITLE)));
                    movieInfo.setPosterPath(cursor.getString(cursor.getColumnIndex(FavouritesProvider.FavouriteMovie.KEY_IMAGE_URL)));
                    mMovieInfos.add(movieInfo);
                    // move to next row
                } while (cursor.moveToNext());
//                cursor.close();
            }
        }

        mRecyclerView.setAdapter(new MoviesListAdapter(getActivity(),
                mMovieInfos,
                new View.OnClickListener() { /* onItemClickListener */
                    @Override
                    public void onClick(View v) {
                        int position = mRecyclerView.getChildAdapterPosition(v);

                        mCallbacks.onItemSelected(mMovieInfos.get(position).getId());
                    }
                }));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
