package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

@Slf4j
public class UnCheckedAppTest {

    @Test
    void printEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
//            e.printStackTrace();
            log.info("exception:", e);
        }

    }

    @Test
    void unChecked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(RuntimeSQLException.class);
    }

    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }


    static class Service {
        Repository repository =  new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call()  {
            try {
                runSQL();
            } catch (SQLException e) {
                // Checked Exception 발생시 UnChecked Exception을 밖으로 던짐.
                throw new RuntimeSQLException(e); // 예외를 전환할 때는 반드시 기존 예외(e)를 포함해야 함.
            }
        }

        public void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException() {
        }

        /**
         * Throwable cause : 이전 예외를 가지고 있음.
         *  즉, Checked Exception 발생시 UnCheckedException으로 변경해서 밖으로 예외를 던져도
         *  Checked Exception 내용과 UnCheckedException 내용을 전부 가지고 있음.
         */
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
