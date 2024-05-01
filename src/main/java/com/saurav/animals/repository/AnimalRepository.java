package com.saurav.animals.repository;

import com.saurav.animals.enitity.Animals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository  extends JpaRepository<Animals,Long> {
    List<Animals> findByNameContaining(String query);
    List<Animals> findByCategoryContaining(String query);

    List<Animals> findAllByOrderByNameAsc();
    List<Animals> findAllByOrderByNameDesc();
}
