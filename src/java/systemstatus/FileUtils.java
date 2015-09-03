package systemstatus;

 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
 
 
/**
 * Utils class for interacting with files
 * @author slenihan
 *
 */
public class FileUtils {
	
    static Logger logger = Logger.getLogger(FileUtils.class);
     
    /**
     * Reads an entire text file and returns its content as a String.
     * @param f The file to read.
     * @return The file contents.
     */
    public static String readFile(File f) throws IOException {
        
    	Reader r = new BufferedReader(new FileReader(f));
        return getFileContent(r);
    }
 
    /**
     * Reads an entire text file and returns its content as a String.
     * @param fileName The name of the file to read.
     * @return The file contents.
     * @throws IOException
     */
    public static String readFile(String fileName) throws IOException {
        
    	Reader r = new BufferedReader(new FileReader(fileName));
        return getFileContent(r);
    }
     
     
    private static String getFileContent(Reader r) throws IOException {
       
    	StringBuilder s = new StringBuilder();
        int c;
        while ((c = r.read()) != -1) {
            s.append((char) c);
        }
        r.close();
        return s.toString();
    }
     
    /**
     * Helper method to get the size of a file.
     * @param fileName The name of the file.
     * @return file size.
     */
    public static long getFileSize(String fileName) {
       
    	return new File(fileName).length();
    }
     
    /**
     * Run a regular expression check against a text file.
     * @param fileName The name of the file to run the regular expression search against.
     * @param regex    The regular expression to search for.
     * @return         true if regular expression matches found in the file, false otherwise.
     */
    public static boolean isRegexInFile(String fileName, String regex) throws Exception{
        logger.debug("Entering - fileName [" + fileName + "], Regex [" + regex + "]");
         
        String fileContent = readFile(fileName);
        boolean found = isRegexInString(fileContent, regex);
         
        logger.debug("Leaving - Returning [" + (found ? "true" : "false") + "]");
        return found;
    }
        
    public static boolean isRegexInString(String strToSearch, String regex) {
        
        // Run the regex comparison
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strToSearch);
         
        boolean found = false;
        while (matcher.find()) {
            logger.debug("Found the text \"" + matcher.group() + "\" starting at index " + matcher.start() + " and ending at index " + matcher.end() + ".");
            found = true;
        }
        if(!found){
            logger.debug("No match found.");
        }
        
        return found;
    }
     
    public static boolean writeToFile(File file, String textToWrite, boolean append) {
        if(!append) {
            file.delete();
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file, append));
            out.write(textToWrite);
            
            if(append) {
                out.newLine();
            }
            
            out.close();
            return true;
        } catch (IOException e) {
            logger.error("error writing to file: " + e);
            return false;
        }
    }
    
    /**
     * Extract map from key value pair csv file
     * @param pathToCsv
     * @param column1
     * @param column2
     * @return
     */
    public static HashMap<String, String> getKeyValuePairsFromCSV(String pathToCsv, String column1, String column2){
    	
    	Reader in = null;
    	
		try {
			in = new FileReader(pathToCsv);
		} catch (FileNotFoundException e) {
			logger.error("Can't find csv file: " + e);
			e.printStackTrace();
		}
		
    	Iterable<CSVRecord> records = null;
		
    	try {
			records = CSVFormat.EXCEL.withHeader().withSkipHeaderRecord(true).parse(in);
		} catch (IOException e) {
			logger.error("Error reading in csv file: " + e);
			e.printStackTrace();
		}
    	
    	HashMap<String, String> keyValuePairs = new HashMap<>();
    	
    	for (CSVRecord record : records) {
    	    keyValuePairs.put(record.get(column1), record.get(column2));
    	}
    	
    	return keyValuePairs;
    }
    
    /**
     * Extract a list of rows from a CSV
     * @param pathToCsv
     * @return
     */
    public static ArrayList<String[]> getListFromCSV(String pathToCsv, String... columns){
    	
    	Reader in = null;
    	
		try {
			in = new FileReader(pathToCsv);
		} catch (FileNotFoundException e) {
			logger.error("Can't find csv file: " + e);
			e.printStackTrace();
		}
		
    	Iterable<CSVRecord> records = null;
		
    	try {
			records = CSVFormat.EXCEL.withHeader().withSkipHeaderRecord(true).parse(in);
		} catch (IOException e) {
			logger.error("Error reading in csv file: " + e);
			e.printStackTrace();
		}
    	
    	ArrayList<String[]> rowList = new ArrayList<String[]>();
    	
    	for (CSVRecord record : records) {
    		
    		String[] row = new String[columns.length];
    		
    		for(int i = 0; i < columns.length; i++){
    			
    			row[i] = record.get(columns[i]);
    		}
    		
    		rowList.add(row);
    	}
    	
    	return rowList;
    }
}
