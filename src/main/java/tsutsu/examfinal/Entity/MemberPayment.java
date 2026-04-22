package tsutsu.examfinal.Entity;

import java.time.LocalDate;

public class MemberPayment {
    private String id;
    private int amount;
    private String paymentMode; // CASH / MOBILE_BANKING / BANK_TRANSFER
    private String accountCreditedId;
    private LocalDate creationDate;

    private String membershipFeeId;
    private String memberId;

    public MemberPayment() {}

    public MemberPayment(String id, int amount, String paymentMode,
                         String accountCreditedId, LocalDate creationDate,
                         String membershipFeeId, String memberId) {
        this.id = id;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.accountCreditedId = accountCreditedId;
        this.creationDate = creationDate;
        this.membershipFeeId = membershipFeeId;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getMembershipFeeId() {
        return membershipFeeId;
    }

    public void setMembershipFeeId(String membershipFeeId) {
        this.membershipFeeId = membershipFeeId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
