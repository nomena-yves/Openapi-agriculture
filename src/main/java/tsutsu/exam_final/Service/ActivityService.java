package tsutsu.exam_final.Service;

import org.springframework.stereotype.Service;
import tsutsu.exam_final.DTO.CreateActivityDTO;
import tsutsu.exam_final.DTO.CreateAttendanceDTO;
import tsutsu.exam_final.Entity.Activity;
import tsutsu.exam_final.Entity.Attendance;
import tsutsu.exam_final.Repository.ActivityRepository;
import tsutsu.exam_final.Repository.CollectivityRepository;
import tsutsu.exam_final.exception.BadRequestException;
import tsutsu.exam_final.exception.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final CollectivityRepository collectivityRepository;

    public ActivityService(ActivityRepository activityRepository,
                           CollectivityRepository collectivityRepository) {
        this.activityRepository = activityRepository;
        this.collectivityRepository = collectivityRepository;
    }

    // POST /collectivities/{id}/activities
    public List<Activity> createActivities(String collectivityId,
                                            List<CreateActivityDTO> dtos) {
        try {
            if (!collectivityRepository.existsById(collectivityId))
                throw new NotFoundException("Collectivity not found: " + collectivityId);

            List<Activity> result = new ArrayList<>();
            for (CreateActivityDTO dto : dtos) {
                Activity activity = Activity.builder()
                        .label(dto.getLabel())
                        .description(dto.getDescription())
                        .date(dto.getDate())
                        .type(dto.getType())
                        .mandatory(dto.getMandatory())
                        .targetOccupations(dto.getTargetOccupations())
                        .build();

                String id = activityRepository.save(collectivityId, activity);
                activity.setId(id);
                result.add(activity);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // GET /collectivities/{id}/activities
    public List<Activity> getActivities(String collectivityId) {
        try {
            if (!collectivityRepository.existsById(collectivityId))
                throw new NotFoundException("Collectivity not found: " + collectivityId);

            return activityRepository.findByCollectivityId(collectivityId);

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // POST /collectivities/{id}/activities/{activityId}/attendance
    public List<Attendance> recordAttendance(String collectivityId,
                                              String activityId,
                                              CreateAttendanceDTO dto) {
        try {
            if (!collectivityRepository.existsById(collectivityId))
                throw new NotFoundException("Collectivity not found: " + collectivityId);

            if (!activityRepository.existsById(activityId))
                throw new NotFoundException("Activity not found: " + activityId);

            List<Attendance> result = new ArrayList<>();
            for (CreateAttendanceDTO.AttendanceRecord record : dto.getRecords()) {
                // Une fois présence marquée, on ne peut plus modifier
                if (activityRepository.attendanceAlreadyRecorded(activityId, record.getMemberId())) {
                    throw new BadRequestException(
                            "Attendance already recorded for member: " + record.getMemberId() +
                            ". Cannot be modified.");
                }

                activityRepository.saveAttendance(
                        activityId,
                        record.getMemberId(),
                        record.isPresent(),
                        record.getExcuseReason()
                );

                result.add(Attendance.builder()
                        .memberId(record.getMemberId())
                        .present(record.isPresent())
                        .excuseReason(record.getExcuseReason())
                        .build());
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // GET /collectivities/{id}/activities/{activityId}/attendance
    public List<Attendance> getAttendance(String collectivityId, String activityId) {
        try {
            if (!collectivityRepository.existsById(collectivityId))
                throw new NotFoundException("Collectivity not found: " + collectivityId);

            if (!activityRepository.existsById(activityId))
                throw new NotFoundException("Activity not found: " + activityId);

            List<ActivityRepository.AttendanceRecord> records =
                    activityRepository.findAttendanceByActivity(activityId);

            List<Attendance> result = new ArrayList<>();
            for (ActivityRepository.AttendanceRecord r : records) {
                result.add(Attendance.builder()
                        .memberId(r.memberId())
                        .firstName(r.firstName())
                        .lastName(r.lastName())
                        .present(r.present())
                        .excuseReason(r.excuseReason())
                        .build());
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
