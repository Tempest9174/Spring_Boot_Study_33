package raisetech.student.management2.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentsCourse {



  private  String id;
  private String studentId;
  private String courseName;
  private Date courseStartAt;
  private Date courseEndAt;


}
