package exercise.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class CarDTO {
    private long id;
    private String model;
    private String manufacturer;
    private Integer enginePower;
    private LocalDate updatedAt;
    private LocalDate createdAt;
}
