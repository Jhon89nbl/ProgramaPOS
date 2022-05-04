package com.jhon89nbl.programpos.model;

import java.util.ArrayList;
import java.util.List;




public class Provider {
    private int idProvider;
    private String name;
    private String nit;
    private String phone;
    private String adress;
    private String email;
    private ArrayList<orderDay> day;
    private List<channelOrder> channel;


    public enum orderDay{
        LUNES,MARTES,MIERCOLES,JUEVES,VIERNES,SABADO
    }
    public enum channelOrder{phone,email,whatsapp,application,visitador,otros};




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

    public ArrayList<orderDay> getDay() {
        return day;
    }

    public void setDay(ArrayList<orderDay> day) {
        this.day = day;
    }

    public List<channelOrder> getChannel() {
        return channel;
    }

    public void setChannel(List<channelOrder> channel) {
        this.channel = channel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "Provider{" +
                "idProvider=" + idProvider +
                ", name='" + name + '\'' +
                ", nit='" + nit + '\'' +
                ", phone='" + phone + '\'' +
                ", adress='" + adress + '\'' +
                ", day=" + day +
                ", channel=" + channel +
                '}';
    }
}
