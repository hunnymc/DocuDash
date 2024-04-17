package doc.backendapi.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AlertSideBarDto {
    int User_all_1;
    int User_waiting_2;
    int User_done_3;
    int User_rejected_4;
    int Admin_all;
    int Manager_incoming;
}
