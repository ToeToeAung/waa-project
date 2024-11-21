package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.entity.Review;
import edu.miu.waa.online_market.entity.Role;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.repo.ProductRepo;
import edu.miu.waa.online_market.repo.ReviewRepo;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.ReviewService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ProductRepo productRepo;
    private final ReviewRepo reviewRepo;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public void create(Review review, Long productId) {
        Product product = productService.getProduct(productId);
        review.setProduct(product);
        product.setRatingSum(product.getRatingSum() + review.getRating());
        product.setTotalUser(product.getTotalUser()+1);
        product.setOverAllRating(product.getRatingSum()/product.getTotalUser());
        product.getReviews().add(review);

        reviewRepo.save(review);
    }

    @Override
    public void delete(Long productId, Long reviewId) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Product product = productService.getProduct(productId);
        Review review = reviewRepo.findById(reviewId).orElse(null);

        if (review != null) {
            float totalUser = product.getTotalUser() - 1;
            float ratingSum = product.getRatingSum() - review.getRating();
            float overAllRating = ratingSum;
            product.setTotalUser(totalUser);
            product.setRatingSum(ratingSum);
            if (totalUser > 1) {
                overAllRating /= totalUser;
            }
            product.setOverAllRating(overAllRating);
        }
        product.getReviews().remove(review);
        productRepo.save(product);
        reviewRepo.deleteById(reviewId);
    }
}
