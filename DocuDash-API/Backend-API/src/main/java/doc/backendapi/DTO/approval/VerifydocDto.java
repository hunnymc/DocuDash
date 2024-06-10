package doc.backendapi.DTO.approval;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link doc.backendapi.entities.Verifydoc}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifydocDto implements Serializable {
    Integer id;
    Integer isPass;
    @Size(max = 255)
    String comment;
    @NotNull
    VerifydocDto.DocumentDto1 documentID;
    @NotNull
    VerifydocDto.UserDto1 managerID;

    /**
     * DTO for {@link doc.backendapi.entities.Document}
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DocumentDto1 implements Serializable {
        Integer id;
        @Size(max = 255)
        String title;
        @Size(max = 255)
        String filePath;
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
        String branchSource;
        @Size(max = 255)
        String emailSource;
        @Size(max = 255)
        String phoneSource;
        @Size(max = 255)
        Integer status;
        @Size(max = 255)
        String description;
        @NotNull
        VerifydocDto.DocumentDto1.UserDto1 usersUserid;

        /**
         * DTO for {@link doc.backendapi.entities.User}
         */
        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UserDto1 implements Serializable {
            Integer id;
            @Size(max = 255)
            String username;
            @Size(max = 255)
            String password;
            @Size(max = 255)
            String fullName;
            @Size(max = 255)
            String role;
            @Size(max = 255)
            String email;
            @Size(max = 255)
            String phone;
            @Size(max = 255)
            String branch;
        }
    }

    /**
     * DTO for {@link doc.backendapi.entities.User}
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto1 implements Serializable {
        Integer id;
        @Size(max = 255)
        String username;
        @Size(max = 255)
        String password;
        @Size(max = 255)
        String fullName;
        @Size(max = 255)
        String role;
        @Size(max = 255)
        String email;
        @Size(max = 255)
        String phone;
        @Size(max = 255)
        String branch;
    }

}