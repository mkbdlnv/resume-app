package kabdulinovmedet.pet.kz.resume.controllers;

import jakarta.validation.Valid;
import kabdulinovmedet.pet.kz.resume.models.User;
import kabdulinovmedet.pet.kz.resume.utils.UserErrorResponse;
import kabdulinovmedet.pet.kz.resume.utils.UserNotCreatedException;
import kabdulinovmedet.pet.kz.resume.utils.UserNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import kabdulinovmedet.pet.kz.resume.services.UserService;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid User user,
                                             BindingResult bindingResult){
       return userService.save(user, bindingResult).isPresent()?ResponseEntity.ok().build():
               ResponseEntity.badRequest().build();
    }


    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<UserErrorResponse> handleUserException(UserNotFoundException e){
        UserErrorResponse response = new UserErrorResponse(
                "User not found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotCreatedException.class)
    private ResponseEntity<UserErrorResponse> handleUserException(UserNotCreatedException e){
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
