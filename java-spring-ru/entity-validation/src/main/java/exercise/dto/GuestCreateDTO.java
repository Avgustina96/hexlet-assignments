package exercise.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
public class GuestCreateDTO {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotNull
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Pattern(regexp = "^\\+\\d{11,13}$")
//    @Size(min = 11, max = 13)
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;

    @CreatedDate
    private LocalDate createdAt;
}