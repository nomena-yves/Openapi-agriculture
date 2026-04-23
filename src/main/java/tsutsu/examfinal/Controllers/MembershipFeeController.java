package tsutsu.examfinal.Controllers;

import org.springframework.web.bind.annotation.*;
import tsutsu.examfinal.Entity.MembershipFee;
import tsutsu.examfinal.Service.MembershipFeeService;

import java.util.List;

@RestController
@RequestMapping("/collectivities/{id}/membershipFees")
public class MembershipFeeController {
    private final MembershipFeeService service;


    public MembershipFeeController(MembershipFeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<MembershipFee> getFees(@PathVariable String id) {
        return service.getFeesByCollectivity(id);
    }

    @PostMapping
    public List<MembershipFee> createFees(@PathVariable String id,
                                          @RequestBody List<MembershipFee> fees) {
        return service.createFees(id, fees);
    }
}
