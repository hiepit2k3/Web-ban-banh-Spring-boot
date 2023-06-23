package com.tiembanhhoangtube.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class orderDto {
    private Long orderId;
    private Date orderDate;
    private short status;
    private double amount;
    private double price;
    private Long productId;
    private int quantity;
    private Long cartitemId;
}
