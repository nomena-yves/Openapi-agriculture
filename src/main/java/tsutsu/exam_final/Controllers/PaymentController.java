package tsutsu.exam_final.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.exam_final.DTO.CreateMemberPayementDto;
import tsutsu.exam_final.Entity.MemberPayment;
import tsutsu.exam_final.Service.MembrePaymentService;

import java.util.List;

@RestController
@RequestMapping("/members/{id}/payments")
public class PaymentController {

    private final MembrePaymentService memberPaymentService;

    public PaymentController(MembrePaymentService memberPaymentService) {
        this.memberPaymentService = memberPaymentService;
    }
    @PostMapping
    public ResponseEntity<List<MemberPayment>> createPayments(
            @PathVariable("id") String memberId,
            @RequestBody List<CreateMemberPayementDto> dtos) {

        List<MemberPayment> created = memberPaymentService.createPayments(memberId, dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
