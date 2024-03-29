package doc.backendapi.service;

import doc.backendapi.DTO.CreateDocDto;
import doc.backendapi.DTO.DocumentDto;
import doc.backendapi.DTO.UserDocDTOpack.UserdocumentDto;
import doc.backendapi.entities.Document;
import doc.backendapi.entities.Notification;
import doc.backendapi.entities.Userdocument;
import doc.backendapi.notification.WSService;
import doc.backendapi.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    private final DocumentstatusRepository documentStatusRepository;

    @Autowired
    private final NotificationRepository notificationRepository;

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
        return getDocumentByUserId(id.orElseThrow(() -> new ResponseStatusException(HttpStatus.OK,
                "User with email " + email + " not found. Please ensure the email is correct and try again.")));
    }

    public List<UserdocumentDto> getDocumentByUserId(int id) {

        //  Get all documents that isShow is 1 in UserDocument table for the user with the given ID
        Optional<Userdocument> Documents = userdocumentRepository.findByUsersUserid_IdAndIsShow(id, 1);
        if (Documents.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.OK,
                    "User with id " + id + " do not have any documents.");
        }
        return Documents.stream().map(ud -> modelMapper.map(ud, UserdocumentDto.class)).collect(Collectors.toList());
    }

    public Integer saveDocument(CreateDocDto createDocDto, String filePath) {

        Document document = modelMapper.map(createDocDto, Document.class);
        document.setFilePath(filePath);
        document.setDateAdd(Instant.now());
        document.setDateUpdate(Instant.now());

        // Save the Document entity to the database
        Document savedDocument = documentRepository.save(document);

        // Notify the frontend that a new document has been added
        wsService.notifyFrontend("New document added to all users.");
        wsService.notifyUser(String.valueOf(createDocDto.getUsersUserid()), "New document added to userID: " + createDocDto.getUsersUserid());

        return savedDocument.getId(); // return the ID of the saved document
    }

    public DocumentDto updateDocument(int id, DocumentDto document, String filePath) {
        Document originalDocument = documentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
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
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Document with id " + id + " not found. Please ensure the document id is correct and try again."));
    }

    public int getNewDocId() {
        return documentRepository.getNewDocId();
    }

    public boolean checkDocument(int id) {
        return documentRepository.existsById(id);
    }
}