package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.entity.Review;
import edu.miu.waa.online_market.entity.dto.ProductDto;

import java.util.List;

public interface ProductService {
    void createProduct(ProductDto productdto, long categoryId, long userId);
    List<ProductDto> listProducts();
    Product getProduct(Long id);
    ProductDto getProductDto(Long id);
    void updateProduct(long id, int quantity);
    void deleteProduct(Long id);
    List<Review> findReviewsByProduct(Long id);
}
