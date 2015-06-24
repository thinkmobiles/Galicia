package com.cristaliza.alimentacion.models;

import com.cristaliza.mvc.models.estrella.Product;

import java.io.Serializable;

public class ProductSerializable implements Serializable {
    private Product product;

    public ProductSerializable() {
    }

    public ProductSerializable(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
