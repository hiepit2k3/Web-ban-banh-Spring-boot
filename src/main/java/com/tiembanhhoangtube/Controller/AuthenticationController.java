package com.tiembanhhoangtube.Controller;


import com.tiembanhhoangtube.Ath.AuthenticationResponse;
import com.tiembanhhoangtube.Ath.AuthenticationResquest;
import com.tiembanhhoangtube.Ath.AuthenticationService;
import com.tiembanhhoangtube.Ath.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
//        return ResponseEntity.ok((authenticationService.register(request)));
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationResquest request) {
//        return ResponseEntity.ok((authenticationService.authentication(request)));
//    }
    @GetMapping("403")
    public String error403(){
        return "403";
    }
}
