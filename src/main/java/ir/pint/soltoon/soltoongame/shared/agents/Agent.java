package ir.pint.soltoon.soltoongame.shared.agents;

import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.actions.Action;
import ir.pint.soltoon.soltoongame.shared.map.Game;
import ir.pint.soltoon.utils.clients.proxy.ProxyReturnStorage;
import ir.pint.soltoon.utils.clients.proxy.TimeAwareBean;

import java.io.Serializable;

/**
 * @author Payam Mohammadi
 * @since 1.0.0
 */
public abstract class Agent implements Serializable, TimeAwareBean {

    private TimeAwareBean parentBean;
    private Long id;

    protected Agent() {
    }

    public abstract void init(Game g);

    public abstract void lastThingsToDo(Game g);

    public abstract Action getAction(Game g);

    public void recieveResults(Result result, Command command) {

    }

    public void setParentBean(TimeAwareBean parentBean) {
        this.parentBean = parentBean;
    }

    @Override
    public void repair() {

    }

    @Override
    public void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage) {
        // do nothing
    }

    @Override
    public ProxyReturnStorage getProxyReturnStorage() {
        if (this.parentBean != null)
            return parentBean.getProxyReturnStorage();
        else
            return null;
    }

    @Override
    public int getRemainingTime() {
        if (this.parentBean != null)
            return parentBean.getRemainingTime();
        else
            return -2;
    }

    @Override
    public void setRemainingTime(int i) {
        // ignore
    }

    @Override
    public int getDurationType() {
        return parentBean.getDurationType();
    }

    @Override
    public void setDurationType(int i) {
        // ignore
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
