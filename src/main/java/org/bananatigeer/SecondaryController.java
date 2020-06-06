package org.bananatigeer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import org.bananatigeer.database.SqliteConnection;
import org.bananatigeer.read_csv.ReadCSVBeanListProcessor;
import org.bananatigeer.read_csv.ReadCSVColumns;
import org.bananatigeer.read_csv.ValidCSVs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}