package controller;

public class ControllerError {
    private String error;

    public ControllerError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String newError) {
        this.error = newError;
    }
}