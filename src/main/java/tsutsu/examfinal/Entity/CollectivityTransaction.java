package tsutsu.examfinal.Entity;

import java.time.LocalDate;

public class CollectivityTransaction {

    private String id;
    private LocalDate creationDate;
    private double amount;
    private String paymentMode;

    private String accountCreditedId;
    private String memberDebitedId;
    private String collectivityId;


    public CollectivityTransaction() {}

    public CollectivityTransaction(String id, LocalDate creationDate,
                                   double amount, String paymentMode,
                                   String accountCreditedId,
                                   String memberDebitedId,
                                   String collectivityId) {
        this.id = id;
        this.creationDate = creationDate;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.accountCreditedId = accountCreditedId;
        this.memberDebitedId = memberDebitedId;
        this.collectivityId = collectivityId;
    }
}
