package com.maksise4ka.microcats.dao.entities;

import com.maksise4ka.microcats.dao.entities.authorize.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@Getter @Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private LocalDate birthdate;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Cat> cats;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private User user;
    public Owner(String name, LocalDate birthdate, User user) {
        this.name = name;
        this.birthdate = birthdate;
        this.user = user;

        cats = new ArrayList<>();
    }

    protected Owner() {
    }
}
