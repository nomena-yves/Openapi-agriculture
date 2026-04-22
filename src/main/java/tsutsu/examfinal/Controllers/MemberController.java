package tsutsu.examfinal.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tsutsu.examfinal.DTO.CreateMemberDTO;
import tsutsu.examfinal.Entity.MembreEntity;
import tsutsu.examfinal.Service.MemberService;


import java.util.List;
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping
    public ResponseEntity<List<MembreEntity>> createMembers(
            @RequestBody @Valid List<CreateMemberDTO> DTOS) {

        List<MembreEntity> created = memberService.createMembers(DTOS);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
