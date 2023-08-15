package com.tiembanhhoangtube.model;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productsId;
    private String cakeName;
    private String description;
    private int quantity;
    private double price;
    private String image;
    @Transient
    private MultipartFile imageFile;
    private Boolean isEdit = false;
}
