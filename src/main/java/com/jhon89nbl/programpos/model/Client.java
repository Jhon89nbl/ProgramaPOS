package com.jhon89nbl.programpos.model;

import java.util.Date;

public class Client extends Person{
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Client(){  }
}
