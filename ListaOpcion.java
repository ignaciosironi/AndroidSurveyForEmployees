package com.example.nacho.encuestadepuntaje;

public class ListaOpcion {

    private int IDOpcion;
    private String Descripcion;
    private boolean EstadoActividad;
    private int Nivel;
    public ListaOpcion() {
    }

    public ListaOpcion(int IDOpcion, String descripcion, boolean estadoActividad, int nivel) {
        this.IDOpcion = IDOpcion;
        Descripcion = descripcion;
        EstadoActividad = estadoActividad;
        Nivel = nivel;

    }


    public int getIDOpcion() {
        return IDOpcion;
    }

    public void setIDOpcion(int IDOpcion) {
        this.IDOpcion = IDOpcion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public boolean isEstadoActividad() {
        return EstadoActividad;
    }

    public void setEstadoActividad(boolean estadoActividad) {
        EstadoActividad = estadoActividad;
    }

    public int getNivel() {
        return Nivel;
    }

    public void setNivel(int nivel) {
        Nivel = nivel;
    }

}
