package com.jhon89nbl.programpos.model;

public class User extends Person {
    private String user;
    private int idRol;
    private static User INSTANCE;
    private User(){
        super();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public static User getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new User();
        }
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", idRol=" + idRol +
                '}';
    }
}
