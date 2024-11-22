package edu.miu.waa.online_market.repo;

import edu.miu.waa.online_market.entity.OrderItem;
import edu.miu.waa.online_market.entity.OrderStatus;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    @Query("select count(o) from OrderItem o where o.product.id = :productId")
    int countByProductId(long productId);

    @Query("select o from OrderItem o where o.product.user.id = :sellerId and (:orderStatus is null or o.orderStatus = :orderStatus)")
    List<OrderItem> findAllBySellerId(long sellerId, OrderStatus orderStatus);
}
