package edu.miu.waa.online_market.entity.dto;
import edu.miu.waa.online_market.entity.Order;
import edu.miu.waa.online_market.entity.OrderItem;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
public class OrderMapper {
    public OrderDto toDTO(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderItems(order.getOrderItems().stream()
                .map(this::toItemDo)
                .collect(Collectors.toList()));
        return dto;
    }

    private OrderItemDto toItemDo(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setOrderStatus(item.getOrderStatus());
        return dto;
    }
}
