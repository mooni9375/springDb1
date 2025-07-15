package hello.jdbc.repository.ex;

/**
 * 커스텀한 예외기 때문에 특정 객체에 의존적이지 않음.
 * 따라서 변경에 유리함.
 */
public class MyDuplicateKeyException extends MyDbException {

    public MyDuplicateKeyException() {
    }

    public MyDuplicateKeyException(String message) {
        super(message);
    }

    public MyDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
