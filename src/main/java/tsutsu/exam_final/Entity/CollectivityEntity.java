package tsutsu.exam_final.Entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectivityEntity {

    private String id;
    private String number;
    private String name;
    private String location;
    private CollectivityStructureEntity structure;
    private List<MembreEntity> members;
}
