package com.maksise4ka.microcats.dao.entities;

import com.maksise4ka.microcats.dao.models.Breed;
import com.maksise4ka.microcats.dao.models.Color;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cats")
@Getter @Setter
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Breed breed;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne
    private Owner owner;

    @ManyToMany
    @JoinTable(name = "friends_links")
    private List<Cat> friends;

    public Cat(String name, LocalDate birthdate, Breed breed, Color color, Owner owner) {
        this.name = name;
        this.birthdate = birthdate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;

        friends = new ArrayList<>();
    }

    protected Cat() {
    }
}