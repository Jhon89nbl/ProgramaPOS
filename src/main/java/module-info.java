module com.jhon89nbl.programpos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;
    requires javafx.swing;
    requires poi;
    //requires javafx.swing;

    opens com.jhon89nbl.programpos to javafx.fxml;
    exports com.jhon89nbl.programpos;
    exports com.jhon89nbl.programpos.controller;
    opens com.jhon89nbl.programpos.controller to javafx.fxml;
    opens com.jhon89nbl.programpos.model to javafx.fxml;
    exports com.jhon89nbl.programpos.model;

}