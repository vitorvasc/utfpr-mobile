package com.vitor.controlefilmes.dto;

public final class Review {

    private final String title;
    private final String genre;
    private final float rating;
    private final String review;
    private final boolean hasSpoilers;
    private final int recommends;

    public Review(Builder builder) {
        this.title = builder.title;
        this.genre = builder.genre;
        this.rating = builder.rating;
        this.review = builder.review;
        this.hasSpoilers = builder.hasSpoilers;
        this.recommends = builder.recommends;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public float getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public boolean hasSpoilers() {
        return hasSpoilers;
    }

    public int getRecommends() {
        return recommends;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String title;
        private String genre;
        private float rating;
        private String review;
        private boolean hasSpoilers;
        private int recommends;

        private Builder() {
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
