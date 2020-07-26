package com.example.domain.primary.dto;

import com.example.domain.primary.entity.DtbShippingPoint;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class ShippingPointDTO {

    public Double latitude;

    public Double longitude;

    public String deliveryAddress;

    @NotNull
    @JsonFormat(pattern = "MM-DD-YYYY HH:SS")
    public Timestamp dischargingTime;

    public ShippingPointDTO toDTO(DtbShippingPoint dtbShippingPoint) {
        ShippingPointDTO shippingPointDTO = new ShippingPointDTO();
        shippingPointDTO.deliveryAddress = dtbShippingPoint.getDeliveryAddress();
        shippingPointDTO.dischargingTime = dtbShippingPoint.getDischargingTime();
        shippingPointDTO.latitude = dtbShippingPoint.getLatitude();
        shippingPointDTO.longitude =dtbShippingPoint.getLongitude();
        return shippingPointDTO;
    }

    public  DtbShippingPoint toEntity(ShippingPointDTO shippingPointDTO,DtbShippingPoint dtbShippingPoint){
        dtbShippingPoint.setDeliveryAddress(shippingPointDTO.deliveryAddress);
        dtbShippingPoint.setDischargingTime(shippingPointDTO.dischargingTime) ;
        dtbShippingPoint.setLatitude(shippingPointDTO.latitude);
        dtbShippingPoint.setLongitude(shippingPointDTO.longitude);
        return dtbShippingPoint;
    }


}
