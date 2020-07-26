package com.example.domain.primary.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class DtbAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username",unique = true, length = 20, nullable = false)
    private String userName;

    @Column(name = "fullname", length = 50, nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String passWord;

}
