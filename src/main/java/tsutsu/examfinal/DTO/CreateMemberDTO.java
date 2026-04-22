package tsutsu.examfinal.DTO;
import jakarta.validation.constraints.*;
import lombok.*;
import tsutsu.examfinal.Entity.GenderEntity;
import tsutsu.examfinal.Entity.MemberOccupationEntity;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Gender is required")
    private GenderEntity gender;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Profession is required")
    private String profession;

    @NotNull(message = "Phone number is required")
    private Long phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Occupation is required")
    private MemberOccupationEntity occupation;

    @NotBlank(message = "Collectivity identifier is required")
    private String collectivityIdentifier;

    @NotNull(message = "Referees list is required")
    @Size(min = 2, message = "At least 2 referees are required for new members")
    private List<String> referees;

    @NotNull(message = "registrationFeePaid is required")
    private Boolean registrationFeePaid;

    @NotNull(message = "membershipDuesPaid is required")
    private Boolean membershipDuesPaid;
}

