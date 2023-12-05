package doc.backendapi.controller;

import doc.backendapi.DTO.DocumentDto;
import doc.backendapi.entities.Document;
import doc.backendapi.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doc")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/")
    public List<DocumentDto> getDocument() {
        return documentService.getAllDocument();
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable int id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping("/")
    public DocumentDto saveDocument(@RequestBody DocumentDto documentDto) {
        return documentService.saveDocument(documentDto);
    }

    @PatchMapping("/{id}")
    public Document updateDocument(@PathVariable int id, @RequestBody DocumentDto document) {
        return documentService.updateDocument(id, document);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable int id) {
        documentService.deleteDocument(id);
    }

}