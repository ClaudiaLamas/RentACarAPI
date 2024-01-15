package mindSwap.mindera.porto.RentACarAPI.service;


import mindSwap.mindera.porto.RentACarAPI.converter.RentalConverter;
import mindSwap.mindera.porto.RentACarAPI.model.Car;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import mindSwap.mindera.porto.RentACarAPI.model.Rental;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalCreateDto;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalPostDto;
import mindSwap.mindera.porto.RentACarAPI.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CarService carService;
    private final ClientService clientService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, CarService carService, ClientService clientService) {
        this.rentalRepository = rentalRepository;
        this.carService = carService;
        this.clientService = clientService;
    }

    public List<RentalCreateDto> getRentals() {
        List<Rental> rental = rentalRepository.findAll();
        return rental.stream()
                .map(RentalConverter::fromRentalToCreateDto)
                .toList();
    }

    public void addNewRental(RentalPostDto rentalDto) {

        Car car = carService.getCarById(rentalDto.carId());
        Client client = clientService.getClientById(rentalDto.clientId());

        //Rental newRental = RentalConverter.fromRentalDtoToRental(client, car, rentalDto.initialRent(), rentalDto.lastDayRental());

        Rental newRental = new Rental(client, car, rentalDto.initialDate(), rentalDto.lastDayRent());

        rentalRepository.save(newRental);

    }



}
