package mindSwap.mindera.porto.RentACarAPI.rentalDto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RentalPostDto(
        @NotBlank(message = "Client Id not valid")
        Long clientId,
        @NotBlank(message = "Car Id not valid")
        Long carId,
        @NotBlank(message = "Initial date not valid")
        LocalDate initialDate,
        @NotBlank(message = "Last day rental date not valid")
        LocalDate lastDayRent
) {
}
