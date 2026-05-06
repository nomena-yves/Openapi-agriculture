package tsutsu.exam_final.DTO;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateAttendanceDTO {
    private List<AttendanceRecord> records;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class AttendanceRecord {
        private String memberId;
        private boolean present;
        private String excuseReason;
    }
}
