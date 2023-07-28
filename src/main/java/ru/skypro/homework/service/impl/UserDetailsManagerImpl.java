package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserSecurity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsManagerImpl implements UserDetailsService {
    private final UserRepository repository;

    public void changePassword(String newPassword, String name) {
        UserEntity entity = getEntity(name);
        entity.setPassword(newPassword);
        repository.save(entity);
    }

    public boolean userExists(String username) {
        return repository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new UserSecurity(getEntity(username));
    }

    public UserEntity getEntity(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new FindNoEntityException("пользователь"));
    }

    public void createUser(UserEntity user) {
        repository.save(user);
    }
}
