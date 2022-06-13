package com.jhon89nbl.programpos.Static;

public class QueryDB {
    public static final String CONSULT_LOGIN = """
                                               select per.idUser as id, per.name as name, per.lastname as lastName, emp.user as user, ro.idRol as rol
                                               from dbprogramaccount.employee as emp, dbprogramaccount.person as per, dbprogramaccount.role as ro
                                               where emp.person_idUser = per.idUser and emp.role_idRol= ro.idRol and emp.user = binary '%s' and
                                               aes_decrypt(emp.user_password,emp.user) = binary'%s';""";

    public static final String CONSULT_PROVIDER_COMBO= """
                                                select idprovider, name, nit, phone, adress
                                                from dbprogramaccount.provider
                                                where provider_active = 1;
                                                """;

    public static final String CONSULT_CATEGORY_COMBO= """
                                                select idcategoria, categoria,max_profit_percentage, min_profit_percentage
                                                from dbprogramaccount.category;
                                                """;


    public static final String REPEATED_PRODUCTS = """
                                                   SELECT code, name
                                                   FROM dbprogramaccount.product
                                                   WHERE code=binary'%s' or name = binary'%s'
            """;
    public static final String REPEATED_CATEGORIES = """
                                                   SELECT categoria
                                                   FROM dbprogramaccount.category
                                                   WHERE categoria='%s'
            """;

    public static final String ADD_PRODUCTS = """
                INSERT INTO dbprogramaccount.product(code,name,description,sale_price,iva,categoria_idCategoria,photo,employee_person_idUser)
                values(?,?,?,?,?,(select idcategoria
                                 from dbprogramaccount.category
                                 where categoria = binary(?)),?,?);
            """;
    public static final String ADD_CATEGORY = """
                INSERT INTO dbprogramaccount.category(categoria,min_profit_percentage,max_profit_percentage,employee_person_idUser)
                values(?,?,?,?);
            """;
    public static final String STORE = """
                INSERT INTO dbprogramaccount.store(provider_idprovider,product_code,date,cost,amount,iva_percent)
                values((select idprovider
                       from dbprogramaccount.provider
                       where name = CAST(? AS BINARY)),?,?,?,?,?);
            """;

    public static final String SEARCH_PRODUCTS = """
                                                   SELECT *
                                                   FROM dbprogramaccount.product
                                                   WHERE name like ?
                                                   """;

    public static final String CONSULT_PRODUCT_AMOUNT = """
            select CEILING( (select sum(sto.amount) FROM dbprogramaccount.store as sto where sto.product_code =?)
            -COALESCE(sum(amount),0)) as available
            from dbprogramaccount.detail_sale
            where product_code= ?;
                                                   """;
    public static final String INSERT_SALE = """
                                            INSERT INTO dbprogramaccount.sale(payment,date,employee_Person_idUser,client_Person_idUser)
                                            values(?,?,(select person_idUser
                       from dbprogramaccount.employee
                       where person_idUser = ?),(select Person_idUser
                       from dbprogramaccount.client
                       where Person_idUser = ?))
                                                   """;
    public static final String INSERT_SALE_DETAIL= """
                                            INSERT INTO dbprogramaccount.detail_sale(sale_idventa,product_code,amount)
                                            value(last_insert_id(),?,?)
                                                   """;

    public static final String CONSULT_PROVIDER_REPEAT= """
                                                select  name, nit
                                                from dbprogramaccount.provider
                                                WHERE name = '%s' or nit = '%s';
                                                """;
    public static final String INSERT_PROVIDER= """
                                            INSERT INTO dbprogramaccount.provider(name,nit,phone,adress,order_day,
                                            email,channel_order,employee_Person_idUser)
                                            values(?,?,?,?,?,?,?,?);
                                                   """;
    public static final String CODE_PRODUCT_ORDERS= """
                                            Select  distinct(st.product_code)
                                            from dbprogramaccount.store as st
                                            INNER JOIN dbprogramaccount.provider as pr on st.provider_idprovider=pr.idprovider
                                            where pr.name = CAST(? AS BINARY)
                                                """;

    public static final String CONSULT_AMOUNT_ORDER = """
                        SELECT  sum(ds.amount) as sales, pr.name
                        , (select sum(amount) from dbprogramaccount.store WHERE date BETWEEN ?  AND ? and 
                        product_code = ? ) as store
                        FROM dbprogramaccount.detail_sale as ds
                        INNER JOIN dbprogramaccount.sale as s on ds.sale_idventa = s.idventa
                        INNER JOIN dbprogramaccount.product as pr on ds.product_code = pr.code
                        WHERE s.date BETWEEN ? AND ? and ds.product_code = ?
            """;
}
