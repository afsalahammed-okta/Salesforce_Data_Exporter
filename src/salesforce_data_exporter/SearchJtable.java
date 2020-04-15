/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesforce_data_exporter;

import javax.swing.table.DefaultTableModel;
//import static salesforce_data_exporter.SObjectSelection.sObjectName;

/**
 *
 * @author Afzy
 */
public class SearchJtable {

    public static int searchJTab(javax.swing.JTable searchableTable, String searchText, int selectedRow, int ColumnPostn) {

        System.out.println("Inside loop for :" + searchText + " Row Postion:" + selectedRow + "  Column Position:" + ColumnPostn);

        int returnval = 0;
        Boolean foundRec = false;
        DefaultTableModel model = (DefaultTableModel) searchableTable.getModel();
        String sColumnValue = null;

        for (int row = selectedRow + 1; row < model.getRowCount(); row++) {
            sColumnValue = (String) model.getValueAt(row, ColumnPostn);
            if ((sColumnValue.startsWith(searchText.toUpperCase()) || sColumnValue.startsWith(searchText.toLowerCase()))) {
                // System.out.println("Inside Loop sColumnValue:"+sColumnValue + " Row No:"+row);
                returnval = row;
                foundRec = true;
                break;
            }
        }

        if (!foundRec) {
            for (int row = 0; row < model.getRowCount(); row++) {
                sColumnValue = (String) model.getValueAt(row, ColumnPostn);
                
                if ((sColumnValue.startsWith(searchText.toUpperCase()) || sColumnValue.startsWith(searchText.toLowerCase()))) {
                    returnval = row;
                    foundRec = true;
                    break;
                }
            }
        }

        if (!foundRec) {
            returnval = selectedRow;
        }

        return returnval;

    }

}
