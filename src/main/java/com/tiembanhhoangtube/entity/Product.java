package com.tiembanhhoangtube.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productsId;
    @Column(columnDefinition = "nvarchar(100) not null")
    private String cakeName;
    @Column(columnDefinition = "nvarchar(2000) not null")
    private String description;
    @Column
    private int quantity;
    @Column(nullable = false)
    private double price;
    @Column
    private String image;
    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL)
    private Set<Orderdetail> orderDetails;
}
