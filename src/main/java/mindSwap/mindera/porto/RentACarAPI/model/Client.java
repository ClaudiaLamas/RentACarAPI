package mindSwap.mindera.porto.RentACarAPI.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private int driverLicence;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private int nif;

    public Client() {
    }

    public Client(String name, String email, int driverLicence, LocalDate dateOfBirth, int nif) {
        this.name = name;
        this.email = email;
        this.driverLicence = driverLicence;
        this.dateOfBirth = dateOfBirth;
        this.nif = nif;
    }

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDriverLicence() {
        return driverLicence;
    }

    public void setDriverLicence(int driverLicence) {
        this.driverLicence = driverLicence;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }
}
