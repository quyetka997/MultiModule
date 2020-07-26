package com.example.application.service.impl;

import com.example.application.service.ITransportRequestService;
import com.example.application.service.IUserService;
import com.example.domain.primary.dto.ShippingPointDTO;
import com.example.domain.primary.dto.TransportRequestDTO;
import com.example.domain.primary.entity.DtbShippingPoint;
import com.example.domain.primary.entity.DtbTransportRequest;
import com.example.domain.primary.entity.DtbUser;
import com.example.domain.primary.repository.ShippingPointRepository;
import com.example.domain.primary.repository.TransportRespository;
import com.example.domain.primary.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TransportRequestService implements ITransportRequestService {

    @Autowired
    TransportRespository transportRespository;

    @Autowired
    UserRespository userRespository;

    @Autowired
    ShippingPointRepository shippingPointRepository;

    @Override
    public TransportRequestDTO insert(TransportRequestDTO transportRequestDTO) {
        DtbTransportRequest dtbTransportRequest = transportRequestDTO.toEntity(transportRequestDTO, new DtbTransportRequest());
        DtbUser dtbShipper = userRespository.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        dtbTransportRequest.setShipper(dtbShipper);

        dtbTransportRequest = transportRespository.save(dtbTransportRequest);

        for(ShippingPointDTO shippingPointDTO:transportRequestDTO.shippingPointDTOs) {
            DtbShippingPoint shippingPoint = shippingPointDTO.toEntity(shippingPointDTO,new DtbShippingPoint());
            shippingPoint.setTransportRequest(dtbTransportRequest);
            shippingPointRepository.save(shippingPoint);
        }
        return transportRequestDTO.toDTO(dtbTransportRequest);
    }

    @Autowired
    EntityManager em;

    @Override
    public List<TransportRequestDTO> getAllPostRequest(Long goodId, String loadingAddress, String deliveryAddress) {

        String sql = "SELECT * FROM dtb_transport_request WHERE dtb_transport_request.id IS NOT NULL";
        if(goodId != null) {
            sql += " AND good_id ="+goodId;
        }
        if(loadingAddress != null) {
            sql += " AND loadingaddress LIKE ''%"+loadingAddress+"%'";
        }
        if(deliveryAddress != null) {
            sql += " AND deliveryaddress LIKE '%"+deliveryAddress+"%'";
        }
        Query query = em.createNativeQuery(sql,DtbTransportRequest.class);
        return toListDTO(query.getResultList());
    }

    @Override
    public List<TransportRequestDTO> getPostedRequest() {
        DtbUser dtbShipper = userRespository.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        return toListDTO(dtbShipper.getPostedTransportRequests());
    }

    @Override
    public List<TransportRequestDTO> getReceivedRequest(Long id) {
        DtbUser dtbUser = userRespository.findById(id).get();
        return toListDTO(dtbUser.getReceivedTransportRequests());
    }

    private List<TransportRequestDTO> toListDTO(List<DtbTransportRequest> dtbTransportRequests) {
        List<TransportRequestDTO> transportRequestDTOs = new ArrayList<>();
        if(dtbTransportRequests != null) {
            for(DtbTransportRequest dtbTransportRequest: dtbTransportRequests) {
                TransportRequestDTO transportRequestDTO = new TransportRequestDTO();
                transportRequestDTOs.add(transportRequestDTO.toDTO(dtbTransportRequest));
            }
        }
        return transportRequestDTOs;
    }

    @Override
    public void remove(long id) {
        transportRespository.deleteById(id);
    }
}
