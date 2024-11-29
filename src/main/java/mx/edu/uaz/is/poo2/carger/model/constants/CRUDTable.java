package mx.edu.uaz.is.poo2.carger.model.constants;

// DBTableNames
public enum CRUDTable {
    PLAYER("Jugador"),
    TEAM("Equipo"),
    MATCH("Partido"),
    EVENT("Evento");

    private final String name;

    private CRUDTable(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
