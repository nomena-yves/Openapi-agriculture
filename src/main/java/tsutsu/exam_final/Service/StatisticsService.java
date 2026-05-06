package tsutsu.exam_final.Service;

import org.springframework.stereotype.Service;
import tsutsu.exam_final.Controllers.CollectivityStatisticsDto;
import tsutsu.exam_final.Controllers.FederationStatisticsDto;
import tsutsu.exam_final.Repository.ActivityRepository;
import tsutsu.exam_final.Repository.CollectivityRepository;
import tsutsu.exam_final.Repository.StatisticsRepository;
import tsutsu.exam_final.exception.NotFoundException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final CollectivityRepository collectivityRepository;
    private final ActivityRepository activityRepository;

    public StatisticsService(StatisticsRepository statisticsRepository,
                              CollectivityRepository collectivityRepository,
                              ActivityRepository activityRepository) {
        this.statisticsRepository = statisticsRepository;
        this.collectivityRepository = collectivityRepository;
        this.activityRepository = activityRepository;
    }

    // GET /collectivites/{id}/statistics
    // Retourne pour chaque membre : earnedAmount + unpaidAmount + attendanceRate (Bonus 2)
    public List<CollectivityStatisticsDto> getCollectivityStatistics(String collectivityId,
                                                                      LocalDate from,
                                                                      LocalDate to) {
        try {
            if (!collectivityRepository.existsById(collectivityId))
                throw new NotFoundException("Collectivity not found: " + collectivityId);

            double expectedAmount =
                    statisticsRepository.getExpectedAmountForPeriod(collectivityId, from, to);

            List<StatisticsRepository.MemberPaymentStat> paymentStats =
                    statisticsRepository.getMemberPaymentStats(collectivityId, from, to);

            List<CollectivityStatisticsDto> result = new ArrayList<>();
            for (StatisticsRepository.MemberPaymentStat stat : paymentStats) {
                double unpaid = Math.max(0, expectedAmount - stat.totalPaid());

                // Bonus 2 — taux d'assiduité individuel
                double attendanceRate = activityRepository.getAttendanceRate(
                        stat.memberId(), collectivityId, from, to);

                CollectivityStatisticsDto.MemberDescription desc =
                        CollectivityStatisticsDto.MemberDescription.builder()
                                .id(stat.memberId())
                                .firstName(stat.firstName())
                                .lastName(stat.lastName())
                                .email(stat.email())
                                .occupation(stat.occupation())
                                .build();

                result.add(CollectivityStatisticsDto.builder()
                        .memberDescription(desc)
                        .earnedAmount(stat.totalPaid())
                        .unpaidAmount(unpaid)
                        .attendanceRate(attendanceRate)
                        .build());
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // GET /collectivites/statistics
    // Retourne pour chaque collectivité : upToDatePercentage + newMembers + attendanceRate (Bonus 2)
    public List<FederationStatisticsDto> getFederationStatistics(LocalDate from, LocalDate to) {
        try {
            List<StatisticsRepository.CollectivityFedStat> fedStats =
                    statisticsRepository.getFederationStats(from, to);

            List<FederationStatisticsDto> result = new ArrayList<>();
            for (StatisticsRepository.CollectivityFedStat stat : fedStats) {
                int upToDate = statisticsRepository.countMembersUpToDate(
                        stat.collectivityId(), from, to);

                double percentage = stat.totalMembers() == 0 ? 0.0
                        : Math.round((double) upToDate / stat.totalMembers() * 10000.0) / 100.0;

                // Bonus 2 — taux d'assiduité global de la collectivité
                double attendanceRate = activityRepository.getCollectivityAttendanceRate(
                        stat.collectivityId(), from, to);

                FederationStatisticsDto.CollectivityInformation info =
                        FederationStatisticsDto.CollectivityInformation.builder()
                                .name(stat.collectivityName())
                                .number(stat.collectivityNumber())
                                .build();

                result.add(FederationStatisticsDto.builder()
                        .collectivityInformation(info)
                        .newMembersNumber(stat.newMembers())
                        .overallMemberCurrentDuePercentage(percentage)
                        .overallAttendanceRate(attendanceRate)
                        .build());
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
