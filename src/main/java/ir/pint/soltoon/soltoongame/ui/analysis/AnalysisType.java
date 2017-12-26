package ir.pint.soltoon.soltoongame.ui.analysis;


public enum AnalysisType {
    PRESENCE, GOT_KILLED, KILL, CREATE;

    public static String[] valuesAsString() {
        AnalysisType[] values = values();
        String[] strings = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strings[i] = values[i].toString();
        }

        return strings;
    }
}
