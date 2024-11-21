package edu.miu.waa.online_market.repo;

import edu.miu.waa.online_market.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    @Query("select count(o) from OrderItem o where o.product.id = :productId")
    int countByProductId(long productId);
}
