package tsutsu.exam_final.Entity;

import lombok.*;
import java.time.LocalDate;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectivityTransaction {

    private String id;
    private LocalDate creationDate;
    private Double amount;
    private PaymentMode paymentMode;
    private FinancialAccount accountCredited;
    private MembreEntity memberDebited;
}
