package tsutsu.examfinal.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectivityStructureDTO {

    @NotBlank(message = "President ID is required")
    private String president;

    @NotBlank(message = "Vice-president ID is required")
    private String vicePresident;

    @NotBlank(message = "Treasurer ID is required")
    private String treasurer;

    @NotBlank(message = "Secretary ID is required")
    private String secretary;
}