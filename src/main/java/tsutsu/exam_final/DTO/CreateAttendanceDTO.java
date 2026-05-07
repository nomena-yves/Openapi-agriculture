package tsutsu.exam_final.DTO;

import lombok.*;
import tsutsu.exam_final.Entity.AttendanceStatus;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateAttendanceDTO {
    private String memberIdentifier;
    private AttendanceStatus attendanceStatus;
}
