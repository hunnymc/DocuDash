package doc.backendapi.DTO.approval.rejectInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class rejectInfoDto  implements Serializable {

    String rejectFrom;
    String rejectComment;

}
