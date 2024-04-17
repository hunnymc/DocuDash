package doc.backendapi.service;

import doc.backendapi.DTO.UserDocDTOpack.CreateUserDocDTO;
import doc.backendapi.DTO.UserDocDTOpack.UserdocumentDto;
import doc.backendapi.entities.Document;
import doc.backendapi.entities.Notification;
import doc.backendapi.entities.User;
import doc.backendapi.entities.Userdocument;
import doc.backendapi.hadlers.CustomHttpException;
import doc.backendapi.notification.NotificationService;
import doc.backendapi.repositories.DocumentRepository;
import doc.backendapi.repositories.UserRepository;
import doc.backendapi.repositories.UserdocumentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CreateUserDocDTO save(CreateUserDocDTO data) {
        Document document = documentRepository.findById(data.getDocumentsDocumentid1Id())
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "Document not found"));

        User user = userRepository.findById(data.getUsersUseridId())
                .orElseThrow(() -> new CustomHttpException(HttpStatus.NOT_FOUND, "User not found"));

        Userdocument userdocument = modelMapper.map(data, Userdocument.class);
        userdocument.setDocumentsDocumentid1(document);
        userdocument.setUsersUserid(user);
        userdocument.setOwnerDocument(data.getOwnerDocument());
        userdocument.setIsRead(0);

        Userdocument savedUserdocument = userdocumentRepository.save(userdocument);

        // if isShow is 1 then create notification for each user
        if (data.getIsShow() == 1) {
            notificationService.createNotification(data.getUsersUseridId(), data.getDocumentsDocumentid1Id(), "Document shared with you", data.getOwnerDocument(), 1);
        }

        return modelMapper.map(savedUserdocument, CreateUserDocDTO.class);
    }
}
