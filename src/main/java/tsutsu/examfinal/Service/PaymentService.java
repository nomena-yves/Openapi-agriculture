package tsutsu.examfinal.Service;

import tsutsu.examfinal.Entity.CollectivityTransaction;
import tsutsu.examfinal.Entity.MemberPayment;
import tsutsu.examfinal.Repository.CollectivityRepository;

public class PaymentService {

    private final CollectivityRepository repository;

    public PaymentService(CollectivityRepository repository) {
        this.repository = repository;
    }

    public MemberPayment createPayment(MemberPayment payment) throws Exception {


        CollectivityTransaction tx = new CollectivityTransaction(
                null,
                java.time.LocalDate.now(),
                payment.getAmount(),
                payment.getPaymentMode(),
                payment.getAccountCreditedId(),
                payment.getMemberId(),
                "collectivityId"
        );

        System.out.println("Transaction créée pour trésorerie");

        return payment;
    }
}
