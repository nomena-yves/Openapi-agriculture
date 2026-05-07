package tsutsu.exam_final.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tsutsu.exam_final.Entity.Activity;
import tsutsu.exam_final.Entity.MemberOccupationEntity;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateActivityDTO {

    @NotBlank(message = "Label is required")
    private String label;

    @NotNull(message = "Activity type is required")
    private Activity.ActivityType activityType;

    // Occupations concernées — null = tous les membres
    private List<MemberOccupationEntity> memberOccupationConcerned;

    // Soit recurrenceRule soit executiveDate — pas les deux en même temps
    private Activity.MonthlyRecurrenceRule recurrenceRule;
    private LocalDate executiveDate;
}
