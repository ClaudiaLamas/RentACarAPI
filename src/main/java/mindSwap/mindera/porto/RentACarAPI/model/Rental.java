package mindSwap.mindera.porto.RentACarAPI.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate initialRent;
    private LocalDate lastDayRental;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Car car;

    public Rental(Client client, Car car, LocalDate initialRent, LocalDate lastDayRental) {
        this.initialRent = initialRent;
        this.lastDayRental = lastDayRental;
        this.client = client;
        this.car = car;
    }


    public int getId() {
        return id;
    }

    public LocalDate getInitialRent() {
        return initialRent;
    }

    public void setInitialRent(LocalDate initialRent) {
        this.initialRent = initialRent;
    }

    public LocalDate getLastDayRental() {
        return lastDayRental;
    }

    public void setLastDayRental(LocalDate lastDayRental) {
        this.lastDayRental = lastDayRental;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}
