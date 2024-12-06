package mx.edu.uaz.is.poo2.carger.model.constants;

// Messages
public class Messages {
    public static final String SEPARATOR = "\n##################################################\n";

    public static final String CANT_GET_TABLES = "no se pudo obtener las tablas";
    public static final String CANT_SAVE_ENTITY = "no se pudo crear un nuevo registro";
    public static final String CANT_UPDATE_ENTITY = "no se pudo actualizar el registro";
    public static final String TABLES_NOT_FOUND = "No hay tablas para administrar";

    public static final String TABLE_OPTIONS = "Escoge que tabla deseas modificar (Escribe 0 en cualquier selección si deseas cerrar):\n";
    public static final String WELCOME_ADMIN = "Bienvenido al administrador de la base de datos!\n";
    public static final String TABLE_SELECTION_MESSAGE = "Escribe el número de la tabla: ";
    public static final String SELECTION_MESAGGE = "\nSelecciona una opcion:\n";
    public static final String CONSULT_OPTIONS = "\nConsultar por:\n";
    public static final String SELECCION_OPTIONS = "Escribe el número con la opción elegida: ";
    public static final String ID_CREATED_MESSAGE = "\nIMPORTANTE: El elemento creado tiene id: " ;
    public static final String ID_NOT_FOUND = "\nNo hay ningún elemento con el id dado" ;
    public static final String CANT_DELETE_ENTITY = "no se pudo eliminar";
    public static final String FAILED_TO_FETCH = "no se pudo consultar";
    public static final String NO_ELEMENTS = "No hay elementos en la tabla";
    public static final String CONNECTED_TO_DB = "Se ha conectado a la base de datos";
    public static final String TABLES_CREATED = "Tablas creadas exitosamente";

    public static final String CANT_BE_EMPTY = "Dato no puede estar vacío";

    public static final String ASK_FOR_DATE = "Escribe la fecha en formato \"aaaa-mm-dd hh:mm:ss\": ";
    public static final String INVALID_DATE = "La fecha especificada no es válida.";
    public static final String ASK_FOR_ID = "Escribe el ID: ";
    public static final String INVALID_NUMBER = "Número no válido. Intenta de nuevo: ";
    public static final String INVALID_INPUT = "Input no válido. Intenta de nuevo: ";
    public static final String INVALID_INT_RANGE = "Número debe de estar en el rango %d a %d Intenta de nuevo: ";
    public static final String INVALID_LEN_RANGE = "La longitud del texto debe de estar entre %d y %d. Intenta de nuevo: ";
    public static final String INVALID_FLOAT_RANGE = "Número debe de estar en el rango %f.3 a %f.3 Intenta de nuevo: ";
    public static final String INVALID_Y_OR_N = "El valor escrito debe ser \"s\" o \"n\". Intenta de nuevo: ";
    public static final String NUMBER_CANT_BE_NEGATIVE = "Número no puede ser negativo. Intenta de nuevo: ";

    public static final String ENTITY_SAVED = "El elemento ha sido guardado.";
    public static final String ENTITY_UPDATED = "El elemento ha sido actualizado.";
    public static final String ENTITY_DELETED = "El elemento ha sido elimindado.";

    public static final String NO_EVENTS = "No hay eventos para consultar.";
    public static final String NO_EVENTS_MODIFY = "No hay eventos para modificar.";

    public static final String READY = "¡Listo!";

    private static final String TABLE_IMPORTED = "Tabla %s importada exitosamente.";
    private static final String IMPORTING_TABLE = "Importando la tabla %s...";

    public static final String NO_MATCHES_NOT_PLAYED = "No hay partidos que aun no se han jugado";
    public static final String NO_MATCHES_PLAYED = "No hay partidos que ya fueron jugados";
    public static final String ASK_TO_ADD_EVENTS = "Deseas añadir eventos?? (s/n): ";
    public static final String ASK_TO_MODIFY_DELETE_EVENT = "Selecciona 1 si quieres Modificar este evento, 2 para eliminarlo y 0 para continuar: ";
    public static final String SELECT_EVENT_KIND = "Selecciona el numero del tipo de evento: ";
    public static final String ASK_UPDATED_MINUTE = "introduce el minuto nuevo: ";
    public static final String ASK_TO_MODIFY = "Deseas Modificarlo (s/n): ";
    public static final String ASK_TO_CONTINUE = "Estas seguro que deseas continuar? (s/n): ";
    public static final String DELETE_ALL_MATCHES_WARNING = "Si continuas se perderan PERMANENTEMENTE todos los partidos de la liga y esta se reiniciara";


    public static String tableImported(String tableName) {
        return String.format(TABLE_IMPORTED, tableName);
    }

    public static String importingTable(String tableName) {
        return String.format(IMPORTING_TABLE, tableName);
    }
}
