package mindSwap.mindera.porto.RentACarAPI.converter;

import mindSwap.mindera.porto.RentACarAPI.carDto.CarCreateDto;
import mindSwap.mindera.porto.RentACarAPI.model.Car;

public class CarConverter {

    public static CarCreateDto fromCarToCreateDto(Car car) {
        return new CarCreateDto(
                car.getBrand(),
                car.getPlate(),
                car.getHorsePower(),
                car.getKm(),
                car.getAcquisitionDate());
    }

    public static Car fromDtaToCreateCar(CarCreateDto carCreateDto) {
        return new Car(
                carCreateDto.brand(),
                carCreateDto.plate(),
                carCreateDto.horsePower(),
                carCreateDto.km(),
                carCreateDto.acquisitionDate()
                );
    }



}
