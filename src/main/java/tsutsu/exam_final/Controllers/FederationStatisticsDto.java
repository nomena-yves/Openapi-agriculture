package tsutsu.exam_final.Controllers;

import lombok.*;

// CollectivityOverallStatistics selon YAML v0.0.7
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FederationStatisticsDto {
    private CollectivityInformation collectivityInformation;
    private int newMembersNumber;
    private double overallMemberCurrentDuePercentage;
    private double overallMemberAssiduityPercentage; // Bonus 2 — selon YAML v0.0.7

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CollectivityInformation {
        private String name;
        private Integer number;
    }
}
