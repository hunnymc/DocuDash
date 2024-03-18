package doc.backendapi.service;

import doc.backendapi.DTO.approval.UserDocAndManagersDto;
import doc.backendapi.DTO.approval.UserVerifydocDto;
import doc.backendapi.DTO.approval.VerifydocDto;
import doc.backendapi.entities.Document;
import doc.backendapi.entities.User;
import doc.backendapi.entities.Verifydoc;
import doc.backendapi.repositories.DocumentRepository;
import doc.backendapi.repositories.UserRepository;
import doc.backendapi.repositories.VerifydocRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprovalService {

    @Autowired
    private VerifydocRepository verifydocRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<VerifydocDto> getApprovedDocByManagerID(int id) {
        User manager = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        List<Verifydoc> verifydocs = verifydocRepository.findByManagerID(manager);
        return verifydocs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<VerifydocDto> getAllApprovedDoc() {
        List<Verifydoc> verifydocs = verifydocRepository.findAll();
        return verifydocs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private VerifydocDto convertToDto(Verifydoc verifydoc) {
        VerifydocDto verifydocDto = modelMapper.map(verifydoc, VerifydocDto.class);

        // Assuming that getDocument() and getManager() methods exist in your Verifydoc entity
        verifydocDto.setDocumentID(modelMapper.map(verifydoc.getDocumentID(), VerifydocDto.DocumentDto1.class));
        verifydocDto.setManagerID(modelMapper.map(verifydoc.getManagerID(), VerifydocDto.UserDto1.class));

        return verifydocDto;
    }

    public List<UserDocAndManagersDto> getDocInfoAndManagersByUserId(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Document> documents = documentRepository.findByUserID(user);

        List<UserDocAndManagersDto> userDocAndManagersDtos = documents.stream()
                .map(document -> {
                    UserVerifydocDto documentInfo = modelMapper.map(document, UserVerifydocDto.class);
                    List<Verifydoc> verifydocs = verifydocRepository.findByDocumentID(document);
                    List<VerifydocDto> managersWhoVerified = verifydocs.stream()
                            .map(this::convertToDto2)
                            .collect(Collectors.toList());
                    return new UserDocAndManagersDto(documentInfo, managersWhoVerified);
                })
                .filter(dto -> !dto.getManagersWhoVerified().isEmpty()) // filter out documents with no verified managers
                .collect(Collectors.toList());

        if (userDocAndManagersDtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not have any documents that have a manager to verify");
        }

        return userDocAndManagersDtos;
    }

    private VerifydocDto convertToDto2(Verifydoc verifydoc) {
        VerifydocDto verifydocDto = new VerifydocDto();
        verifydocDto.setId(verifydoc.getId());
        verifydocDto.setIsPass(verifydoc.getIsPass());
        verifydocDto.setComment(verifydoc.getComment());

        // Set only the necessary fields for the manager
        VerifydocDto.UserDto1 managerDto = new VerifydocDto.UserDto1();
        managerDto.setId(verifydoc.getManagerID().getId());
        managerDto.setUsername(verifydoc.getManagerID().getUsername());
        managerDto.setFullName(verifydoc.getManagerID().getFullName());
        managerDto.setRole(verifydoc.getManagerID().getRole());
        managerDto.setEmail(verifydoc.getManagerID().getEmail());
        managerDto.setPhone(verifydoc.getManagerID().getPhone());
        managerDto.setBranch(verifydoc.getManagerID().getBranch());
        verifydocDto.setManagerID(managerDto);

        return verifydocDto;
    }
}