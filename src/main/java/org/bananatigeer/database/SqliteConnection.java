package org.bananatigeer.database;

import org.bananatigeer.customer.Customer;
import org.bananatigeer.get_csv_file.CSVFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteConnection {

    private Connection con;
    private  boolean hasData = false;
    private List<Customer> customers = new ArrayList<>();

    final Logger logger = LoggerFactory.getLogger(SqliteConnection.class);

    public SqliteConnection(List<Customer> customers){
        this.customers = customers;
    }

    private void getConnection() throws ClassNotFoundException, SQLException {
        String inputName = CSVFile.getName();
        final String extension = ".db";
        String fileName = inputName+extension;

        File file = new File(inputName+extension);

        if(file.exists()){
            //appendfile name if file with similar name already exists
            int count = 0;
            while(file.exists()){
                count++;
                fileName = inputName+"("+count+")"+extension;
                file = new File(fileName);
            }

            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:"+fileName);
            logger.info("Succesfully created {}",fileName);
        }
        else{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:"+fileName);
            logger.info("Succesfully created {}", fileName);
        }
    }

    public void initialiseDatabase() throws ClassNotFoundException, SQLException{
        if(con == null){
            getConnection();
        }

        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='customer'");

        if(!res.next()){
            createTable();
            insertCustomers();
        }
    }

    private void createTable() throws SQLException{
        logger.info("Building customer table...");

        Statement state2 = con.createStatement();
        state2.execute("CREATE TABLE customer(A varchar(60)," + "B varchar(60)," + "C varchar(60)," + "D varchar(60),"
                + "E varchar(255)," + "F varchar(60)," + "G varchar(60)," + "H varchar(60)," + "I varchar(60),"
                + "J varchar(60));");
    }

    private void insertCustomers() throws SQLException{

        logger.info("Batch inserting data...");

        //using transaction to speed up insertion
        con.setAutoCommit(false);
        final int batchSize = 1000;
        int count = 0;
        long start = System.currentTimeMillis();
        String query = "INSERT into customer values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prep = con.prepareStatement(query);
        for(Customer cus : customers){
            prep.setString(1, cus.getFirstName());
            prep.setString(2, cus.getLastName());
            prep.setString(3, cus.getEmail());
            prep.setString(4, cus.getGender());
            prep.setString(5, cus.getImage());
            prep.setString(6, cus.getCompany());
            prep.setDouble(7, cus.getPrice());
            prep.setBoolean(8, cus.getH());
            prep.setBoolean(9, cus.getI());
            prep.setString(10, cus.getCity());
            prep.addBatch();
            if(++count % batchSize == 0){
                long startInternal = System.currentTimeMillis();
                prep.executeBatch();
                logger.info("each transaction time taken: {} ms",(System.currentTimeMillis() - startInternal));
            }
        }
        logger.info("Batch inserting remaining data data...");
        prep.executeBatch();
        logger.info("Batch inserted data...");
        long end = System.currentTimeMillis();
        logger.info("total time taken: {}ms",end-start);
        prep.close();
        con.setAutoCommit(true);
    }

    public ResultSet displayCustomers() throws ClassNotFoundException, SQLException{
        if(con == null){
            getConnection();
        }

        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM customer");
        return res;
    }
}
