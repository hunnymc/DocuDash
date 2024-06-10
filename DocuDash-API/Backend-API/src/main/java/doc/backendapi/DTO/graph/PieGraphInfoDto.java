package doc.backendapi.DTO.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieGraphInfoDto implements Serializable {

    // จำนวน approve ทั้งหมด
    int totalApprove;

    // จำนวนเอกสารรอการอนุมัติจาก admin
    int totalStatus2;

    // จำนวนเอกสารที่รอการอนุมัติจาก manager
    int totalStatus3;

    // จำนวนเอกสารที่ผ่านการอนุมัติ
    int totalStatus4;

    // จำนวนเอกสารที่ถูกปฏิเสธ
    int totalStatus5;
}
