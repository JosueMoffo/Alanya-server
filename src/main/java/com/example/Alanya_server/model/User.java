package com.example.Alanya_server.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "User")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID") // Mappe le champ 'id' de Java à la colonne 'userID' de la DB
    private Long userId;

    @Column(name = "userName", length = 255, nullable = false)
    private String userName;

    @Column(name = "phoneNumber", length = 255, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "profilePicture", length = 255)
    private String profilePicture = "/images/profils/default_profile.png";

    @Column(name = "codeAccess")
    private Integer codeAccess;

    @Column(name = "statut", length = 20)
    private String statut = "OFFLINE";


    public User(Long id, String userName, String phoneNumber, String password,
                String profilePicture, Integer codeAccess, String statut) {
        this.userId = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profilePicture = (profilePicture != null) ? profilePicture : "/images/profils/default_profile.png";
        this.codeAccess = codeAccess;
        this.statut = (statut != null) ? statut : "OFFLINE";
    }

}
