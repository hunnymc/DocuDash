package doc.backendapi.model;


import doc.backendapi.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

//    private static final long serialVersionUID = -8091879091924046844L;
//    private final String jwttoken;

    private String message;
    private String accessToken;
    private String refreshToken;
    private String role;

}