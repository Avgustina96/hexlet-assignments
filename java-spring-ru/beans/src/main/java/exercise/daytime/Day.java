package exercise.daytime;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    public int init() {
        int hour = LocalDateTime.now().getHour();
        System.out.println("It is " + getName() + " now! Welcome to Spring!");
        return hour;

    }

}
