package mx.edu.uaz.is.poo2.carger.model.constants;

import static mx.edu.uaz.is.poo2.carger.model.constants.AnsiEscapeCodes.*;

public class WindowMessages {
    public static String ASK_FOR_ACTION = "Selecciona la acción a realizar: ";
    public static String ASK_FOR_OPTION_OR_RETURN = String.format(
        "Escribe el %snúmero de la acción%s a realizar o escribe %s0%s para regresar a la pantalla anterior: ",
        ANSI_GREEN, ANSI_RESET, ANSI_YELLOW, ANSI_RESET
    );
    public static String LEAGUE_OPTIONS = String.format(
        "Escribe el %snúmero del equipo%s a consultar (posición), la %sopción de ordenamiento%s, %s-1%s para ingresar como admin, %s-2%s para ver los jugadores de la liga o %s0%s para salir: ",
        ANSI_GREEN, ANSI_RESET, ANSI_PURPLE, ANSI_RESET, ANSI_YELLOW, ANSI_RESET, ANSI_BLUE, ANSI_RESET, ANSI_RED, ANSI_RESET
    );
    public static String RETURN_WINDOW = String.format("Escribe %s0%s para regresar a la ventana anterior: ", ANSI_YELLOW, ANSI_RESET);
    public static String ASK_TABLE_SELECTION = String.format(
        "Selecciona la tabla a gestionar o escribe %s0%s para regresar a la pantalla anterior: ", ANSI_YELLOW, ANSI_RESET
    );

    public static String LEAGUE_COLUMN_SORT_OPTIONS = String.format(
        "Las siguientes son opciones válidas para ordenar los equipos:\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
        "Puntos: p", "Nombre: n", "Victorias: v", "Derrotas: d", "Empates: e",
        "Goles a favor: gf", "Goles en contra: gc", "Diferencia de gol: dg",
        "Escribe un signo + o - después de la opción para ordenar en orden ascendente o descendente. (dg-, por ejemplo)"
    );

    public static String PLAYER_COLUMN_SORT_OPTIONS = String.format(
        "Las siguientes son opciones válidas para ordenar los Jugadores:\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
        "Nombre: n", "Edad: e", "Goles: g", "Asistencias: a",
        "Tarjetas amarillas: ta", "Tarjetas rojas: tr",
        "Escribe un signo + o - después de la opción para ordenar en orden ascendente o descendente. (ta-, por ejemplo)"
    );

    public static String askToUpdate(String attributeName, Object value) {
        return String.format("Valor actual de \"%s\": %s\nActualizar? (s/n): ", attributeName, value);
    }

    static public String windowTitle(String windowName) {
        return windowTitle(windowName, 60);
    }

    static public String windowTitle(String windowName, int len) {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        len -= windowName.length();
        for (int i = 0; i < len / 2; i++) sb.append('-');
        sb.append(windowName);
        for (int i = 0; i < len / 2; i++) sb.append('-');
        if (len % 2 != 0) sb.append('-');
        return sb.toString();
    }

    public static String consultOrReturn(String entityName) {
        return String.format(
            "Escribe el %snúmero del %s%s a consultar o escribe %s0%s para regresar a la pantalla anterior: ", 
            ANSI_GREEN, entityName, ANSI_RESET, ANSI_YELLOW, ANSI_RESET
        );
    }
    public static String consultOrReturnOrModify(String entityName) {
        return String.format(
            "Escribe el %snúmero del %s%s a consultar o escribe %s0%s para regresar a la pantalla anterior, escribe %s-1%s para modificar: ", 
            ANSI_GREEN, entityName, ANSI_RESET, ANSI_YELLOW, ANSI_RESET, ANSI_PURPLE, ANSI_RESET
        );
    }
    public static String selectToFillOrReturn(String entityName) {
        return String.format(
            "Escribe el %snúmero del %s%s que deseas vaciar, escribe %s-1%s para cambiar entre partidos no jugados y partidos ya jugados o escribe %s0%s para regresar a la pantalla anterior: ",
            ANSI_GREEN, entityName, ANSI_RESET, ANSI_PURPLE, ANSI_RESET, ANSI_YELLOW, ANSI_RESET
        );
    }
    public static String selectEventOrReturn = String.format(
        "Escribe el %snúmero del evento%s que ocurrió o escribe %s0%s para dejar de registrar eventos: ",
        ANSI_GREEN, ANSI_RESET, ANSI_YELLOW, ANSI_RESET
    );
}