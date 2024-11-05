package mx.edu.uaz.is.poo2.carger.model.constants;

// ErrorKind
public enum ErrorKind {

    GENERIC(1, "Error"),
    CLASS_NOT_FOUND(2, "ClassNotFound"),
    DATABASE_NOT_FOUND(3, "DatabaseNotFound"),
    QUERY_NOT_EXECUTED(4, "QueryNotExecuted"),
    DAO_NOT_FOUND(50, "DaoNotFound"),
    ENTITY_NOT_FOUND(51, "EntityNotFound"),
    PARSE_ERROR(200, "ParseError"),
    SQLITE_ERROR(100, "SQLiteError"),
    INPUT_ERROR(201, "InputError");

    private final int code;
    private final String errName;

    private ErrorKind(int i, String errName) {
        this.code = i;
        this.errName = errName;
    }

    public int asInt() {
        return this.code;
    }

    public String getErrName() {
        return errName;
    }
}