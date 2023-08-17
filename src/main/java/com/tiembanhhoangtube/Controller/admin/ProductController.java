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
import org.springframework.web.multipart.MultipartFile;
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
        model.addAttribute("product", new ProductDto());
        return "admin/Admin-form-AddProduct";
    }


    @PostMapping("product/AddOrEdit")
    public ResponseEntity<?> saveOrUpdate( @RequestBody ProductDto dto, BindingResult result) {
        System.out.println("Data từ client gửi về: " + dto);
        MultipartFile img = dto.getImageFile();
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            System.out.println("Tên File: " + uuidString);

            // Lưu tên tệp hoặc đường dẫn vào entity hoặc thực hiện thao tác cần thiết
            entity.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
            stogareService.store(dto.getImageFile(), entity.getImage());
        }

        productService.save(entity);
        System.out.println("Tên file sau khi chuyển đổi: " + entity.getImage());
        return ResponseEntity.ok("Thêm sản phẩm thành công");
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

    @RequestMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = stogareService.loadAsResource(filename);
        System.out.println("fiole anh: " + file);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("")
    public String list(ModelMap model, @ModelAttribute("product") Product product) {
        System.out.println("data");
        return "admin/Admin-table-listProduct";
    }

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
}
