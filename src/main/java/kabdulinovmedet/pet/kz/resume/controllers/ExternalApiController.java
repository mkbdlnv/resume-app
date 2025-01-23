package kabdulinovmedet.pet.kz.resume.controllers;

import kabdulinovmedet.pet.kz.resume.models.Weather;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("/api")
public class ExternalApiController {
    @GetMapping("/weather")
    public String getWeather(Model model){
//        WebClient webClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?q=London&appid=e4839e43fe0c9e52422f14e0b38ccc07");
//        Weather weather = webClient.get().retrieve().bodyToMono(Weather.class).block();
//        weather.getMain().setPressure(2);
//        model.addAttribute("weather", new Weather());
        return "test";
    }

    @PostMapping("/weather")
    public String getWeatherByCity(@RequestParam String city, Model model){
        WebClient webClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=e4839e43fe0c9e52422f14e0b38ccc07");
        Weather weather = webClient.get().retrieve().bodyToMono(Weather.class).block();
        weather.getMain().setPressure(2);
        model.addAttribute("weather", new Weather());
        return "test";
    }
}
