package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.entity.Review;
import edu.miu.waa.online_market.repo.ProductRepo;
import edu.miu.waa.online_market.repo.ReviewRepo;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ProductRepo productRepo;
    private ReviewRepo reviewRepo;
    private ProductService productService;
    @Autowired
    public ReviewServiceImpl(ReviewRepo reviewRepo, ProductService productService, ProductRepo productRepo) {
        this.reviewRepo = reviewRepo;
        this.productService = productService;
        this.productRepo = productRepo;
    }
    @Override
    public void create(Review review, Long productId) {
        Product product = productService.getProduct(productId);
        product.setRatingSum(product.getRatingSum() + review.getRating());
        product.setTotalUser(product.getTotalUser()+1);
        product.setOverAllRating(product.getRatingSum()/product.getTotalUser());
        product.getReviews().add(review);
        reviewRepo.save(review);
    }

    @Override
    public void delete(Long productId, Long reviewId) {
        Product product = productService.getProduct(productId);
        Review review = reviewRepo.findById(reviewId).orElse(null);
        product.getReviews().remove(review);
        productRepo.save(product);
        reviewRepo.deleteById(reviewId);
    }
}
