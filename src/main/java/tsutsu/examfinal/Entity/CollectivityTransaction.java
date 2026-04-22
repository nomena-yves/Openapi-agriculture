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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAccountCreditedId() {
        return accountCreditedId;
    }

    public void setAccountCreditedId(String accountCreditedId) {
        this.accountCreditedId = accountCreditedId;
    }

    public String getMemberDebitedId() {
        return memberDebitedId;
    }

    public void setMemberDebitedId(String memberDebitedId) {
        this.memberDebitedId = memberDebitedId;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }
}
