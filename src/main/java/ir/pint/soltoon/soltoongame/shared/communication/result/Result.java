package ir.pint.soltoon.soltoongame.shared.communication.result;

import ir.pint.soltoon.soltoongame.shared.communication.Message;

import java.util.HashMap;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Result extends Message {
    public HashMap <String,Object> data;
    public Status status;

    public Result(Long id, Status status, HashMap data) {
        super(id);
        this.data = data;
        this.status=status;
    }

    public Status getStatus() {
        return status;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
