package tsutsu.exam_final.Service;

import org.springframework.stereotype.Service;
import tsutsu.exam_final.DTO.CreateActivityDTO;
import tsutsu.exam_final.DTO.CreateAttendanceDTO;
import tsutsu.exam_final.Entity.Activity;
import tsutsu.exam_final.Entity.Attendance;
import tsutsu.exam_final.Entity.AttendanceStatus;
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
                // Validation : recurrenceRule ET executiveDate ne peuvent pas être tous les deux fournis
                if (dto.getRecurrenceRule() != null && dto.getExecutiveDate() != null) {
                    throw new BadRequestException(
                            "Cannot provide both recurrenceRule and executiveDate at the same time.");
                }
                if (dto.getRecurrenceRule() == null && dto.getExecutiveDate() == null) {
                    throw new BadRequestException(
                            "Either recurrenceRule or executiveDate must be provided.");
                }

                Activity activity = Activity.builder()
                        .label(dto.getLabel())
                        .activityType(dto.getActivityType())
                        .memberOccupationConcerned(dto.getMemberOccupationConcerned())
                        .recurrenceRule(dto.getRecurrenceRule())
                        .executiveDate(dto.getExecutiveDate())
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
    // dto est maintenant une liste de CreateAttendanceDTO
    public List<Attendance> recordAttendance(String collectivityId,
                                              String activityId,
                                              List<CreateAttendanceDTO> dtos) {
        try {
            if (!collectivityRepository.existsById(collectivityId))
                throw new NotFoundException("Collectivity not found: " + collectivityId);
            if (!activityRepository.existsById(activityId))
                throw new NotFoundException("Activity not found: " + activityId);

            // Vérifier d'abord que personne n'a déjà une présence confirmée
            for (CreateAttendanceDTO dto : dtos) {
                if (activityRepository.attendanceAlreadyConfirmed(
                        activityId, dto.getMemberIdentifier())) {
                    throw new BadRequestException(
                            "Attendance already confirmed for member: " +
                            dto.getMemberIdentifier() + ". Cannot be modified.");
                }
            }

            List<Attendance> result = new ArrayList<>();
            for (CreateAttendanceDTO dto : dtos) {
                activityRepository.saveAttendance(
                        activityId,
                        dto.getMemberIdentifier(),
                        dto.getAttendanceStatus()
                );

                result.add(Attendance.builder()
                        .memberDescription(Attendance.MemberDescription.builder()
                                .id(dto.getMemberIdentifier())
                                .build())
                        .attendanceStatus(dto.getAttendanceStatus())
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
                        .id(r.id())
                        .memberDescription(Attendance.MemberDescription.builder()
                                .id(r.memberId())
                                .firstName(r.firstName())
                                .lastName(r.lastName())
                                .email(r.email())
                                .occupation(r.occupation())
                                .build())
                        .attendanceStatus(r.status())
                        .build());
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
