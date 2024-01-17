package mindSwap.mindera.porto.RentACarAPI.rentalDto;

import jakarta.validation.constraints.NotBlank;
import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.model.Client;

import java.time.LocalDate;

public record RentalCreateDto(
        @NotBlank(message = "Client not valid")
        Client client,
        @NotBlank(message = "Car not valid")
        Car car,
        @NotBlank(message = "initial date not valid")
        LocalDate initialRent,
        @NotBlank(message = "last day date not valid")
        LocalDate lastDayRental

) {
}
