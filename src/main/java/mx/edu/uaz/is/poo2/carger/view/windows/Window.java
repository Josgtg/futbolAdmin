package mx.edu.uaz.is.poo2.carger.view.windows;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.Controller;
import  mx.edu.uaz.is.poo2.carger.view.Logger;
import mx.edu.uaz.is.poo2.carger.model.constants.Errors;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;

public abstract class Window {
    protected Controller controller;
    private final Scanner scanner;
    protected final Logger logger;

    public Window(Controller controller) {
        this.scanner = new Scanner(System.in);
        this.logger = new Logger(Window.class.getSimpleName());
        this.controller = controller;
    }

    public void close() {
        this.println("Window Closed.");
    }

    public void print(Object toShow) {
        System.out.print(toShow);
    }

    public void println(Object toShow) {
        System.out.println(toShow);
    }

    public void printerr(ErrorKind errorKind, String errorMessage) {
        this.println(Errors.format(errorKind, errorMessage));
    }

    public void printerr(ErrorKind errorKind, String errorMessage, Exception error) {
        this.println(Errors.format(errorKind, errorMessage, error));
    }

    public <T> T readGeneric(String message, Function<String, T> converter) {
        T data;
        String input;
        while (true) {
            this.print(message);
            try {
                input = this.scanner.nextLine().trim();
                data = converter.apply(input);
                return data;
            } catch (IllegalStateException | NoSuchElementException e) {
                logger.warnf(ErrorKind.INPUT_ERROR, Errors.INPUT_ERROR, e);
            } catch (Exception e) {
                this.printerr(ErrorKind.PARSE_ERROR, Errors.PARSE_ERROR);
            }
        }
    }

    public int readInt(String message, int lo, int hi) {
        int data;
        while (true) { 
            data = this.readGeneric(message, Integer::parseInt);
            if (data < lo || data > hi) {
                this.printerr(ErrorKind.INPUT_ERROR, String.format(Messages.INVALID_INT_RANGE, lo, hi));
                continue;
            }
            return data;
        }
    }

    public Long readId() {
        Long data;
        while (true) { 
            data = this.readGeneric(Messages.ASK_FOR_ID, Long::parseLong);
            if (data < 0) {
                this.printerr(ErrorKind.INPUT_ERROR, Messages.INVALID_NUMBER);
                continue;
            }
            return data;
        }
    }

    public double readDouble(String message, double lo, double hi) {
        double data;
        while (true) { 
            data = this.readGeneric(message, Double::parseDouble);
            if (data < lo || data > hi) {
                this.printerr(ErrorKind.INPUT_ERROR, String.format(Messages.INVALID_FLOAT_RANGE, lo, hi));
                continue;
            }
            return data;
        }
    }

    public String readString(String message, int lo, int hi) {
        String data;
        while (true) { 
            data = this.readGeneric(message, (String x) -> { return x; });
            if (data.length() < lo || data.length() > hi) {
                this.printerr(ErrorKind.INPUT_ERROR, String.format(Messages.INVALID_LEN_RANGE, lo, hi));
                continue;
            }
            return data;
        }
    }

    public <T> String listAsNumeratedStr(List<T> options) {
        var str = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            str.append(String.format("%d. %s\n", i + 1, options.get(i).toString()));
        }
        return str.toString();
    }

    public abstract void mainView();
}