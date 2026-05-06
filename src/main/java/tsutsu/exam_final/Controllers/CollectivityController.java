package tsutsu.exam_final.Controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.exam_final.DTO.AssignIdentityDto;
import tsutsu.exam_final.DTO.CreateCollectivityDTO;
import tsutsu.exam_final.DTO.CreateMemberShipFeeDto;
import tsutsu.exam_final.Entity.*;
import tsutsu.exam_final.Service.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService collectivityService;
    private final MemberShipFeeService membershipFeeService;
    private final TransactionService transactionService;
    private final FinancialAccountService financialAccountService;
    private final StatisticsService statisticsService;

    public CollectivityController(CollectivityService collectivityService,
                                  MemberShipFeeService membershipFeeService,
                                  TransactionService transactionService,
                                  FinancialAccountService financialAccountService,
                                  StatisticsService statisticsService) {
        this.collectivityService = collectivityService;
        this.membershipFeeService = membershipFeeService;
        this.transactionService = transactionService;
        this.financialAccountService = financialAccountService;
        this.statisticsService = statisticsService;
    }

    @PostMapping
    public ResponseEntity<List<CollectivityEntity>> createCollectivities(
            @RequestBody @Valid List<CreateCollectivityDTO> dtos) {
        List<CollectivityEntity> created = collectivityService.createCollectivities(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectivityEntity> getCollectivity(
            @PathVariable("id") String collectivityId) {
        CollectivityEntity collectivity = collectivityService.getById(collectivityId);
        return ResponseEntity.ok(collectivity);
    }

    // PUT /collectivities/{id}/informations
    @PutMapping("/{id}/informations")
    public ResponseEntity<CollectivityEntity> assignInformations(
            @PathVariable("id") String collectivityId,
            @RequestBody AssignIdentityDto dto) {
        CollectivityEntity updated = collectivityService.assignIdentity(collectivityId, dto);
        return ResponseEntity.ok(updated);
    }

    // GET /collectivities/{id}/financialAccounts?at=2026-01-01
    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<List<FinancialAccount>> getFinancialAccounts(
            @PathVariable("id") String collectivityId,
            @RequestParam(value = "at", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        List<FinancialAccount> accounts = financialAccountService.getAccounts(collectivityId, at);
        return ResponseEntity.ok(accounts);
    }

    // GET /collectivities/{id}/membershipFees
    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<List<MembershipFee>> getMembershipFees(
            @PathVariable("id") String collectivityId) {
        List<MembershipFee> fees = membershipFeeService.getByCollectivity(collectivityId);
        return ResponseEntity.ok(fees);
    }

    // POST /collectivities/{id}/membershipFees
    @PostMapping("/{id}/membershipFees")
    public ResponseEntity<List<MembershipFee>> createMembershipFees(
            @PathVariable("id") String collectivityId,
            @RequestBody List<CreateMemberShipFeeDto> dtos) {
        List<MembershipFee> created = membershipFeeService.create(collectivityId, dtos);
        return ResponseEntity.ok(created);
    }

    // GET /collectivities/{id}/transactions?from=...&to=...
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<CollectivityTransaction>> getTransactions(
            @PathVariable("id") String collectivityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<CollectivityTransaction> transactions =
                transactionService.getTransactions(collectivityId, from, to);
        return ResponseEntity.ok(transactions);
    }

    // GET /collectivities/{id}/statistics?from=...&to=...
    @GetMapping("/{id}/statistics")
    public ResponseEntity<CollectivityStatisticsDto> getCollectivityStatistics(
            @PathVariable("id") String collectivityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        CollectivityStatisticsDto stats =
                statisticsService.getCollectivityStatistics(collectivityId, from, to);
        return ResponseEntity.ok(stats);
    }

    // GET /collectivities/statistics?from=...&to=...
    @GetMapping("/statistics")
    public ResponseEntity<List<FederationStatisticsDto>> getFederationStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<FederationStatisticsDto> stats =
                statisticsService.getFederationStatistics(from, to);
        return ResponseEntity.ok(stats);
    }
}
