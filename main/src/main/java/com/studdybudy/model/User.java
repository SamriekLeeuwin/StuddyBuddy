package com.studdybudy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "User")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="User_Name" , nullable = false)
    private String firstname;

    @Column(name= "User_LastName", nullable = false)
    private String lastname;

   @Email
   @NotBlank
    @Column(name= "User_Email", nullable = false, unique = true)
    private String email;


    @Column(name ="User_Password" ,nullable = false)
    private String password;




}
