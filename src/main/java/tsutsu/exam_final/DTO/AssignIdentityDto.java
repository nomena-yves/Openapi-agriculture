package tsutsu.exam_final.DTO;

public class AssignIdentityDto {

    private String number;
    private String name;

    public AssignIdentityDto() {}

    public AssignIdentityDto(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
