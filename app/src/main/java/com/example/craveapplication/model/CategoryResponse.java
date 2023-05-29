package com.example.craveapplication.model;

import java.util.List;

public class CategoryResponse {
    private List<CategoryPojo> categories;

    public CategoryResponse() {
    }

    public List<CategoryPojo> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryPojo> categories) {
        this.categories = categories;
    }
}
