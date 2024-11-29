package mx.edu.uaz.is.poo2.carger.view.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public enum WindowSignal {
    EXIT,
    CLOSE,
    START,
    RETURN_TO_MAIN,
    ENTER_AS_ADMIN,
    OPEN_TEAM,
    OPEN_PLAYER,
    OPEN_MATCHES,
    OPEN_MATCH,
    OPEN_CRUD_SELECT,
    OPEN_CRUD,

    OPEN_TEAM_CRUD,
    OPEN_PLAYER_CRUD,
    OPEN_MATCH_CRUD,

    SAVE_ENTITY,
    UPDATE_ENTITY,
    DELETE_ENTITY,
    FIND_ENTITY_BY_ID,
    FIND_ALL_ENTITIES;

    private List<Object> entities;

    private WindowSignal() {
        this.entities = new ArrayList<>();
    }

    public WindowSignal setEntities(Object... entities) {
        this.entities = Arrays.asList(entities);
        return this;
    }

    public WindowSignal setEntities(List<Object> entities) {
        this.entities = (List<Object>) entities;
        return this;
    }

    public List<Object> getEntities() {
        return this.entities;
    }

    public boolean hasEntities() {
        return !this.entities.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("WindowSignal{ actionCode: %s, hasEntities: %b }", this.name(), this.hasEntities());
    }
}