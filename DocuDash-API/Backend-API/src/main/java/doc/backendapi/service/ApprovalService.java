package doc.backendapi.service;

import doc.backendapi.DTO.approval.CreateVerifyManagerDto;
import doc.backendapi.DTO.approval.UserDocAndManagersDto;
import doc.backendapi.DTO.approval.UserVerifydocDto;
import doc.backendapi.DTO.approval.VerifydocDto;
import doc.backendapi.DTO.approval.admin.AdminApproveDocDto;
import doc.backendapi.DTO.approval.manager.ManagerApproveDocDto;
import doc.backendapi.entities.*;
import doc.backendapi.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApprovalService {

    @Autowired
    private VerifydocRepository verifydocRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentstatusRepository documentStatusRepository;
    @Autowired
    private DocumentApproveTypeRepository documentApproveTypeRepository;
    @Autowired
    private VerifyadmindocRepository verifyadmindocRepository;
    @Autowired
    private VerifystatustypeRepository verifystatustypeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<UserDocAndManagersDto> getApprovedDocByManagerID(int id) {

        // check if the user exists and throw an exception if not
        User manager = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // check the user is manager or not
        if (!manager.getRole().equals("MANAGER")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user is not a manager");
        }

        // get all the documents that the manager has not approved or rejected
        List<Verifydoc> unverifiedDocs = verifydocRepository.findByManagerID_IdAndIsPassNotAndIsPassNot(id, 1, 0);

        // return UserDocAndManagersDto list if the manager has verified any document using getAllApproveDoc method
        return getAllApproveDoc(unverifiedDocs.stream().map(Verifydoc::getDocumentID).collect(Collectors.toList()));
    }

    public List<VerifydocDto> getAllApprovedDoc() {
        List<Verifydoc> verifydocs = verifydocRepository.findAll();
        return verifydocs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private VerifydocDto convertToDto(Verifydoc verifydoc) {
        VerifydocDto verifydocDto = modelMapper.map(verifydoc, VerifydocDto.class);

        verifydocDto.setDocumentID(modelMapper.map(verifydoc.getDocumentID(), VerifydocDto.DocumentDto1.class));
        verifydocDto.setManagerID(modelMapper.map(verifydoc.getManagerID(), VerifydocDto.UserDto1.class));

        return verifydocDto;
    }

    public List<UserDocAndManagersDto> getDocInfoAndManagersByUserId(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Document> documents = documentRepository.findByUserID(user);
        List<UserDocAndManagersDto> userDocAndManagersDtos = getAllApproveDoc(documents);

        if (userDocAndManagersDtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not have any documents that have a manager to verify");
        }

        return userDocAndManagersDtos;
    }

    private VerifydocDto convertToDto2(Verifydoc verifydoc) {
        VerifydocDto verifydocDto = new VerifydocDto();
        verifydocDto.setId(verifydoc.getId());
        verifydocDto.setIsPass(verifydoc.getIsPass());
        verifydocDto.setComment(verifydoc.getComment());

        // Set only the necessary fields for the manager
        VerifydocDto.UserDto1 managerDto = new VerifydocDto.UserDto1();
        managerDto.setId(verifydoc.getManagerID().getId());
        managerDto.setUsername(verifydoc.getManagerID().getUsername());
        managerDto.setFullName(verifydoc.getManagerID().getFullName());
        managerDto.setRole(verifydoc.getManagerID().getRole());
        managerDto.setEmail(verifydoc.getManagerID().getEmail());
        managerDto.setPhone(verifydoc.getManagerID().getPhone());
        managerDto.setBranch(verifydoc.getManagerID().getBranch());
        verifydocDto.setManagerID(managerDto);

        return verifydocDto;
    }

    public ResponseEntity addManager(List<CreateVerifyManagerDto> userDocAndManagersDtos) {
        for (CreateVerifyManagerDto userDocAndManagersDto : userDocAndManagersDtos) {
            User manager = userRepository.findById(userDocAndManagersDto.getManagerID()).orElseThrow(() -> new RuntimeException("Manager not found"));
            Document document = documentRepository.findById(userDocAndManagersDto.getDocumentID()).orElseThrow(() -> new RuntimeException("Document not found"));

            // check if the user is not an admin
            if (userRepository.existsByIdAndRoleNot(document.getUsersUserid().getId(), "ADMIN")) {
                Verifydoc verifydoc = new Verifydoc();
                verifydoc.setDocumentID(document);
                verifydoc.setManagerID(manager);
                verifydoc.setIsPass(-1);
                verifydocRepository.save(verifydoc);

                if (verifyadmindocRepository.findByDocumentID_Id(document.getId()).isEmpty()){
                    Verifyadmindoc verifyadmindoc = new Verifyadmindoc();
                    verifyadmindoc.setDocumentID(document);
                    verifyadmindoc.setIsPass(-1);
                    verifyadmindocRepository.save(verifyadmindoc);
                }

                if (documentStatusRepository.getStatusByDocumentId(document.getId()) == null) {
                    Documentstatus documentstatus = new Documentstatus();
                    documentstatus.setDocumentID(document);
                    Verifystatustype status = verifystatustypeRepository.findById(2).orElseThrow(() -> new RuntimeException("Status not found")); // replace 1 with the actual status ID
                    documentstatus.setStatusID(status);
                    DocumentApproveType documentApproveType = documentApproveTypeRepository.findById(userDocAndManagersDto.getDocumentApproveTypeID()).orElseThrow(() -> new RuntimeException("DocumentApproveType not found"));
                    documentstatus.setDocumentApproveTypeID(documentApproveType);

                    documentStatusRepository.save(documentstatus);

                }

            // in case the document has been added by the admin
            } else {
                Verifydoc verifydoc = new Verifydoc();
                verifydoc.setDocumentID(document);
                verifydoc.setManagerID(manager);
                verifydoc.setIsPass(-1);
                verifydocRepository.save(verifydoc);

                if (verifyadmindocRepository.findByDocumentID_Id(document.getId()).isEmpty()){
                    Verifyadmindoc verifyadmindoc = new Verifyadmindoc();
                    verifyadmindoc.setDocumentID(document);
                    verifyadmindoc.setIsPass(1);
                    verifyadmindocRepository.save(verifyadmindoc);
                }

                if (documentStatusRepository.getStatusByDocumentId(document.getId()) == null) {
                    Documentstatus documentstatus = new Documentstatus();
                    documentstatus.setDocumentID(document);
                    Verifystatustype status = verifystatustypeRepository.findById(3).orElseThrow(() -> new RuntimeException("Status not found")); // replace 1 with the actual status ID
                    documentstatus.setStatusID(status);
                    DocumentApproveType documentApproveType = documentApproveTypeRepository.findById(userDocAndManagersDto.getDocumentApproveTypeID()).orElseThrow(() -> new RuntimeException("DocumentApproveType not found"));
                    documentstatus.setDocumentApproveTypeID(documentApproveType);

                    documentStatusRepository.save(documentstatus);

                }
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("The operation was successful and the manager has been added.");

    }

    public List<UserDocAndManagersDto> getAllDocInfoAndManagers() {
        List<Document> documents = documentRepository.findAll();
        List<UserDocAndManagersDto> userDocAndManagersDtos = getAllApproveDoc(documents);

        if (userDocAndManagersDtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no documents that have a manager to verify");
        }

        return userDocAndManagersDtos;
    }

    private List<UserDocAndManagersDto> getAllApproveDoc(List<Document> documents) {

        // Fetch all Verifydoc and group by documentID
        Map<Integer, List<Verifydoc>> verifyDocsMap = verifydocRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(vd -> vd.getDocumentID().getId()));

        // Fetch all Documentstatus and group by documentID
        Map<Integer, Documentstatus> documentStatusMap = documentStatusRepository.findAll()
                .stream()
                .collect(Collectors.toMap(ds -> ds.getDocumentID().getId(), ds -> ds));

        return documents.stream()
                .sorted(Comparator.comparing(Document::getDateUpdate).reversed())
                .map(document -> {
                    UserVerifydocDto documentInfo = modelMapper.map(document, UserVerifydocDto.class);

                    List<Verifydoc> verifydocs = verifyDocsMap.getOrDefault(document.getId(), Collections.emptyList());
                    List<VerifydocDto> managersWhoVerified = verifydocs.stream()
                            .map(this::convertToDto2)
                            .collect(Collectors.toList());

                    Documentstatus documentstatus = documentStatusMap.get(document.getId());
                    int status_type_id = documentstatus != null ? documentstatus.getStatusID().getId() : 0;
                    documentInfo.setStatus(status_type_id);

                    int totalManagers = managersWhoVerified.size();

                    int totalManagersWhoVerified = (int) managersWhoVerified.stream().filter(vd -> vd.getIsPass() == 1).count();

                    int approve_type_Id = documentstatus != null ? documentstatus.getDocumentApproveTypeID().getId() : 0;

                    return new UserDocAndManagersDto(status_type_id, totalManagers, totalManagersWhoVerified, approve_type_Id, managersWhoVerified, documentInfo);
                })
                .filter(dto -> !dto.getManagersWhoVerified().isEmpty())
                .toList();
    }

    public ResponseEntity approveDocByAdmin(AdminApproveDocDto adminApproveDocDto) {
        Document document = documentRepository.findById(adminApproveDocDto.getDocument_id()).orElseThrow(() -> new RuntimeException("Document not found"));
        Documentstatus documentstatus = documentStatusRepository.findByDocumentID(document);

        if (documentstatus == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document status not found");
        }

        Verifystatustype verifystatustype = verifystatustypeRepository.findById(3).orElseThrow(() -> new RuntimeException("Status not found"));

        if (adminApproveDocDto.getIs_pass() == 1 && documentstatus.getStatusID().getId() == 2) {

            verifyadmindocRepository.updateIsPassByDocumentID(1, document);

            documentstatus.setStatusID(verifystatustype);
            documentStatusRepository.save(documentstatus);
            throw new ResponseStatusException(HttpStatus.OK, "The operation was successful and the document has been approved.");
        } else if (adminApproveDocDto.getIs_pass() == 0 && documentstatus.getStatusID().getId() == 2) {
            documentstatus.setStatusID(verifystatustypeRepository.findById(5).orElseThrow(() -> new RuntimeException("Status not found")));
            documentStatusRepository.save(documentstatus);
            throw new ResponseStatusException(HttpStatus.OK, "The operation was successful and the document has been rejected.");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The document has already been approved or rejected");
        }
    }

    public ResponseEntity approveDocByManager(ManagerApproveDocDto managerApproveDocDto) {
        Document document = documentRepository.findById(managerApproveDocDto.getDocument_id()).orElseThrow(() -> new RuntimeException("Document not found"));
        Documentstatus documentstatus = documentStatusRepository.findByDocumentID(document);

        if (documentstatus == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document status not found");
        }

        if (documentstatus.getStatusID().getId() != 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The document has already been approved or rejected");
        }

        Verifydoc verifydoc = verifydocRepository.findByDocumentID_IdAndManagerID_Id(document.getId(), managerApproveDocDto.getManager_id()).orElseThrow(() -> new RuntimeException("Document not found"));

        if (verifydoc.getIsPass() != -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The document has already been approved or rejected");
        }

        if (managerApproveDocDto.getIs_pass() == 1) {
            verifydoc.setIsPass(1);
            verifydocRepository.save(verifydoc);

            // if all managers approve the document, change the status to approved
            List<Verifydoc> verifydocs = verifydocRepository.findByDocumentID(document);
            int totalManagers = verifydocs.size();
            int totalManagersWhoVerified = (int) verifydocs.stream().filter(vd -> vd.getIsPass() >= 0).count();

            if (totalManagers == totalManagersWhoVerified) {
                Verifystatustype verifystatustype = verifystatustypeRepository.findById(4).orElseThrow(() -> new RuntimeException("Status not found"));
                documentstatus.setStatusID(verifystatustype);
                documentStatusRepository.save(documentstatus);
                throw new ResponseStatusException(HttpStatus.OK, "The operation was successful and the document has been approved.");
            }

        } else if (managerApproveDocDto.getIs_pass() == 0) {
            verifydoc.setIsPass(0);
            verifydocRepository.save(verifydoc);
            documentstatus.setStatusID(verifystatustypeRepository.findById(5).orElseThrow(() -> new RuntimeException("Status not found")));
            documentStatusRepository.save(documentstatus);
            throw new ResponseStatusException(HttpStatus.OK, "The operation was successful and the document has been rejected.");

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid value for is_pass");
        }

        return ResponseEntity.status(HttpStatus.OK).body("The operation was successful and the document has been approved.");

    }

    public UserDocAndManagersDto getApprovedDocByDocID(int documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
        return getAllApproveDoc(Collections.singletonList(document)).get(0);
    }
}