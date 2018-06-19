package app.movie.ano.io.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.awt.font.TextAttribute;

import app.movie.ano.io.R;
import app.movie.ano.io.model.Movie;
import app.movie.ano.io.rest.ApiClient;
import app.movie.ano.io.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagePreviewActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public int idMov;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_preview);
        imageView = findViewById(R.id.imagePreview);
        idMov = getIntent().getIntExtra("id",0);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> movieCall = apiService.getMovieDetails(idMov,ApiClient.API_KEY);
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()){
                    Movie movie = response.body();
                    String path = ApiClient.IMAGE_URL_BASE_PATH+ApiClient.getPhotoSize(0)+movie.getPosterPath();
                    Picasso.with(getBaseContext())
                            .load(path)
                            .into(imageView);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });


    }
}
