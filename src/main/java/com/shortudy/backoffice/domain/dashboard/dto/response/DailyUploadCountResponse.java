package com.shortudy.backoffice.domain.dashboard.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 일 단위 업로드 수 응답 DTO
 */
@Getter
@NoArgsConstructor
@Builder
public class DailyUploadCountResponse {
    // 집계 기준 일자
    private LocalDate date;
    // 해당 일자의 게시 업로드 건수
    private long uploadCount;

    /**
     * JPQL constructor projection 전용 생성자
     * function('date', ...) 반환 타입 편차를 흡수한다.
     */
    public DailyUploadCountResponse(Object dateValue, long uploadCount) {
        this.date = toLocalDate(dateValue);
        this.uploadCount = uploadCount;
    }

    private LocalDate toLocalDate(Object value) {
        if (value instanceof LocalDate localDate) {
            return localDate;
        }
        if (value instanceof Date sqlDate) {
            return sqlDate.toLocalDate();
        }
        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime.toLocalDate();
        }
        return LocalDate.parse(value.toString());
    }
}
