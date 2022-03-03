package com.vitor.controlefilmes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.adapter.MovieListAdapter;
import com.vitor.controlefilmes.entity.Category;
import com.vitor.controlefilmes.entity.Movie;

import java.util.ArrayList;

public final class MoviesListActivity extends AppCompatActivity {

    private ListView listViewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        listViewMovies = findViewById(R.id.listViewMovies);

        listViewMovies.setOnItemClickListener((adapterView, view, position, id) -> {
            Movie movie = (Movie) listViewMovies.getItemAtPosition(position);
            Toast.makeText(this, movie.toString(), Toast.LENGTH_LONG).show();
        });

        populateMoviesListView();
    }

    private void populateMoviesListView() {

        final String[] arrayName = getResources().getStringArray(R.array.popular_movies_names);
        final TypedArray arrayImages = getResources().obtainTypedArray(R.array.popular_movies_images);
        final int[] arrayYear = getResources().getIntArray(R.array.popular_movies_year);
        final String[] arrayDescription = getResources().getStringArray(R.array.popular_movies_descriptions);
        final int[] arrayDuration = getResources().getIntArray(R.array.popular_movies_duration);
        final String[] arrayGenres = getResources().getStringArray(R.array.popular_movies_genres);

        final ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < arrayName.length; i++) {
            movies.add(createNewMovie(
                    arrayName[i],
                    arrayImages.getDrawable(i),
                    arrayYear[i],
                    arrayDescription[i],
                    arrayDuration[i],
                    arrayGenres[i]
            ));
        }

        MovieListAdapter adapter = new MovieListAdapter(
                this,
                movies
        );

        listViewMovies.setAdapter(adapter);
    }

    private Movie createNewMovie(final String name,
                                 final Drawable image,
                                 final int year,
                                 final String description,
                                 final int duration,
                                 final String genreName
    ) {
        final Movie.Builder movieBuilder = Movie.newBuilder();

        return movieBuilder.withName(name)
                .withImage(image)
                .withYear(year)
                .withDescription(description)
                .withDuration(duration)
                .withCategory(createNewCategory(genreName))
                .build();
    }

    private Category createNewCategory(String genreName) {
        final Category.Builder categoryBuilder = Category.newBuilder();

        return categoryBuilder.withName(genreName)
                .build();
    }
}