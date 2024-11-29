package mx.edu.uaz.is.poo2.carger.model.constants;

public class WindowMessages {
    public static String ASK_FOR_ACTION = "Selecciona la acción a realizar: ";
    public static String ASK_FOR_OPTION_OR_RETURN = "Escribe el número de la acción a realizar o escribe 0 para regresar a la pantalla anterior: ";
    public static String LEAGUE_OPTIONS = "Escribe el número del equipo a consultar o escribe -1 para ingresar como admin, 0 para salir: ";
    public static String RETURN_WINDOW = "Escribe 0 para regresar a la ventana anterior: ";
    public static String ASK_TABLE_SELECTION = "Selecciona la tabla a gestionar o escribe 0 para regresar a la pantalla anterior: ";

    public static String askToUpdate(String attributeName, Object value) {
        return String.format("Valor actual de \"%s\": %s\nActualizar? (s/n): ", attributeName, value);
    }

    static public String windowTitle(String windowName) {
        return String.format("\n------------%s------------", windowName);
    }

    public static String consultOrReturn(String entityName) {
        return String.format(
            "Escribe el número del %s a consultar o escribe 0 para regresar a la pantalla anterior: ", entityName
        );
    }
    public static String consultOrReturnOrModify(String entityName) {
        return String.format(
            "Escribe el número del %s a consultar o escribe 0 para regresar a la pantalla anterior, escribe -1 para modificar: ", entityName
        );
    }
    public static String selectToFillOrReturn(String entityName) {
        return String.format(
            "Escribe el número del %s que deseas vaciar o escribe 0 para regresar a la pantalla anterior: ", entityName
        );
    }
}