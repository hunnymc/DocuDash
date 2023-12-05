package doc.backendapi.service;

import doc.backendapi.DTO.DocumentDto;
import doc.backendapi.entities.Document;
import doc.backendapi.repositories.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DocumentDto> getAllDocument() {
        return documentRepository.findAll().stream().map((element) ->
                modelMapper.map(element, DocumentDto.class)).collect(Collectors.toList());
    }

    public DocumentDto saveDocument(DocumentDto documentDto) {
        Document document = modelMapper.map(documentDto, Document.class);
        Document savedDocument = documentRepository.save(document);
        return modelMapper.map(savedDocument, DocumentDto.class);
    }

    public Document updateDocument(int id, DocumentDto document) {
        Document existingDocument = documentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Document with id " + id + " not found. Please ensure the document id is correct and try again."));

        if (document.getTitle() != null)
            existingDocument.setTitle(document.getTitle());
        if (document.getDateAdd() != null)
            existingDocument.setDateAdd(document.getDateAdd());
        if (document.getDateUpdate() != null)
            existingDocument.setDateUpdate(document.getDateUpdate());
        if (document.getCategory() != null)
            existingDocument.setCategory(document.getCategory());
        if (document.getSecrecyLevel() != null)
            existingDocument.setSecrecyLevel(document.getSecrecyLevel());
        if (document.getUrgency() != null)
            existingDocument.setUrgency(document.getUrgency());
        if (document.getFromSource() != null)
            existingDocument.setFromSource(document.getFromSource());
        if (document.getStatus() != null)
            existingDocument.setStatus(document.getStatus());
        return documentRepository.save(existingDocument);
    }

    public void deleteDocument(int id) {
        if (!documentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Document with id " + id + " not found. Please ensure the document id is correct and try again.");
        }
        documentRepository.deleteById(id);
    }

    public Document getDocumentById(int id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Document with id " + id + " not found. Please ensure the document id is correct and try again."));
    }
}
