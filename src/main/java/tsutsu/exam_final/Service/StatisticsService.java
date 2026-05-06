package tsutsu.exam_final.Service;

import org.springframework.stereotype.Service;
import tsutsu.exam_final.Controllers.CollectivityStatisticsDto;
import tsutsu.exam_final.Controllers.FederationStatisticsDto;
import tsutsu.exam_final.Repository.StatisticsRepository;
import tsutsu.exam_final.exception.NotFoundException;
import tsutsu.exam_final.Repository.CollectivityRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final CollectivityRepository collectivityRepository;

    public StatisticsService(StatisticsRepository statisticsRepository,
                              CollectivityRepository collectivityRepository) {
        this.statisticsRepository = statisticsRepository;
        this.collectivityRepository = collectivityRepository;
    }

    // GET /collectivities/{id}/statistics
    public CollectivityStatisticsDto getCollectivityStatistics(String collectivityId,
                                                                LocalDate from,
                                                                LocalDate to) {
        try {
            if (!collectivityRepository.existsById(collectivityId)) {
                throw new NotFoundException("Collectivity not found: " + collectivityId);
            }

            double expectedAmount =
                    statisticsRepository.getExpectedAmountForPeriod(collectivityId, from, to);

            List<StatisticsRepository.MemberPaymentStat> paymentStats =
                    statisticsRepository.getMemberPaymentStats(collectivityId, from, to);

            List<CollectivityStatisticsDto.MemberStatDto> memberStats = new ArrayList<>();
            for (StatisticsRepository.MemberPaymentStat stat : paymentStats) {
                double unpaid = Math.max(0, expectedAmount - stat.totalPaid());
                memberStats.add(CollectivityStatisticsDto.MemberStatDto.builder()
                        .memberId(stat.memberId())
                        .firstName(stat.firstName())
                        .lastName(stat.lastName())
                        .totalPaid(stat.totalPaid())
                        .totalUnpaid(unpaid)
                        .build());
            }

            return CollectivityStatisticsDto.builder()
                    .collectivityId(collectivityId)
                    .memberStats(memberStats)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // GET /collectivities/statistics
    public List<FederationStatisticsDto> getFederationStatistics(LocalDate from, LocalDate to) {
        try {
            List<StatisticsRepository.CollectivityFedStat> fedStats =
                    statisticsRepository.getFederationStats(from, to);

            List<FederationStatisticsDto> result = new ArrayList<>();
            for (StatisticsRepository.CollectivityFedStat stat : fedStats) {
                int upToDate = statisticsRepository.countMembersUpToDate(
                        stat.collectivityId(), from, to);

                double upToDatePercentage = stat.totalMembers() == 0
                        ? 0.0
                        : (double) upToDate / stat.totalMembers() * 100;

                result.add(FederationStatisticsDto.builder()
                        .collectivityId(stat.collectivityId())
                        .collectivityName(stat.collectivityName())
                        .totalMembers(stat.totalMembers())
                        .newMembers(stat.newMembers())
                        .upToDatePercentage(Math.round(upToDatePercentage * 100.0) / 100.0)
                        .build());
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
