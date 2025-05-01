package raisetech.student.management2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {


  // カスタムメッセージを設定できるコンストラクタ
  public StudentNotFoundException(String message) {
    super(message);
  }

  // メッセージと原因の両方を受け取るコンストラクタ
  public StudentNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
