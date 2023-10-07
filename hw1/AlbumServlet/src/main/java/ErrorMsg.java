import java.util.Objects;

public class ErrorMsg {

    private String msg;

    public ErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorMsg)) {
            return false;
        }
        ErrorMsg errorMsg = (ErrorMsg) o;
        return Objects.equals(msg, errorMsg.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg);
    }

    @Override
    public String toString() {
        return "ErrorMsg{" +
            "msg='" + msg + '\'' +
            '}';
    }
}
