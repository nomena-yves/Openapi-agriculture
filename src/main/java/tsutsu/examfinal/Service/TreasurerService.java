package tsutsu.examfinal.Service;

import tsutsu.examfinal.DTO.TreasurerSituation;

public class TreasurerService {
    public TreasurerSituation getSituation(String collectivityId) {

        double totalIncome = 50000;
        double totalExpense = 20000;

        return new TreasurerSituation(
                collectivityId,
                totalIncome,
                totalExpense
        );
    }
}
