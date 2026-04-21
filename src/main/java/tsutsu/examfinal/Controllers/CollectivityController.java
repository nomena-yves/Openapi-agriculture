package tsutsu.examfinal.Controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tsutsu.examfinal.Entity.CollectivityEntity;

@RestController
public class CollectivityController {
    @PostMapping ("/collectivity")
    public RequestEntity<?> createCollectivity(@RequestBody CollectivityEntity collectivityEntity) {


    }
}
