package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO create (@RequestBody ContactCreateDTO contactCreateDTO){
        var contact = toEntity(contactCreateDTO);
        contactRepository.save(contact);
        var contractDTO = toDTO(contact);

        return contractDTO;
    }

    private ContactDTO toDTO(Contact contact) {
        var dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setCreatedAt(contact.getCreatedAt());
        dto.setUpdatedAt(contact.getUpdatedAt());

        dto.setLastName(contact.getLastName());
        dto.setFirstName(contact.getFirstName());
        dto.setPhone(contact.getPhone());
        return dto;
    }


    private Contact toEntity(ContactCreateDTO contactDTO) {
        var contract = new Contact();
        contract.setFirstName(contactDTO.getFirstName());
        contract.setLastName(contactDTO.getLastName());
        contract.setPhone(contactDTO.getPhone());
        return  contract;
    }
}
