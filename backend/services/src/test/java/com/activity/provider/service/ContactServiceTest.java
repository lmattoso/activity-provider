package com.activity.provider.service;

import com.activity.provider.error.exception.SISPJException;
import com.activity.provider.utils.annotations.WithMockCustomUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@WithMockCustomUser(username = "admin")
@ActiveProfiles("test")
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Test
    public void test_01_createContact() {
        ContactDTO contactDTO = ContactDTO.builder().name("Name").email("email@test").phone("123456789").build();

        this.testCreateContact(contactDTO);
    }

    private ContactDTO testCreateContact(ContactDTO contactDTO) {
        ContactDTO createdContact = contactService.create(contactDTO);

        assertNotNull(createdContact);
        assertNotNull(createdContact.getId());
        assertEquals(contactDTO.getName(), createdContact.getName());
        assertEquals(contactDTO.getEmail(), createdContact.getEmail());
        assertEquals(contactDTO.getPhone(), createdContact.getPhone());

        return contactService.findById(createdContact.getId());
    }

    @Test
    public void test_02_findAll() {
        testListContacts();
    }

    private List<ContactDTO> testListContacts() {
        List<ContactDTO> contacts = contactService.list(PageRequest.of(0,50));
        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());

        return contacts;
    }

    @Test
    public void test_03_emptyNameCreate() {
        assertThrows(DataIntegrityViolationException.class, () -> contactService.create(ContactDTO.builder().build()));
    }

    @Test
    public void test_04_duplicatedNameCreate() {
        List<ContactDTO> contacts = this.testListContacts();

        ContactDTO contactDTO = contacts.get(0);
        contactDTO.setId(null);

        assertThrows(SISPJException.class, () -> contactService.create(contactDTO));
    }

    @Test
    public void test_05_createNewContact() {
        List<ContactDTO> contacts = this.testListContacts();

        ContactDTO contactDTO = contacts.get(0);
        contactDTO.setId(null);
        contactDTO.setName("abc");

        this.testCreateContact(contactDTO);
    }

    @Test
    public void test_06_emptyNameUpdate() {
        List<ContactDTO> contacts = this.testListContacts();

        ContactDTO contactDTO = contacts.get(0);
        contactDTO.setName(null);

        assertThrows(DataIntegrityViolationException.class, () -> contactService.update(contactDTO));
    }

    @Test
    public void test_07_duplicatedNameUpdate() {
        List<ContactDTO> contacts = this.testListContacts();

        ContactDTO contactDTO = contacts.get(1);
        contactDTO.setName(contacts.get(0).getName());

        assertThrows(SISPJException.class, () -> contactService.update(contactDTO));

        contactDTO.setName(contacts.get(0).getName() + "a");
        contactService.update(contactDTO);
    }

    @Test
    public void test_08_deleteAll() {
        List<ContactDTO> contacts = contactService.list(PageRequest.of(0,Integer.MAX_VALUE));
        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());

        contacts.forEach(contactDTO -> {
            contactService.delete(contactDTO.getId());
        });

        contacts = contactService.list(PageRequest.of(0,Integer.MAX_VALUE));

        assertNotNull(contacts);
        assertTrue(contacts.isEmpty());
    }
}
