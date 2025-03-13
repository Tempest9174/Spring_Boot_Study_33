package raisetech.student.management2.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.data.Student;

@Schema(description = "生徒詳細情報")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StudentDetail
{
    @Valid
    private Student student;
    private List<StudentsCourse> studentsCourseList;

}
