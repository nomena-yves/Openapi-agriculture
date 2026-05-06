package tsutsu.exam_final.Controllers;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CollectivityStatisticsDto {
    private MemberDescription memberDescription;
    private double earnedAmount;
    private double unpaidAmount;
    private double attendanceRate; // Bonus 2 — taux d'assiduité individuel en %

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MemberDescription {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String occupation;
    }
}
