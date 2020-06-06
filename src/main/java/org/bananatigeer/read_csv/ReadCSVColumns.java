package org.bananatigeer.read_csv;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.bananatigeer.get_csv_file.CSVFile;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class ReadCSVColumns {
    //this class reads and returns records with more than 10 columns

    private List<String[]> records;

    public void loadCSV() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        records = parser.parseAll(new File(CSVFile.getPath()));

        cleanRecords();

    }

    private void cleanRecords(){
        //modifies record so that only records with extra columns remain
        for(Iterator<String[]> iterator = records.iterator(); iterator.hasNext();){
            String[] i = iterator.next();
            if(i[10] == null && i[11] == null && i[12] == null && i[13] == null && i[14] == null){
                iterator.remove();
            }
        }

    }

    public List<String[]> getInvalidColumnsList(){
        return records;
    }
}
