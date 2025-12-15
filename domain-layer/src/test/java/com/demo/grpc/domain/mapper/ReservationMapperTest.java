package com.demo.grpc.domain.mapper;

import com.demo.grpc.domain.AbstractReadMyBatisTest;
import com.demo.grpc.domain.dto.ReservationDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@DisplayName("ReservationMapper テスト")
class ReservationMapperTest extends AbstractReadMyBatisTest {

    static {
        // Read専用DBにスキーマと固定seedデータを投入
        mysql.withInitScript("db/migration/V1__create_reservations_table.sql");
    }

    @Autowired
    private ReservationMapper reservationMapper;

    @Test
    @Tag("read")
    @DataSet(value = "datasets/reservation/basic.yml", cleanBefore = false)
    @DisplayName("selectAll: すべての予約を取得できる")
    void testSelectAll() {
        // When
        List<ReservationDto> result = reservationMapper.selectAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getReservationId()).isEqualTo(1L);
        assertThat(result.get(0).getUserId()).isEqualTo("user-001");
        assertThat(result.get(0).getStatus()).isEqualTo("CONFIRMED");
        assertThat(result.get(0).getNote()).isEqualTo("会議室A");
    }

    @Test
    @Tag("read")
    @DataSet(value = "datasets/reservation/basic.yml", cleanBefore = false)
    @DisplayName("selectAll: 予約はreservation_idでソートされている")
    void testSelectAll_Ordered() {
        // When
        List<ReservationDto> result = reservationMapper.selectAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(5);
        for (int i = 0; i < result.size() - 1; i++) {
            assertThat(result.get(i).getReservationId())
                    .isLessThan(result.get(i + 1).getReservationId());
        }
    }

    @Test
    @Tag("read")
    @DataSet(value = "datasets/reservation/basic.yml", cleanBefore = false)
    @DisplayName("selectByUserId: ユーザーIDでフィルタリングして予約を取得できる")
    void testSelectByUserId() {
        // When
        List<ReservationDto> result = reservationMapper.selectByUserId("user-001");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(r -> r.getUserId().equals("user-001"));
        assertThat(result.get(0).getReservationId()).isEqualTo(1L);
        assertThat(result.get(1).getReservationId()).isEqualTo(2L);
    }

    @Test
    @Tag("read")
    @DataSet(value = "datasets/reservation/basic.yml", cleanBefore = false)
    @DisplayName("selectByUserId: ユーザーIDでフィルタリングした結果もreservation_idでソートされている")
    void testSelectByUserId_Ordered() {
        // When
        List<ReservationDto> result = reservationMapper.selectByUserId("user-003");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReservationId()).isEqualTo(4L);
        assertThat(result.get(1).getReservationId()).isEqualTo(5L);
        assertThat(result.get(0).getReservationId())
                .isLessThan(result.get(1).getReservationId());
    }

    @Test
    @Tag("read")
    @DataSet(value = "datasets/reservation/basic.yml", cleanBefore = false)
    @DisplayName("selectByUserId: 存在しないユーザーIDの場合は空のリストを返す")
    void testSelectByUserId_NotFound() {
        // When
        List<ReservationDto> result = reservationMapper.selectByUserId("user-999");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @Tag("read")
    @DataSet(value = "datasets/reservation/basic.yml", cleanBefore = false)
    @DisplayName("selectById: 予約IDで単一の予約を取得できる")
    void testSelectById() {
        // When
        ReservationDto result = reservationMapper.selectById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReservationId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo("user-001");
        assertThat(result.getReservedAt()).isEqualTo(LocalDateTime.of(2025, 12, 15, 10, 0));
        assertThat(result.getStatus()).isEqualTo("CONFIRMED");
        assertThat(result.getNote()).isEqualTo("会議室A");
    }

    @Test
    @Tag("read")
    @DataSet(value = "datasets/reservation/basic.yml", cleanBefore = false)
    @DisplayName("selectById: 存在しない予約IDの場合はnullを返す")
    void testSelectById_NotFound() {
        // When
        ReservationDto result = reservationMapper.selectById(999L);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @Tag("read")
    @DataSet(value = "datasets/reservation/basic.yml", cleanBefore = false)
    @DisplayName("selectByUserId: 最小データセットでも正常に動作する")
    void testSelectByUserId_MinimalDataset() {
        // When
        List<ReservationDto> result = reservationMapper.selectByUserId("user-001");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(r -> r.getUserId().equals("user-001"));
    }
}
