package com.tiembanhhoangtube.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class accountDto {
    private Long accountId;
    private String username;
    private String password;
    private String image;
    private String adress;
    private String role;
    private MultipartFile imageFile;
    private String comfirmpassword;
    private String fullname;
    private String email;
    private int sdt;
    private String newpassword;
}
