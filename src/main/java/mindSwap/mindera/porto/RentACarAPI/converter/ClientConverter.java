package mindSwap.mindera.porto.RentACarAPI.converter;

import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientCreateDto;
import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientGetDto;
import mindSwap.mindera.porto.RentACarAPI.model.Client;

public class ClientConverter {

    public static Client fromClientDtoToCrearteClient(ClientCreateDto clientCreateDto) {
        return new Client(
                clientCreateDto.name(),
                clientCreateDto.email(),
                clientCreateDto.driverLicence(),
                clientCreateDto.dateOfBirth(),
                clientCreateDto.nif()
        );
    }

    public static ClientCreateDto fromClientToCreateDto(Client client){
        return new ClientCreateDto(
                client.getName(),
                client.getEmail(),
                client.getDriverLicence(),
                client.getDateOfBirth(),
                client.getNif()
        );
    }

    public static Client fromClientGetDtoToCrearteClient(ClientGetDto clientGetDto) {
        return new Client(
                clientGetDto.name(),
                clientGetDto.email()
        );
    }

    public static ClientGetDto fromClientToGetDto(Client client){
        return new ClientGetDto(
                client.getName(),
                client.getEmail()
        );
    }
}
