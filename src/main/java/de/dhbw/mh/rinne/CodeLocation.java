package de.dhbw.mh.rinne;

public class CodeLocation {

    private final int line;
    private final int column;

    public CodeLocation(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public String inColonNotation() {
        return String.format("%d:%d", line, column);
    }

}
