package tsutsu.examfinal.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TreasurerSituation {
    private String collectivityId;
    private double totalIncome;
    private double totalExpense;
    private double balance;

    public TreasurerSituation() {}

    public TreasurerSituation(String collectivityId,
                              double totalIncome,
                              double totalExpense) {
        this.collectivityId = collectivityId;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = totalIncome - totalExpense;
    }

    public double getBalance() {
        return totalIncome - totalExpense;
    }
}
