package tsutsu.exam_final.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tsutsu.exam_final.Entity.Activity;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateActivityDTO {

    @NotBlank(message = "Label is required")
    private String label;

    private String description;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Type is required")
    private Activity.ActivityType type;

    @NotNull(message = "Mandatory flag is required")
    private Boolean mandatory;

    // null = tous les membres, sinon liste des occupations ciblées
    private List<String> targetOccupations;
}
