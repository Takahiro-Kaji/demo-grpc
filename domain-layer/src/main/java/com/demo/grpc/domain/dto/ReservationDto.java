package com.demo.grpc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long reservationId;
    private String userId;
    private LocalDateTime reservedAt;
    private String status;
    private String note;
}
