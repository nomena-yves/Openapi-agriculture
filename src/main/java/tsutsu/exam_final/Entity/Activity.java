package tsutsu.exam_final.Entity;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Activity {
    private String id;
    private String label;
    private ActivityType activityType;
    private List<MemberOccupationEntity> memberOccupationConcerned;
    private MonthlyRecurrenceRule recurrenceRule;
    private LocalDate executiveDate;

    public enum ActivityType {
        MEETING,
        TRAINING,
        OTHER
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MonthlyRecurrenceRule {
        private Integer weekOrdinal; // 1-5
        private String dayOfWeek;    // MO, TU, WE, TH, FR, SA, SU
    }
}
