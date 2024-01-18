package mindSwap.mindera.porto.RentACarAPI.carDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CarCreateDto(
        @NotBlank(message = "Name is mandatory")
        String brand,
        @NotBlank(message = "Plate is mandatory")
        String plate,
        @NotBlank(message = "Horse Power is mandatory")
        int horsePower,
        @NotBlank(message = "Plate is mandatory")
        int km,
        @Past
        LocalDate acquisitionDate

) {
}
