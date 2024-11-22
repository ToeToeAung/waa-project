package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.entity.Review;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    void createProduct(ProductDto product);
    List<Product> listProducts();
    Page<Product> findByFilters(
            Long categoryId,
            Float ratingGt,
            Float ratingLt,
            Float priceGt,
            Float priceLt,
            Pageable pateTable
           );

   // Page<Product> findByFilters(long categoryId,int page,int pageSize);

    Product getProduct(Long id);
    ProductDto getProductDto(Long id);
    void updateProduct(Long id, int quantity);
    void deleteProduct(Long id);
    List<Review> findReviewsByProduct(Long id);
    Boolean subQuantity(Long productId, int quantity);
    List<Product> getProductsBySellerId(long sellserId, Integer stockQty);
    void sellerDeleteProduct(long id);
    Boolean addQuantity(Long productId, int quantity);
}

