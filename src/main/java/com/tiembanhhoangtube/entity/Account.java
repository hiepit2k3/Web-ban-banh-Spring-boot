package com.tiembanhhoangtube.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "accounts")
public class Account implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Column(columnDefinition = "nvarchar(20) not null")
    private String username;
    @Column
    private String image;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "nvarchar(60) not null")
    private String fullname;
    @Column(columnDefinition = "nvarchar(60) not null")
    private String email;
    @Column(length = 60)
    private int sdt;
    @Column(columnDefinition = "nvarchar(60)")
    private String adress;
    @Column
    private String role;
}
