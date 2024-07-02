package com.example.SecondQuestion.model;

public class ProductResponse {
    private String id;
    private Product product;

    public ProductResponse(String id, Product product) {
        this.id = id;
        this.product = product;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
