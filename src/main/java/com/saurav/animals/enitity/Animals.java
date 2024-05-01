package com.saurav.animals.enitity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Arrays;
import java.util.Date;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="animals")
public class Animals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="category",columnDefinition = "TEXT")
    private String category;

    @Lob
    @Column(name="image",columnDefinition = "MEDIUMBLOB")
    private String image;


    @Column(name="description")
    private String description;

    @Column(name="life")
    private String life;

    @Column(name="created")
    private Date createdAt;

}
