package doc.backendapi.service;

import doc.backendapi.DTO.AlertSideBarDto;
import doc.backendapi.DTO.approval.*;
import doc.backendapi.DTO.approval.admin.AdminApproveDocDto;
import doc.backendapi.DTO.approval.manager.ManagerApproveDocDto;
import doc.backendapi.DTO.approval.rejectInfo.rejectInfoDto;
import doc.backendapi.DTO.graph.PieGraphInfoDto;
import doc.backendapi.DTO.graph.WaveGraphInfoDto;
import doc.backendapi.entities.*;
import doc.backendapi.hadlers.CustomHttpException;
import doc.backendapi.notification.NotificationService;
import doc.backendapi.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApprovalService {

    @Autowired
    private VerifydocRepository verifydocRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserdocumentRepository userdocumentRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private NotificationService notificationService;
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

//    public List<UserDocAndManagersDto> getApprovedDocByManagerID(int id) {
//
//        // check if the user exists and throw an exception if not
//        User manager = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // check the user is manager or not
//        if (!manager.getRole().equals("MANAGER")) {
//            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The user is not a manager");
//        }
//
//        // get all the documents that the manager has not approved or rejected
//        List<Verifydoc> unverifiedDocs = verifydocRepository.findByManagerID_IdAndIsPassNotAndIsPassNot(id, 1, 0);
//
//        // return UserDocAndManagersDto list if the manager has verified any document using getAllApproveDoc method
//        return getAllApproveDoc(unverifiedDocs.stream().map(Verifydoc::getDocumentID).collect(Collectors.toList()));
//    }

    public List<ManagerVerifyListDto> getApprovedDocByManagerID(int managerId) {
        // check if the user exists and throw an exception if not
        User manager = userRepository.findById(managerId).orElseThrow(() -> new RuntimeException("User not found"));

        // check the user is manager or not
        if (!manager.getRole().equals("MANAGER")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The user is not a manager");
        }

        // get all the documents that the manager has not approved or rejected
        List<Verifydoc> unverifiedDocs = verifydocRepository.findByManagerID_IdAndIsPassNotAndIsPassNot(managerId, 1, 0);

        // Fetch all documents and their corresponding Verifydoc entities in one go
        Map<Integer, List<Verifydoc>> verifyDocsMap = unverifiedDocs.stream()
                .collect(Collectors.groupingBy(vd -> vd.getDocumentID().getId()));

        // return UserDocAndManagersDto list if the manager has verified any document using getAllApproveDoc method
        List<ManagerVerifyListDto> managerVerifyListDtos = getAllApproveDoc(unverifiedDocs.stream().map(Verifydoc::getDocumentID).collect(Collectors.toList()))
                .stream()
                .map(userDocAndManagersDto -> modelMapper.map(userDocAndManagersDto, ManagerVerifyListDto.class))
                .peek(managerVerifyListDto -> {
                    List<Verifydoc> verifydocs = verifyDocsMap.get(managerVerifyListDto.getDocumentInfo().getId());
                    for (Verifydoc vd : verifydocs) {
                        if (vd.getManagerID().getId() == managerId) {
                            managerVerifyListDto.setIsManagerRead(vd.getIsRead());
                        }
                    }
                })
                .collect(Collectors.toList());

        if (managerVerifyListDtos.isEmpty()) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND, "There are no documents that have a manager to verify");
        }

        return managerVerifyListDtos;
    }

    // get all approve document for admin
    public List<AdminVerifyListDto> getAdminDocInfoAndManagers() {
        List<Document> documents = documentRepository.findAll();
        List<UserDocAndManagersDto> userDocAndManagersDtos = getAllApproveDoc(documents);

        // filter userDocAndManagersDtos that status is 2
        userDocAndManagersDtos = userDocAndManagersDtos.stream()
                .filter(userDocAndManagersDto -> userDocAndManagersDto.getStatus_type_id() == 2)
                .toList();

        // transfer the data from UserDocAndManagersDto to AdminVerifyListDto using modelMapper
        List<AdminVerifyListDto> adminVerifyListDto = userDocAndManagersDtos.stream()
                .map(userDocAndManagersDto -> modelMapper.map(userDocAndManagersDto, AdminVerifyListDto.class))
                .toList();

        if (adminVerifyListDto.isEmpty()) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND, "There are no documents that have a manager to verify");
        }

        // add isRead field to the UserDocAndManagersDto
        for (AdminVerifyListDto adminVerifyListDto1 : adminVerifyListDto) {
            Verifyadmindoc verifyadmindoc = verifyadmindocRepository
                    .findByDocumentID_Id(adminVerifyListDto1.getDocumentInfo().getId()).orElseThrow(()
                            -> new RuntimeException("Document not found"));
            adminVerifyListDto1.setIsRead(verifyadmindoc.getIsRead());
        }

        return adminVerifyListDto;
    }

