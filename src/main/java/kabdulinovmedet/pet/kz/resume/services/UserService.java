package kabdulinovmedet.pet.kz.resume.services;


import com.auth0.jwt.exceptions.JWTVerificationException;
import kabdulinovmedet.pet.kz.resume.Security.JWTUtil;
import kabdulinovmedet.pet.kz.resume.models.User;
import kabdulinovmedet.pet.kz.resume.repositories.UserRepository;
import kabdulinovmedet.pet.kz.resume.utils.PasswordValidator;
import kabdulinovmedet.pet.kz.resume.utils.UserErrorResponse;
import kabdulinovmedet.pet.kz.resume.utils.UserNotCreatedException;
import kabdulinovmedet.pet.kz.resume.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.model.Property;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class    UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User findOne(long id){
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    
    @Transactional
    public Optional<User> save(User user, BindingResult bindingResult){
        if(!PasswordValidator.isValid(user.getPassword())){
            bindingResult.addError(new FieldError(
                    "User","password",
                    "Password must contain:\n" +
                            "1. At least one digit\n" +
                            "2. At least one lowercase letter\n" +
                            "3.At least one uppercase letter\n" +
                            "4.At least one special character (@#!$%^&-+=())\n" +
                            "5.No whitespaces\n" +
                            "6.Length between 8 and 20"
            ));
        }
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error:errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new UserNotCreatedException(errorMsg.toString());
        } else if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserNotCreatedException("User with email " + user.getEmail() + " already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return Optional.of(user);
    }

    public String authorize(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            if(passwordEncoder.matches(password, user.get().getPassword())){
                return jwtUtil.generateToken(email);
            }

        }
        return "Invalid email or password";


    }
}
