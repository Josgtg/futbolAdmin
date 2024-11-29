package mx.edu.uaz.is.poo2.carger.view.windows.consult;

import mx.edu.uaz.is.poo2.carger.controller.view.ConsultController;
import mx.edu.uaz.is.poo2.carger.model.entities.IEntity;
import mx.edu.uaz.is.poo2.carger.view.windows.Window;

public abstract class ConsultWindow<T extends IEntity> extends Window<ConsultController> {

    public ConsultWindow(String title) {
        super(title);
    }

    public abstract void start(T entity);
}
