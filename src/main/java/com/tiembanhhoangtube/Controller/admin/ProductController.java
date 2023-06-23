package com.tiembanhhoangtube.Controller.admin;


import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.ProductService;
import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.entity.Product;
import com.tiembanhhoangtube.model.ProductDto;
import com.tiembanhhoangtube.utisl.AuthenticationUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("admin/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    StogareService stogareService;

    @Autowired
    HttpSession session;

    @Autowired
    AccountService accountService;
    @GetMapping("add")
    public String add(Model model) {
        String username = (String) session.getAttribute("username");
        Account account = accountService.findByUsername(username);
        if(!AuthenticationUtils.isUserLoggedIn(session)){
            return "redirect:/account/loginandregister";
        }
        if(account.getRole() == false){
            return "redirect:/account/loginandregister";
        }
        model.addAttribute("product", new ProductDto());
        return "admin/Admin-form-AddProduct";
    }


    @PostMapping("/AddOrEdit")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDto dto, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Loi: " + result.toString());
            return new ModelAndView("admin/Admin-form-AddProduct");
        }
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);

        if (!dto.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            System.out.println("Tên File: " + uuidString);
            entity.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
            stogareService.store(dto.getImageFile(), entity.getImage());
        }
        productService.save(entity);
        System.out.println("Tện file sau khi chuyển đổi: " + entity.getImage());
        model.addAttribute("message", "Products is Saved!");
        return new ModelAndView("redirect:/admin/products", model);
    }


    @GetMapping("/edit/id={productId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
        Optional<Product> opt = productService.findById(productId);
        ProductDto dto = new ProductDto();
        if (opt.isPresent()) {
            Product entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            model.addAttribute("product", dto);
            dto.setIsEdit(true);
            return new ModelAndView("admin/Admin-form-AddProduct", model);

        }
        model.addAttribute("messages", "CA");
        return new ModelAndView("forward:/admin/products", model);
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        System.out.println("--------------------------------------------------------------------------------- -" + filename);
        Resource file = stogareService.loadAsResource(filename);
        System.out.println("fiole anh: " + file);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/delete/id={productId}")
    public ModelAndView delete(ModelMap model, @PathVariable("productId") Long productId) throws Exception {
        Optional<Product> otp = productService.findById(productId);
        if (otp.isPresent()) {
            if (!StringUtils.isEmpty(otp.get().getImage())) {
                stogareService.delete(otp.get().getImage());
            }
        }
        productService.delete(otp.get());
        return new ModelAndView("forward:/admin/products");
    }

    @GetMapping("")
    public String list(ModelMap model, @ModelAttribute("product") Product product) {
        /*model.addAttribute("")*/
        System.out.println("data");
        return "admin/Admin-table-listProduct";
    }

//    @ModelAttribute("categories")
//    public Page<Category> getAll(@RequestParam("keyword") Optional<String> keywork, Model model
//            , @RequestParam("page") Optional<Integer> page) {
//        Pageable pageable = PageRequest.of(page.orElse(0), 5);
//        Page<Category> categories = categoryService.findAllByNameLike("%" + keywork.orElse("") + "%", pageable);
//        return categories;
//    }

    @ModelAttribute("products")
    public List<Product> getAllcategory() {
        List<Product> list = productService.findAll();
        for (Product product : list) {
            if (product.getImage() != null) {
                System.out.println("ten file: " + product.getImage());
            }
        }
        return list;
    }


//    @GetMapping("search")
//    public String getSearch(@RequestParam("keyword") String keywork, Model model
//            , @RequestParam("page") Optional<Integer> page) {
//        Pageable pageable = PageRequest.of(page.orElse(0), 5);
//        Page<Category> categories = categoryService.findAllByNameLike("%" + keywork + "%", pageable);
//        model.addAttribute("categories", categories);
//        return "forward:/admin/category";
//    }
//
//    @GetMapping("searchall")
//    public String getSearch1(@RequestParam("keyword") String keyword, Model model,
//                             @RequestParam("page") Optional<Integer> page) {
//        int pageSize = 5; // Số lượng phần tử trên mỗi trang
//        Pageable pageable = PageRequest.of(page.orElse(0), pageSize);
//        Page<Category> categories = categoryService.findAllByNameLike("%" + keyword + "%", pageable);
//        model.addAttribute("page", pageable);
//        model.addAttribute("categories", categories);
//        return "forward:/admin/category";
//    }
}
