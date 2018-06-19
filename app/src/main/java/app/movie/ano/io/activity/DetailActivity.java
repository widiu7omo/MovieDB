package app.movie.ano.io.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.movie.ano.io.R;
import app.movie.ano.io.adapter.MoviesAdapter;
import app.movie.ano.io.model.Genre;
import app.movie.ano.io.model.Movie;
import app.movie.ano.io.rest.ApiClient;
import app.movie.ano.io.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.movie.ano.io.rest.ApiClient.API_KEY;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    TextView movieTitle;
    TextView date;
    TextView movieDescription;
    TextView rating;
    ImageView movieImage;
    TextView genreTextView;
    public int idMov;
    public List<Genre> idGenre;
    TextView runTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieTitle = findViewById(R.id.movie_title_details);
        date = findViewById(R.id.movie_release_date);
        movieDescription = findViewById(R.id.movie_overview);
        rating = findViewById(R.id.movie_rating);
        movieImage = findViewById(R.id.movie_cover);
        genreTextView = findViewById(R.id.movie_genre);
        runTimeTextView = findViewById(R.id.movie_runtime);

        detailMovie();
        movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Image Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ImagePreviewActivity.class);
                intent.putExtra("id", idMov);
                getApplicationContext().startActivity(intent);

            }
        });
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Cant get API KEY", Toast.LENGTH_LONG).show();
        }

    }

    public void detailMovie() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        idMov = getIntent().getIntExtra("id_mov", 0);
        Call<Movie> call = apiService.getMovieDetails(idMov, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Link Array " + ApiClient.getPhotoSize(2), Toast.LENGTH_LONG).show();
                    Movie movie = response.body();
                    String image_url = ApiClient.IMAGE_URL_BASE_PATH + ApiClient.getPhotoSize(2) + movie.getPosterPath();
                    Picasso.with(getBaseContext())
                            .load(image_url)
                            .placeholder(android.R.drawable.sym_def_app_icon)
                            .error(android.R.drawable.sym_def_app_icon)
                            .into(movieImage);
                    movieTitle.setText(movie.getTitle());
                    date.setText(movie.getReleaseDate());
                    movieDescription.setText(movie.getOverview());
                    runTimeTextView.setText(movie.getRunTime().toString()+" Minutes");
                    rating.setText(movie.getVoteAverage().toString());
                    idGenre = movie.getGenreIds();
                    Log.d(TAG, "genretotal" + idGenre);
                    for (int i = 0;i<idGenre.size();i++){
                        Log.d(TAG,"idval"+idGenre.get(i).getGenreName());
                        genreTextView.setText(genreTextView.getText()+idGenre.get(i).getGenreName()+", ");
                    }


                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(TAG + "detailFailed", t.toString());
            }
        });

    }




}
