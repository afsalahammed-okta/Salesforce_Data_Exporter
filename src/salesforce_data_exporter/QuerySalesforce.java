/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesforce_data_exporter;

/**
 *
 * @author Afzy
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

//import org.json.simple.JSONValue;
public class QuerySalesforce {

    
    private static String tokenURL = null;
    private static String fieldtokenURL = null;
    private static String versionURL = null;
    public static String sInstanceURL = null;
    public static String sAccessToken = null;
    public static String sQueryObject = null;
    public static List<String> sQueryField = new ArrayList<String>();
    public static String sQuery = null;
    public static String csvFile = "";
    public static Object nextRecordURL = new Object();
    public static FileWriter writer;
    public static List<String> strings = new ArrayList<>();
    public static int iNumberofRecordExported = 0;
    public static Object oTotalSize = new Object();
    public static int returnCode = 0;
    private static JSONObject response;
    private static final String snextRecordURL = "nextRecordsUrl";
    
    public static void main(String[] args) throws IOException {
        
        HttpClient http;
        http = new HttpClient();

        
        salesforce_data_exporter.Main.main(null);
        sInstanceURL = salesforce_data_exporter.Main.sInstanceURL.substring(0, salesforce_data_exporter.Main.sInstanceURL.indexOf("/services"));
        tokenURL = sInstanceURL + "/services/data/v37.0/query";
        fieldtokenURL = sInstanceURL + "/services/data/v37.0/sobjects/Task/describe/";
        versionURL = sInstanceURL + "/services/data";
        sAccessToken = salesforce_data_exporter.Main.sSessionId;
        csvFile = "/Users/Afzy/NetBeansProjects/JavaApplication2/SITR8_Account_Extract9.csv";
        writer = new FileWriter(csvFile);
        strings = new ArrayList<>();
        SQueryResultFields.listFieldSelected.add("Id");
        for (int j = 0; j < SQueryResultFields.listFieldSelected.size(); j++) {
            strings.add(SQueryResultFields.listFieldSelected.get(j));
        }
        CSVUtils.writeLine(writer, strings, ',');
        writer.flush();
        QueryRecords(tokenURL);
        writer.close();
    }

    

    public static void QueryRecords(String url) throws FileNotFoundException {
        nextRecordURL = "nextRecordsUrl";
        oTotalSize = "totalSize";
        //tokenURL = sInstanceURL + "/services/data/v37.0/query";
        tokenURL = url;
        iNumberofRecordExported = 0;
        // int j=0;
        
        HttpClient http;
        http = new HttpClient();

        if (sQuery == null) {
            sQuery = "select Id from Account";
        }

        GetMethod get = new GetMethod(tokenURL);
        
        NameValuePair[] params = new NameValuePair[1];
        params[0] = new NameValuePair("q", sQuery);
        get.setRequestHeader("Authorization", "OAuth " + sAccessToken);
        get.setQueryString(params);
        System.out.println("get:"+get);
        System.out.println("Query:"+sQuery);
        try {

            returnCode = http.executeMethod(get);
            System.out.println("returnCode:" + returnCode);
            List<Object> list;
            JSONObject jObject;
            JSONObject menu;
            list = new ArrayList<Object>();
            strings = new ArrayList<>(list.size());

            Map<String, Object> map = new HashMap<String, Object>();
            if (get.getStatusCode() == HttpStatus.SC_OK && returnCode == 200) {

                response = new JSONObject(new JSONTokener(new InputStreamReader(get.getResponseBodyAsStream())));
                System.out.println("Response:"+response.getJSONArray("records"));
                BufferedReader br = null;
                JSONArray results = response.getJSONArray("records");
                String sNullcheck;

                for (int i = 0; i < results.length(); i++) {
                    list = new ArrayList<Object>();
                    strings = new ArrayList<>(list.size());

                    map = new HashMap<String, Object>();
                    // then
                    for (int j = 0; j < SQueryResultFields.listFieldSelected.size(); j++) {
                        list.add(results.getJSONObject(i).get(SQueryResultFields.listFieldSelected.get(j)));
                        
                    }
                    iNumberofRecordExported = iNumberofRecordExported + 1;
                    for (Object object : list) {
                        strings.add('\"' +Objects.toString(object, null)+'\"');
                    }
                    if (strings.size() > 0) {
                        CSVUtils.writeLine(writer, strings, ',');
                        writer.flush();
                    }
                }

            } 
            
            if (response != null && response.keySet().contains(nextRecordURL)) {
                tokenURL = QuerySalesforce.sInstanceURL + (String) response.get(snextRecordURL);
                QueryRecords(tokenURL);
            }

            /*if (response != null && response.keySet().contains(oTotalSize)) {
                iNumberofRecordExported = (int) response.get((String) oTotalSize);
            }*/
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }

}
