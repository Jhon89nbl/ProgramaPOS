package com.jhon89nbl.programpos.model;

public class Provider {
    private int idProvider;
    private String name;
    private String nit;
    private String phone;
    private String adress;
    private enum orderDay{
        Lunes(1),Martes(2),Miercoles(3),Jueves(4),Viernes(5),Sabado(6),Domingo(7);
        private int day;

        private orderDay(int value){
            this.day = value;
        }
    }
    private enum channelOrder{phone,email,whatsapp,application,visitador,otros};

    public int getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(int idProvider) {
        this.idProvider = idProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Provider() {
    }

    public Provider(int idProvider, String name, String nit, String phone, String adress) {
        this.idProvider = idProvider;
        this.name = name;
        this.nit = nit;
        this.phone = phone;
        this.adress = adress;

    }
}
