package com.vitor.controlefilmes.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public final class Review implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String title;

    @NonNull
    private String genre;

    private float rating;

    @NonNull
    private String review;
    private boolean hasSpoilers;
    private int recommends;

    public Review() {
    }

    public Review(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.genre = builder.genre;
        this.rating = builder.rating;
        this.review = builder.review;
        this.hasSpoilers = builder.hasSpoilers;
        this.recommends = builder.recommends;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getGenre() {
        return genre;
    }

    public void setGenre(@NonNull String genre) {
        this.genre = genre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @NonNull
    public String getReview() {
        return review;
    }

    public void setReview(@NonNull String review) {
        this.review = review;
    }

    @Ignore
    public boolean hasSpoilers() {
        return hasSpoilers;
    }

    public boolean isHasSpoilers() {
        return hasSpoilers;
    }

    public void setHasSpoilers(boolean hasSpoilers) {
        this.hasSpoilers = hasSpoilers;
    }

    public int getRecommends() {
        return recommends;
    }

    public void setRecommends(int recommends) {
        this.recommends = recommends;
    }

    @Override
    public String toString() {
        return "Nome: " + getTitle() + "\n" +
                "Gênero: " + getGenre() + "\n" +
                "Nota: " + getRating() + "\n" +
                "Recomenda? " + (getRecommends() == 0 ? "Não" : "Sim");
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private long id;
        private String title;
        private String genre;
        private float rating;
        private String review;
        private boolean hasSpoilers;
        private int recommends;

        private Builder() {
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder withRating(float rating) {
            this.rating = rating;
            return this;
        }

        public Builder withReview(String review) {
            this.review = review;
            return this;
        }

        public Builder hasSpoilers(boolean hasSpoilers) {
            this.hasSpoilers = hasSpoilers;
            return this;
        }

        public Builder withRecommends(int recommends) {
            this.recommends = recommends;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
