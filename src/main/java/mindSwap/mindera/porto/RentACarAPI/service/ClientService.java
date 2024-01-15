package mindSwap.mindera.porto.RentACarAPI.service;

import mindSwap.mindera.porto.RentACarAPI.clientDto.ClientCreateDto;
import mindSwap.mindera.porto.RentACarAPI.converter.ClientConverter;
import mindSwap.mindera.porto.RentACarAPI.model.Client;
import mindSwap.mindera.porto.RentACarAPI.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindSwap.mindera.porto.RentACarAPI.converter.ClientConverter.fromClientDtoToCrearteClient;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientCreateDto> getClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(ClientConverter::fromClientToCreateDto)
                .toList();
    }

    public void addNewClient(ClientCreateDto client) {
        Optional<Client> clientOptional = this.clientRepository.findClientByEmail(client.email());
        if (clientOptional.isPresent()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
            return;
        }

        Client newClient = fromClientDtoToCrearteClient(client);
        clientRepository.save(newClient);
    }

    public void deleteClient(Long clientId) {
        if(!clientRepository.existsById(clientId)) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client with id " + clientId + "does not exist");
        }
        clientRepository.deleteById(clientId);
    }

    public void updateClient(Long id, Client client) {

        Optional<Client> clientOptional = clientRepository.findById(id);
        if(!clientOptional.isPresent()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client with id " + id + " does not exist");
        }
        Client clientToUpdate = clientOptional.get();
        if(client.getName() != null && client.getName().length() > 0 && !client.getName().equals(clientToUpdate.getName())) {
            clientToUpdate.setName(client.getName());
        }
        if(client.getEmail() != null && client.getEmail().length() > 0 && !client.getEmail().equals(clientToUpdate.getEmail())){
            Optional<Client> clientOptionalEmail = clientRepository.findClientByEmail(client.getEmail());
            if (clientOptionalEmail.isPresent())
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email already exists");
            clientToUpdate.setEmail(client.getEmail());
        }
        if(client.getDriverLicence() >= 0 && client.getDriverLicence() != clientToUpdate.getDriverLicence()) {

            clientToUpdate.setDriverLicence(client.getDriverLicence());

        }
        if (client.getDateOfBirth() != null && !client.getDateOfBirth().equals(clientToUpdate.getDateOfBirth())) {
            clientToUpdate.setDateOfBirth(client.getDateOfBirth());
        }
        clientRepository.save(clientToUpdate);
    }

    public Client getClientById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isEmpty()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client with id " + id + " does not exist");
        }
        return optionalClient.get();
    }

}
