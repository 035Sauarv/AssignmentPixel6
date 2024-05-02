package com.saurav.animals.controllers;

import com.saurav.animals.enitity.Animals;
import com.saurav.animals.repository.AnimalRepository;
import com.saurav.animals.service.AnimalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/animals")
public class AnimalController {
    private AnimalService animalService;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    public AnimalController(AnimalService theAnimalService) {
        this.animalService = theAnimalService;
    }

    //list of the animals
    @GetMapping("/list")
    public String showAnimals(Model model) {
        List<Animals> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        return "animals/list-animals";
    }


    //search for the animal
    @GetMapping("/search")
    public String search(@RequestParam("query") String query,Model model){
        List<Animals>searchAnimals=  animalRepository.findByNameContaining(query);
        List<Animals>searchCategory = animalRepository.findByCategoryContaining(query);
        model.addAttribute("animals",searchAnimals);
        model.addAttribute("animals",searchCategory);
        return "animals/list-animals";
    }


    //sort for animal
    @GetMapping("/sort")
    public String sort(@RequestParam(name = "sort", defaultValue = "asc") String sort, Model model) {
        List<Animals> sortedAnimals;
        if ("desc".equals(sort)) {
            sortedAnimals = animalRepository.findAllByOrderByNameDesc();
        } else {
            sortedAnimals = animalRepository.findAllByOrderByNameAsc();
        }
        model.addAttribute("animals", sortedAnimals);
        return "animals/list-animals";
    }


    // add animal
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model, HttpServletRequest request) {
        Animals animals = new Animals();
        model.addAttribute("animals", animals);
        String captcha = generateRandomCaptcha(6);
        model.addAttribute("captcha", captcha);
        request.getSession().setAttribute("captcha", captcha);
        return "animals/animal-form";
    }

    // save animal
    @PostMapping("/save")
    public String saveAnimals(@ModelAttribute Animals animals,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("captcha") String userCaptcha,
                              Model model,
                              HttpServletRequest request) throws IOException {
        String sessionCaptcha = (String) request.getSession().getAttribute("captcha");
        if(sessionCaptcha!= null&& sessionCaptcha.equals(userCaptcha)){
            animalService.save(animals, file);
            return "redirect:/animals/list";
        }else{
            model.addAttribute("captchaError", "Invalid Captcha!");
            model.asMap().remove("animals", animals);
            model.addAttribute("captcha", generateRandomCaptcha(6));
            request.getSession().setAttribute("captcha", model.getAttribute("captcha"));
            return "animals/animal-form";
        }
    }


    // update animal
    @GetMapping("/showFormForUpdate")
    public String updateAnimals(@RequestParam("animalId") long id , Model model){
        Animals animals = animalService.findById(id);
        model.addAttribute("animals",animals);
        return "animals/animal-form";
    }

    // image of animal in blob
    @GetMapping(value = "/image/{animalId}")
    @ResponseBody
    public ResponseEntity<byte[]> getAnimalImage(@PathVariable Long animalId){
        Optional<Animals> animalsOptional = animalService.findAnimals(animalId);
        if (animalsOptional.isPresent()) {
            Animals animals = animalsOptional.get();
            byte[] imageBytes = java.util.Base64.getDecoder().decode(animals.getImage());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete for animal
    @GetMapping("/delete")
    public String delete(@RequestParam("animalId") long id){
        animalService.deleteById(id);
        return "redirect:/animals/list";
    }


    @GetMapping("/captcha")
    public String generateCaptcha(HttpServletRequest request,Model model){
        String captcha = generateRandomCaptcha(6);
        model.addAttribute("captcha",captcha);
        return "animals/animal-form";
    }
    private String generateRandomCaptcha(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i <length ; i++) {
            captcha.append(characters.charAt(random.nextInt(characters.length())));
        }
        return captcha.toString();
    }

}

