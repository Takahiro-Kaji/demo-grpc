package com.demo.grpc.domain.service;

import com.demo.grpc.domain.dto.ReservationDto;
import com.demo.grpc.domain.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    public List<ReservationDto> findAll(String userId) {
        if (userId != null && !userId.isEmpty()) {
            return reservationMapper.selectByUserId(userId);
        }
        return reservationMapper.selectAll();
    }

    public ReservationDto findById(long reservationId) {
        ReservationDto reservation = reservationMapper.selectById(reservationId);
        if (reservation == null) {
            throw new ResponseStatusException(NOT_FOUND, "Reservation not found");
        }
        return reservation;
    }
}

