package com.abyte.proyecto2021.Class;


public class Mascota {

    private String tipoMascota, barrioDeparta, description,foto,user;


    private boolean expandido=false;

    public boolean isExpandido() {
        return expandido;
    }

    public void setExpandido(boolean expandido) {
        this.expandido = expandido;
    }


    public Mascota() {}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTipoMascota() {
        return tipoMascota;
    }

    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public String getBarrioDeparta() {
        return barrioDeparta;
    }

    public void setBarrioDeparta(String barrioDeparta) {
        this.barrioDeparta = barrioDeparta;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


}
