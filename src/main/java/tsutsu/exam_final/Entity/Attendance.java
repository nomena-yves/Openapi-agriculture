package tsutsu.exam_final.Entity;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Attendance {
    private String id;
    private MemberDescription memberDescription;
    private AttendanceStatus attendanceStatus;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MemberDescription {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String occupation;
    }
}
