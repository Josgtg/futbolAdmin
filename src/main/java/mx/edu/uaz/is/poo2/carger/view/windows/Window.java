package mx.edu.uaz.is.poo2.carger.view.windows;

import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.Controller;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;
import mx.edu.uaz.is.poo2.carger.view.Logger;
import mx.edu.uaz.is.poo2.carger.model.constants.Errors;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;

public abstract class Window<C extends Controller> {
    protected C controller;
    public final String TITLE;
    private final Scanner scanner;
    protected final Logger logger;

    protected Window(String title) {
        this.TITLE = title;
        this.scanner = new Scanner(System.in);
        this.logger = new Logger(Window.class.getSimpleName());
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

    public String readLine() {
        return this.scanner.nextLine().trim();
    }

    protected <T> T readGeneric(String message, Function<String, T> converter) {
        T data;
        String input;
        while (true) {
            this.print(message);
            try {
                input = this.readLine();
                if (input.equals("0m"))
                    continue;
                data = converter.apply(input);
                return data;
            } catch (IllegalStateException | NoSuchElementException e) {
                logger.warnf(ErrorKind.INPUT_ERROR, Errors.INPUT_ERROR, e);
                this.printerr(ErrorKind.INPUT_ERROR, Errors.INPUT_ERROR);
            } catch (Exception e) {
                this.printerr(ErrorKind.PARSE_ERROR, Errors.INPUT_ERROR);
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

    public Long readId(String message, Long lo, Long hi) {
        Long data;
        while (true) { 
            data = this.readGeneric(message, Long::parseLong);
            if (data < lo || data > hi) {
                this.printerr(ErrorKind.INPUT_ERROR, Messages.INVALID_NUMBER);
                continue;
            }
            return data;
        }
    }

    public Long readId(String message) {
        return this.readId(message, 0L, Long.MAX_VALUE);
    }

    public Long readId() {
        return this.readId(Messages.ASK_FOR_ID, 0L, Long.MAX_VALUE);
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

    public <E extends IEntity> String basicListAsNumeratedStr(List<E> options) {
        var str = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            str.append(String.format("%d. %s\n", i + 1, options.get(i).basicToString()));
        }
        return str.toString();
    }

    public <T> String listAsNumeratedStr(List<T> options) {
        var str = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            str.append(String.format("%d. %s\n", i + 1, options.get(i).toString()));
        }
        return str.toString();
    }

    public Timestamp readDate() {
        String dateString;
        while (true) {
            this.println(Messages.ASK_FOR_DATE);
            dateString = this.readLine();
            try {
                return Timestamp.valueOf(dateString);
            } catch (IllegalArgumentException e) {
                this.printerr(ErrorKind.PARSE_ERROR, Messages.INVALID_DATE);
            }
        }
    }

    @SuppressWarnings("ConvertToStringSwitch")
    protected boolean askYesOrNo(String message) {
        String input;
        while (true) { 
            this.print(message);
            input = this.readLine().toLowerCase();
            if (input.equals("s"))
                return true;
            else if (input.equals("n"))
                return false;
            else
                this.printerr(ErrorKind.INPUT_ERROR, Messages.INVALID_Y_OR_N);
        }
    }

    public void setController(C controller) {
        this.controller = controller;
    }

    public abstract void start();

}