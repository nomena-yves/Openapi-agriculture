package tsutsu.examfinal.Service;

import org.springframework.stereotype.Service;
import tsutsu.examfinal.Entity.CollectivityEntity;

@Service
public class CollectivityService {
    public CollectivityEntity addCollectivity(CollectivityEntity collectivityEntity) {
        if (collectivityEntity == null) {
            throw new IllegalArgumentException("the parameter collectivityEntity is null");
        }else{

        }
    }
}
