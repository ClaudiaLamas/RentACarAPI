package mindSwap.mindera.porto.RentACarAPI.utils;

public enum Messages {

    CLIENT_NOT_FOUND ("Client id not found"),
    RENTAL_NOT_FOUND ("Rental id not found"),
    EMAIL_EXISTS ("Email already exists"),
    LAST_DAY_RENTAL ("Last day rental date must be in after Initial date rental"),
    CAR_EXISTS ("This car Licence Plate already exists"),
    CANNOT_DELETE ("This entity cannot be deleted. It seems there is a rental with this model."),
    CAR_NOT_FOUND ("Car id does not exist");

    private String message;
    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

