package mx.edu.uaz.is.poo2.carger.controller;

import mx.edu.uaz.is.poo2.carger.view.Logger;
import mx.edu.uaz.is.poo2.carger.view.windows.WindowSignal;

public abstract class Controller {
    protected final Logger logger;

    public Controller() {
        this.logger = new Logger(this.getClass().getSimpleName());
    }

    public abstract void windowAction(WindowSignal signal);
    public abstract void close();
}