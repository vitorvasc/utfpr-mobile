package com.vitor.controlefilmes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.entity.Movie;

import java.util.List;

public class MovieListAdapter extends BaseAdapter {

    Context context;
    List<Movie> movieList;

    private static class MovieListHolder {
        public ImageView imageViewMovieImage;
        public TextView textViewMovieName;
        public TextView textViewMovieYear;
        public TextView textViewMovieDuration;
        public TextView textViewMovieGenre;
    }

    public MovieListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        MovieListHolder holder;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_movies, viewGroup, false);

            holder = new MovieListHolder();

            holder.imageViewMovieImage = view.findViewById(R.id.imageViewMovieImage);
            holder.textViewMovieName = view.findViewById(R.id.textViewMovieName);
            holder.textViewMovieYear = view.findViewById(R.id.textViewMovieYear);
            holder.textViewMovieDuration = view.findViewById(R.id.textViewMovieDuration);
            holder.textViewMovieGenre = view.findViewById(R.id.textViewMovieGenre);

            view.setTag(holder);
        } else {
            holder = (MovieListHolder) view.getTag();
        }

        holder.imageViewMovieImage.setImageDrawable(movieList.get(i).getImage());
        holder.textViewMovieName.setText(movieList.get(i).getName());
        holder.textViewMovieYear.setText(String.valueOf(movieList.get(i).getYear()));
        holder.textViewMovieDuration.setText(String.valueOf(movieList.get(i).getDuration()));
        holder.textViewMovieGenre.setText(movieList.get(i).getCategory().getName());

        return view;
    }
}
