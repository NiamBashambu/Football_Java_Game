module niamb2023.augustusb2023.Design.Project{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    opens org.headroyce.dp1 to javafx.fxml;
    exports org.headroyce.dp1;
}