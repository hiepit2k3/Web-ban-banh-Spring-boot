package com.tiembanhhoangtube.RestfulAPI;

import com.tiembanhhoangtube.Service.ProductService;
import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.entity.Product;
import com.tiembanhhoangtube.model.ProductDto;
import com.tiembanhhoangtube.model.accountDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("admin/products")
public class RestfulProductController {
    @Autowired
    ProductService productService;
    @Autowired
    StogareService stogareService;
    @PostMapping("add")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDto dto,
                                        @RequestParam("imageFile") MultipartFile imageFile) {
        System.out.println("Product data: " + dto);
        try {
            Product entity = new Product();
            BeanUtils.copyProperties(dto, entity);
            if (!imageFile.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String uuidString = uuid.toString();
                System.out.println("Tên File: "+uuidString);
                entity.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
                stogareService.store(dto.getImageFile(), entity.getImage());
            }
            productService.save(entity);
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/id={productsId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("productsId") Long productsId) throws Exception {
        Product product = productService.findByproducId(productsId);
        if (product != null) {
            if (StringUtils.isEmpty(product.getImage())) {
                stogareService.delete(product.getImage());
            }
        } else {
        }
        productService.delete(product);
        System.out.println("đã xóa");
        return ResponseEntity.ok("Successfully deleted product");
    }
}
