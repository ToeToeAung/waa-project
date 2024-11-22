package edu.miu.waa.online_market.entity.dto.request;


import lombok.Data;

@Data
public class AddCartItemRequest {
    private long productId;
    private int quantity;
}
