package tsutsu.exam_final.DTO;

public class AssignIdentityDto {

    private Integer number;
    private String name;

    public AssignIdentityDto() {}

    public AssignIdentityDto(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
