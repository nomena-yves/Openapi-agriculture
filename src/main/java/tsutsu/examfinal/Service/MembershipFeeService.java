package tsutsu.examfinal.Service;

import org.springframework.stereotype.Service;
import tsutsu.examfinal.Entity.MembershipFee;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembershipFeeService {

    public List<MembershipFee> createFees(String collectivityId,
                                          List<MembershipFee> fees) {

        List<MembershipFee> result = new ArrayList<>();

        for (MembershipFee fee : fees) {

            if (fee.getAmount() < 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }

            fee.setStatus("ACTIVE");

            System.out.println("Cotisation créée pour collectivity "
                    + collectivityId + " : " + fee.getAmount());

            result.add(fee);
        }

        return result;
    }


    public List<MembershipFee> getFeesByCollectivity(String collectivityId) {


        List<MembershipFee> fees = new ArrayList<>();

        System.out.println("Récupération cotisations pour " + collectivityId);

        return fees;
    }
}
