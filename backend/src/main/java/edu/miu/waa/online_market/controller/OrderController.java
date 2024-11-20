package edu.miu.waa.online_market.controller;
import edu.miu.waa.online_market.entity.Order;
import edu.miu.waa.online_market.entity.OrderItem;
import edu.miu.waa.online_market.entity.OrderStatus;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.OrderDto;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.repo.UserRepo;
import edu.miu.waa.online_market.service.OrderService;
import edu.miu.waa.online_market.util.CurrentUser;
import edu.miu.waa.online_market.util.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import edu.miu.waa.online_market.service.LoggerService;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/cart/{id}")
    public ResponseEntity<Order> saveOrder(@PathVariable Long id) {
        Order savedOrder = orderService.saveOrder(id);
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

}
