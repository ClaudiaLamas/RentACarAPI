package mindSwap.mindera.porto.RentACarAPI.utils;

public enum Messages {

    CLIENT_NOT_FOUND ("Client id not found"),
    RENTAL_NOT_FOUND ("Rental id not found"),
    CAR_NOT_FOUND ("Car id not found");

    private String message;
    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

