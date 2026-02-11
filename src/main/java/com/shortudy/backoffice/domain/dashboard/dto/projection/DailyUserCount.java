package com.shortudy.backoffice.domain.dashboard.dto.projection;

import java.time.LocalDate;

public interface DailyUserCount {
    LocalDate getDate();
    long getCount();
}
