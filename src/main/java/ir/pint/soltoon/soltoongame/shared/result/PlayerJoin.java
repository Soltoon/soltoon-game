package ir.pint.soltoon.soltoongame.shared.result;

import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

public class PlayerJoin extends Event {
    private long id;
    private ComRemoteInfo remoteInfo;
    private int initialMoney = 0;

    public PlayerJoin(long id, ComRemoteInfo remoteInfo) {
        super(EventType.PLAYER_JOIN);

        this.id = id;
        this.remoteInfo = remoteInfo;
    }

    public PlayerJoin(long id, ComRemoteInfo remoteInfo, int initialMoney) {
        this(id, remoteInfo);
        this.initialMoney = initialMoney;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ComRemoteInfo getRemoteInfo() {
        return remoteInfo;
    }

    public void setRemoteInfo(ComRemoteInfo remoteInfo) {
        this.remoteInfo = remoteInfo;
    }

    public int getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(int initialMoney) {
        this.initialMoney = initialMoney;
    }
}
