package doc.backendapi.controller;

import doc.backendapi.DTO.approval.UserDocAndManagersDto;
import doc.backendapi.DTO.approval.VerifydocDto;
import doc.backendapi.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/approve/")
public class ApproveController {

    @Autowired
    private ApprovalService approvalService;

    @GetMapping("/doc")
    public List<VerifydocDto> getAllApprovedDoc() {
        return approvalService.getAllApprovedDoc();
    }

    @GetMapping("/doc/mg/{managerId}")
    public List<VerifydocDto> getApprovedDocByManagerID(@PathVariable int managerId) {
        return approvalService.getApprovedDocByManagerID(managerId);
    }

    @GetMapping("/info/{userId}")
    public List<UserDocAndManagersDto> getDocInfoAndManagersByUserId(@PathVariable int userId) {
        return approvalService.getDocInfoAndManagersByUserId(userId);
    }

    @PostMapping("/")
    public void AddApproval(@RequestBody VerifydocDto verifydocDto) {
        throw new ResponseStatusException(HttpStatus.OK, "TESTING OK RESPONSE STATUS EXCEPTION");
    }
}