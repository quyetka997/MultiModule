package com.example.application.controller;


import com.example.application.service.ITransportRequestService;
import com.example.application.service.IUserService;
import com.example.domain.primary.dto.BookDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrier")
public class CarrierController {

    @Autowired
    ITransportRequestService transportRequest;

    @Autowired
    IUserService userService;

    @GetMapping("/transportrequest")
    ResponseEntity<?> getAllPost(@RequestParam(required = false) Long goodId,
                                 @RequestParam(required = false) String loadingAddress,
                                 @RequestParam(required = false) String deliveryAddress) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transportRequest.getAllPostRequest(goodId, loadingAddress, deliveryAddress));
    }

    @PostMapping("/transportrequest/book")
    ResponseEntity<?> bookTransportRequest(@RequestBody BookDetailDTO bookDetailDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.bookTransportRequest(bookDetailDTO));
    }

    @GetMapping("/transportrequest/deny/{id}")
    ResponseEntity<?> denyTransportRequest(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.denyTransportRequest(id));
    }
}
