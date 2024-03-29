package doc.backendapi.DTO.approval.admin;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminApproveDocDto implements Serializable {
    Integer document_id; // document id
    Integer is_pass; // 1 = pass, 0 = fail
}