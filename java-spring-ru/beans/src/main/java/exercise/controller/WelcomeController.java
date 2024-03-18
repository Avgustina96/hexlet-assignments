package exercise.controller;

import exercise.Application;
import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private Application application;

    @GetMapping(path = "")
    public Daytime showBetween() {
        int hour = LocalDateTime.now().getHour();
        if (hour > 6 && hour < 12) {
            return application.getDay();
        } else {
            return application.getNigth();
        }
    }

}