package edu.miu.waa.online_market.repo;

import edu.miu.waa.online_market.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart save(Cart cart);
}
