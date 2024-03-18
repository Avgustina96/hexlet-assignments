package exercise.daytime;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Night implements Daytime {
    private String name = "night";

    public String getName() {
        return name;
    }

    public int getHours() {
        int hour = LocalDateTime.now().getHour();
        System.out.println("It is " + getName() + " now! Welcome to Spring!");
        return hour;
    }


}
