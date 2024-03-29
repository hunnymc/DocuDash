package doc.backendapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import doc.backendapi.DTO.CreateDocDto;
import doc.backendapi.DTO.DocumentDto;
import doc.backendapi.DTO.EmailDto;
import doc.backendapi.DTO.UserDocDTOpack.UserdocumentDto;
import doc.backendapi.DTO.UserInfoDto;
import doc.backendapi.FileUpload.FileService;
import doc.backendapi.service.DocumentService;
import doc.backendapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/doc")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public List<UserdocumentDto> getAllDocument() {
        return documentService.getAllDocument();
    }

    @GetMapping("/newdocid")
    public int getNewDocId() {
        return documentService.getNewDocId();
    }

    @GetMapping("/user/{id}")
    public List<UserdocumentDto> getDocumentByUserId(@PathVariable int id) {
        return documentService.getDocumentByUserId(id);
    }

    @PostMapping("/user/email")
    public List<UserdocumentDto> getDocumentByUserEmail(@RequestBody EmailDto emailDto) {
        return documentService.getDocumentByUserEmail(emailDto.getEmail());
    }

    @GetMapping("/{id}")
    public DocumentDto getDocumentById(@PathVariable int id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping("/")
    public Integer saveDocument(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("file");

        String createDocDtoJson = request.getParameter("data");
        if (createDocDtoJson == null || createDocDtoJson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data cannot be null or empty");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CreateDocDto createDocDto = objectMapper.readValue(createDocDtoJson, CreateDocDto.class);
        if (createDocDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to parse data into CreateDocDto");
        }

        if (file != null) {
            Integer userId = createDocDto.getUsersUserid().getId();
            System.out.println("userId from file: " + userId);
            fileService.store(file, userId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be null");
        }

        return documentService.saveDocument(createDocDto, file.getOriginalFilename());
    }

    //    @PostMapping("/")
    //    public CreateDocDto saveDocument(@RequestBody CreateDocDto createDocDto) {
    //        return documentService.saveDocument(createDocDto);
    //    }

//    @PatchMapping("/{id}")
//    public EditDocDto updateDocument(@PathVariable int id, @RequestBody MultipartHttpServletRequest request) throws IOException {
//        MultipartFile file = request.getFile("file");
//        String editDocDtoJson = request.getParameter("data");
//        ObjectMapper objectMapper = new ObjectMapper();
//        EditDocDto editDocDto = objectMapper.readValue(editDocDtoJson, EditDocDto.class);
//        fileService.store(file);
//
//        return documentService.updateDocument(id, editDocDto, file.getOriginalFilename());
//    }

    @PatchMapping(value = "/{id}")
    public DocumentDto updateDocument(@PathVariable int id, MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("file");
        String editDocDtoJson = request.getParameter("data");
        ObjectMapper objectMapper = new ObjectMapper();
        DocumentDto editDocDto = objectMapper.readValue(editDocDtoJson, DocumentDto.class);

        String filename = null;
        if (file != null) {
            fileService.store(file, editDocDto.getUsersUserid().getId());
            filename = file.getOriginalFilename();
        }

        return documentService.updateDocument(id, editDocDto, filename);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable int id) {
        documentService.deleteDocument(id);
    }

    @GetMapping("/check/{id}")
    public boolean checkDocument(@PathVariable int id) {
        return documentService.checkDocument(id);
    }

    @GetMapping("/user")
    public List<UserInfoDto> getAllUsers() {
        return userService.getAllUsers();
    }

}