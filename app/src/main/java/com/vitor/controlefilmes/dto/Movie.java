package com.vitor.controlefilmes.dto;

import android.graphics.drawable.Drawable;

public final class Movie {

    private final String name;
    private final Drawable image;
    private final int year;
    private final int duration;
    private final String description;
    private final Category category;

    public Movie(Builder builder) {
        this.name = builder.name;
        this.image = builder.image;
        this.year = builder.year;
        this.duration = builder.duration;
        this.description = builder.description;
        this.category = builder.category;
    }

    public String getName() {
        return name;
    }

    public Drawable getImage() {
        return image;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Nome: " + name + "\n" +
                "Ano: " + year + "\n" +
                "Duração: " + duration + " minutos \n" +
                "Categoria: " + category.getName();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private Drawable image;
        private int year;
        private int duration;
        private String description;
        private Category category;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withYear(int year) {
            this.year = year;
            return this;
        }

        public Builder withImage(Drawable image) {
            this.image = image;
            return this;
        }

        public Builder withDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
