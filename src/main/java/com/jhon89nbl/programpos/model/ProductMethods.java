package com.jhon89nbl.programpos.model;

import java.util.ArrayList;
import java.util.List;

public class ProductMethods {

   public List<String> fieldsEmpty(Product product){
        List<String> fields = new ArrayList<>();
        if(product.getCode().trim().isEmpty()){
            fields.add("Codigo");
        }if (product.getName().trim().isEmpty()) {
            fields.add("Nombre Producto");
        }if(product.getDescription().trim().isEmpty()){
            fields.add("Descripcion");
        }if (product.getCategory().trim().isEmpty()) {
            fields.add("Categoria");
        }if(product.getProvider().trim().isEmpty()){
            fields.add("Proveedor");
        }if (product.getAmount()==0) {
            fields.add("Cantidad");
        }if(product.getCost()==0){
            fields.add("Costo");
        }if(product.getSalePrice()==0){
            fields.add("Precio Venta");
       }
    return fields ;
    }

}
