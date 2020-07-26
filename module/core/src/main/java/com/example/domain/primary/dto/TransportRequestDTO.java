package com.example.domain.primary.dto;

import com.example.domain.primary.entity.DtbShippingPoint;
import com.example.domain.primary.entity.DtbTransportRequest;
import com.example.domain.primary.repository.TransportRespository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransportRequestDTO {

    public long id;

    @NotNull
    public long goodId;

    @NotNull
    public long unitId;

    @NotNull
    public Double price;

    @NotNull
    public Double expectedFee;

    @NotNull
    @JsonFormat(pattern = "MM-DD-YYYY hh:ss")
    public Timestamp validUntil;

    @NotNull
    @JsonFormat(pattern = "MM-DD-YYYY hh:ss")
    public Timestamp dischargingTime;

    @NotNull
    @JsonFormat(pattern = "MM-DD-YYYY hh:ss")
    public Timestamp loadingTime;

    @NotNull
    public String deliveryAddress;

    @NotNull
    public String loadingAddress;

    @NotNull
    public String otherInformation;

    @NotNull
    public List<ShippingPointDTO> shippingPointDTOs;

    public long shipperId;

    public TransportRequestDTO toDTO(DtbTransportRequest dtbTransportRequest) {
        TransportRequestDTO transportRequestDTO = new TransportRequestDTO();

        transportRequestDTO.goodId = dtbTransportRequest.getGoodId();
        transportRequestDTO.unitId = dtbTransportRequest.getUnitId();
        transportRequestDTO.price = dtbTransportRequest.getPrice();
        transportRequestDTO.expectedFee = dtbTransportRequest.getExpectedFee();
        transportRequestDTO.validUntil = dtbTransportRequest.getValidUntil();
        transportRequestDTO.dischargingTime = dtbTransportRequest.getDischargingTime();
        transportRequestDTO.loadingTime = dtbTransportRequest.getLoadingTime();
        transportRequestDTO.loadingAddress = dtbTransportRequest.getLoadingAddress();
        transportRequestDTO.deliveryAddress = dtbTransportRequest.getDeliveryAddress();
        transportRequestDTO.otherInformation = dtbTransportRequest.getOtherInformation();
        List<ShippingPointDTO> shippingPointDTOS = new ArrayList<>();
        for (DtbShippingPoint dtbShippingPoint: dtbTransportRequest.getShippingPoints()) {
            ShippingPointDTO shippingPointDTO = new ShippingPointDTO();
            shippingPointDTOS.add(shippingPointDTO.toDTO(dtbShippingPoint));
        }
        transportRequestDTO.shippingPointDTOs = shippingPointDTOS;
        transportRequestDTO.shipperId = dtbTransportRequest.getShipper().getId();
        return transportRequestDTO;
    }

    public DtbTransportRequest toEntity(TransportRequestDTO transportRequestDTO,DtbTransportRequest dtbTransportRequest) {
        dtbTransportRequest.setGoodId(transportRequestDTO.goodId);
        dtbTransportRequest.setUnitId(transportRequestDTO.unitId);
        dtbTransportRequest.setPrice(transportRequestDTO.price);
        dtbTransportRequest.setExpectedFee(transportRequestDTO.expectedFee);
        dtbTransportRequest.setValidUntil(transportRequestDTO.validUntil);
        dtbTransportRequest.setDischargingTime(transportRequestDTO.dischargingTime);
        dtbTransportRequest.setLoadingTime(transportRequestDTO.loadingTime);
        dtbTransportRequest.setLoadingAddress(transportRequestDTO.loadingAddress);
        dtbTransportRequest.setDeliveryAddress(transportRequestDTO.deliveryAddress);
        dtbTransportRequest.setOtherInformation(transportRequestDTO.otherInformation);

        List<DtbShippingPoint> shippingPoints = new ArrayList<>();
        for (ShippingPointDTO shippingPointDTO: transportRequestDTO.shippingPointDTOs) {
            shippingPoints.add(shippingPointDTO.toEntity(shippingPointDTO, new DtbShippingPoint()));
        }

        dtbTransportRequest.setShippingPoints(shippingPoints);
        return dtbTransportRequest;
    }

}
