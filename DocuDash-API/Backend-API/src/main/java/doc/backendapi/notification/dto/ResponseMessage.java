package doc.backendapi.notification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {
    private String content;
    private String username;

    public ResponseMessage(String content) {
        this.content = content;
    }

}
