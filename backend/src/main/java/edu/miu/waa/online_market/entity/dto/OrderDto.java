package edu.miu.waa.online_market.entity.dto;
import edu.miu.waa.online_market.entity.OrderItem;
import edu.miu.waa.online_market.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItems;
}
