package com.tiembanhhoangtube.Controller;

import com.tiembanhhoangtube.Repository.CartitemRepository;
import com.tiembanhhoangtube.Repository.OrderRepository;
import com.tiembanhhoangtube.Repository.OrdertailRepository;
import com.tiembanhhoangtube.Repository.ProductRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.ProductService;
import com.tiembanhhoangtube.entity.*;
import com.tiembanhhoangtube.model.cartitemDto;
import com.tiembanhhoangtube.model.orderDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("cuahangbanbanh")
public class OrderController {

    @Autowired
    HttpSession session;

    @Autowired
    AccountService accountService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrdertailRepository ordertailRepository;

    @Autowired
    CartitemRepository cartitemRepository;

    @PostMapping("addorder")
    public ModelAndView processFormData(ModelMap model, @RequestBody List<orderDto> orders, BindingResult result) {
        System.out.println("CartItem: " + orders);
        String name = (String) session.getAttribute("username");
        Account account = accountService.findByUsername(name);
        Order order = new Order();
        Date orderdate = new Date();
        order.setOrderDate(orderdate);
        order.setAccount(account);
        order.setAmount(orders.get(0).getAmount());
        orderRepository.save(order);
        for (orderDto o : orders) {
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
        return new ModelAndView("redirect:/cuahangbanbanh/shoppingcart", model);
    }
//    @PostMapping("addorder")
//    public ModelAndView addOrder(@RequestBody List<orderDto> dto) {
//        System.out.println("Oder:" + dto);
//        String name = (String) session.getAttribute("username");
//        Account account = accountService.findByUsername(name);
//        Order order = new Order();
//        Date orderdate = new Date();
//        order.setOrderDate(orderdate);
//        order.setAccount(account);
//        order.setAmount(dto.get(0).getAmount());
//        orderRepository.save(order);
//        for (orderDto o : dto) {
//            Orderdetail orderdetail = new Orderdetail();
//            Optional<Product> product = productService.findById(o.getProductId());
//            orderdetail.setProduct(product.get());
//            orderdetail.setQuantity(o.getQuantity());
//            orderdetail.setUnitPrice(o.getPrice());
//            orderdetail.setOrder(order);
//            ordertailRepository.save(orderdetail);
//            cartitemRepository.deleteById(o.getCartitemId());
//            System.out.println("xóa");
//        }
//        return new ModelAndView("redirect:/cuahangbanbanh/shoppingcart");
//    }
}