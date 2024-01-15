package mindSwap.mindera.porto.RentACarAPI.rentalDto;

import java.time.LocalDate;

public record RentalPostDto(
        Long clientId,
        Long carId,
        LocalDate initialDate,
        LocalDate lastDayRent
) {
}
