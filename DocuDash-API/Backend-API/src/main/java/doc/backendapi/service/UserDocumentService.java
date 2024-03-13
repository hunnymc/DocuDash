package doc.backendapi.service;

import doc.backendapi.DTO.UserDocDTOpack.CreateUserDocDTO;
import doc.backendapi.DTO.UserDocDTOpack.UserdocumentDto;
import doc.backendapi.entities.Document;
import doc.backendapi.entities.User;
import doc.backendapi.entities.Userdocument;
import doc.backendapi.notification.NotificationService;
import doc.backendapi.repositories.DocumentRepository;
import doc.backendapi.repositories.UserRepository;
import doc.backendapi.repositories.UserdocumentRepository;
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
public class UserDocumentService {

    @Autowired
    private UserdocumentRepository userdocumentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserdocumentDto> getAllUserDocument() {
        return userdocumentRepository.findAll().stream().map((element) ->
                modelMapper.map(element, UserdocumentDto.class)).collect(Collectors.toList());
    }

    public CreateUserDocDTO save(CreateUserDocDTO data) {
        Document document = documentRepository.findById(data.getDocumentsDocumentid1Id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        User user = userRepository.findById(data.getUsersUseridId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (document == null || user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document or User not found");
        }

        Userdocument userdocument = modelMapper.map(data, Userdocument.class);
        userdocument.setDocumentsDocumentid1(document);
        userdocument.setUsersUserid(user);
        userdocument.setOwnerDocument(data.getOwnerDocument());

        Userdocument savedUserdocument = userdocumentRepository.save(userdocument);

        // Create notification entity for each user
        notificationService.createNotification(user.getId(), document.getId(), "Document shared with you", data.getOwnerDocument() );

        return modelMapper.map(savedUserdocument, CreateUserDocDTO.class);
    }
}
