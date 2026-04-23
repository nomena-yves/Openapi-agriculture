package tsutsu.exam_final.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.exam_final.DTO.CreateMemberPaymentDto;
import tsutsu.exam_final.Entity.MemberPayment;
import tsutsu.exam_final.Service.MemberPaymentService;

import java.util.List;

@RestController
@RequestMapping("/members/{id}/payments")
public class PaymentController {

    private final MemberPaymentService memberPaymentService;

    public PaymentController(MemberPaymentService memberPaymentService) {
        this.memberPaymentService = memberPaymentService;
    }
    @PostMapping
    public ResponseEntity<List<MemberPayment>> createPayments(
            @PathVariable("id") String memberId,
            @RequestBody List<CreateMemberPaymentDto> dtos) {

        List<MemberPayment> created = memberPaymentService.createPayments(memberId, dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
