package mindSwap.mindera.porto.RentACarAPI.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
//@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String brand;
    @Column(unique = true)
    private String plate;
    private int horsePower;
    private int km;
    private LocalDate acquisitionDate;


    public Car() {
    }

    public Car(String brand, String plate, int horsePower, int km, LocalDate acquisitionDate) {
        this.brand = brand;
        this.plate = plate;
        this.horsePower = horsePower;
        this.km = km;
        this.acquisitionDate = acquisitionDate;
    }


    public Car(long id, String brand, String plate, int horsePower, int km, LocalDate acquisitionDate) {
        this.id = id;
        this.brand = brand;
        this.plate = plate;
        this.horsePower = horsePower;
        this.km = km;
        this.acquisitionDate = acquisitionDate;
    }



    public long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }


}
