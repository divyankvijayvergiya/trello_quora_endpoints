package com.upgrad.quora.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("all")
@Entity
@Table(name = "USERS", schema = "quora")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid")
    @Size(max = 64)
    private String uuid;

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    @NotNull
    @Size(max = 200)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "FIRST_NAME")
    @NotNull
    @Size(max = 200)
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    @Size(max = 200)
    private String lastName;

    @Column(name = "MOBILE_PHONE")
    @NotNull
    @Size(max = 50)
    private String mobilePhone;
}
