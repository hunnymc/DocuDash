package doc.backendapi.DTO.approval.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentToUserDto implements Serializable {

    @NotBlank(message = "Document ID must not be blank")
    @NotNull(message = "Document ID must not be null")
    Integer document_id; // document id

}