//    public List<VerifydocDto> getAllApprovedDoc() {
//        List<Verifydoc> verifydocs = verifydocRepository.findAll();
//        return verifydocs.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

    public List<UserDocAndManagersDto> getAllDocInfoAndManagers() {
        List<Document> documents = documentRepository.findAll();
        List<UserDocAndManagersDto> userDocAndManagersDtos = getAllApproveDoc(documents);

        if (userDocAndManagersDtos.isEmpty()) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND, "There are no documents that have a manager to verify");
        }

        return userDocAndManagersDtos;
    }

    public UserDocAndManagersDto getApprovedDocByDocID(int documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
        return getAllApproveDoc(Collections.singletonList(document)).get(0);
    }

    private VerifydocDto convertToDto(Verifydoc verifydoc) {
        VerifydocDto verifydocDto = modelMapper.map(verifydoc, VerifydocDto.class);

        verifydocDto.setDocumentID(modelMapper.map(verifydoc.getDocumentID(), VerifydocDto.DocumentDto1.class));
        verifydocDto.setManagerID(modelMapper.map(verifydoc.getManagerID(), VerifydocDto.UserDto1.class));

        return verifydocDto;
    }

    public List<UserDocAndManagersDto> getDocInfoAndManagersByUserId(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND,
                "User with id " + userId + " not found. Please ensure the user id is correct and try again."));
        List<Document> documents = documentRepository.findByUserID(user);
        List<UserDocAndManagersDto> userDocAndManagersDtos = getAllApproveDoc(documents);

        if (userDocAndManagersDtos.isEmpty()) {
            throw new CustomHttpException(HttpStatus.OK, "User does not have any documents that have a manager to verify");
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

    // ---------------  Add New relation between document and manager ----------------

    public CustomHttpException addManager(List<CreateVerifyManagerDto> userDocAndManagersDtos) {

        // alert sidebar to fetch new document
        notificationService.sendNewDocNotificationToAdmin();

        for (CreateVerifyManagerDto userDocAndManagersDto : userDocAndManagersDtos) {
            User manager = userRepository.findById(userDocAndManagersDto.getManagerID()).orElseThrow(()
                    -> new CustomHttpException(HttpStatus.NOT_FOUND, "Manager not found"));

            // validate the user id is manager or not
            if (!manager.getRole().equals("MANAGER")) {
                throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The user is not a manager");
            }

            Document document = documentRepository.findById(userDocAndManagersDto.getDocumentID()).orElseThrow(()
                    -> new CustomHttpException(HttpStatus.NOT_FOUND, "Document not found"));

            // check if the user is not an admin
            if (userRepository.existsByIdAndRoleNot(document.getUsersUserid().getId(), "ADMIN")) {
                Verifydoc verifydoc = new Verifydoc();
                verifydoc.setDocumentID(document);
                verifydoc.setManagerID(manager);
                verifydoc.setIsPass(-1);
                verifydoc.setIsRead(1);
                verifydocRepository.save(verifydoc);

                // alert sidebar to fetch new document
                notificationService.sendNewDocNotificationToAdmin();

                if (verifyadmindocRepository.findByDocumentID_Id(document.getId()).isEmpty()) {
                    Verifyadmindoc verifyadmindoc = new Verifyadmindoc();
                    verifyadmindoc.setDocumentID(document);
                    verifyadmindoc.setIsPass(-1);
                    verifyadmindoc.setIsRead(0);
                    verifyadmindocRepository.save(verifyadmindoc);

                    // alert sidebar to fetch new document
                    notificationService.sendNewDocNotificationToAdmin();
                }

                if (documentStatusRepository.getStatusByDocumentId(document.getId()) == null) {
                    Documentstatus documentstatus = new Documentstatus();
                    documentstatus.setDocumentID(document);
                    Verifystatustype status = verifystatustypeRepository.findById(2).orElseThrow(()
                            -> new RuntimeException("Status not found")); // replace 1 with the actual status ID
                    documentstatus.setStatusID(status);
                    DocumentApproveType documentApproveType = documentApproveTypeRepository
                            .findById(userDocAndManagersDto.getDocumentApproveTypeID()).orElseThrow(()
                                    -> new RuntimeException("DocumentApproveType not found"));
                    documentstatus.setDocumentApproveTypeID(documentApproveType);
                    documentstatus.setSentToUser(0);
                    documentstatus.setIsOwnerRead(0);

                    documentStatusRepository.save(documentstatus);

                }

                // alert sidebar to fetch new document
                notificationService.sendNewDocNotificationToAdmin();

            // in case the document has been added by the admin
            } else {
                Verifydoc verifydoc = new Verifydoc();
                verifydoc.setDocumentID(document);
                verifydoc.setManagerID(manager);
                verifydoc.setIsPass(-1);
                verifydoc.setIsRead(0);
                verifydocRepository.save(verifydoc);

                if (verifyadmindocRepository.findByDocumentID_Id(document.getId()).isEmpty()) {
                    Verifyadmindoc verifyadmindoc = new Verifyadmindoc();
                    verifyadmindoc.setDocumentID(document);
                    verifyadmindoc.setIsPass(1);
                    verifyadmindoc.setIsRead(1);
                    verifyadmindocRepository.save(verifyadmindoc);

                    // alert sidebar to fetch new document
                    notificationService.sendNewDocNotificationToAdmin();
                }

                if (documentStatusRepository.getStatusByDocumentId(document.getId()) == null) {
                    Documentstatus documentstatus = new Documentstatus();
                    documentstatus.setDocumentID(document);
                    Verifystatustype status = verifystatustypeRepository.findById(3).orElseThrow(()
                            -> new RuntimeException("Status not found")); // replace 1 with the actual status ID
                    documentstatus.setStatusID(status);
                    DocumentApproveType documentApproveType = documentApproveTypeRepository
                            .findById(userDocAndManagersDto.getDocumentApproveTypeID()).orElseThrow(()
                                    -> new RuntimeException("DocumentApproveType not found"));
                    documentstatus.setDocumentApproveTypeID(documentApproveType);
                    documentstatus.setSentToUser(0);
                    documentstatus.setIsOwnerRead(0);

                    documentStatusRepository.save(documentstatus);

                    // ส่ง notification ไปยัง manager ที่ต้องตรวจสอบเอกสาร โดยใช้ verifydoc table
                    List<Verifydoc> verifydocs = verifydocRepository.findByDocumentID(document);
                    for (Verifydoc vd : verifydocs) {
                        notificationService.createNotification(vd.getManagerID().getId(), document.getId(),
                                "New Request For Manager", document.getUsersUserid().getFullName(), 7);
                    }

                }
            }

            // alert sidebar to fetch new document
            notificationService.sendNewDocNotificationToAdmin();

        }

        // alert sidebar to fetch new document
        notificationService.sendNewDocNotificationToAdmin();

        return new CustomHttpException(HttpStatus.OK, "The operation was successful and the manager has been added.");

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

                    int sentToUser = documentstatus != null ? documentstatus.getSentToUser() : 0;

                    int isOwnerRead = documentstatus != null ? documentstatus.getIsOwnerRead() : 0;

                    // Fetch rejectInfo from the database
                    rejectInfoDto rejectInfo = fetchRejectInfo(document.getId());

                    return new UserDocAndManagersDto(status_type_id, totalManagers, totalManagersWhoVerified,
                            approve_type_Id, isOwnerRead, sentToUser, rejectInfo, managersWhoVerified, documentInfo);
                })
                .filter(dto -> !dto.getManagersWhoVerified().isEmpty())
                .toList();
    }

    private rejectInfoDto fetchRejectInfo(int documentId) {
        Documentstatus documentstatus = documentStatusRepository
                .findByDocumentID(documentRepository.findById(documentId)
                        .orElseThrow(() -> new RuntimeException("Document not found")));

        // if the document status is 5 (rejected by admin)
        if (documentstatus != null && documentstatus.getStatusID().getId() == 5) {
            Verifyadmindoc verifyadmindoc = verifyadmindocRepository.findByDocumentID_Id(documentId)
                    .orElseThrow(() -> new RuntimeException("Verifyadmindoc not found"));
            return new rejectInfoDto("Admin", verifyadmindoc.getComment());

        // if the document status is 6 (rejected by manager)
        } else if (documentstatus != null && ((documentstatus.getStatusID().getId() == 6) || (documentstatus.getStatusID().getId() == 7))){
            // find the manager who rejected the document
            Verifydoc verifydoc = (Verifydoc) verifydocRepository.findByDocumentID_IdAndIsPass(documentId, 0)
                    .orElseThrow(() -> new RuntimeException("Verifydoc not found"));
            return new rejectInfoDto("Manager: " + verifydoc.getManagerID().getFullName(), verifydoc.getComment());
        } else {
            return new rejectInfoDto("-", "-");
        }
    }

    // ---------------  Approval Process ----------------

    public CustomHttpException approveDocByAdmin(AdminApproveDocDto adminApproveDocDto) {
        Document document = documentRepository.findById(adminApproveDocDto.getDocument_id()).orElseThrow(()
                -> new RuntimeException("Document not found"));
        Documentstatus documentstatus = documentStatusRepository.findByDocumentID(document);

        if (documentstatus == null) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND, "Document status not found");
        }

        Verifystatustype verifystatustype = verifystatustypeRepository.findById(3).orElseThrow(() -> new RuntimeException("Status not found"));

        // if the admin approves the document
        if (adminApproveDocDto.getIs_pass() == 1 && documentstatus.getStatusID().getId() == 2) {

            verifyadmindocRepository.updateIsPassByDocumentID(1, document);

            documentstatus.setStatusID(verifystatustype);
            documentStatusRepository.save(documentstatus);

            // set isRead back to 0
            documentstatus.setIsOwnerRead(0);

            // ส่ง notification ไปยัง user ที่สร้างเอกสาร
            notificationService.createNotification(document.getUsersUserid().getId(), document.getId(),
                    "Request has been approved by Admin", document.getUsersUserid().getFullName(), 2);

            // ส่ง notification ไปยัง manager ที่ต้องตรวจสอบเอกสาร โดยใช้ verifydoc table
            List<Verifydoc> verifydocs = verifydocRepository.findByDocumentID(document);
            for (Verifydoc vd : verifydocs) {

                // set isRead back to 0
                vd.setIsRead(0);

                notificationService.createNotification(vd.getManagerID().getId(), document.getId(),
                        "New Request For Manager", document.getUsersUserid().getFullName(), 7);
            }

            throw new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been approved.");

        // if the admin rejects the document
        } else if (adminApproveDocDto.getIs_pass() == 0 && documentstatus.getStatusID().getId() == 2) {
            documentstatus.setStatusID(verifystatustypeRepository.findById(5).orElseThrow(() -> new RuntimeException("Status not found")));
            documentStatusRepository.save(documentstatus);

            // set isPass to 0 in verifyadmindoc table
            verifyadmindocRepository.updateIsPassByDocumentID(0, document);
            verifyadmindocRepository.updateCommentByDocumentID(adminApproveDocDto.getComment(), document);

            // set isRead back to 0
            documentstatus.setIsOwnerRead(0);

            notificationService.createNotification(document.getUsersUserid().getId(), document.getId(),
                    "Request has been rejected by Admin", document.getUsersUserid().getFullName(), 3);

            throw new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been rejected.");

            // if the document has already been approved or rejected
        } else {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The document has already been approved or rejected");
        }
    }

    public CustomHttpException approveDocByManager(ManagerApproveDocDto managerApproveDocDto) {
        Document document = documentRepository.findById(managerApproveDocDto.getDocument_id()).orElseThrow(() -> new RuntimeException("Document not found"));
        Documentstatus documentstatus = documentStatusRepository.findByDocumentID(document);

        if (documentstatus == null) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND, "Document status not found");
        }

        if (documentstatus.getStatusID().getId() != 3) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The document has already been approved or rejected");
        }

        Verifydoc verifydoc = verifydocRepository.findByDocumentID_IdAndManagerID_Id(document.getId(), managerApproveDocDto.getManager_id()).orElseThrow(() -> new RuntimeException("Document not found"));

        if (verifydoc.getIsPass() != -1) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The document has already been approved or rejected");
        }

        if (managerApproveDocDto.getIs_pass() == 1) {
            verifydoc.setIsPass(1);
            verifydocRepository.save(verifydoc);

            // set isRead back to 0
            documentstatus.setIsOwnerRead(0);

            // if all managers approve the document, change the status to approved
            List<Verifydoc> verifydocs = verifydocRepository.findByDocumentID(document);
            int totalManagers = verifydocs.size();
            int totalManagersWhoVerified = (int) verifydocs.stream().filter(vd -> vd.getIsPass() >= 0).count();

            // if all managers approve the document
            if (totalManagers == totalManagersWhoVerified) {
                Verifystatustype verifystatustype = verifystatustypeRepository.findById(4).orElseThrow(() -> new RuntimeException("Status not found"));
                documentstatus.setStatusID(verifystatustype);
                documentStatusRepository.save(documentstatus);

                // ส่ง notification ไปยัง user ที่สร้างเอกสาร
                notificationService.createNotification(document.getUsersUserid().getId(), document.getId(),
                        "Request has been approved by all managers", document.getUsersUserid().getFullName(), 6);

                // if the document approve type id is 2 (approved by all managers), update isShow to 1 in userdocument table
                if (documentstatus.getDocumentApproveTypeID().getId() == 2) {
                    List<Userdocument> userdocument = userdocumentRepository.findByDocumentIdAndIsShow(document.getId(), 0);
                    userdocument.forEach(ud -> ud.setIsShow(1));
                    userdocumentRepository.saveAll(userdocument);

                    // set SentToUser to 1 in documentstatus table
                    documentstatus.setSentToUser(1);

                    // if isShow is 1 then create notification
                    for (Userdocument ud : userdocument) {
                        Notification thisnoti = notificationService.createNotification(ud.getUsersUserid().getId(), document.getId(),
                                "Document shared with you", ud.getOwnerDocument(), 1);
                        System.out.println("(ALL-MANAGER-APPROVE-DOC)Notification created for user with id: " + thisnoti.getUsersUserid() + " and document id: " + thisnoti.getDocumentId());
                    }
                }

                throw new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been approved.");

            } else {
                // ส่ง notification ไปยัง user ที่สร้างเอกสารว่ามีจำนวน manager ที่อนุมัติเอกสารแล้วกี่คน
                notificationService.createNotification(document.getUsersUserid().getId(), document.getId(),
                        totalManagersWhoVerified + "/" + totalManagers, document.getUsersUserid().getFullName(), 4);
            }

        } else if (managerApproveDocDto.getIs_pass() == 0) {
            verifydoc.setIsPass(0);
            verifydoc.setComment(managerApproveDocDto.getComment() == null ? "" : managerApproveDocDto.getComment());
            verifydocRepository.save(verifydoc);
            documentstatus.setStatusID(verifystatustypeRepository.findById(6).orElseThrow(() -> new RuntimeException("Status not found")));
            documentStatusRepository.save(documentstatus);


            // set isRead back to 0
            documentstatus.setIsOwnerRead(0);

            notificationService.createNotification(document.getUsersUserid().getId(), document.getId(),
                    "Request has been rejected by Manager", document.getUsersUserid().getFullName(), 5);

            throw new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been rejected.");

        // เอกสารถูกตีกลับ
        } else if (managerApproveDocDto.getIs_pass() == 2) {
            verifydoc.setIsPass(0);
            verifydoc.setComment(managerApproveDocDto.getComment() == null ? "" : managerApproveDocDto.getComment());
            verifydocRepository.save(verifydoc);
            documentstatus.setStatusID(verifystatustypeRepository.findById(7).orElseThrow(() -> new RuntimeException("Status not found")));
            documentStatusRepository.save(documentstatus);

            // set isRead back to 0
            documentstatus.setIsOwnerRead(0);

            notificationService.createNotification(document.getUsersUserid().getId(), document.getId(),
                    "Request has been rejected by Manager", document.getUsersUserid().getFullName(), 9);

            throw new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been rejected.");

        } else {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Invalid value for is_pass");
        }

        return new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been approved.");
    }

    // ---------------  Sent Document to User eDoc ----------------

    public CustomHttpException sentDocToUserEdoc(Integer documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
        Documentstatus documentstatus = documentStatusRepository.findByDocumentID(document);

        if (documentstatus == null) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND, "Document status not found");
        }

        if (documentstatus.getStatusID().getId() != 4) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "The document has not been approved by all managers");
        }

        // if the document approve type id is 1 (approved by any manager), update isShow to 1 in userdocument table
        if (documentstatus.getDocumentApproveTypeID().getId() == 1) {
            List<Userdocument> userdocuments = userdocumentRepository.findByDocumentIdAndIsShow(documentId, 0);
            if (userdocuments.isEmpty()) {
                throw new CustomHttpException(HttpStatus.NOT_FOUND, "No Userdocument entities found for the given documentId and isShow value");
            }
            userdocuments.forEach(ud -> ud.setIsShow(1));

            // set SentToUser to 1 in documentstatus table
            documentstatus.setSentToUser(1);

            userdocumentRepository.saveAll(userdocuments); // batch update
        }

        // Fetch all Userdocument entities with isShow = 1 in one go
        List<Userdocument> userdocumentsToShow = userdocumentRepository.findByDocumentIdAndIsShow(documentId, 1);
        // userdocumentsToShow.forEach(ud -> notificationService.createNotification(ud.getUsersUserid().getId(), documentId, "Document shared with you", ud.getOwnerDocument()));
        for (Userdocument ud : userdocumentsToShow) {
            Notification thisnoti = notificationService.createNotification(ud.getUsersUserid().getId(), documentId,
                    "Document shared with you", ud.getOwnerDocument(), 1);
            System.out.println("(SENT-TO-eDOC)Notification created for user with id: " + thisnoti.getUsersUserid() + " and document id: " + thisnoti.getDocumentId());
        }

        return new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been sent to the user's eDoc.");
    }

    // ---------------  Graphical Data Retrieval ----------------

    public WaveGraphInfoDto getWaveGraph() {
        WaveGraphInfoDto waveGraphInfoDto = new WaveGraphInfoDto();
        WaveGraphInfoDto waveGraphInfoDto_LastWeek = new WaveGraphInfoDto();

        // get 7 days ago in waveGraphInfoDto
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 14; i++) {
            Date day = calendar.getTime();
            switch (i) {
                case 0:
                    waveGraphInfoDto.setDay1(day);
                    break;
                case 1:
                    waveGraphInfoDto.setDay2(day);
                    break;
                case 2:
                    waveGraphInfoDto.setDay3(day);
                    break;
                case 3:
                    waveGraphInfoDto.setDay4(day);
                    break;
                case 4:
                    waveGraphInfoDto.setDay5(day);
                    break;
                case 5:
                    waveGraphInfoDto.setDay6(day);
                    break;
                case 6:
                    waveGraphInfoDto.setDay7(day);
                    break;
                case 7:
                    waveGraphInfoDto_LastWeek.setDay1(day);
                    break;
                case 8:
                    waveGraphInfoDto_LastWeek.setDay2(day);
                    break;
                case 9:
                    waveGraphInfoDto_LastWeek.setDay3(day);
                    break;
                case 10:
                    waveGraphInfoDto_LastWeek.setDay4(day);
                    break;
                case 11:
                    waveGraphInfoDto_LastWeek.setDay5(day);
                    break;
                case 12:
                    waveGraphInfoDto_LastWeek.setDay6(day);
                    break;
                case 13:
                    waveGraphInfoDto_LastWeek.setDay7(day);
                    break;
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        // Get the number of approved documents for the last 1- 7 days
        waveGraphInfoDto.setCountOfDay1(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto.getDay1().toInstant()));
        waveGraphInfoDto.setCountOfDay2(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto.getDay2().toInstant()));
        waveGraphInfoDto.setCountOfDay3(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto.getDay3().toInstant()));
        waveGraphInfoDto.setCountOfDay4(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto.getDay4().toInstant()));
        waveGraphInfoDto.setCountOfDay5(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto.getDay5().toInstant()));
        waveGraphInfoDto.setCountOfDay6(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto.getDay6().toInstant()));
        waveGraphInfoDto.setCountOfDay7(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto.getDay7().toInstant()));

        // Get the number of approved documents for the last 8 - 14 days
        waveGraphInfoDto_LastWeek.setCountOfDay1(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto_LastWeek.getDay1().toInstant()));
        waveGraphInfoDto_LastWeek.setCountOfDay2(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto_LastWeek.getDay2().toInstant()));
        waveGraphInfoDto_LastWeek.setCountOfDay3(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto_LastWeek.getDay3().toInstant()));
        waveGraphInfoDto_LastWeek.setCountOfDay4(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto_LastWeek.getDay4().toInstant()));
        waveGraphInfoDto_LastWeek.setCountOfDay5(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto_LastWeek.getDay5().toInstant()));
        waveGraphInfoDto_LastWeek.setCountOfDay6(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto_LastWeek.getDay6().toInstant()));
        waveGraphInfoDto_LastWeek.setCountOfDay7(documentStatusRepository.countByDocumentID_DateAdd(waveGraphInfoDto_LastWeek.getDay7().toInstant()));


        // find new request this week
        waveGraphInfoDto.setNewRequestsThisWeek(waveGraphInfoDto.getCountOfDay1() + waveGraphInfoDto.getCountOfDay2()
                + waveGraphInfoDto.getCountOfDay3() + waveGraphInfoDto.getCountOfDay4() + waveGraphInfoDto.getCountOfDay5()
                + waveGraphInfoDto.getCountOfDay6() + waveGraphInfoDto.getCountOfDay7());

        // find new request last week
        int newRequestsLastWeek = waveGraphInfoDto_LastWeek.getCountOfDay1() + waveGraphInfoDto_LastWeek.getCountOfDay2()
                + waveGraphInfoDto_LastWeek.getCountOfDay3() + waveGraphInfoDto_LastWeek.getCountOfDay4() + waveGraphInfoDto_LastWeek.getCountOfDay5()
                + waveGraphInfoDto_LastWeek.getCountOfDay6() + waveGraphInfoDto_LastWeek.getCountOfDay7();

        waveGraphInfoDto.setNewRequestsLastWeek(newRequestsLastWeek);


        double percentageIncreaseFromLastWeek = 0.0;

        if (waveGraphInfoDto.getNewRequestsLastWeek() > 0) {
            percentageIncreaseFromLastWeek = ((double) waveGraphInfoDto.getNewRequestsThisWeek() - (double) waveGraphInfoDto.getNewRequestsLastWeek()) / (double) waveGraphInfoDto.getNewRequestsLastWeek() * 100;
        } else if (waveGraphInfoDto.getNewRequestsThisWeek() > 0) {
            percentageIncreaseFromLastWeek = 100.0;
        }

        waveGraphInfoDto.setPercentageIncreaseFromLastWeek((int) Math.round(percentageIncreaseFromLastWeek));

        return waveGraphInfoDto;
    }


    public PieGraphInfoDto getPieGraph() {
        PieGraphInfoDto pieGraphInfoDto = new PieGraphInfoDto();

        // จำนวนเอกสารทั้งหมด
        pieGraphInfoDto.setTotalApprove((int) documentStatusRepository.count());

        // Status 2 = รอการตรวจสอบจากผู้ดูแล
        pieGraphInfoDto.setTotalStatus2(documentStatusRepository.countByStatusID_Id(2));

        // Status 3 = รอการอนุมัติจาก manager
        pieGraphInfoDto.setTotalStatus3(documentStatusRepository.countByStatusID_Id(3));

        // Status 4 = ผ่านการอนุมัติ
        pieGraphInfoDto.setTotalStatus4(documentStatusRepository.countByStatusID_Id(4));

        // Status 5 = ไม่ผ่านการอนุมัติ
        pieGraphInfoDto.setTotalStatus5(documentStatusRepository.countByStatusID_Id(5));

        return pieGraphInfoDto;

    }

    // ------------------- Process to Mark Document as Read ------------------------------------------------------

    public CustomHttpException readAdminVerifyDoc(int documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "Document status not found"));
        verifyadmindocRepository.updateIsReadByDocumentID(1, document);
        return new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been marked as read.");
    }

    public CustomHttpException readUserVerifyDoc(int documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "Document not found"));
        Documentstatus documentstatus = documentStatusRepository.findByDocumentID(document);
        documentstatus.setIsOwnerRead(1);
        documentStatusRepository.save(documentstatus);
        return new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been marked as read.");
    }

    public CustomHttpException readManagerVerifyDoc(int managerId, int documentId) {
        documentRepository.findById(documentId).orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "Document not found"));
        userRepository.findById(managerId).orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "Manager not found"));
        Verifydoc verifydoc = verifydocRepository.findByDocumentID_IdAndManagerID_Id(documentId, managerId).orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "Document not found"));
        verifydoc.setIsRead(1);
        verifydocRepository.save(verifydoc);
        return new CustomHttpException(HttpStatus.OK, "The operation was successful and the document has been marked as read.");
    }

    // ------------------- Notifier ------------------------------------------------------------------------------

    public AlertSideBarDto getNewStatusForAll(int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "User not found"));

        AlertSideBarDto alertSideBarDto = new AlertSideBarDto();

        if (Objects.equals(user.getRole(), "ADMIN") || user.getRole().equals("USER")) {
            alertSideBarDto.setUser_all_1(documentStatusRepository.countUserAll(user.getId()));
            alertSideBarDto.setUser_waiting_2(documentStatusRepository.countUserWaiting(user.getId()));
            alertSideBarDto.setUser_done_3(documentStatusRepository.countUserDone(user.getId()));
            alertSideBarDto.setUser_rejected_4(documentStatusRepository.countUserRejected(user.getId()));
        } else {
            alertSideBarDto.setUser_all_1(-1);
            alertSideBarDto.setUser_waiting_2(-1);
            alertSideBarDto.setUser_done_3(-1);
            alertSideBarDto.setUser_rejected_4(-1);
        }

        if (Objects.equals(user.getRole(), "ADMIN")) {
            alertSideBarDto.setAdmin_all(verifyadmindocRepository.countAdminAll());
        } else {
            alertSideBarDto.setAdmin_all(-1);
        }

        if (Objects.equals(user.getRole(), "MANAGER")) {
            alertSideBarDto.setManager_incoming(verifydocRepository.countManagerIncoming(user.getId()));
        } else {
            alertSideBarDto.setManager_incoming(-1);
        }

        return alertSideBarDto;
    }

    // ------------------------------------------------------------------------------------------------------------

}