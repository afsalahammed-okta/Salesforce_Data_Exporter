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
//import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.partner.DescribeGlobalResult;
//import com.sforce.soap.enterprise.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static  String ENDPOINTURL="";
    public static  String USERNAME ="";
    public static  String PASSWORD ="";
    //public static  String ENDPOINTURL;
    static PartnerConnection connection;
    //public static com.sforce.soap.enterprise.DescribeGlobalSObjectResult[] sobjects = new com.sforce.soap.enterprise.DescribeGlobalSObjectResult[0];
    
    public static List<String> listSObjectField = new ArrayList<String>();
    public static List<String> listSObjectResultField = new ArrayList<String>();
    public static List<String> listSObject = new ArrayList<String>();
    
    public static Map<String, String> mapSObject = new HashMap<String, String>();
    public static Map<String, String> mapSField = new HashMap<String, String>();
    public static Map<String, String> mapObjectField = new HashMap<String, String>();
    
    public static Set<String> setObjectSort = new HashSet<String>();
    public static Set<String> setFieldSort = new HashSet<String>();
    public static Set<String> setResultFieldSort = new HashSet<String>();
    
    private static final String sLoginErrorMessage = "Please check your login credentials and service URL";
    private static final String sObjectExtractionError = "Object Extraction has been failed";
    private static final String sFieldExtractionError = "Field Extraction has been failed";
    private static final String sLogoutError = "Logout Operation Failed";
    private static final String sId = "Id";
    public static String connectionError;
    public static String sSessionId = null;
    public static String sInstanceURL = null;
    public static Boolean sTraceTraffic = false;

    public static void main(String[] args) throws FileNotFoundException {

        ConnectorConfig config = new ConnectorConfig();

        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setAuthEndpoint(ENDPOINTURL);
        //config.setTraceMessage(true);
        //config.setTraceFile("/Users/Afzy/NetBeansProjects/JavaApplication2/test.csv");
        //config.setTraceMessage(true);

        try {

            System.out.println("USERNAME: " + USERNAME);
            System.out.println("PASSWORD: " + PASSWORD);
            config.setPrettyPrintXml(sTraceTraffic);
            config.setTraceMessage(sTraceTraffic);
            
            String sCurrent = new java.io.File( "." ).getCanonicalPath();
            if(sTraceTraffic)
            {
                String traceFilePath = sCurrent + "//TraceFile_FileSearch.txt"; 
                config.setTraceFile(traceFilePath);
            }
            
            connection = Connector.newConnection(config);

            // display some current settings
            System.out.println("Auth EndPoint: " + config.getAuthEndpoint());
            System.out.println("Service EndPoint: " + config.getServiceEndpoint());
            System.out.println("Username: " + config.getUsername());
            System.out.println("SessionId: " + config.getSessionId());
            System.out.println("ConnectionHeader: " + config.getHeaders());
            sSessionId = config.getSessionId();
            sInstanceURL = config.getServiceEndpoint();
            
        } catch (ConnectionException e) {
            System.out.println("Inside Connection Exception:"+e.getMessage());
            connectionError = sLoginErrorMessage;
            System.out.print(connectionError);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void describeGlobalObjects() {
        try {
            // Make the describeGlobal() call
            System.out.println("Connection Time Stamp "+connection.getServerTimestamp());
            DescribeGlobalResult describeGlobalResult = connection.describeGlobal();

            // Get the sObjects from the describe global result
            DescribeGlobalSObjectResult[] sobjectResults
                    = describeGlobalResult.getSobjects();

            // Write the name of each sObject to the console
            for (int i = 0; i < sobjectResults.length; i++) {
                //setObjectSort.add(sobjectResults[i].getName());
                if (sobjectResults[i].isQueryable()) {
                    mapSObject.put(sobjectResults[i].getName(), sobjectResults[i].getLabel());
                }
            }
            
           Map<String, String> map = new TreeMap<String, String>(mapSObject); 
           // System.out.println("After Sorting:");
            Set set2 = map.entrySet();
            Iterator iterator2 = set2.iterator();
            while(iterator2.hasNext()) {
              Map.Entry me2 = (Map.Entry)iterator2.next();
              //System.out.print(me2.getKey() + ": ");
              //System.out.println(me2.getValue());
            }
            
            setObjectSort.addAll(mapSObject.keySet());
            listSObject = new ArrayList(new TreeSet(setObjectSort));
            //System.out.println("mapSObject:" + mapSObject);
            //System.out.println("setObjectSort:" + setObjectSort);

        } catch (ConnectionException ce) {
            connectionError = sFieldExtractionError;
        }
        //System.out.println("List SObjectResult:" + listSObject);
    }

    public static void describeGlobalObjectField(String sObjectName) {
        try {
            Field f;
            mapObjectField.clear();
            System.out.println("Inside Global Object Field for Object:" + sObjectName);
            DescribeSObjectResult describeSObjectResult = connection.describeSObject(sObjectName);
                
            if (describeSObjectResult != null) {
                Field[] fields = describeSObjectResult.getFields();
                for (int i = 0; i < fields.length; i++) {
                    f = fields[i];
                    
                    listSObjectField.add(f.getName());
                    mapSField.put(f.getName(), f.getLabel());
                    if (f.getExternalId() || f.getNameField() || f.getAutoNumber() || f.getUnique() || f.getName().equals(sId)) {
                        mapObjectField.put(f.getName(), f.getType().toString());
                    }
                }
            }
            
            setFieldSort.addAll(mapObjectField.keySet());
            listSObjectField = new ArrayList(new TreeSet(setFieldSort));

            setResultFieldSort.addAll(mapSField.keySet());
            listSObjectResultField = new ArrayList(new TreeSet(setResultFieldSort));

        } catch (ConnectionException e) {
            connectionError = sObjectExtractionError;
        }
    }

    public static void logout() throws ConnectionException {
        try {
            connection.logout();
        } catch (ConnectionException e) {
            connectionError = sLogoutError;
        }

    }
}
