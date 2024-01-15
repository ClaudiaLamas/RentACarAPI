package mindSwap.mindera.porto.RentACarAPI.service;


import mindSwap.mindera.porto.RentACarAPI.converter.RentalConverter;
import mindSwap.mindera.porto.RentACarAPI.model.Rental;
import mindSwap.mindera.porto.RentACarAPI.rentalDto.RentalCreateDto;
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

    public void addNewRental(RentalCreateDto rentalDto) {

        //Car car = carService.getCarById(rentalDto.car().getId());
        //Client client = clientService.getClientById(rentalDto.client().getId());

        //Rental newRental = RentalConverter.fromRentalDtoToRental(client, car, rentalDto.initialRent(), rentalDto.lastDayRental());


        //Rental newRental = new Rental(client, car, rentalDto.initialRent(), rentalDto.lastDayRental());

        Rental newRental = RentalConverter.fromRentalDtoToCrearteRental(rentalDto);
        rentalRepository.save(newRental);

    }




}