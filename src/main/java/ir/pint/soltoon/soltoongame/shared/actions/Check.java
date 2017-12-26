package ir.pint.soltoon.soltoongame.shared.actions;

import ir.pint.soltoon.soltoongame.shared.check.KhadangDescribe;

import java.util.ArrayList;

public class Check extends Action {
    private ArrayList<KhadangDescribe> khadangDescribes = new ArrayList<>();

    public Check(ArrayList<KhadangDescribe> khadangDescribes) {
        this.khadangDescribes = khadangDescribes;
    }

    public Check() {
    }

    public ArrayList<KhadangDescribe> getKhadangDescribes() {
        return khadangDescribes;
    }

    public void setKhadangDescribes(ArrayList<KhadangDescribe> khadangDescribes) {
        this.khadangDescribes = khadangDescribes;
    }
}
