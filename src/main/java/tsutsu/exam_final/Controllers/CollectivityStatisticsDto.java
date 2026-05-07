package tsutsu.exam_final.Controllers;

import lombok.*;

// CollectivityLocalStatistics selon YAML v0.0.7
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CollectivityStatisticsDto {
    private MemberDescription memberDescription;
    private double earnedAmount;
    private double unpaidAmount;
    private double assiduityPercentage; // Bonus 2 — selon YAML v0.0.7

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MemberDescription {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String occupation;
    }
}
