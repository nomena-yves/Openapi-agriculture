package tsutsu.examfinal.Entity;

import java.time.LocalDate;

public class MembershipFee {
    private String id;
    private LocalDate eligibleFrom;
    private String frequency;
    private double amount;
    private String label;
    private String status;

    public MembershipFee() {}

    public MembershipFee(String id, LocalDate eligibleFrom, String frequency,
                         double amount, String label, String status) {
        this.id = id;
        this.eligibleFrom = eligibleFrom;
        this.frequency = frequency;
        this.amount = amount;
        this.label = label;
        this.status = status;
    }
}
