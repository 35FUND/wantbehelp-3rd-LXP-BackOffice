package com.shortudy.backoffice.domain.dashboard.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public record UserStatsResponse (
    int days,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate from,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate to,

    long totalUsers,          // 전체 가입자 수(현재)
    long newUsersInRange,     // from~to 신규 가입자 합(= daily 합)

    List<DailyCount> daily
) {
    public record DailyCount(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        long count
    ) {}
}
