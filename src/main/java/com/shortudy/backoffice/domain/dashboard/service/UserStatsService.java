package com.shortudy.backoffice.domain.dashboard.service;

import com.shortudy.backoffice.domain.dashboard.dto.projection.DailyUserCount;
import com.shortudy.backoffice.domain.dashboard.dto.response.UserStatsResponse;
import com.shortudy.backoffice.domain.dashboard.dto.response.UserStatsResponse.DailyCount;
import com.shortudy.backoffice.domain.user.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserStatsService {

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    private final UserRepository userRepository;

    public UserStatsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserStatsResponse getUserStats(int days) {

        LocalDate toDate = LocalDate.now(KOREA_ZONE);
        LocalDate fromDate = toDate.minusDays(days - 1L);

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.plusDays(1L).atStartOfDay();

        long totalUsers = userRepository.count();
        List<DailyUserCount> counts = userRepository.countDailyUsers(fromDateTime, toDateTime);

        Map<LocalDate, Long> countByDate = new HashMap<>();
        for (DailyUserCount count : counts) {
            countByDate.put(count.getDate(), count.getCount());
        }

        List<DailyCount> daily = new ArrayList<>(days);
        long newUsersInRange = 0L;
        for (int i = 0; i < days; i++) {
            LocalDate current = fromDate.plusDays(i);
            long count = countByDate.getOrDefault(current, 0L);
            newUsersInRange += count;
            daily.add(new DailyCount(current, count));
        }

        return new UserStatsResponse(
                days,
                fromDate,
                toDate,
                totalUsers,
                newUsersInRange,
                daily
        );
    }


}
