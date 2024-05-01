package com.saurav.animals.service;

import com.saurav.animals.enitity.Animals;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AnimalService {

    Animals save(Animals animals, MultipartFile multipartFile) throws IOException;

    List<Animals> findAll();

    Animals findById(Long id);
    Optional<Animals>findAnimals(Long id);

    void deleteById(long id);

//    List<Animals> searchAnimals(String query);
}
