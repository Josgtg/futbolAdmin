package mx.edu.uaz.is.poo2.carger.view.windows;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;

public class WindowSignal<T extends IEntity> {
    public static final int EXIT = -1;
    public static final int CLOSE = 0;
    public static final int START = 1;
    public static final int RETURN_TO_MAIN = 2;
    public static final int ENTER_AS_ADMIN = 3;
    public static final int OPEN_TEAM = 4;
    public static final int OPEN_PLAYER = 5;
    public static final int OPEN_MATCHES = 6;
    public static final int OPEN_MATCH = 7;

    public int actionCode;
    public List<T> entities; 

    public WindowSignal(int actionCode) {
        this.actionCode = actionCode;
        this.entities = new ArrayList<>();
    }

    public WindowSignal(int actionCode, T... entities) {
        this.actionCode = actionCode;
        this.entities = Arrays.asList(entities);
    }

    public WindowSignal(int actionCode, List<T> entities) {
        this.actionCode = actionCode;
        this.entities = entities;
    }

    public boolean hasEntities() {
        return !this.entities.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("WindowSignal{ actionCode: %d, hasEntities: %b }", this.actionCode, this.hasEntities());
    }
}