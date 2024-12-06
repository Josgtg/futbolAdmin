package mx.edu.uaz.is.poo2.carger.view.windows.selection;

import java.util.ArrayList;
import java.util.List;

import mx.edu.uaz.is.poo2.carger.controller.crud.CRUDSelectController;
import mx.edu.uaz.is.poo2.carger.view.windows.*;
import mx.edu.uaz.is.poo2.carger.model.constants.CRUDTable;
import mx.edu.uaz.is.poo2.carger.model.constants.WindowMessages;

public class CRUDSelectWindow extends Window<CRUDSelectController> {

    public CRUDSelectWindow() {
        super("CRUDSelect");
    }

    @Override
    public void start() {
        boolean conntinue = true;
        while (conntinue) {
            this.println(WindowMessages.windowTitle("Table Selection"));
            conntinue = this.askOptions();
        }
    }

    private boolean askOptions() {
        List<CRUDTable> tables = new ArrayList<>();
        for (var t : CRUDTable.values())
            if (t != CRUDTable.EVENT && t != CRUDTable.MATCH)
                tables.add(t);
        this.println(WindowMessages.ASK_TABLE_SELECTION);
        this.println(this.listAsNumeratedStr(tables));
        int option = this.readInt(WindowMessages.ASK_FOR_OPTION_OR_RETURN, 0, tables.size());
        if (option == 0) 
            return false;
        this.controller.startCRUDWindow(tables.get(--option));
        return true;
    }
}