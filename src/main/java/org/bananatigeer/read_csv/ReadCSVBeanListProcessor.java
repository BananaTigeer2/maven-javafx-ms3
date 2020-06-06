package org.bananatigeer.read_csv;

import com.univocity.parsers.common.RowProcessorErrorHandler;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import org.bananatigeer.custom_counter.CustomCounter;
import org.bananatigeer.customer.Customer;
import org.bananatigeer.get_csv_file.CSVFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadCSVBeanListProcessor {
    //reads and validates record entry

    private CustomCounter counterFail = new CustomCounter();
    private ReadCSVProcessesCount processCount = new ReadCSVProcessesCount();
    private List<Customer> validRecs;
    private List<String> partialInvalidRecs = new ArrayList<String>(); //using this to hold data from error handler
    private List<String[]> invalidRecs = new ArrayList<>(); //converted invalidRecords data and pass to ValidCSVs


    public void loadCSV(){
        BeanListProcessor<Customer> rowProcessor = new BeanListProcessor<>(Customer.class);

        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.setProcessor(rowProcessor);
        settings.setProcessorErrorHandler((RowProcessorErrorHandler) (e, objects, parsingContext) -> {
            partialInvalidRecs.add(Arrays.toString(objects));
            incrementBadRecsCount();
        });
        settings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(settings);
        parser.parse(new File(CSVFile.getPath()));

        validRecs = rowProcessor.getBeans();

        convertBadRecs();
        formatDecimals();
        setSuccessCount();
        setFailCount();

    }

    //region somestuff

    private void convertBadRecs(){
        //changing all records in partialInvalidRecs for comparison with List<String[]> types in ValidCSVs
        for(String i : partialInvalidRecs){
            invalidRecs.add(i.replaceAll("\\[","").replaceAll("\\]","").trim().split(", "));
        }
    }

    private void formatDecimals(){
        for(int i = 0; i <= invalidRecs.size()-1; i++){
            if((invalidRecs.get(i)[6].contains("."))){
                invalidRecs.get(i)[6] = "$" + invalidRecs.get(i)[6];
            }
        }
    }

    private void incrementBadRecsCount(){
        counterFail.increment();
    }

    private void setSuccessCount(){
        processCount.setSuccessCount(validRecs.size());
    };

    private void setFailCount(){
        processCount.setFailedCount(counterFail.getCount());
    };

    public List<Customer> getCustomersList(){
        return validRecs;
    }

    public List<String[]> getInvalidRecs(){
        return invalidRecs;
    }


    //endregion

}
