package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public User update(User user, String name) {
        return mapper.entityToUserDto(userRepository.save(mapper.userDtoToEntity(user, getEntity(name))));
    }

    @Override
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public User get(String username) {
        return mapper.entityToUserDto(getEntity(username));
    }

    @Override
    public UserEntity getEntity(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new FindNoEntityException("пользователь"));
    }

    @Override
    public void uploadImage(MultipartFile image, String username) throws IOException {
        UserEntity userEntity = getEntity(username);
        ImageEntity imageEntity = userEntity.getImage();
        userEntity.setImage(imageService.saveImage(image));
        userRepository.save(userEntity);
        if (imageEntity != null) {
            imageService.deleteImage(imageEntity);
        }
    }

    @Override
    public UserEntity getEntityById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new FindNoEntityException("пользователь"));
    }
}
