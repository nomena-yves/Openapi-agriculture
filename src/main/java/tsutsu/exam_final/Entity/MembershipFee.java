package tsutsu.exam_final.Entity;

import lombok.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipFee {

    private String id;
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private Double amount;
    private String label;
    private ActivityStatus status;
    private String collectivityId;
}
