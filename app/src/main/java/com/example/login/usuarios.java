package com.example.login;

public class usuarios {

    private String uid;
    private String Nombre;
    private String Rut;
    private String Telefono;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getRut() { return Rut; }

    public void setRut(String rut) {Rut = rut;}

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
