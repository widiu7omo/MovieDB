package app.movie.ano.io.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    //define retrofit object as null object
    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find xml with id recyler_view
        initRecycleView();
        initAPI();
    }
    public void initRecycleView(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);//define recycler view has fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//set recyclerview layout as linearlayout

    }
    public void initAPI(){
        //prepare APIKEY
        if (API_KEY.isEmpty()){
            Toast.makeText(getApplicationContext(),"Cant get API KEY", Toast.LENGTH_LONG).show();
            return;
        }
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies,R.layout.list_item_movie,getApplicationContext()));
                Log.d(TAG,"Number of movies received "+movies.size());
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }
    // This method create an instance of Retrofit
    // set the base url
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view_menu_item,menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this,SearchActivity.class)));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                break;
            case R.id.item1:

                break;
            case R.id.item2:
                System.exit(1);
                break;

                default:super.onOptionsItemSelected(item);
        }
        return true;
    }
}
