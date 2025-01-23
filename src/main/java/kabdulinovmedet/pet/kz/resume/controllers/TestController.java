package kabdulinovmedet.pet.kz.resume.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    @PatchMapping("/{id}")
    public String  update(@PathVariable("id") int id){

        return "test";
    }

    @GetMapping()
    public String test(){
        return "test";
    }
}
