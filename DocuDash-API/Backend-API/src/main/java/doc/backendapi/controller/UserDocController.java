package doc.backendapi.controller;

import doc.backendapi.DTO.UserDocDTOpack.CreateUserDocDTO;
import doc.backendapi.service.UserDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/userdoc")
public class UserDocController {

    @Autowired
    UserDocumentService userDocumentService;

    @PostMapping("/")
    public List<CreateUserDocDTO> saveUserDocs(@RequestBody List<CreateUserDocDTO> userDocJsons) {
        List<CreateUserDocDTO> savedUserDocs = new ArrayList<>();

        for (CreateUserDocDTO userDocJson : userDocJsons) {
            CreateUserDocDTO savedUserDoc = userDocumentService.save(userDocJson);
            savedUserDocs.add(savedUserDoc);
        }

        return savedUserDocs;
    }
}
