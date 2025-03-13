
package raisetech.student.management2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.domain.StudentDetail;
import raisetech.student.management2.exception.InvalidStudentDetailException;
//import raisetech.student.management2.exception.InvalidStudentIdException;
import raisetech.student.management2.exception.MissingParameterException;
import raisetech.student.management2.exception.StudentNotFoundException;
import raisetech.student.management2.service.StudentService;

/**
 * 受講生の検索や登録、更新等を行うREST APIとして実行するコントローラ
 */

@Validated
@RestController
public class StudentController {
  private StudentService service;

  /**
   * コンストラクタ
   * @param service
   * @param
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索
   * 全件検索を行う。条件指定は行わないもの。
   * @return 受講生一覧（全件）
   */

  @Operation(summary = "受講生一覧検索", description = "受講生一覧を全件検索します")
  @GetMapping("/studentList")
  public List<StudentDetail>  getStudentList()  {

    return service.searchStudentList();

   }

  /**
   * 受講生詳細検索を行う
   * IDに紐づく受講生の情報を取得する
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable(required = false) String id) {
    if (Objects.isNull(id) || id.trim().isEmpty() || !id.matches("\\d{1,2}")) {

      throw new StudentNotFoundException("IDに紐づく受講生が存在しません");
    }
    return service.searchStudent(id);
  }

//難しい箇所👆AIツールの使い方


  @GetMapping("/courseList")
  public List<StudentsCourse> getCourseList() {
    return service.searchCourseList();

  }

  /**
   * 受講生詳細の登録を行う
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */

  @Operation(summary = "受講生登録", description = "受講生情報を登録します", responses =
      {@ApiResponse(responseCode = "400", content = @Content())})
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }



  //下、レッスン33

  /**
   * 受講生詳細を更新します。
   * キャンセルフラグの更新もここで行う（論理削除）
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */

  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {

    service.updateStudent(studentDetail);
    System.out.println(studentDetail.getStudent().getName() + "さんの受講生受講生情報が新たに更新されました。");
    return ResponseEntity.ok("更新処理が成功しました");
  }


  @GetMapping("/courseList/{studentId}")
  public List<StudentsCourse> getCourseList(@PathVariable @Size(min = 1,max = 10) Long studentId) {
    return service.searchCourseList();
    //引数消した
  }
  //対応するサービス層がないため有無を言わさず全件検索
}
