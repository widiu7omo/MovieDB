package app.movie.ano.io.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.movie.ano.io.R;
import app.movie.ano.io.activity.DetailActivity;
import app.movie.ano.io.model.Movie;
import app.movie.ano.io.rest.ApiClient;

import com.squareup.picasso.Picasso;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView movieImage;
        CardView cvMovie;

        public MovieViewHolder(View v) {
            super(v);
            cvMovie = v.findViewById(R.id.cv_movie);
            moviesLayout = v.findViewById(R.id.movies_layout);
            movieImage = v.findViewById(R.id.movie_image);
            movieTitle = v.findViewById(R.id.title);
            data = v.findViewById(R.id.date);
            movieDescription = v.findViewById(R.id.description);
            rating = v.findViewById(R.id.rating);

        }
    }


    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.cvMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Button Clicked",Toast.LENGTH_SHORT).show();
                int movieId = movies.get(position).getId();
                Intent intentDetail = new Intent(v.getContext(),DetailActivity.class);
                intentDetail.putExtra("id_mov", movieId);
                Toast.makeText(v.getContext(),"The Movie Id is "+movieId,Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intentDetail);
            }
        });
        String image_url = ApiClient.IMAGE_URL_BASE_PATH + ApiClient.getPhotoSize(3)+ movies.get(position).getPosterPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "movies from adapter size " + movies.size());
        return movies.size();
    }
}
