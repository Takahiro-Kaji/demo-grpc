package com.demo.grpc.domain.controller;

import com.demo.grpc.domain.dto.ReservationDto;
import com.demo.grpc.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getReservations(
            @RequestParam(required = false) String userId) {
        List<ReservationDto> reservations = reservationService.findAll(userId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable long reservationId) {
        ReservationDto reservation = reservationService.findById(reservationId);
        return ResponseEntity.ok(reservation);
    }
}

