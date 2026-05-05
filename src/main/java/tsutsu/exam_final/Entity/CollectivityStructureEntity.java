package tsutsu.exam_final.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CollectivityStructureEntity {
    MembreEntity president;
    MembreEntity vicePresident;
    MembreEntity treasurer;
    MembreEntity secretary;

    public CollectivityStructureEntity(MembreEntity president, MembreEntity vicePresident, MembreEntity treasurer, MembreEntity secretary) {
        this.president = president;
        this.vicePresident = vicePresident;
        this.treasurer = treasurer;
        this.secretary = secretary;
    }
}
