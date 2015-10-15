package in.vasudev.popularmovies.model;

/**
 * Created by vineet on 13-Oct-15.
 */
public class TheMovieDbUtils {

    public static final int SORT_ORDER_MOST_POPULAR = 0;
    public static final int SORT_ORDER_HIGHEST_RATED = 1;

    private static final String MOVIE_LIST_URL_POPULARITY = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";
    private static final String MOVIE_LIST_URL_VOTE_AVERAGE = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=";

    private static final String MOVIE_DETAIL_URL = "http://api.themoviedb.org/3/movie/";

    private static final String API_KEY = "?api_key=";

    public static final String MOVIE_IMAGE_PATH_URL = "http://image.tmdb.org/t/p/w185/";

    public static String moviesListUrl(int sortOrder) {
        switch (sortOrder) {
            case SORT_ORDER_MOST_POPULAR:
                return new StringBuilder().append(MOVIE_LIST_URL_POPULARITY).append(TheMovieDbApiKey.VALUE).toString();

            case SORT_ORDER_HIGHEST_RATED:
                return new StringBuilder().append(MOVIE_LIST_URL_VOTE_AVERAGE).append(TheMovieDbApiKey.VALUE).toString();
        }
        return new StringBuilder().append(MOVIE_LIST_URL_POPULARITY).append(TheMovieDbApiKey.VALUE).toString();
    }

    public static String movieDetailUrl(String movieId) {
        return new StringBuilder().append(MOVIE_DETAIL_URL)
                .append(movieId)
                .append(API_KEY)
                .append(TheMovieDbApiKey.VALUE)
                .toString();
    }
}
