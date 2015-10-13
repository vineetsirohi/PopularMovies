package in.vasudev.popularmovies.model;

/**
 * Created by vineet on 13-Oct-15.
 */
public class TheMovieDbUtils {

    private static final String MOVIE_LIST_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";
    private static final String MOVIE_DETAIL_URL = "http://api.themoviedb.org/3/movie/";
    private static final String MOVIE_DETAIL_URL_API_KEY = "?api_key=";

    public static final String MOVIE_IMAGE_PATH_URL = "http://image.tmdb.org/t/p/w185/";

    public static String moviesListUrl() {
        return new StringBuilder().append(MOVIE_LIST_URL).append(TheMovieDbApiKey.API_KEY).toString();
    }

    public static String movieDetailUrl(String movieId) {
        return new StringBuilder().append(MOVIE_DETAIL_URL)
                .append(movieId)
                .append(MOVIE_DETAIL_URL_API_KEY)
                .append(TheMovieDbUtils.MOVIE_DETAIL_URL_API_KEY)
                .toString();
    }
}
