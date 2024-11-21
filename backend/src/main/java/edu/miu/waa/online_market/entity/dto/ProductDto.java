package edu.miu.waa.online_market.entity.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String description;
    private int quantity;
    private float price;
    private Long categoryId;
    private Long sellerId;
}
