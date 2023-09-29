package com.tiembanhhoangtube.RestfulAPI;


import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.model.accountDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin("*")
@RestController
@RequestMapping("users")
public class AccountProfileUserController {
    @Autowired
    HttpSession session;
    @Autowired
    AccountService accountService;
    @Autowired
    StogareService stogareService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AccountRepository accountRepository;

    @GetMapping("profile")
    public ResponseEntity<Account> getProfile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Account> account = accountService.findByUsername(username);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("profile/update/add")
    public ResponseEntity<?> updateProfile(accountDto dto, @RequestParam("imageFile") MultipartFile imageFile) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Account> existingAccount = accountService.findByUsername(username);
        System.out.println("email put về:" + dto);
        if (dto.getEmail().equals(existingAccount.get().getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }
        // Rest of your code to update the profile
        dto.setRole(existingAccount.get().getRole());
        Account account = new Account();
        BeanUtils.copyProperties(dto, account);

        if (!imageFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            account.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
            stogareService.store(dto.getImageFile(), account.getImage());
        }
        accountService.save(account);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("profile/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody accountDto dto){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Account> existingAccount = accountService.findByUsername(username);
        System.out.println("pass database:"+existingAccount.get().getPassword());
        System.out.println("pass người dùng nhập:"+dto);
        if(!bCryptPasswordEncoder.matches(dto.getPassword(),existingAccount.get().getPassword())){
            return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng");
        }
        Account account = existingAccount.get();
        account.setPassword(bCryptPasswordEncoder.encode(dto.getNewpassword()));
        accountService.save(account);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Kiểm tra xem email có tồn tại trong hệ thống không
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        // Tạo mật khẩu ngẫu nhiên
        String newPassword = generateRandomPassword();

        // Cập nhật mật khẩu mới cho tài khoản
        account.setPassword(newPassword);
        accountRepository.save(account);

        // Gửi email chứa mật khẩu mới
        sendPasswordEmail(email, newPassword);

        return ResponseEntity.ok("Password reset successful");
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        int passwordLength = 10;

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(characters.length());
            newPassword.append(characters.charAt(randomIndex));
        }

        return newPassword.toString();
    }

    private void sendPasswordEmail(String toEmail, String newPassword) {
        // Thay thế thông tin email của bạn
        String fromEmail = "adtiembanh@gmail.com";
        String password = "Hiephoa123";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset");
            message.setText("Hello,\n" +
                    "\n" +
                    "We received a request to reset your password. Here's your new password:\n" +
                    "\n" +
                    "New Password:  "+ newPassword +
                    "\n" +
                    "Please make sure to change your password after logging in for security reasons.\n" +
                    "\n" +
                    "If you didn't request a password reset, please ignore this email.\n" +
                    "\n" +
                    "Best regards,\n" +
                    "Your Application Team");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
