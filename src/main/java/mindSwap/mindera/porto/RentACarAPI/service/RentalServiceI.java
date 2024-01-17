package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.model.Rental;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalCreateDto;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalPostDto;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalUpdateDto;

import java.util.List;

public interface RentalServiceI {

    List<RentalCreateDto> getRentals();
    Rental addNewRental(RentalPostDto rentalPostDto);
    void updateRental(Long id, RentalUpdateDto rentalUpdateDto);
    void deleteRental(Long id);

}
