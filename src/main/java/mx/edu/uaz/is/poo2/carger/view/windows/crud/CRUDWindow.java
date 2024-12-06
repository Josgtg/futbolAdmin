package mx.edu.uaz.is.poo2.carger.view.windows.crud;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;

import mx.edu.uaz.is.poo2.carger.controller.crud.CRUDController;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.ErrorKind;
import mx.edu.uaz.is.poo2.carger.model.constants.Messages;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;
import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;
import mx.edu.uaz.is.poo2.carger.model.entities.Player;
import mx.edu.uaz.is.poo2.carger.view.windows.*;

public abstract class CRUDWindow<T extends IEntity> extends Window<CRUDController<T>> {
    protected CRUDTable table;

    public CRUDWindow(String title, CRUDTable table) {
        super(title);
        this.table = table;
    }

    @Override 
    public void start() {
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle(this.TITLE));
            conntinue = this.askOptions();
        }
    }
    
    protected boolean askOptions() {
        return this.askOptions(new String[]{"Consultar", "Guardar", "Actualizar", "Borrar"});
    }

    protected boolean askOptions(String[] options) {
        this.println(WindowMessages.ASK_FOR_ACTION);
        this.println(this.listAsNumeratedStr(Arrays.asList(options)));
        int option = this.readInt(WindowMessages.ASK_FOR_OPTION_OR_RETURN, 0, options.length);
        boolean conntinue = true;
        switch (option) {
            case 0 -> conntinue = false;
            case 1 -> this.showWindow();
            case 2 -> this.saveEntity();
            case 3 -> this.updateEntity();
            default -> this.deleteEntity();
        }
        return conntinue;
    }

    protected abstract void showWindow();

    private void saveEntity() {
        T entity = this.readEntity();
        if (entity instanceof Player player) {
            this.controller.savePlayer(player);
            return;
        }
        if (this.controller.save(entity).isPresent())
            this.println(Messages.ENTITY_SAVED);
        else
            this.printerr(ErrorKind.QUERY_NOT_EXECUTED, Messages.CANT_SAVE_ENTITY);
    }

    private void updateEntity() {
        Long id = this.readId();
        Optional<T> entityOpt = this.controller.getEntity(id, this.getTable());
        if(entityOpt.isEmpty()) {
            this.printerr(ErrorKind.ENTITY_NOT_FOUND, Messages.ID_NOT_FOUND);
            return;
        }
        T entity = entityOpt.get();
        T updatedEntity = this.readEntityToUpdate(entity);
        if (this.controller.update(updatedEntity).isPresent())
            this.println(Messages.ENTITY_UPDATED);
        else
            this.printerr(ErrorKind.QUERY_NOT_EXECUTED, Messages.CANT_UPDATE_ENTITY);
    }

    private void deleteEntity() {
        Long id = this.readId();
        if (this.controller.deleteById(id))
            this.println(Messages.ENTITY_DELETED);
        else
            this.printerr(ErrorKind.QUERY_NOT_EXECUTED, Messages.CANT_DELETE_ENTITY);
    }

    private boolean askToUpdate(String attributeName, Object object) {
        return this.askYesOrNo(WindowMessages.askToUpdate(attributeName, object));
    }

    public Timestamp readToUpdateDate(String attributeName, Timestamp date) {
        if (!this.askToUpdate(attributeName, date))
            return date;
        return this.readDate();
    }

    public int readToUpdateInt(String attributeName, int value, int lo, int hi) {
        if (!this.askToUpdate(attributeName, value))
            return value;
        return this.readInt("Nuevo valor: ", lo, hi);
    }

    public Long readToUpdateId(String attributeName, Long id, Long lo, Long hi) {
        if (!this.askToUpdate(attributeName, id))
            return id;
        return this.readId(Messages.ASK_FOR_ID, lo, hi);
    }

    public Long readToUpdateId(String attributeName, Long id) {
        return this.readToUpdateId(attributeName, id, 0L, Long.MAX_VALUE);
    }

    public String readToUpdateString(String attributeName, String str, int lo, int hi) {
        if (!this.askToUpdate(attributeName, str))
            return str;
        return this.readString("Nuevo valor: ", lo, hi);
    }

    public CRUDTable getTable() {
        return this.table;
    }

    public abstract T readEntity();

    public abstract T readEntityToUpdate(T entity);
}