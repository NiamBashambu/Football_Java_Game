module niamb2023.augustusb2023.Design.Project{
    requires javafx.controls;
    requires javafx.fxml;

    opens org.headroyce.dp1 to javafx.fxml;
    exports org.headroyce.dp1;
}