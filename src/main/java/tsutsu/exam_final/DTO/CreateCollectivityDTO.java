package tsutsu.exam_final.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateCollectivityDTO {

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotEmpty(message = "At least 10 members are required")
    private List<String> members;

    @NotNull(message = "Federation approval is required")
    private Boolean federationApproval;

    @NotNull(message = "Structure is required")
    @Valid
    private CreateCollectivityStructureDTO structure;
}
