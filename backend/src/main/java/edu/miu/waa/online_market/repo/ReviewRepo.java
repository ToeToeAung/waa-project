package edu.miu.waa.online_market.repo;

import edu.miu.waa.online_market.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    Review save(Review review);
    void deleteById(Long id);
}
