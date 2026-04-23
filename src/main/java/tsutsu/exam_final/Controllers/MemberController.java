package tsutsu.exam_final.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.exam_final.DTO.CreateMemberDTO;
import tsutsu.exam_final.Entity.MembreEntity;
import tsutsu.exam_final.Service.MemberService;

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
            @RequestBody @Valid List<CreateMemberDTO> dtos) {

        List<MembreEntity> created = memberService.createMembers(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
