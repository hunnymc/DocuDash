package doc.backendapi.hadlers;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorClass {
    private Date timestamp = new Date();
    private Integer status;
    private String error;
    private String message;
    private String path;
}
