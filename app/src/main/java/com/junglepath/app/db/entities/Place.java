package com.junglepath.app.db.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Place implements Parcelable {
    private String descripcion;
    private String direccion;

    private ArrayList<String> imagenes;

    private String latitude;
    private String longitud;
    private String nombre;
    private String telefono;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.descripcion);
        dest.writeString(this.direccion);
        dest.writeStringList(this.imagenes);
        dest.writeString(this.latitude);
        dest.writeString(this.longitud);
        dest.writeString(this.nombre);
        dest.writeString(this.telefono);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.descripcion = in.readString();
        this.direccion = in.readString();
        this.latitude = in.readString();
        this.longitud = in.readString();
        this.nombre = in.readString();
        this.telefono = in.readString();
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
