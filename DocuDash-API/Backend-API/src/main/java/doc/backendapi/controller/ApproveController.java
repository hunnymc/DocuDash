package doc.backendapi.controller;

import doc.backendapi.DTO.approval.CreateVerifyManagerDto;
import doc.backendapi.DTO.approval.UserDocAndManagersDto;
import doc.backendapi.DTO.approval.VerifydocDto;
import doc.backendapi.DTO.approval.admin.AdminApproveDocDto;
import doc.backendapi.DTO.approval.manager.ManagerApproveDocDto;
import doc.backendapi.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/approve/")
public class ApproveController {

    @Autowired
    private ApprovalService approvalService;

//    @GetMapping("/doc")
//    public List<VerifydocDto> getAllApprovedDoc() {
//        return approvalService.getAllApprovedDoc();
//    }

    @GetMapping("/mg/{managerId}")
    public List<UserDocAndManagersDto> getApprovedDocByManagerID(@PathVariable int managerId) {
        return approvalService.getApprovedDocByManagerID(managerId);
    }

    @GetMapping("/info/{userId}")
    public List<UserDocAndManagersDto> getDocInfoAndManagersByUserId(@PathVariable int userId) {
        return approvalService.getDocInfoAndManagersByUserId(userId);
    }

    @GetMapping("/doc/{documentId}")
    public UserDocAndManagersDto getApprovedDocByDocID(@PathVariable int documentId) {
        return approvalService.getApprovedDocByDocID(documentId);
    }

    @GetMapping("/info/all")
    public List<UserDocAndManagersDto> getAllDocInfoAndManagers() {
        return approvalService.getAllDocInfoAndManagers();
    }

    @PostMapping("/")
    public void AddApproval(@RequestBody VerifydocDto verifydocDto) {
        throw new ResponseStatusException(HttpStatus.OK, "TESTING OK RESPONSE STATUS EXCEPTION");
    }

    @PostMapping("/admin/check-approve")
    public ResponseEntity approveDocByAdmin(@RequestBody AdminApproveDocDto AdminApproveDocDto) {
        return approvalService.approveDocByAdmin(AdminApproveDocDto);
    }

    @PostMapping("/mg/check-approve")
    public ResponseEntity approveDocByManager(@RequestBody ManagerApproveDocDto ManagerApproveDocDto) {
        return approvalService.approveDocByManager(ManagerApproveDocDto);
    }

    @PostMapping("/add-manager")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addManager(@RequestBody @Valid List<CreateVerifyManagerDto> newManagers) {
        return approvalService.addManager(newManagers);
    }
}