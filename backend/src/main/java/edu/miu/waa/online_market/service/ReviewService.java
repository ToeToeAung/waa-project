package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.Review;

public interface ReviewService {
    void create(Review review, Long productId);
    void delete(Long productId, Long reviewId);
}
