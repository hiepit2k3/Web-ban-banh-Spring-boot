package com.tiembanhhoangtube.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class cartitemDto {
    private Long cartitemId;
    private int quantity;
    private Long product_id;
}
