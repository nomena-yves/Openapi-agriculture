package tsutsu.exam_final.Entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembreEntity {
    private String id;

    private String firstName;
    private String lastName;
    private GenderEntity gender;
    private String address;
    private String profession;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private MemberOccupationEntity occupation;
    private LocalDate membershipDate;
    private CollectivityEntity collectivity;
    private List<MembreEntity> referees;


}
