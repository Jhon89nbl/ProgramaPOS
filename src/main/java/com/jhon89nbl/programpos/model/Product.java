package com.jhon89nbl.programpos.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private String name;
    private String code;
   /* private String descripcion;
    private String categoria;
    private String proveedor;
    private String cantidad;
    private String costo;
    private String precioVenta;
    private boolean iva;
    private String ivaPercent;
    private String foto;*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   /* public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isIva() {
        return iva;
    }

    public void setIva(boolean iva) {
        this.iva = iva;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getIvaPercent() {
        return ivaPercent;
    }

    public void setIvaPercent(String ivaPercent) {
        this.ivaPercent = ivaPercent;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }*/

    public Product() {
    }

    public Product(String codigo, String nombre ){ //, String descripcion, String categoria, String proveedor, String cantidad, String costo, String precioVenta, boolean iva, String ivaPercent, String foto) {
        this.name = codigo;
        this.code = nombre;
       /* this.descripcion = descripcion;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.costo = costo;
        this.precioVenta = precioVenta;
        this.iva = iva;
        this.ivaPercent = ivaPercent;
        this.foto = foto;*/
    }

    public static ObservableList<Product> productList(Product product){
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(product);
        return products;
    }
}
