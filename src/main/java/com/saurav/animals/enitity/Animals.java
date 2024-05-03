package com.saurav.animals.enitity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

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
    @NotNull(message = "Name not empty")
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
