package edu.miu.waa.online_market.service.impl;
import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.repo.CartRepo;
import edu.miu.waa.online_market.repo.OrderRepo;
import edu.miu.waa.online_market.service.CartService;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import edu.miu.waa.online_market.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private final LoggerService loggerService;
    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final ProductService  productService;
    public OrderServiceImpl(LoggerService loggerService, OrderRepo orderRepo, CartRepo cartRepo, ProductService productService) {
        this.loggerService = loggerService;
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.productService = productService;
    }

   @Transactional
   public Order saveOrder(List<Long> cartItemsList) {
        Order order = new Order(LocalDateTime.now());
        try {
            for(Long cartItemId : cartItemsList) {
                CartItem cartItem= orderRepo.findCartItemById(cartItemId);
                OrderItem orderItem = new OrderItem(cartItem.getProduct(), cartItem.getQuantity());
                orderItem.setOrder(order);
                loggerService.logOperation("CartItem: " + cartItem.getId() + " Quantity " +cartItem.getQuantity());
                productService.updateProduct(cartItem.getProduct().getId(),cartItem.getQuantity());
                order.getOrderItems().add(orderItem);
            }

            Order savedOrder = orderRepo.save(order);

            for(Long cartItemId : cartItemsList) {
                cartRepo.deleteCartItemById(cartItemId);
            }

            return savedOrder;

        } catch (Exception e) {
            e.printStackTrace();
            loggerService.logOperation("Error saving order: " + e.getMessage());
            throw new RuntimeException("Order could not be saved due to an error.");
        }
    }

     public List<Order> getOrderItemsBySellerId(long sellerId, long orderId) {
        return orderRepo.getOrderItemsBySellerId(sellerId,orderId);
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Order findById(long id) {
        return orderRepo.findById(id);
    }
    @Override
    public void deleteById(long id) {
        orderRepo.deleteById(id);
    }

    public  void deleteOrderByItemId(long productId, long orderId) {
        orderRepo.deleteOrderByItemId(productId,orderId);
    }

//    @Override
//    public int updateOrderStatus(long orderId, OrderStatus status) {
//        return orderRepo.updateOrderStatus(orderId,status);
//    }
//
//    @Override
//    public int updateOrderItemStatus(long orderItemId, OrderStatus status) {
//        return orderRepo.updateOrderItemStatus(orderItemId,status);
//    }


}
