package com.tiembanhhoangtube.Controller;

import com.tiembanhhoangtube.Repository.CartitemRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.CartitemService;
import com.tiembanhhoangtube.Service.ProductService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.entity.Cartitem;
import com.tiembanhhoangtube.entity.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequestMapping("cuahangbanbanh")
public class CartitemController {

    @Autowired
    CartitemRepository cartitemRepository;

    @Autowired
    CartitemService cartitemService;

    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;

    @Autowired
    HttpSession session;


    @GetMapping("shoppingcart")
    public ModelAndView getShoppingCart(ModelMap model) {
        String id = (String) session.getAttribute("username");
        System.out.println("id = " + id);
        Account account = accountService.findByUsername(id);
        model.addAttribute("listproducts",cartitemRepository.getCartitemAndProduct(account.getAccountId()));
        int sl = cartitemService.countByCustomerId(id);
        session.setAttribute("quantity",sl);
        return new ModelAndView( "shoping-cart",model);
    }

    @GetMapping("cartitem/id={productsId}")
    public ModelAndView oderProduct(ModelMap model, @PathVariable("productsId") String productId) {
        String username = (String) session.getAttribute("username");
        System.out.println("sp: "+ productId);
        Account account = accountService.findByUsername(username);
        Cartitem cartitem = cartitemService.findByCartitemProductId(Long.parseLong(productId));
        if (cartitem != null) {
            cartitem.setQuantity(cartitem.getQuantity() + 1);
            cartitemRepository.save(cartitem);
            System.out.println(":chạy chuyen trang");
            return new ModelAndView("redirect:/cuahangbanbanh/shoppingcart",model);
        }
        Cartitem newCartitem = new Cartitem();
        Product product = productService.findByproducId(Long.parseLong(productId));
        newCartitem.setQuantity(1);
        newCartitem.setProduct(product);
        newCartitem.setAccount(account);
        cartitemRepository.save(newCartitem);
        return new ModelAndView("redirect:/cuahangbanbanh/shoppingcart",model);
    }

    @GetMapping("/cartitem/delete/id={itemId}")
    public String removeCartItem(@PathVariable("itemId") Long itemId) {
        // Xử lý xóa cart item với id được cung cấp
        Optional<Cartitem> cartitem = cartitemService.findById(itemId);
        System.out.println("id: " +cartitem );
        // Đảm bảo thực hiện xử lý xóa cart item trong service hoặc repository
        cartitemRepository.delete(cartitem.get());
        System.out.println("xóa thành công");
        // Redirect hoặc forward đến trang hiển thị cart sau khi xóa thành công
        return "redirect:/cuahangbanbanh/shoppingcart"; // hoặc "forward:/cart"
    }
}
