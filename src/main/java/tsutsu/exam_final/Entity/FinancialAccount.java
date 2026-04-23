package tsutsu.exam_final.Entity;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialAccount {

    private String id;
    private Double amount;
    private String holderName;
    private MobileBankingService mobileBankingService;
    private Long mobileNumber;
    private Bank bankName;
    private Integer bankCode;
    private Integer bankBranchCode;
    private Long bankAccountNumber;
    private Integer bankAccountKey;
    private AccountType accountType;

    public enum AccountType {
        CASH,
        MOBILE_BANKING,
        BANK
    }
}
