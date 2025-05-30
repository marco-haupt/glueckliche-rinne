package de.dhbw.mh.rinne;

public class ErrorLogger {

    private StringBuilder sb = new StringBuilder();

    public void error(String message) {
        sb.append(message);
    }

    @Override
    public String toString() {
        return sb.toString();
    }

}
