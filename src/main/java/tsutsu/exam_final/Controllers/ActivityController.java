package tsutsu.exam_final.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.exam_final.DTO.CreateActivityDTO;
import tsutsu.exam_final.DTO.CreateAttendanceDTO;
import tsutsu.exam_final.Entity.Activity;
import tsutsu.exam_final.Entity.Attendance;
import tsutsu.exam_final.Service.ActivityService;

import java.util.List;

@RestController
@RequestMapping("/collectivities/{id}/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // POST /collectivities/{id}/activities
    @PostMapping
    public ResponseEntity<List<Activity>> createActivities(
            @PathVariable("id") String collectivityId,
            @RequestBody @Valid List<CreateActivityDTO> dtos) {
        List<Activity> created = activityService.createActivities(collectivityId, dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /collectivities/{id}/activities
    @GetMapping
    public ResponseEntity<List<Activity>> getActivities(
            @PathVariable("id") String collectivityId) {
        List<Activity> activities = activityService.getActivities(collectivityId);
        return ResponseEntity.ok(activities);
    }

    // POST /collectivities/{id}/activities/{activityId}/attendance
    @PostMapping("/{activityId}/attendance")
    public ResponseEntity<List<Attendance>> recordAttendance(
            @PathVariable("id") String collectivityId,
            @PathVariable("activityId") String activityId,
            @RequestBody CreateAttendanceDTO dto) {
        List<Attendance> attendance =
                activityService.recordAttendance(collectivityId, activityId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
    }

    // GET /collectivities/{id}/activities/{activityId}/attendance
    @GetMapping("/{activityId}/attendance")
    public ResponseEntity<List<Attendance>> getAttendance(
            @PathVariable("id") String collectivityId,
            @PathVariable("activityId") String activityId) {
        List<Attendance> attendance =
                activityService.getAttendance(collectivityId, activityId);
        return ResponseEntity.ok(attendance);
    }
}
