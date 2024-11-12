package mx.edu.uaz.is.poo2.carger.model.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import static jakarta.persistence.CascadeType.*;

@Entity
public class Player implements IEntity, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;
    @Column
    private int edad;
    @Column
    private int partidosJugados;
    @Column
    private int goles;
    @Column
    private int asistencias;
    @Column
    private int tarjetasRojas;
    @Column
    private int tarjetasAmarillas;
    @ManyToOne(cascade={PERSIST, MERGE, REFRESH})
    private Team team;

    public Player() {
        id = null;
    }

    public Player(String nombre, int edad, Team team) {
        this.nombre = nombre;
        this.edad = edad;
        this.team = team;
        this.partidosJugados = 0;
        this.goles = 0;
        this.asistencias = 0;
        this.tarjetasRojas = 0;
        this.tarjetasAmarillas = 0;
        this.id = null;
    }

    public Player(
        String nombre, 
        int edad, 
        Team team,
        int partidosJugados,
        int goles, 
        int asistencias,
        int tarjetasRojas, 
        int tarjetasAmarillas
    ){
        this(nombre, edad, team);
        this.partidosJugados = partidosJugados;
        this.goles = goles;
        this.asistencias = asistencias;
        this.tarjetasRojas = tarjetasRojas;
        this.tarjetasAmarillas = tarjetasAmarillas;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    public void setTarjetasRojas(int tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public void setTarjetasAmarillas(int tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
