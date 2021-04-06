package com.rajan.moviecatalogservice.catalog;

public class CatalogInfo {
    private String name;
    private String description;
    private Double rating;

    public CatalogInfo(final String _name, final String _description, final Double _rating) {
        this.name = _name;
        this.description = _description;
        this.rating = _rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
