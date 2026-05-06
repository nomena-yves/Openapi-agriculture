package tsutsu.exam_final.Entity;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Activity {
    private String id;
    private String label;
    private String description;
    private LocalDate date;
    private ActivityType type;
    private boolean mandatory;
    private List<String> targetOccupations; // null = tous les membres

    public enum ActivityType {
        MONTHLY_GENERAL_ASSEMBLY,
        JUNIOR_TRAINING,
        EXCEPTIONAL
    }
}
