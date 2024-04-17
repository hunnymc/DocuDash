package doc.backendapi.DTO.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaveGraphInfoDto implements Serializable {

    // จำนวน request ทั้งหมดของสัปดาห์นี้
    int newRequestsThisWeek;

    // จำนวน request ทั้งหมดของสัปดาห์ที่แล้ว
    int newRequestsLastWeek;

    // จำนวนเปอร์เซ็นต์ของ request ที่เพิ่มขึ้นจากสัปดาห์ที่แล้ว
    int percentageIncreaseFromLastWeek;

    // วันที่ 1-7
    Date day1;
    Date day2;
    Date day3;
    Date day4;
    Date day5;
    Date day6;
    Date day7;

    // จำนวน request ของแต่ละวัน
    int countOfDay1;
    int countOfDay2;
    int countOfDay3;
    int countOfDay4;
    int countOfDay5;
    int countOfDay6;
    int countOfDay7;

}
