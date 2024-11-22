package edu.miu.waa.online_market.repo;

import edu.miu.waa.online_market.entity.CartItem;
import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product save(Product product);
    List<Product> findAll();
    @Query("SELECT p FROM Product p WHERE "
            + "(:categoryId IS NULL OR p.category.id = :categoryId) AND "
            + "(:ratingGt IS NULL OR p.overAllRating >= :ratingGt) AND "
            + "(:ratingLt IS NULL OR p.overAllRating <= :ratingLt) AND "
            + "(:priceGt IS NULL OR p.price >= :priceGt) AND "
            + "(:priceLt IS NULL OR p.price <= :priceLt)")
    Page<Product> findByFilters(
            @Param("categoryId") Long categoryId,
            @Param("ratingGt") Float ratingGt,
            @Param("ratingLt") Float ratingLt,
            @Param("priceGt") Float priceGt,
            @Param("priceLt") Float priceLt,
            Pageable pageable);

    void deleteById(Long id);
    @Query("Select r From Product p Join p.reviews r Where p.id = :id")
    List<Review> findReviewsByProductId(Long id);

    @Query("Select p From Product p Where p.user.id = :sellerId and (:stockQty is null or p.quantity = :stockQty)")
    List<Product> findProductsBySellerId(@Param("sellerId") long sellerId, Integer stockQty);


}
