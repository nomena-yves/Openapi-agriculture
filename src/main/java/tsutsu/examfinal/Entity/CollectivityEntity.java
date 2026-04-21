package tsutsu.examfinal.Entity;

import java.util.List;

public class CollectivityEntity {
    private String id;
    private String location;
    private CollectivityStructureEntity structure;
    private List<MembreEntity> membre;

    public CollectivityEntity(String id, String location, CollectivityStructureEntity structure, List<MembreEntity> membre) {
        this.id = id;
        this.location = location;
        this.structure = structure;
        this.membre = membre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CollectivityStructureEntity getStructure() {
        return structure;
    }

    public void setStructure(CollectivityStructureEntity structure) {
        this.structure = structure;
    }

    public List<MembreEntity> getMembre() {
        return membre;
    }

    public void setMembre(List<MembreEntity> membre) {
        this.membre = membre;
    }
}
