package com.m2w.sispj.service;

import com.m2w.sispj.domain.Contact;
import com.m2w.sispj.dto.ContactDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.ContactMapper;
import com.m2w.sispj.repository.ContactRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public List<ContactDTO> list(Pageable pageable) {
        return repository.findAll(pageable).stream()
            .map(ContactMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    public ContactDTO findById(Long contactId) {
        return repository.findById(contactId)
            .map(ContactMapper.getInstance()::entityToDTO)
            .orElseThrow(() -> new RuntimeException("Contacto não existe!"));
    }

    @Transactional
    public ContactDTO create(ContactDTO contactDTO) {
        this.validateNewContact(contactDTO);

        Contact contact = ContactMapper.getInstance().dtoToEntity(contactDTO);
        repository.saveAndFlush(contact);

        return ContactMapper.getInstance().entityToDTO(contact);
    }

    private void validateNewContact(ContactDTO contactDTO) {
        if(!ObjectUtils.isEmpty(contactDTO.getName()) && repository.existsByName(contactDTO.getName())) {
            throw new SISPJException(SISPJExceptionDefinition.CONTACT_DUPLICATED_NAME);
        }
    }

    @Transactional
    public ContactDTO update(ContactDTO contactDTO) {
        Contact contact = repository.findById(contactDTO.getId())
            .orElseThrow(() -> new RuntimeException("Contacto não existe!"));

        this.validateUpdateContact(contactDTO);

        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());

        repository.saveAndFlush(contact);

        return ContactMapper.getInstance().entityToDTO(contact);
    }

    private void validateUpdateContact(ContactDTO contactDTO) {
        if(!ObjectUtils.isEmpty(contactDTO.getName()) && repository.existsByNameAndIdNot(contactDTO.getName(), contactDTO.getId())) {
            throw new SISPJException(SISPJExceptionDefinition.CONTACT_DUPLICATED_NAME);
        }
    }

    @Transactional
    public void delete(Long contactId) {
        repository.deleteById(contactId);
    }
}