package app.movie.ano.io.rest;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String API_KEY = "2ed1faaea39dd29056427430651b5f24";
    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static String getPhotoSize(int index) {
        ArrayList<String> sizePhoto = new ArrayList<String>();
        sizePhoto.add("w780//");
        sizePhoto.add("w500//");
        sizePhoto.add("w342//");
        sizePhoto.add("w185//");
        sizePhoto.add("w154//");
        return String.valueOf(sizePhoto.get(index));
    }

}
