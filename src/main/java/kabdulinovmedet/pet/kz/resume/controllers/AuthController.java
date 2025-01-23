package kabdulinovmedet.pet.kz.resume.controllers;

import kabdulinovmedet.pet.kz.resume.Security.JWTUtil;
import kabdulinovmedet.pet.kz.resume.dto.AuthDTO;
import kabdulinovmedet.pet.kz.resume.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;
    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthDTO auth){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                auth.getEmail(), auth.getPassword()
        );
        try{
            authenticationManager.authenticate(authToken);
        }catch (AuthenticationException e){
            return Map.of("message", "Incorrect data");
        }
        String token = jwtUtil.generateToken(auth.getEmail());
        return Map.of("JWT token: ", token);
    }
}
