package doc.backendapi.DTO.UserDocDTOpack;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link doc.backendapi.entities.Userdocument}
 */
@Data
@NoArgsConstructor
public class UserdocumentDto implements Serializable {
    Integer id;
    @Size(max = 45)
    String accessLevel;
    @NotNull
    UserdocumentDto.DocumentDto documentsDocumentid1;
    @NotNull
    UserdocumentDto.UserDto usersUserid;

    /**
     * DTO for {@link doc.backendapi.entities.Document}
     */
    @Data
    @NoArgsConstructor
    public static class DocumentDto implements Serializable {
        Integer id;
        @Size(max = 255)
        String title;
        @Size(max = 255)
        String filePath;;
        Instant dateAdd;
        Instant dateUpdate;
        @Size(max = 255)
        String category;
        @Size(max = 255)
        String secrecyLevel;
        @Size(max = 255)
        String urgency;
        @Size(max = 255)
        String fromSource;
        @Size(max = 255)
        String status;
        @Size(max = 255)
        String description;
        @Size(max = 255)
        String branchSource;
        @Size(max = 255)
        String emailSource;
        @Size(max = 255)
        String phoneSource;
        @NotNull
        UserdocumentDto.DocumentDto.UserDto usersUserid;

        /**
         * DTO for {@link doc.backendapi.entities.User}
         */
        @Data
        @NoArgsConstructor
        public static class UserDto implements Serializable {
            Integer id;
            @Size(max = 255)
            String username;
            @Size(max = 255)
            String fullName;
            @Size(max = 255)
            String role;
            @Size(max = 255)
            String email;
            @Size(max = 255)
            String branch;
        }
    }

    /**
     * DTO for {@link doc.backendapi.entities.User}
     */
    @Data
    @NoArgsConstructor
    public static class UserDto implements Serializable {
        Integer id;
    }
}