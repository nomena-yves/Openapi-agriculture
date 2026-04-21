package tsutsu.examfinal.Entity;

import java.util.List;

public class MembreEntity {
    private String id;

    private String firstName;
    private String lastName;
    private GenderEntity gender;
    private String adress;
    private String profession;
    private String email;
    private String phoneNumber;
    private MemberOccupationEntity Occupation;
    private CollectivityEntity collectivity;
    private List<MembreEntity> referees;

    public MembreEntity(String id, String firstName, String lastName, GenderEntity gender, String adress, String profession, String email, String phoneNumber, MemberOccupationEntity occupation, CollectivityEntity collectivity, List<MembreEntity> referees) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.adress = adress;
        this.profession = profession;
        this.email = email;
        this.phoneNumber = phoneNumber;
        Occupation = occupation;
        this.collectivity = collectivity;
        this.referees = referees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderEntity getGender() {
        return gender;
    }

    public void setGender(GenderEntity gender) {
        this.gender = gender;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MemberOccupationEntity getOccupation() {
        return Occupation;
    }

    public void setOccupation(MemberOccupationEntity occupation) {
        Occupation = occupation;
    }

    public CollectivityEntity getCollectivity() {
        return collectivity;
    }

    public void setCollectivity(CollectivityEntity collectivity) {
        this.collectivity = collectivity;
    }

    public List<MembreEntity> getReferees() {
        return referees;
    }

    public void setReferees(List<MembreEntity> referees) {
        this.referees = referees;
    }
}
