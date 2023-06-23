package com.tiembanhhoangtube.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ordertailDto {
    private Long orderdetailId;
    private int quantity;
    private double unitPrice;
}
