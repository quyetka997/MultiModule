package com.example.domain.primary.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "DtbDetailBook")
@Table
public class DtbBookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private DtbUser user;

    @ManyToOne
    private DtbTransportRequest transportRequest;

    @Column(nullable = false)
    private Double price;

    @Column(name = "otherinformation", nullable = false, length = 200)
    private String otherInformation;
}
