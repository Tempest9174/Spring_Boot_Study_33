package raisetech.student.management2.exception;


import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

  //private static final Logger LOGGER = Logger.getLogger(StudentExceptionHandler.class.getName());
  private static final Logger logger = LoggerFactory.getLogger(StudentExceptionHandler.class);


  /**
   * リクエストボディのバリデーションエラーを処理する
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    // ユーザー向けのエラーメッセージをMapで整理
    List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> Map.of("field", error.getField(), "message", error.getDefaultMessage()))
        .collect(Collectors.toList());

    // ログ出力（SLF4J）
    logger.error("バリデーションエラー発生: {}",
        errors.stream().map(e -> e.get("field") + " - " + e.get("message"))
            .collect(Collectors.joining(", ")));

    // ユーザー向けレスポンス
    Map<String, Object> response = Map.of(
        "message", "入力エラーが発生しました",
        "errors", errors
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  //  @ExceptionHandler(BadRequestException.class)
//  public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
//    return ResponseEntity.badRequest().body(ex.getMessage());
//  }
//  @ExceptionHandler(BadRequestException.class)  とりあえずのコメントアウト
//  public ResponseEntity<Map<String, String>> handleBadRequest(BadRequestException ex) {
//    return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
//  }


  /**
   * パラメータの入力エラーを処理します。
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */
  @ExceptionHandler(MissingParameterException.class)
  public ResponseEntity<Map<String, String>> handleMissingParameterException(
      MissingParameterException ex) {
    // ターミナルにログ出力（開発者向け）
    logger.error("パラメータの入力エラー: {}", ex.getMessage());

    // ユーザーにJSON形式でエラーメッセージを返す
    Map<String, String> response = new HashMap<>();
    response.put("error", "Validation failed: 空ではなく何らかのパラメータを入れてください。");
    response.put("message", ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  /**
   * 予期しないエラーを処理します。(例：受講生検索で何も入力がない場合>>正確にはスペースが入力されている場合)
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */
//  @ExceptionHandler(StudentNotFoundException.class)
//  public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex) {
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
//  }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
      Map<String, Object> errorResponse = new LinkedHashMap<>();
      errorResponse.put("timestamp", Instant.now().toString());
      errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
      errorResponse.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
      errorResponse.put("message", ex.getMessage());
      errorResponse.put("path", request.getRequestURI());
      return ResponseEntity.badRequest().body(errorResponse);
    }
  }



















//  // StudentNotFoundException をキャッチしてカスタムレスポンスを返す
//  @ExceptionHandler(StudentNotFoundException.class)
//  public ResponseEntity<String> HandleStudentNotExceptionHandler(StudentNotFoundException ex) {
//    // エラーメッセージを構築
//    System.out.println("エラーメッセージを構築");
//    String message = "Not Found\n" +
//        "Validation failed: 指定されたIDに該当する受講生が見つかりません。\n" +
//        ex.getMessage();
//
//    // loggerでコンソールに表示
//
//    logger.error("パラメータが不正または存在しません: {}", ex.getMessage());
//
//    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
//  }
//}
//@ExceptionHandler(Exception.class)
//public ResponseEntity<Map<String, String>> StudentException(Exception ex) {
//  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//      .body(Map.of("error", "サーバー内部エラー", "message", ex.getMessage()));
//}


