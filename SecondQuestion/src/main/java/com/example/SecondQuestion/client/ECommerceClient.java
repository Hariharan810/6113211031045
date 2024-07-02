package com.example.SecondQuestion.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.example.SecondQuestion.model.Product;

import java.util.Arrays;
import java.util.List;

@Component
public class ECommerceClient {

    private static final String BASE_URL = "http://20.244.56.144/test/companies/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Product> fetchProducts(String company, String category, int top, double minPrice, double maxPrice) {
        String url = BASE_URL + company + "/categories/" + category + "/products?top=" + top + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
        return Arrays.asList(restTemplate.getForObject(url, Product[].class));
    }
}
