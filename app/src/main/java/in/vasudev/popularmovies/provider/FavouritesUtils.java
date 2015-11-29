package in.vasudev.popularmovies.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;

import in.vasudev.popularmovies.R;
import in.vasudev.popularmovies.model.movie_detail.MovieDetail;

/**
 * Created by vineet on 29-Nov-15.
 */
public class FavouritesUtils {

    public static Uri getFavouritesUri(Context context) {
        return Uri.parse("content://" + context.getString(R.string.authority) + "/favouritemovies");
    }

    public static void addToFavourites(Context context, MovieDetail movieDetail) {
        ContentValues values = new ContentValues();
        values.put(FavouritesProvider.FavouriteMovie.KEY_ID, movieDetail.getId());
        values.put(FavouritesProvider.FavouriteMovie.KEY_TITLE, movieDetail.getTitle());
        values.put(FavouritesProvider.FavouriteMovie.KEY_IMAGE_URL, movieDetail.getPosterPath());
        context.getContentResolver().insert(getFavouritesUri(context), values);
    }

    public static void removeFromFavourites(Context activity, String movieId) {
        activity.getContentResolver().delete(getFavouritesUri(activity), FavouritesProvider.FavouriteMovie.KEY_ID + " = " + DatabaseUtils.sqlEscapeString(movieId), null);
    }

    public static boolean isNotMarkedAsFavourite(Context activity, String movieId) {
        Cursor c = activity.getContentResolver().query(
                getFavouritesUri(activity), null, FavouritesProvider.FavouriteMovie.KEY_ID + " = " + DatabaseUtils.sqlEscapeString(movieId), null, null);
        return c.getCount() == 0;
    }

}
