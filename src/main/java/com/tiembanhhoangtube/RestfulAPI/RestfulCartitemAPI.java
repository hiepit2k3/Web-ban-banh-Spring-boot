package com.tiembanhhoangtube.RestfulAPI;


import com.tiembanhhoangtube.Repository.CartitemRepository;
import com.tiembanhhoangtube.Service.ProductService;
import com.tiembanhhoangtube.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("tiembanhhoangtube")
public class RestfulCartitemAPI {

    @Autowired
    ProductService productService;
    @Autowired
    CartitemRepository cartitemRepository;
//    @RequestMapping("products/detail/{productId}")
//    public ResponseEntity<Product> getDetailProduct(@PathVariable("productId") Long productId){
//        Optional<Product> product = productService.findById(productId);
//        return ResponseEntity.ok(product.get());
//    }

    @GetMapping("slgiohang")
    public ResponseEntity<?> getslgiohang(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        int sl = cartitemRepository.countByCustomerId(username);
        return ResponseEntity.ok(sl);
    }
}
