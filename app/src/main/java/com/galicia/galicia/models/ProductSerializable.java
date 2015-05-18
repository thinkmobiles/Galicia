package com.galicia.galicia.models;

import com.cristaliza.mvc.models.estrella.Product;

import java.io.Serializable;

/**
 * Created by Bogdan on 16.05.2015.
 */
public class ProductSerializable implements Serializable {
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
