package app.movie.ano.io.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.List;
import app.movie.ano.io.R;
import app.movie.ano.io.adapter.MoviesAdapter;
import app.movie.ano.io.model.Movie;
import app.movie.ano.io.model.MovieResponse;
import app.movie.ano.io.rest.ApiClient;
import app.movie.ano.io.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.movie.ano.io.rest.ApiClient.API_KEY;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);//define recycler view has fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//set recyclerview layout as linearlayout

        if (API_KEY.isEmpty()){
            Toast.makeText(getApplicationContext(),"Cant get API KEY", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
            onSearch(query);
        }
    }
    public void onSearch(String query){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.searchMovies(API_KEY,query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                int totalResults = response.body().getTotalResults();
                Log.d(TAG,"Count Movie" + movies.size());
                recyclerView.setAdapter(new MoviesAdapter(movies,R.layout.list_item_movie,getApplicationContext()));
                Log.d(TAG,"Total Result Search " + totalResults);
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 3){
            onSearch(newText);
        }
        return true;
    }

}
