package edu.miu.waa.online_market.service;
import edu.miu.waa.online_market.entity.Order;
import edu.miu.waa.online_market.entity.OrderItem;
import edu.miu.waa.online_market.entity.OrderStatus;
import edu.miu.waa.online_market.entity.dto.OrderDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderService {
   // Order saveOrder(Order order);
    Order saveOrder(List<Long> cartItems);
    List<Order> getOrderItemsBySellerId(long sellerId, long orderId);
    List<Order> findAll();
    Order findById(long id);
    OrderItem findByOrderItemId(long orderItemId);
    void deleteById(long id);
    void deleteOrderByItemId(long productId, long orderId);
    int updateOrderStatus(long orderId,OrderStatus status);
    int updateOrderItemStatus(long orderItemId, OrderStatus status);
}
