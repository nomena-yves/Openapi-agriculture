package tsutsu.examfinal.Controllers;

import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.examfinal.DTO.CreateCollectivityDTO;
import tsutsu.examfinal.Entity.CollectivityEntity;
import tsutsu.examfinal.Service.CollectivityService;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService collectivityService;

    public CollectivityController(CollectivityService collectivityService) {
        this.collectivityService = collectivityService;
    }
    @PostMapping
    public ResponseEntity<List<CollectivityEntity>> createCollectivities(
            @RequestBody @Valid List<CreateCollectivityDTO> dtos) {

        List<CollectivityEntity> created = collectivityService.createCollectivities(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}

