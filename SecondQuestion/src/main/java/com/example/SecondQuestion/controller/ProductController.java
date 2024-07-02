package com.example.SecondQuestion.controller;

import com.example.SecondQuestion.model.Product;
import com.example.SecondQuestion.model.ProductResponse;
import com.example.SecondQuestion.service.ECommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/categories")
public class ProductController {

    @Autowired
    private ECommerceService eCommerceService;

    @GetMapping("/{category}/products")
    public ResponseEntity<?> getTopProducts(
            @PathVariable String category,
            @RequestParam int top,
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order) {

        List<String> companies = Arrays.asList("AMZ", "FLP", "SNP", "MYN", "AZO");
        List<Product> allProducts = new ArrayList<>();

        for (String company : companies) {
            allProducts.addAll(eCommerceService.fetchProducts(company, category, top, minPrice, maxPrice));
        }

        if (sortBy != null) {
            allProducts.sort(getComparator(sortBy, order));
        }

        int pageSize = top > 10 ? 10 : top;
        int fromIndex = (page != null ? page - 1 : 0) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allProducts.size());

        List<Product> paginatedProducts = allProducts.subList(fromIndex, toIndex);

        List<ProductResponse> response = new ArrayList<>();
        for (Product product : paginatedProducts) {
            response.add(new ProductResponse(UUID.randomUUID().toString(), product));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{category}/products/{productId}")
    public ResponseEntity<?> getProductDetails(@PathVariable String category, @PathVariable String productId) {
        ProductResponse productResponse = eCommerceService.findProductById(productId);
        return productResponse != null ? ResponseEntity.ok(productResponse) : ResponseEntity.notFound().build();
    }

    private Comparator<Product> getComparator(String sortBy, String order) {
        Comparator<Product> comparator;
        switch (sortBy) {
            case "price":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "rating":
                comparator = Comparator.comparing(Product::getRating);
                break;
            case "discount":
                comparator = Comparator.comparing(Product::getDiscount);
                break;
            case "company":
                comparator = Comparator.comparing(Product::getCompanyName);
                break;
            default:
                throw new IllegalArgumentException("Invalid sortBy parameter");
        }
        return "desc".equalsIgnoreCase(order) ? comparator.reversed() : comparator;
    }
}
