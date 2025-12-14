package com.demo.grpc.domain.mapper;

import com.demo.grpc.domain.dto.ReservationDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {

    @Select("""
            SELECT reservation_id, user_id, reserved_at, status, note 
            FROM reservations 
            ORDER BY reservation_id
            """)
    List<ReservationDto> selectAll();

    @Select("""
            SELECT reservation_id, user_id, reserved_at, status, note 
            FROM reservations 
            WHERE user_id = #{userId} 
            ORDER BY reservation_id
            """)
    List<ReservationDto> selectByUserId(@Param("userId") String userId);

    @Select("""
            SELECT reservation_id, user_id, reserved_at, status, note 
            FROM reservations 
            WHERE reservation_id = #{reservationId}
            """)
    ReservationDto selectById(@Param("reservationId") long reservationId);
}
