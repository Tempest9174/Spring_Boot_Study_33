
package raisetech.student.management2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.domain.StudentDetail;
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
  @GetMapping("/studentList")
  public List<StudentDetail>  getStudentList() {
    //StudentDetailにまとめるのが依然と異なる。

    // model.addAttribute("studentList",);//コースリストを取得

  return service.searchStudentList();

    //変数でなくStudent studentなのか？
    //表示
    //return student.getName() + " " + student.getAge() + "歳";
  }

  /**
   * 受講生詳細検索を行う
   * IDに紐づくに似の受講生の情報を取得する
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Size(min=1, max=2, message="入力して！！！") String id) {

    //studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourse()));
    return service.searchStudent(id);
  }
//難しい箇所👆AIツールの使い方


  @GetMapping("/courseList")
  public List<StudentsCourse> getCourseList() {
    return service.searchCourseList();

  }

//  @GetMapping("/newStudent")
//  public String newStudent(Model model) {
//    StudentDetail studentDetail = new StudentDetail();
//    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourse()));
//    model.addAttribute("studentDetail", studentDetail);
//    return "registerStudent";
//  }
//難しい箇所👆登録処理が実装＞＞不要



  /**
   * 受講生詳細の登録を行う
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */

//studentDetailの名前にバリデーションチェックを追加

    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent( @RequestBody @Valid  StudentDetail studentDetail) {



    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail) ;
     }
  //生徒一覧に一件をformから追加する
  //ここに何か処理入る。
  //下のDetailもおかし？


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
    System.out.println(studentDetail.getStudent().getName() + "さんの受講生受講生情報が新たに登録されました。");
    return ResponseEntity.ok("更新処理が成功しました");
  }


  @GetMapping("/courseList/{studentId}")
  public List<StudentsCourse> getCourseList(@PathVariable @Size(min = 1,max = 10) Long studentId) {
    return service.searchCourseList();
    //引数消した
  }
  //対応するサービス層がないため有無を言わさず全件検索
}
