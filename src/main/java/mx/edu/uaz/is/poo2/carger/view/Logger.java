package mx.edu.uaz.is.poo2.carger.view;

import java.util.logging.Level;

import mx.edu.uaz.is.poo2.carger.model.constants.Errors;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;

public class Logger extends java.util.logging.Logger {

    public Logger(String name) {
        super(name, null);
    }

    public void fatal(ErrorKind errCode, String message) {
        this.log(Level.SEVERE, message);
        System.exit(errCode.asInt());
    }
    public void fatal(ErrorKind errCode, String message, Exception e) {
        this.log(Level.SEVERE, message, e);
        System.exit(errCode.asInt());
    }
    public void fatalf(ErrorKind errCode, String message, Exception e) {
        this.log(Level.SEVERE, Errors.format(errCode, message, e));
        System.exit(errCode.asInt());
    }

    public void warn(String message) {
        this.log(Level.WARNING, message);
    }
    public void warn(String message, Exception e) {
        this.log(Level.WARNING, message, e);
    }
    public void warnf(ErrorKind errCode, String message, Exception e) {
        this.log(Level.WARNING, Errors.format(errCode, message, e));
    }
}