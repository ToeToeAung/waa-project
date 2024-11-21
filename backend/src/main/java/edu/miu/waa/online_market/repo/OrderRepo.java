package edu.miu.waa.online_market.repo;
import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepo  extends JpaRepository<Order, Long> {
    Order save(Order order);
    List<Order> findAll();
    Order findById(long id);
    void deleteById(long id);

    @Modifying
    @Query("DELETE FROM OrderItem o WHERE o.product.id = :productId AND o.order.id = :orderId")
    void deleteOrderByItemId(@Param("productId") long productId, @Param("orderId") long orderId);

    @Query("SELECT o FROM Order o JOIN o.orderItems d WHERE d.product.user.id = :sellerId AND o.id = :orderId")
    List<Order> getOrderItemsBySellerId(@Param("sellerId") long sellerId, @Param("orderId") long orderId);

    @Query("Select c From CartItem c Where c.id = :cartItemId")
    CartItem findCartItemById(@Param("cartItemId") long cartItemId);

    //    @Modifying
//    @Query("UPDATE Order o SET o.orderStatus = :status WHERE o.id = :orderId")
//    int updateOrderStatus(@Param("orderId") long orderId, @Param("status") OrderStatus status);
//    @Modifying
//    @Query("UPDATE OrderItem o SET o.orderStatus = :status WHERE o.id = :orderItemId")
//    int updateOrderItemStatus(@Param("orderId") long orderItemId, @Param("status") OrderStatus status);

}