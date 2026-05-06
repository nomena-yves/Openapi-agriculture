package tsutsu.exam_final.Controllers;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FederationStatisticsDto {
    private CollectivityInformation collectivityInformation;
    private int newMembersNumber;
    private double overallMemberCurrentDuePercentage;
    private double overallAttendanceRate; // Bonus 2 — taux d'assiduité global en %

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CollectivityInformation {
        private String name;
        private Integer number;
    }
}
