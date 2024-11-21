package edu.miu.waa.online_market.controller;
import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.entity.dto.OrderDto;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.repo.UserRepo;
import edu.miu.waa.online_market.service.OrderService;
import edu.miu.waa.online_market.util.CurrentUser;
import edu.miu.waa.online_market.util.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import edu.miu.waa.online_market.service.LoggerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = {"*"})
@PreAuthorize("#username == authentication.name")
public class OrderController {
    private final OrderService orderService;
    private final UserRepo userRepo;
    private final LoggerService loggerService;
    public OrderController(OrderService orderService, UserRepo userRepo, LoggerService loggerService) {
        this.orderService = orderService;
        this.userRepo = userRepo;
        this.loggerService = loggerService;
    }

    @PostMapping("/cart")
    public ResponseEntity<Order> saveOrder(@RequestBody List<Long> cartItems) {
        Order savedOrder = orderService.saveOrder(cartItems);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/seller/{id}")
    public List<Order> getOrderItemsBySellerId(@PathVariable Long id) {
        User user=userRepo.findByUsername(CurrentUser.getCurrentUser());
        loggerService.logOperation("getOrderItemsBySellerId " + user.getUsername());
        return orderService.getOrderItemsBySellerId(user.getId(),id);
    }

    @GetMapping()
    public List<Order> getAllOrder() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public List<Order> getOrderByid(@PathVariable Long id) {
        return (List<Order>) orderService.findById(id);
    }

    @PutMapping("/{id}")
    public void deleteOrderById(@PathVariable Long id) {
         orderService.deleteById(id);
    }

    @PutMapping("/{productId}/{orderId}")
    public void deleteOrderByItemId(@PathVariable Long productId, @PathVariable Long orderId) {
        orderService.deleteOrderByItemId(productId,orderId);
    }

    @PutMapping("/status")
    public void updateOrderStatus(@RequestParam Long orderId, @RequestParam String status) {
        Order order = orderService.findById(orderId);
        //loggerService.logOperation("order " + order.getOrderDate());
        if (order != null) {
            try {
                OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
                orderService.updateOrderStatus(orderId, orderStatus);
             //   loggerService.logOperation("updateOrderStatus from enum " + orderStatus);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Status Value");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Order");
        }
    }


    @PutMapping("/order-item/status")
    public void updateOrderItemStatus(@RequestParam Long orderItemId, @RequestParam String status) {
        OrderItem orderItem = orderService.findByOrderItemId(orderItemId);
        if (orderItem != null) {
            try {
                OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
                orderService.updateOrderItemStatus(orderItemId, orderStatus);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Status Value");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Order");
        }
    }


}
