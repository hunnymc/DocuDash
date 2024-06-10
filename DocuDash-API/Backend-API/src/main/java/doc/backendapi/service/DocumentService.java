package doc.backendapi.service;

import doc.backendapi.DTO.CreateDocDto;
import doc.backendapi.DTO.DocumentDto;
import doc.backendapi.DTO.UserDocDTOpack.UserdocumentDto;
import doc.backendapi.entities.*;
import doc.backendapi.hadlers.CustomHttpException;
import doc.backendapi.notification.WSService;
import doc.backendapi.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final WSService wsService;

    @Autowired
    private final UserdocumentRepository userdocumentRepository;

    @Autowired
    private final NotificationRepository notificationRepository;

    @Autowired
    private final DocumentstatusRepository documentStatusRepository;

    @Autowired
    private final NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private final VerifyadmindocRepository verifyadmindocRepository;

    @Autowired
    private final VerifydocRepository verifydocRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserdocumentDto> getAllDocument() {
        Map<Integer, Userdocument> uniqueDocuments = userdocumentRepository.findAll().stream()
                .collect(Collectors.toMap(
                        ud -> ud.getDocumentsDocumentid1().getId(),  // Key is documentsDocumentid1.id
                        ud -> ud,  // Value is the Userdocument entity
                        (ud1, ud2) -> ud1  // If there are duplicates, keep the first one
                ));
        return new ArrayList<>(uniqueDocuments.values()).stream()
                .map(ud -> modelMapper.map(ud, UserdocumentDto.class))
                .collect(Collectors.toList());
    }

    public List<UserdocumentDto> getDocumentByUserEmail(String email) {
        Optional<Integer> id = userRepository.findIdByEmail(email);
        return getDocumentByUserId(id.orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND,
                "User with email " + email + " not found. Please ensure the email is correct and try again.")));
    }

    public List<UserdocumentDto> getDocumentByUserId(int id) {
        // check if user exists
        if (!userRepository.existsById(id)) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND,
                    "User with id " + id + " not found. Please ensure the user id is correct and try again.");
        }

        Map<Integer, Userdocument> uniqueDocuments = userdocumentRepository.findByUserIdAndIsShow(id, 1).stream()
                .collect(Collectors.toMap(
                        ud -> ud.getDocumentsDocumentid1().getId(),  // Key is documentsDocumentid1.id
                        ud -> ud,  // Value is the Userdocument entity
                        (ud1, ud2) -> ud1  // If there are duplicates, keep the first one
                ));

        if (uniqueDocuments.isEmpty()) {
            throw new CustomHttpException(HttpStatus.OK,
                    "No documents found for user with id " + id + ".");
        }

        return new ArrayList<>(uniqueDocuments.values()).stream()
                .map(ud -> modelMapper.map(ud, UserdocumentDto.class))
                .collect(Collectors.toList());
    }

    public Integer saveDocument(CreateDocDto createDocDto, String filePath) {

        // validate title
        if (createDocDto.getTitle() == null || createDocDto.getTitle().isBlank()
                || createDocDto.getTitle().isEmpty() || createDocDto.getTitle().equalsIgnoreCase("null")
                || createDocDto.getTitle().equalsIgnoreCase("undefined")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Title cannot be null, empty or undefined. Please ensure the title is correct and try again.");
        }

        // validate from source
        if (createDocDto.getFromSource() == null || createDocDto.getFromSource().isBlank()
                || createDocDto.getFromSource().isEmpty() || createDocDto.getFromSource().equalsIgnoreCase("null")
                || createDocDto.getFromSource().equalsIgnoreCase("undefined")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "From Source cannot be null, empty or undefined. Please ensure the from source is correct and try again.");
        }

        // validate branch source
        if (createDocDto.getBranchSource() == null || createDocDto.getBranchSource().isBlank() || createDocDto.getBranchSource().isEmpty() || createDocDto.getBranchSource().equalsIgnoreCase("null") || createDocDto.getBranchSource().equalsIgnoreCase("undefined")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Branch Source cannot be null, empty or undefined. Please ensure the branch source is correct and try again.");
        }

        // validate email source
        if (createDocDto.getEmailSource() == null || createDocDto.getEmailSource().isBlank() || createDocDto.getEmailSource().isEmpty() || createDocDto.getEmailSource().equalsIgnoreCase("null") || createDocDto.getEmailSource().equalsIgnoreCase("undefined")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Email Source cannot be null, empty or undefined. Please ensure the email source is correct and try again.");
        }

        //VALIDATE EMAIL FORMAT
        if (!createDocDto.getEmailSource().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Email Source is invalid. Please ensure the email source is correct and try again.");
        }

        // validate phone source
        if (createDocDto.getPhoneSource() == null || createDocDto.getPhoneSource().isBlank() || createDocDto.getPhoneSource().isEmpty() || createDocDto.getPhoneSource().equalsIgnoreCase("null") || createDocDto.getPhoneSource().equalsIgnoreCase("undefined")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Phone Source cannot be null, empty or undefined. Please ensure the phone source is correct and try again.");
        }

        // validate category
        if (createDocDto.getCategory() == null || createDocDto.getCategory().isBlank() || createDocDto.getCategory().isEmpty() || createDocDto.getCategory().equalsIgnoreCase("null") || createDocDto.getCategory().equalsIgnoreCase("undefined")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Category cannot be null, empty or undefined. Please ensure the category is correct and try again.");
        }

        // validate urgency
        if (createDocDto.getUrgency() == null || createDocDto.getUrgency().isBlank() || createDocDto.getUrgency().isEmpty() || createDocDto.getUrgency().equalsIgnoreCase("null") || createDocDto.getUrgency().equalsIgnoreCase("undefined")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Urgency cannot be null, empty or undefined. Please ensure the urgency is correct and try again.");
        }

        //validate category is "เอกสารภายใน" or "เอกสารภายนอก"
        if (!createDocDto.getCategory().equals("เอกสารภายใน") && !createDocDto.getCategory().equals("เอกสารภายนอก")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Category must be either 'เอกสารภายใน' or 'เอกสารภายนอก'. Please ensure the category is correct and try again.");
        }

        // validate secrecy level is "ปกติ" or "ปกปิด" or "ลับมาก" or "ลับ" or "ลับที่สุด"
        if (!createDocDto.getSecrecyLevel().equals("ปกติ") && !createDocDto.getSecrecyLevel().equals("ปกปิด") &&
                !createDocDto.getSecrecyLevel().equals("ลับมาก") && !createDocDto.getSecrecyLevel().equals("ลับ") &&
                !createDocDto.getSecrecyLevel().equals("ลับที่สุด")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Secrecy Level must be either 'ปกติ', 'ปกปิด', 'ลับมาก', 'ลับ', or 'ลับที่สุด'. Please ensure the secrecy level is correct and try again.");
        }

        // validate urgency is "ปกติ" or "ด่วน" or "ด่วนที่สุด"
        if (!createDocDto.getUrgency().equals("ปกติ") && !createDocDto.getUrgency().equals("ด่วน") &&
                !createDocDto.getUrgency().equals("ด่วนที่สุด")) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST,
                    "Urgency must be either 'ปกติ', 'ด่วน', or 'ด่วนที่สุด'. Please ensure the urgency is correct and try again.");
        }

        Document document = modelMapper.map(createDocDto, Document.class);
        document.setFilePath(filePath);
        document.setDateAdd(Instant.now());
        document.setDateUpdate(Instant.now());

        // Save the Document entity to the database
        Document savedDocument = documentRepository.save(document);

        // Notify the frontend that a new document has been added
        // wsService.notifyFrontend("New document added to all users.");
        wsService.notifyUser(String.valueOf(createDocDto.getUsersUserid()), "New document added to userID: " + createDocDto.getUsersUserid());

        return savedDocument.getId(); // return the ID of the saved document
    }

    public DocumentDto updateDocument(int id, DocumentDto document, String filePath) {
        Document originalDocument = documentRepository.findById(id)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND,
                        "Document with id " + id + " not found. Please ensure the document id is correct and try again."));

        Document existingDocument = new Document();
        BeanUtils.copyProperties(originalDocument, existingDocument);
        existingDocument.setId(id);
        existingDocument.setDateUpdate(Instant.now());

        if (document.getTitle() != null)
            existingDocument.setTitle(document.getTitle());
        if (filePath != null)
            existingDocument.setFilePath(filePath);
        if (document.getCategory() != null)
            existingDocument.setCategory(document.getCategory());
        if (document.getFromSource() != null)
            existingDocument.setFromSource(document.getFromSource());
        if (document.getDescription() != null)
            existingDocument.setDescription(document.getDescription());
        if (document.getPhoneSource() != null)
            existingDocument.setPhoneSource(document.getPhoneSource());
        if (document.getEmailSource() != null)
            existingDocument.setEmailSource(document.getEmailSource());
        if (document.getBranchSource() != null)
            existingDocument.setBranchSource(document.getBranchSource());

        return modelMapper.map(documentRepository.save(existingDocument), DocumentDto.class);
    }

    @Transactional
    public void deleteDocument(int id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND,
                        "Document with id " + id + " not found. Please ensure the document id is correct and try again."));

        // Fetch all notifications related to the document
        List<Notification> notifications = notificationRepository.findByDocumentId(id);

        // Delete all fetched notifications
        notificationRepository.deleteAll(notifications);

        verifyadmindocRepository.deleteByDocumentID(document);
        userdocumentRepository.deleteByDocumentsDocumentid1Id(id);
        verifydocRepository.deleteByDocumentID(document);
        documentStatusRepository.deleteByDocumentId(id);
        documentRepository.deleteById(id);
    }

    public DocumentDto getDocumentById(int id) {
        return documentRepository.findById(id).map((element) ->
                modelMapper.map(element, DocumentDto.class)).orElseThrow(() ->
                new CustomHttpException(HttpStatus.NOT_FOUND,
                        "Document with id " + id + " not found. Please ensure the document id is correct and try again."));
    }

    public int getNewDocId() {
        return documentRepository.getNewDocId();
    }

    public boolean checkDocument(int id) {
        return documentRepository.existsById(id);
    }

    @Transactional
    public void markDocumentAsRead(int userId, int docId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND,
                        "User with id " + userId + " not found."));

        Document document = documentRepository.findById(docId)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND,
                        "Document with id " + docId + " not found."));

        userdocumentRepository.updateIsReadByDocumentsDocumentid1AndUsersUserid(1, document, user);

        // update notification status
        NotificationType notificationType = notificationTypeRepository.findById(1)
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND,
                        "Notification type with id 1 not found."));
        notificationRepository.
                updateReadStatusByUsersUseridAndDocumentIdAndNotificationTypeID("READ", userId, docId, notificationType);

    }
}