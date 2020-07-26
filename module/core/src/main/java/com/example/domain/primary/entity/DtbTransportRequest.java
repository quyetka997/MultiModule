package com.example.domain.primary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table
public class DtbTransportRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "good_id", nullable = false)
    private long goodId;

    @NotNull
    private Double price;

    @Column(name = "expectedfee", nullable = false)
    private Double expectedFee;

    @JsonFormat(pattern = "MM-DD-YYYY hh:ss")
    @Column(name = "validuntil",nullable = false)
    private Timestamp validUntil;

    @JsonFormat(pattern = "MM-DD-YYYY HH:SS")
    @Column(name = "dischargingtime",nullable = false)
    private Timestamp dischargingTime;

    @JsonFormat(pattern = "MM-DD-YYYY HH:SS")
    @Column(name = "loadingtime",nullable = false)
    private Timestamp loadingTime;

    @Column(name = "loadingaddress", nullable = false)
    private String loadingAddress;

    @Column(name = "deliveryaddress", nullable = false)
    private String deliveryAddress;

    @Column(name = "isbooked",columnDefinition = "boolean default false")
    private boolean isBooked;

    @Column(name = "otherinformation", length = 200, nullable = false)
    private String otherInformation;

    @Column(name = "unit_id", nullable = false)
    private long unitId;

    @NotNull
    @ManyToOne
    private DtbUser shipper;

    @ManyToOne
    private DtbUser winner;

    @OneToMany(mappedBy = "transportRequest")
    private List<DtbBookDetail> detailBooks = new ArrayList<>();

    @NotNull
    @OneToMany(mappedBy = "transportRequest")
    private List<DtbShippingPoint> shippingPoints = new ArrayList<>();
}
