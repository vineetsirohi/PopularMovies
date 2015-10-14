package in.vasudev.popularmovies.model.movie_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class MovieList {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<MovieInfo> movieInfos = new ArrayList<MovieInfo>();
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    /**
     *
     * @return
     * The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The results
     */
    public List<MovieInfo> getMovieInfos() {
        return movieInfos;
    }

    /**
     *
     * @param movieInfos
     * The results
     */
    public void setMovieInfos(List<MovieInfo> movieInfos) {
        this.movieInfos = movieInfos;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The total_results
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(page).append(movieInfos).append(totalPages).append(totalResults).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MovieList) == false) {
            return false;
        }
        MovieList rhs = ((MovieList) other);
        return new EqualsBuilder().append(page, rhs.page).append(movieInfos, rhs.movieInfos).append(totalPages, rhs.totalPages).append(totalResults, rhs.totalResults).isEquals();
    }

}