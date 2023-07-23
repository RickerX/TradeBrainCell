package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageModel;

import java.io.IOException;

public interface ImageService {

    ImageModel upload(MultipartFile imageFile, Float newWidth) throws IOException;

    ImageModel save(ImageModel image);


    ImageModel read(long id);

}
