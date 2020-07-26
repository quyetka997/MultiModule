package com.example.domain.primary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table
public class DtbShippingPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private DtbTransportRequest transportRequest;

    private Double latitude;

    private Double longitude;

    @Column(name = "deliveryaddress", length = 100, nullable = false)
    private String deliveryAddress;

    @NotNull
    @JsonFormat(pattern = "MM-DD-YYYY HH:SS")
    private Timestamp dischargingTime;
}
