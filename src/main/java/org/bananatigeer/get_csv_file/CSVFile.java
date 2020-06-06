package org.bananatigeer.get_csv_file;

public class CSVFile {
    private static String path;
    private static String name;

    public static void setPath(String filepath){
        path = filepath;
    }

    public static void setName(String filename){
        name = filename;
    }

    public static String getPath(){
        return path;
    }

    public static String getName(){return name.replace(".csv","");}
}