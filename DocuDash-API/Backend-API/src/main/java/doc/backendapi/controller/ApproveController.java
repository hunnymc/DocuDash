package doc.backendapi.controller;

import doc.backendapi.DTO.AlertSideBarDto;
import doc.backendapi.DTO.approval.*;
import doc.backendapi.DTO.approval.admin.AdminApproveDocDto;
import doc.backendapi.DTO.approval.manager.ManagerApproveDocDto;
import doc.backendapi.DTO.graph.PieGraphInfoDto;
import doc.backendapi.DTO.graph.WaveGraphInfoDto;
import doc.backendapi.hadlers.CustomHttpException;
import doc.backendapi.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/approve/")
public class ApproveController {

    @Autowired
    private ApprovalService approvalService;

    @GetMapping("/mg/{managerId}")
    public List<ManagerVerifyListDto> getApprovedDocByManagerID(@PathVariable int managerId) {
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

    @GetMapping("/info/admin")
    public List<AdminVerifyListDto> getAdminDocInfoAndManagers() {
        return approvalService.getAdminDocInfoAndManagers();
    }

//    @PostMapping("/")
//    public void AddApproval(@RequestBody VerifydocDto verifydocDto) {
//        throw new CustomHttpException(HttpStatus.OK, "TESTING OK RESPONSE STATUS EXCEPTION");
//    }

    @PostMapping("/admin/check-approve")
    public CustomHttpException approveDocByAdmin(@RequestBody AdminApproveDocDto AdminApproveDocDto) {
        return approvalService.approveDocByAdmin(AdminApproveDocDto);
    }

    @PostMapping("/mg/check-approve")
    public CustomHttpException approveDocByManager(@RequestBody ManagerApproveDocDto ManagerApproveDocDto) {
        return approvalService.approveDocByManager(ManagerApproveDocDto);
    }

    @PostMapping("/add-manager")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomHttpException addManager(@RequestBody @Valid List<CreateVerifyManagerDto> newManagers) {
        return approvalService.addManager(newManagers);
    }

    @PostMapping("/sent-doc")
    public CustomHttpException sentDocToUserEdoc(@RequestParam int documentID) {
        return approvalService.sentDocToUserEdoc(documentID);
    }

    // ---------------  Graphical Data Retrieval ----------------

    @GetMapping("/graph/wave")
    public WaveGraphInfoDto getWaveGraph() {
        return approvalService.getWaveGraph();
    }

    @GetMapping("/graph/pie")
    public PieGraphInfoDto getPieGraph() {
        return approvalService.getPieGraph();
    }

    // ---------------  Process to Mark Document as Read ----------------

    @PostMapping("/read/admin/{documentId}")
    public CustomHttpException readAdminVerifyDoc(@PathVariable int documentId) {
        return approvalService.readAdminVerifyDoc(documentId);
    }

    @PostMapping("/read/user/{documentId}")
    public CustomHttpException readUserVerifyDoc(@PathVariable int documentId) {
        return approvalService.readUserVerifyDoc(documentId);
    }

    @PostMapping("/read/manager/{managerId}/{documentId}")
    public CustomHttpException readManagerVerifyDoc(@PathVariable int managerId, @PathVariable int documentId) {
        return approvalService.readManagerVerifyDoc(managerId, documentId);
    }

    // ---------------  Notifier ----------------

    @GetMapping("/sidebar/alert/{userId}")
    public AlertSideBarDto getNewUserVerifyDoc(@PathVariable int userId) {
        return approvalService.getNewStatusForAll(userId);
    }

}