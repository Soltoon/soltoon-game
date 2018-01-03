package ir.pint.soltoon.soltoongame.shared.communication.result;

import java.util.HashMap;

public class ResultAction extends Result {

    public ResultAction(Long id, Status status, HashMap data) {
        super(id, status, data);
    }

    public ResultAction(Long id, Status status) {
        this(id, status, null);
    }

    @Override
    public String toString() {
        return "ResultAction{" +
                "data=" + data +
                ", status=" + status +
                '}';
    }
}
