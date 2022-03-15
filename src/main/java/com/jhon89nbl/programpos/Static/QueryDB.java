package com.jhon89nbl.programpos.Static;

public class QueryDB {
    public static final String CONSULT_LOGIN = """
                                               select per.idUser as id, per.name as name, per.lastname as lastName, emp.user as user,ro.idRol as rol
                                               from dbprogramaccount.employee as emp, dbprogramaccount.person as per, dbprogramaccount.role as ro
                                               where emp.person_idUser = per.idUser and emp.role_idRol= ro.idRol and emp.user = binary '%s' and
                                               aes_decrypt(emp.user_password,emp.user) = binary'%s';""";

    public static final String CONSULT_PROVIDER_COMBO= """
                                                select idprovider, name
                                                from dbprogramaccount.provider;
                                                """;

    public static final String CONSULT_CATEGORY_COMBO= """
                                                select idcategoria, categoria,max_profit_percentage, min_profit_percentage
                                                from dbprogramaccount.category;
                                                """;

}
