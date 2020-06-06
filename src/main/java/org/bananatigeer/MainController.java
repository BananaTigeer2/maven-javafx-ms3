package org.bananatigeer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.bananatigeer.database.SqliteConnection;
import org.bananatigeer.get_csv_file.CSVFile;
import org.bananatigeer.read_csv.ReadCSVBeanListProcessor;
import org.bananatigeer.read_csv.ReadCSVColumns;
import org.bananatigeer.read_csv.ValidCSVs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;

public class MainController {

    @FXML
    private Label lbl_path;

    @FXML
    private Label lbl_savePath;

    @FXML
    private Button btn_parseFile;



    @FXML
    public void processFile(){
        ReadCSVBeanListProcessor ob1 = new ReadCSVBeanListProcessor();
        ReadCSVColumns ob2 = new ReadCSVColumns();
        ValidCSVs x = new ValidCSVs(ob1, ob2);
        x.process();


        final Logger logger = LoggerFactory.getLogger(ValidCSVs.class);
        SqliteConnection test = new SqliteConnection(x.getCustomers());
        //ResultSet rs;

        try{
            test.initialiseDatabase();
        }catch(SQLException e){
            logger.error("SQLException exception");
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            logger.error("ClassNotFound exception");
            e.printStackTrace();
        }

        btn_parseFile.setDisable(true);
        lbl_path.setTextFill(Color.GREEN);
        lbl_savePath.setTextFill(Color.GREEN);
        lbl_path.setText("Operation complete!");
        lbl_savePath.setText("Files saved at \n" + System.getProperty("user.dir"));

    }

    @FXML
    public void loadFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV FILES", "*.csv"));

        File file = fileChooser.showOpenDialog(null);

        if(file != null){
            CSVFile.setPath(file.getAbsolutePath());
            CSVFile.setName(file.getName());
            lbl_path.setTextFill(Color.BLACK);
            lbl_path.setText(file.getName());
            btn_parseFile.setDisable(false);
        }
    }
}
