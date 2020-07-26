package com.example.application.controller;

import com.example.application.service.ITransportRequestService;
import com.example.domain.primary.dto.TransportRequestDTO;
import com.example.domain.primary.entity.DtbTransportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/shipper")
public class ShipperController {

    @Autowired
    ITransportRequestService transportRequest;

    @PostMapping("/postrequest")
    ResponseEntity<?> postRequest(@Valid @RequestBody TransportRequestDTO transportRequestDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transportRequest.insert(transportRequestDTO));
    }

    @GetMapping("/transportrequest/posted")
    ResponseEntity<?> getPosted() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transportRequest.getPostedRequest());
    }

    @GetMapping("/transportrequest/all")
    ResponseEntity<?> getAllPost(@RequestParam(required = false) Long goodId,
                                 @RequestParam(required = false) String loadingAddress,
                                 @RequestParam(required = false) String deliveryAddress) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transportRequest.getAllPostRequest(goodId, loadingAddress, deliveryAddress));
    }

    @GetMapping("/transportrequest/received/{id}")
    ResponseEntity<?> getReiceved(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transportRequest.getReceivedRequest(id));
    }

}
