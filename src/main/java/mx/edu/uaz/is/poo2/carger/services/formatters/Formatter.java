package mx.edu.uaz.is.poo2.carger.services.formatters;

import java.util.List;

import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;

public abstract class Formatter<T extends IEntity> {

    public abstract String format(T entity, boolean showId);

    public String format(T entity) {
        return this.format(entity, false);
    }

    public String format(List<T> entities) {
        return this.format(entities, false);
    }

    public String format(List<T> entities, boolean showId) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entities.size(); i++) {
            sb.append(String.format("%d ", i + 1));
            if (i + 1 < 10) sb.append(' ');
            sb.append(this.format(entities.get(i), showId));
            sb.append('\n');
        }
        return sb.toString();
    }

    protected String getFormatted(String label, int maxSpaces, Object text) {
        StringBuilder sb = new StringBuilder();
        int spaces = maxSpaces - text.toString().length();
        for (int i = 0; i < spaces / 2; i++) sb.append(' ');
        sb.append(label);
        sb.append(text);
        for (int i = 0; i < spaces / 2; i++) sb.append(' ');
        if (spaces % 2 != 0) sb.append(' ');
        return sb.toString();
    }
}
