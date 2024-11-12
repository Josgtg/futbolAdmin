package mx.edu.uaz.is.poo2.carger.model.constants;

// Errors
public class Errors {
    public static final String QUERY_NOT_EXECUTED = "no se pudieron crear las tablas";
    public static final String DB_CONN_CLASS_NOT_FOUND = "no se encontró la clase para la conexión a la base de datos";
    public static final String COULD_NOT_CONNECT_TO_DB = "no se pudo conectar a la base de datos";
    public static final String ENTITY_NOT_FOUND = "no se reconoció la entidad";
    public static final String PARSE_ERROR = "error al convertir";
    public static final String INPUT_ERROR = "error al leer línea";

    public static String format(ErrorKind errCode, String message) {
        return String.format("%s: %s", errCode.getErrName(), message);
    }

    public static String format(ErrorKind errCode, String message, Exception e) {
        return String.format("%s: %s\n%s", errCode.getErrName(), message, e);
    }
}