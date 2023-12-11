package doc.backendapi.service;

import doc.backendapi.DTO.CreateDocDto;
import doc.backendapi.DTO.DocumentDto;
import doc.backendapi.DTO.UserDocDTOpack.UserdocumentDto;
import doc.backendapi.entities.Document;
import doc.backendapi.entities.User;
import doc.backendapi.entities.Userdocument;
import doc.backendapi.repositories.DocumentRepository;
import doc.backendapi.repositories.UserdocumentRepository;
import jakarta.annotation.PostConstruct;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final UserdocumentRepository userdocumentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DocumentDto> getAllDocument() {
        return documentRepository.findAll().stream()
                .sorted(Comparator.comparing(Document::getDateUpdate).reversed())
                .map((element) -> modelMapper.map(element, DocumentDto.class))
                .collect(Collectors.toList());
    }

    public List<UserdocumentDto> getDocumentByUserId(int id) {
        Map<Integer, Userdocument> uniqueDocuments = userdocumentRepository.findByUsers_UserID(id).stream()
                .collect(Collectors.toMap(
                        ud -> ud.getDocumentsDocumentid1().getId(),  // Key is documentsDocumentid1.id
                        ud -> ud,  // Value is the Userdocument entity
                        (ud1, ud2) -> ud1  // If there are duplicates, keep the first one
                ));
        return new ArrayList<>(uniqueDocuments.values()).stream()
                .map(ud -> modelMapper.map(ud, UserdocumentDto.class))
                .collect(Collectors.toList());
    }

    public CreateDocDto saveDocument(CreateDocDto createDocDto, String filePath) {
        Document document = modelMapper.map(createDocDto, Document.class);
        document.setFilePath(filePath);
        document.setDateAdd(Instant.now());
        document.setDateUpdate(Instant.now());

        // Save the Document entity to the database
        Document savedDocument = documentRepository.save(document);

        // If the save operation failed, throw a ResponseStatusException
        if (savedDocument == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to save document. Please try again.");
        }
//        Document savedDocument = documentRepository.save(document);
//        return modelMapper.map(savedDocument, CreateDocDto.class);
        return modelMapper.map(savedDocument, CreateDocDto.class);
    }

    public DocumentDto updateDocument(int id, DocumentDto document, String filePath) {
//        if (!documentRepository.existsById(id)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    "Document with id " + id + " not found. Please ensure the document id is correct and try again.");
//        }

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
        if (!documentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Document with id " + id + " not found. Please ensure the document id is correct and try again.");
        }
        userdocumentRepository.deleteByDocumentsDocumentid1Id(id);
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

}