package com.m2w.sispj.service;

import com.m2w.sispj.domain.User;
import com.m2w.sispj.dto.PasswordDTO;
import com.m2w.sispj.dto.TokenDTO;
import com.m2w.sispj.dto.UserDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.ProfileMapper;
import com.m2w.sispj.mapper.UserMapper;
import com.m2w.sispj.repository.UserRepository;
import com.m2w.sispj.util.SISPJUtil;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDTO> list(Pageable pageable) {
        return repository.findAllByDeletedFalse(pageable).stream().map(UserMapper.getInstance()::entityToDTO).collect(Collectors.toList());
    }

    public UserDTO findById(Long userId) {
        Optional<User> user = repository.findById(userId);

        if(user.isPresent()) {
            return UserMapper.getInstance().entityToDTO(user.get());
        }

        throw new SISPJException(SISPJExceptionDefinition.USER_NOT_EXISTS);
    }

    @Transactional
    public PasswordDTO create(UserDTO userDTO) {
        validateNewUser(userDTO);

        String generatedPassword = this.generateRandomSpecialCharacters(10);

        User user = UserMapper.getInstance().dtoToEntity(userDTO);
        user.setPassword(new BCryptPasswordEncoder().encode(generatedPassword));
        user.setChangePassword(true);
        repository.saveAndFlush(user);

        return PasswordDTO.builder().password(generatedPassword).build();
    }

    @Transactional
    public TokenDTO changePassword(PasswordDTO passwordDTO) {
        User user = SISPJUtil.getCurrentUser();

        if(user == null || passwordDTO.getPassword() == null) {
            throw new SISPJException(SISPJExceptionDefinition.BAD_REQUEST);
        }

        user.setPassword(new BCryptPasswordEncoder().encode(passwordDTO.getPassword()));
        user.setChangePassword(false);
        repository.saveAndFlush(user);

        TokenDTO tokenDTO = TokenDTO.builder().build();
        tokenDTO.setChangePassword(user.isChangePassword());

        return tokenDTO;
    }

    @Transactional
    public PasswordDTO generateNewPassword(UserDTO userDTO) {
        User user = repository.findById(userDTO.getId()).orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.USER_NOT_EXISTS));

        String generatedPassword = this.generateRandomSpecialCharacters(10);
        user.setPassword(new BCryptPasswordEncoder().encode(generatedPassword));
        user.setChangePassword(true);
        repository.saveAndFlush(user);

        return PasswordDTO.builder().password(generatedPassword).build();
    }

    public String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(48, 122).build();
        return pwdGenerator.generate(length);
    }

    @Transactional
    public UserDTO update(UserDTO userDTO) {
        User user = repository.findById(userDTO.getId()).orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.USER_NOT_EXISTS));

        this.validateUpdateUser(userDTO);

        this.updateCustomerAttributes(user, userDTO);
        repository.saveAndFlush(user);

        return UserMapper.getInstance().entityToDTO(user);
    }

    private void validateUpdateUser(UserDTO userDTO) {
        if(!ObjectUtils.isEmpty(userDTO.getName()) && repository.existsByNameAndIdNotAndDeleted(userDTO.getName(), userDTO.getId(), false)) {
            throw new SISPJException(SISPJExceptionDefinition.USER_DUPLICATED_NAME);
        }

        if(!ObjectUtils.isEmpty(userDTO.getEmail()) && repository.existsByEmailAndIdNotAndDeleted(userDTO.getEmail(), userDTO.getId(), false)) {
            throw new SISPJException(SISPJExceptionDefinition.USER_DUPLICATED_EMAIL);
        }
    }

    private void updateCustomerAttributes(User user, UserDTO userDTO) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setProfile(ProfileMapper.getInstance().dtoToEntity(userDTO.getProfile()));
    }

    @Transactional
    public void delete(Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.USER_NOT_EXISTS));

        user.setDeleted(true);
        repository.saveAndFlush(user);
    }

    private void validateNewUser(UserDTO userDTO) {
        if(!ObjectUtils.isEmpty(userDTO.getName()) && repository.existsByNameAndDeleted(userDTO.getName(), false)) {
            throw new SISPJException(SISPJExceptionDefinition.USER_DUPLICATED_NAME);
        }

        if(!ObjectUtils.isEmpty(userDTO.getEmail()) && repository.existsByEmailAndDeleted(userDTO.getEmail(), false)) {
            throw new SISPJException(SISPJExceptionDefinition.USER_DUPLICATED_EMAIL);
        }
    }
}