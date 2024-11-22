package edu.miu.waa.online_market.entity.dto;

import lombok.Data;
import java.util.List;

@Data
public class CartDto {
    private long id;
    private long userId;
    private List<CartItemDto> cartItems;
}
