package mindSwap.mindera.porto.RentACarAPI.rentalDto;

import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.model.Client;

import java.time.LocalDate;

public record RentalCreateDto(
        Client client,
        Car car,
        LocalDate initialRent,
        LocalDate lastDayRental

) {
}
