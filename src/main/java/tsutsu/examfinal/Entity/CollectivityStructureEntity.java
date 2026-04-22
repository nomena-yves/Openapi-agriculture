package tsutsu.examfinal.Entity;

import lombok.Builder;

@Builder
public class CollectivityStructureEntity {
    MembreEntity president;
    MembreEntity vice_president;
    MembreEntity tresurer;
    MembreEntity secretary;

    public CollectivityStructureEntity(MembreEntity president, MembreEntity vice_president, MembreEntity tresurer, MembreEntity secretary) {
        this.president = president;
        this.vice_president = vice_president;
        this.tresurer = tresurer;
        this.secretary = secretary;
    }

    public MembreEntity getPresident() {
        return president;
    }

    public void setPresident(MembreEntity president) {
        this.president = president;
    }

    public MembreEntity getVice_president() {
        return vice_president;
    }

    public void setVice_president(MembreEntity vice_president) {
        this.vice_president = vice_president;
    }

    public MembreEntity getTresurer() {
        return tresurer;
    }

    public void setTresurer(MembreEntity tresurer) {
        this.tresurer = tresurer;
    }

    public MembreEntity getSecretary() {
        return secretary;
    }

    public void setSecretary(MembreEntity secretary) {
        this.secretary = secretary;
    }
}
