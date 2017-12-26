package ir.pint.soltoon.soltoongame.shared.exceptions;

import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.exceptions.SoltoonContainerException;

public class ClientInitializationException extends SoltoonContainerException {
    private long client;


    public ClientInitializationException(long client) {
        super();
        this.client = client;
    }

    public ClientInitializationException(Throwable throwable, long client) {
        super(throwable);
        this.client = client;
    }

    public long getClient() {
        return client;
    }

    public void setClient(long client) {
        this.client = client;
    }
}
