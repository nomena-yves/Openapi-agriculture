package tsutsu.exam_final.Controllers;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectivityStatisticsDto {
    private String collectivityId;
    private List<MemberStatDto> memberStats;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemberStatDto {
        private String memberId;
        private String firstName;
        private String lastName;
        private double totalPaid;       // montant encaissé sur la période
        private double totalUnpaid;     // montant impayé potentiel sur la période
    }
}
