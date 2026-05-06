package tsutsu.exam_final.Entity;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Attendance {
    private String memberId;
    private String firstName;
    private String lastName;
    private boolean present;
    private String excuseReason; // motif d'absence si excusé
}
