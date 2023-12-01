package doc.backendapi.service;

import doc.backendapi.Exception.ResourceNotFoundException;
import doc.backendapi.entities.Document;
import doc.backendapi.repositories.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    @Autowired
    private final DocumentRepository documentRepository;

    public List<Document> getAllDocument() {
        return documentRepository.findAll();
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document updateDocument(int id, Document document) {
        Document existingDocument = documentRepository.findById(id).orElse(null);

        if (existingDocument == null) {
            // handle the case where the document with the provided ID does not exist
            // you could throw an exception or return null
            throw new ResourceNotFoundException("Document", "id", id);
        }
        if (document.getTitle() != null)
            existingDocument.setTitle(document.getTitle());
        if (document.getFilePath() != null)
            existingDocument.setFilePath(document.getFilePath());
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
        if (document.getUploadBy() != null)
            existingDocument.setUploadBy(document.getUploadBy());
        return documentRepository.save(existingDocument);
    }

    public void deleteDocument(int id) {
        documentRepository.deleteById(id);
    }

    public Document getDocumentById(int id) {
        return documentRepository.findById(id).orElse(null);
    }
}
