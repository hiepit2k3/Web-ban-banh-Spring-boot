package com.tiembanhhoangtube.RestfulAPI;

import com.tiembanhhoangtube.Repository.CartitemRepository;
import com.tiembanhhoangtube.Repository.OrderRepository;
import com.tiembanhhoangtube.Repository.OrdertailRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.ProductService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.entity.Order;
import com.tiembanhhoangtube.entity.Orderdetail;
import com.tiembanhhoangtube.entity.Product;
import com.tiembanhhoangtube.model.orderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("tiembanbanh")
public class RestfulOrderAPI {
    @Autowired
    AccountService accountService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrdertailRepository  ordertailRepository;
    @Autowired
    CartitemRepository cartitemRepository;

    @PostMapping("order/add")
    public ResponseEntity<?> order(@RequestBody List<orderDto> dto){
        System.out.println("List sản phẩm trả về:"+dto);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Account> account = accountService.findByUsername(username);
        Order order = new Order();
        Date orderdate = new Date();
        order.setOrderDate(orderdate);
        order.setAccount(account.get());
        order.setAmount(dto.get(0).getAmount());
        orderRepository.save(order);
        for (orderDto o : dto) {
            Orderdetail orderdetail = new Orderdetail();
            Optional<Product> product = productService.findById(o.getProductId());
            orderdetail.setProduct(product.get());
            orderdetail.setQuantity(o.getQuantity());
            orderdetail.setUnitPrice(o.getPrice());
            orderdetail.setOrder(order);
            ordertailRepository.save(orderdetail);
            cartitemRepository.deleteById(o.getCartitemId());
            System.out.println("xóa");
        }
        return ResponseEntity.ok("Success");
    }
}
