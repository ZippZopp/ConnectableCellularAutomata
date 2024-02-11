module com.zippzopp.connectebelcellularautomata {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zippzopp.connectebelcellularautomata to javafx.fxml;
    exports com.zippzopp.connectebelcellularautomata;
    exports com.zippzopp.connectebelcellularautomata.Worlds;
    opens com.zippzopp.connectebelcellularautomata.Worlds to javafx.fxml;
}