package app.movie.ano.io.model;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("id")
    private int GenreId;
    @SerializedName("name")
    private String GenreName;

    public Genre(int genreId, String genreName) {
        GenreId = genreId;
        GenreName = genreName;
    }

    public int getGenreId() {
        return GenreId;
    }

    public void setGenreId(int genreId) {
        GenreId = genreId;
    }

    public String getGenreName() {
        return GenreName;
    }

    public void setGenreName(String genreName) {
        GenreName = genreName;
    }
}
