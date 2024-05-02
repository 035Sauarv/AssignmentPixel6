package com.saurav.animals.repository;

import com.saurav.animals.enitity.Animals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository  extends JpaRepository<Animals,Long> {

    // search for name
    List<Animals> findByNameContaining(String query);

    List<Animals> findByNameContainingOrCategoryContaining(String name,String category);
    // search for animal category
    List<Animals> findByCategoryContaining(String query);

    // sort animal in asc
    List<Animals> findAllByOrderByNameAsc();

    // desc
    List<Animals> findAllByOrderByNameDesc();
}
