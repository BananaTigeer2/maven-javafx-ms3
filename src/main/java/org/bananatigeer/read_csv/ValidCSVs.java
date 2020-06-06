package org.bananatigeer.read_csv;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.bananatigeer.customer.Customer;
import org.bananatigeer.get_csv_file.CSVFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ValidCSVs {

    private ReadCSVBeanListProcessor allCustomerRecs;
    private ReadCSVColumns allInvalidRecs;

    private List<Customer> validRecs;
    private List<String[]> extraColumnRecs;
    private List<String[]> invalidRecs;

    private final Logger logger = LoggerFactory.getLogger(ValidCSVs.class);

    public ValidCSVs(ReadCSVBeanListProcessor allCustomerRecs, ReadCSVColumns allInvalidRecs){
        this.allCustomerRecs = allCustomerRecs;
        this.allInvalidRecs = allInvalidRecs;
    }

    public void process(){
        allCustomerRecs.loadCSV();
        allInvalidRecs.loadCSV();

        validRecs = allCustomerRecs.getCustomersList();
        extraColumnRecs = allInvalidRecs.getInvalidColumnsList();
        invalidRecs = allCustomerRecs.getInvalidRecs();

        cleanValidRecs();
        cleanInvalidRecs();

        logger.info("Finished processing");

        createLogStatistics();
        createCSV();


    }

    public List<Customer> getCustomers(){
        return validRecs;
    }

    private void sortRecs(){
        Collections.sort(validRecs, new Comparator<Customer>() {
            @Override
            public int compare(Customer customer, Customer t1) {
                return customer.getFirstName().compareTo(t1.getFirstName());
            }
        });

        Collections.sort(extraColumnRecs, new Comparator<String[]>() {
            @Override
            public int compare(String[] strings, String[] t1) {
                return strings[0].compareTo(t1[0]);
            }
        });

        Collections.sort(invalidRecs, new Comparator<String[]>() {
            @Override
            public int compare(String[] strings, String[] t1) {
                return strings[0].compareTo(t1[0]);
            }
        });
    }

    private void cleanValidRecs(){
        //removes records in valid records with extra columns because parsing validRecords don't check extra columns
        for(int i = 0; i<= validRecs.size()-1; i++){
            for(int j = 0; j<= extraColumnRecs.size()-1; j++){
                if(validRecs.get(i).getFirstName().equals(extraColumnRecs.get(j)[0]) &&
                        validRecs.get(i).getLastName().equals(extraColumnRecs.get(j)[1]) &&
                        validRecs.get(i).getEmail().equals(extraColumnRecs.get(j)[2]) ){
                    validRecs.remove(i);
                    break;
                }
            }
        }
    }

    private void cleanInvalidRecs(){
        /*removes invalid records with extra columns for avoiding duplicates when combined with extraColumnRecs
        to write bad records in csv file */
        for(int i = 0; i<=invalidRecs.size()-1; i++){
            for(int j = 0; j<=extraColumnRecs.size()-1; j++){
                if(Objects.equals(invalidRecs.get(i)[2], extraColumnRecs.get(j)[2]) && Objects.equals(invalidRecs.get(i)[6], extraColumnRecs.get(j)[6])){
                    invalidRecs.remove(invalidRecs.get(i));
                }
            }
        }
    }

    //region export methods

    private void createCSV(){
        String inputName = CSVFile.getName();
        final String tag = "-bad";
        final String extension = ".csv";
        String fileName = inputName + tag + extension;

        try{
            File file = new File(fileName);

            if(file.exists()){
                if(file.exists()){
                    int count = 0;
                    while(file.exists()){
                        count++;
                        fileName = inputName + tag + "("+count+")" + extension;
                        file = new File(fileName);
                    }
                    file.createNewFile();
                }
            }

            CsvWriterSettings settings = new CsvWriterSettings();
            settings.setHeaders("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
            settings.setNullValue("null");
            CsvWriter writer = new CsvWriter(file, settings);
            writer.writeHeaders();

            invalidRecs.addAll(extraColumnRecs);
            sortRecs();

            List<Object[]> rows = new ArrayList<Object[]>(invalidRecs);
            writer.writeRowsAndClose(rows);

            logger.info("Succesfully created {}", fileName);

        }catch(Exception e){
            e.printStackTrace();
            logger.error("Failed creating {}: {}", fileName, e.toString());
        }


    }


    private void createLogStatistics(){
        ReadCSVProcessesCount ob3 = new ReadCSVProcessesCount();
        ob3.setFailedCount(invalidRecs.size() + extraColumnRecs.size());
        ob3.setSuccessCount(validRecs.size());

        String inputName = CSVFile.getName();
        final String extension = ".log";
        String fileName = inputName + extension;

        File file = new File(fileName);

        try{
            if(file.exists()){
                int count = 0;
                while(file.exists()){
                    count++;
                    fileName = inputName+"("+count+")"+extension;
                    file = new File(fileName);
                }
                file.createNewFile();
            }

            PrintWriter pw = new PrintWriter(file);
            pw.println("# of Records received: " + ob3.getReceived());
            pw.println("# of Records failed: " + ob3.getFailedCount());
            pw.println("# of Records succeeded: " + ob3.getSuccessCount());
            pw.close();

            logger.info("Successfully created {}", fileName);
        }
        catch(IOException e){
            logger.error("Failed creating log");
            e.printStackTrace();

        }
    }

    //endregion
}
