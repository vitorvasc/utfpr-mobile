package com.vitor.controlefilmes.entity;

public final class Category {

    private final String name;

    public Category(Builder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
