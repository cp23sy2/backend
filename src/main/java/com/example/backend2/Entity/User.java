package com.example.backend2.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "userName", nullable = false, length = 100)
    private String userName;

    @Size(max = 100)
    @NotNull
    @Column(name = "userEmail", nullable = false, length = 100)
    private String userEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}