package com.demo.grpc.domain.mapper;

import com.demo.grpc.domain.dto.ReservationDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {

    @Results(id = "reservationResultMap", value = {
            @Result(property = "reservationId", column = "reservation_id", id = true),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "reservedAt", column = "reserved_at"),
            @Result(property = "status", column = "status"),
            @Result(property = "note", column = "note")
    })
    @Select("SELECT reservation_id, user_id, reserved_at, status, note FROM reservations ORDER BY reservation_id")
    List<ReservationDto> selectAll();

    @ResultMap("reservationResultMap")
    @Select("SELECT reservation_id, user_id, reserved_at, status, note FROM reservations WHERE user_id = #{userId} ORDER BY reservation_id")
    List<ReservationDto> selectByUserId(@Param("userId") String userId);

    @ResultMap("reservationResultMap")
    @Select("SELECT reservation_id, user_id, reserved_at, status, note FROM reservations WHERE reservation_id = #{reservationId}")
    ReservationDto selectById(@Param("reservationId") long reservationId);
}
