package com.saurav.animals.service;

import com.saurav.animals.enitity.Animals;
import com.saurav.animals.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static java.lang.Byte.parseByte;

@Service
public class AnimalServiceImpl implements AnimalService{

    private AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository  theAnimalRepository){
        this.animalRepository = theAnimalRepository;
    }

    @Override
    public Animals save(Animals theAnimals, MultipartFile file) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if(fileName.contains(".."))
//        {
//            System.out.println("not a a valid file");
//        }
//        try {
//            theAnimals.setImage(Arrays.toString(file.getBytes()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        theAnimals.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        return animalRepository.save(theAnimals);
    }

    @Override
    public List<Animals> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public Animals findById(Long id) {
        Optional<Animals> result = animalRepository.findById(id);
        Animals animals = null;
        if(result.isPresent()) {
            animals = result.get();
        } else {
            throw new RuntimeException("Animal not found");
        }
        return animals;
    }

    @Override
    public Optional<Animals> findAnimals(Long id) {
        return animalRepository.findById(id);
    }

    @Override
    public void deleteById(long id) {
        animalRepository.deleteById(id);
    }

}