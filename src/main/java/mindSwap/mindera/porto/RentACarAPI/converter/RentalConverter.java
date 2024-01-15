package mindSwap.mindera.porto.RentACarAPI.converter;

import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import mindSwap.mindera.porto.RentACarAPI.model.Rental;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalCreateDto;

import java.time.LocalDate;

public class RentalConverter {

    public static Rental fromRentalDtoToCrearteRental(RentalCreateDto rentalCreateDto) {
        return new Rental(
                rentalCreateDto.client(),
                rentalCreateDto.car(),
                rentalCreateDto.initialRent(),
                rentalCreateDto.lastDayRental()
        );
    }

    public static Rental fromRentalDtoToRental(Client client, Car car, LocalDate initialRent, LocalDate lastDayRent) {
        return new Rental(
                client,
                car,
                initialRent,
                lastDayRent
        );
    }



    public static RentalCreateDto fromRentalToCreateDto(Rental rental){
        return new RentalCreateDto(
                rental.getClient(),
                rental.getCar(),
                rental.getInitialRent(),
                rental.getLastDayRental()
        );
    }
}
