package doc.backendapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import doc.backendapi.DTO.CreateDocDto;
import doc.backendapi.DTO.DocumentDto;
import doc.backendapi.DTO.EmailDto;
import doc.backendapi.DTO.UserDocDTOpack.UserdocumentDto;
import doc.backendapi.DTO.UserInfoDto;
import doc.backendapi.FileUpload.FileService;
import doc.backendapi.hadlers.CustomHttpException;
import doc.backendapi.repositories.UserRepository;
import doc.backendapi.service.DocumentService;
import doc.backendapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/doc")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

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
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Data cannot be null or empty");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CreateDocDto createDocDto = objectMapper.readValue(createDocDtoJson, CreateDocDto.class);
        if (createDocDto == null) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "Data cannot be null. Please ensure the data is correct and try again.");
        }

        // validate user id
        if (createDocDto.getUsersUserid() == null || createDocDto.getUsersUserid().getId() == null) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "User id cannot be null. Please ensure the user id is correct and try again.");
        }

        String userId_test = createDocDto.getUsersUserid().getId().toString();
        if (userId_test.isBlank()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "User id cannot be blank. Please ensure the user id is correct and try again.");
        }

        if (!userRepository.existsById(createDocDto.getUsersUserid().getId())) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND,
                    "User with id " + createDocDto.getUsersUserid().getId() + " not found. Please ensure the user id is correct and try again.");
        }

        if (file == null || file.isEmpty() || Objects.requireNonNull(file.getOriginalFilename()).isBlank() || file.getOriginalFilename().isEmpty()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "File cannot be null or empty or blank or empty filename. Please ensure the file is attached and try again.");
        }

        // validate file size 10MB
        if (file.getSize() > 10485760) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "File size must not be more than 10MB. Please ensure the file size is correct and try again.");
        }

        Integer userId = createDocDto.getUsersUserid().getId();
        System.out.println("userId from file: " + userId);
        fileService.store(file, userId);

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

        // validate user id
        if (editDocDto.getUsersUserid() == null || editDocDto.getUsersUserid().getId() == null) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "User id cannot be null. Please ensure the user id is correct and try again.");
        }

        String userId_test = editDocDto.getUsersUserid().getId().toString();
        if (userId_test.isBlank()) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "User id cannot be blank. Please ensure the user id is correct and try again.");
        }

        if (!userRepository.existsById(editDocDto.getUsersUserid().getId())) {
            throw new CustomHttpException(HttpStatus.NOT_FOUND,
                    "User with id " + editDocDto.getUsersUserid().getId() + " not found. Please ensure the user id is correct and try again.");
        }


        String filename = null;
        if (file != null) {

            // validate file size 10MB
            if (file.getSize() > 10485760) {
                throw new CustomHttpException(HttpStatus.BAD_REQUEST, "File size must not be more than 10MB. Please ensure the file size is correct and try again.");
            }

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

    @PostMapping("/read/{userid}/{docid}")
    public void markDocumentAsRead(@PathVariable int userid, @PathVariable int docid) {
        documentService.markDocumentAsRead(userid, docid);
    }

}