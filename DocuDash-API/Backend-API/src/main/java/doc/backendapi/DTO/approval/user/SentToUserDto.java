package doc.backendapi.DTO.approval.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentToUserDto implements Serializable {
    Integer document_id; // document id
}
