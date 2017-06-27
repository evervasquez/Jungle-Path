/*
 *
 *  * Copyright (C) 2016 eveR Vásquez.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.junglepath.app.db.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {
    private String descripcion;
    private String direccion;
    private String imagen;
    private Double latitude;
    private Double longitud;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
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
        dest.writeString(this.imagen);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitud);
        dest.writeString(this.nombre);
        dest.writeString(this.telefono);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.descripcion = in.readString();
        this.direccion = in.readString();
        this.imagen = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitud = (Double) in.readValue(Double.class.getClassLoader());
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
