package tsutsu.exam_final.Controllers;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FederationStatisticsDto {
    private String collectivityId;
    private String collectivityName;
    private int totalMembers;
    private int newMembers;             // nouveaux adhérents sur la période
    private double upToDatePercentage;  // % membres à jour dans leurs cotisations
}
