package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.converter.RentalConverter;
import mindSwap.mindera.porto.RentACarAPI.exceptions.AppExceptions;
import mindSwap.mindera.porto.RentACarAPI.exceptions.RentalIdNotFoundException;
import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import mindSwap.mindera.porto.RentACarAPI.model.Rental;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalCreateDto;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalPostDto;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalUpdateDto;
import mindSwap.mindera.porto.RentACarAPI.repository.RentalRepository;
import mindSwap.mindera.porto.RentACarAPI.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalServiceI{

    private final RentalRepository rentalRepository;
    private final CarServiceImpl carService;
    private final ClientServiceImpl clientService;

    @Autowired
    public RentalServiceImpl(RentalRepository rentalRepository, CarServiceImpl carService, ClientServiceImpl clientService) {
        this.rentalRepository = rentalRepository;
        this.carService = carService;
        this.clientService = clientService;
    }

    @Override
    public List<RentalCreateDto> getRentals() {
        List<Rental> rental = rentalRepository.findAll();
        return rental.stream()
                .map(RentalConverter::fromRentalToCreateDto)
                .toList();
    }

    public Rental addNewRental(RentalPostDto rentalPostDto) {

        Car car = carService.getCarById(rentalPostDto.carId());

        Client client = clientService.getClientById(rentalPostDto.clientId());

        if(rentalPostDto.lastDayRent().isBefore(rentalPostDto.initialDate())) {
            throw new AppExceptions(Messages.LAST_DAY_RENTAL.getMessage());
        }


        //Rental newRental = RentalConverter.fromRentalDtoToRental(client, car, rentalDto.initialRent(), rentalDto.lastDayRental());

        Rental newRental = new Rental(client, car, rentalPostDto.initialDate(), rentalPostDto.lastDayRent());

        return rentalRepository.save(newRental);

    }


    public void updateRental(Long id, RentalUpdateDto rentalUpdateDto) {

        Optional<Rental> rentalOptional = rentalRepository.findById(id);

        if (!rentalOptional.isPresent()) {
            throw new AppExceptions(Messages.CLIENT_NOT_FOUND.getMessage());
        }

        Rental rentalToUpdate = rentalOptional.get();

        if (rentalUpdateDto.client() != null && !rentalUpdateDto.client().equals(rentalToUpdate.getClient())) {
            rentalToUpdate.setClient(rentalUpdateDto.client());
        }

        if (rentalUpdateDto.car() != null && !rentalUpdateDto.car().equals(rentalToUpdate.getCar())) {
            rentalToUpdate.setCar(rentalUpdateDto.car());
        }

        if (rentalUpdateDto.initialRent() != null && !rentalUpdateDto.initialRent().equals(rentalToUpdate.getInitialRent())) {
            rentalToUpdate.setInitialRent(rentalUpdateDto.initialRent());
        }

        if (rentalUpdateDto.lastDayRental() != null && !rentalUpdateDto.lastDayRental().equals(rentalToUpdate.getLastDayRental())) {
            rentalToUpdate.setLastDayRental(rentalUpdateDto.lastDayRental());
        }

        rentalRepository.save(rentalToUpdate);

    }

    public void deleteRental(Long id) {
        boolean rentalExists = rentalRepository.existsById(id);

        if (!rentalExists){
            throw new RentalIdNotFoundException(Messages.RENTAL_NOT_FOUND.getMessage());
        }
        rentalRepository.deleteById(id);
    }
}
