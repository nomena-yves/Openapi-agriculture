package tsutsu.examfinal.Service;

import tsutsu.examfinal.Entity.MembershipFee;

public class MembershipFeeService {
    public MembershipFee createFee(MembershipFee fee) {

        fee.setStatus("ACTIVE");

        System.out.println("Cotisation créée : " + fee.getAmount());

        return fee;
    }
}
