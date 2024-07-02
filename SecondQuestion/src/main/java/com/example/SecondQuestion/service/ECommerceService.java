package com.example.SecondQuestion.service;

import com.example.SecondQuestion.client.ECommerceClient;
import com.example.SecondQuestion.model.Product;
import com.example.SecondQuestion.model.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ECommerceService {

    @Autowired
    private ECommerceClient eCommerceClient;

    private Map<String, ProductResponse> productCache = new HashMap<>();

    public List<Product> fetchProducts(String company, String category, int top, double minPrice, double maxPrice) {
        List<Product> products = eCommerceClient.fetchProducts(company, category, top, minPrice, maxPrice);
        for (Product product : products) {
            String uniqueId = UUID.randomUUID().toString();
            productCache.put(uniqueId, new ProductResponse(uniqueId, product));
        }
        return products;
    }

    public ProductResponse findProductById(String productId) {
        return productCache.get(productId);
    }
}
