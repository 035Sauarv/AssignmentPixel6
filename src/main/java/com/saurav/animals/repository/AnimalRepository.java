package com.saurav.animals.repository;

import com.saurav.animals.enitity.Animals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository  extends JpaRepository<Animals,Long> {
    // search by name and category
    List<Animals> findByNameContainingOrCategoryContaining(String name,String category);
    // sorting by ascending
    List<Animals> findAllByOrderByNameAsc();
    // sorting by descending
    List<Animals> findAllByOrderByNameDesc();

}
