package app.movie.ano.io.rest;
import app.movie.ano.io.model.Genre;
import app.movie.ano.io.model.Movie;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Call;
import app.movie.ano.io.model.MovieResponse;

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> searchMovies(@Query("api_key") String apiKey, @Query("query") String query);
}
