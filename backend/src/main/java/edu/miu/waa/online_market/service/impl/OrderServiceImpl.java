package edu.miu.waa.online_market.service.impl;
import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.entity.dto.OrderItemDto;
import edu.miu.waa.online_market.repo.CartRepo;
import edu.miu.waa.online_market.repo.OrderRepo;
import edu.miu.waa.online_market.service.*;
import edu.miu.waa.online_market.util.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private final LoggerService loggerService;
    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final ProductService  productService;
    private final UserService userService;
    public OrderServiceImpl(LoggerService loggerService, OrderRepo orderRepo, CartRepo cartRepo, ProductService productService,UserService userService) {
        this.loggerService = loggerService;
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.productService = productService;
        this.userService = userService;
    }

   @Transactional
   public Order saveOrder(List<Long> cartItemsList) {
       Order order = new Order(LocalDateTime.now());
       User user = userService.findByUsername(CurrentUser.getCurrentUser());
       if (user == null || !user.getRole().equals(Role.BUYER)) {
           throw new ResponseStatusException(HttpStatus.FORBIDDEN);
       }else{
           order.setUser(user);
       }

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
                CartItem cartItem= orderRepo.findCartItemById(cartItemId);
                cartRepo.deleteCartItemById(cartItemId);
                int itemsCount=cartRepo.findItemCountByCartId(cartItem.getCart().getId());
                if(itemsCount==0){
                    cartRepo.deleteById(cartItem.getCart().getId());
                }
            }

            return savedOrder;

        } catch (Exception e) {
            e.printStackTrace();
            loggerService.logOperation("Error saving order: " + e.getMessage());
            throw new RuntimeException("Order could not be saved due to an error.");
        }
    }
    @Override
     public List<Order> getOrderItemsBySellerId(long sellerId, long orderId) {
        return orderRepo.getOrderItemsBySellerId(sellerId,orderId);
    }

    @Override
    public List<OrderItem> findOrderItemByBuyerId(long buyerId){
       return orderRepo.getOrderItemByBuyerId(buyerId);
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
    public OrderItem findByOrderItemId(long orderItemId) {
        return orderRepo.getOrderItemById(orderItemId);
    }

    @Override
    public void deleteById(long id) {
        orderRepo.deleteById(id);
    }

    public  void deleteOrderByItemId(long productId, long orderId) {
        orderRepo.deleteOrderByItemId(productId,orderId);
    }

    @Override
    @Transactional
    public int updateOrderStatus(long orderId, OrderStatus status) {
        return orderRepo.updateOrderStatus(orderId,status);
    }

    @Override
    @Transactional
    public int updateOrderItemStatus(long orderItemId, OrderStatus status) {
        return orderRepo.updateOrderItemStatus(orderItemId,status);
    }

    @Override
    public List<Order> buyerFindAll() {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if (user == null || !user.getRole().equals(Role.BUYER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return orderRepo.findByBuyerId(user.getId());
    }


}
