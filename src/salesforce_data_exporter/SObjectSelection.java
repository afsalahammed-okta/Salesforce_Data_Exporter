/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesforce_data_exporter;

import static salesforce_data_exporter.Main.listSObjectField;
import salesforce_data_exporter.SObjectSelection.sObjects;
import com.sforce.ws.ConnectionException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Afzy
 */
public class SObjectSelection extends javax.swing.JFrame {

    /**
     * Creates new form SObjectSelection
     */
    public static SObjectSelection SObjectSelection = new SObjectSelection();
    public static String sObjectName;
    public static SQueryFieldSelection sField;
    public int columPostn = 0;
    private final String sObjectSelectionError;
    private final String sMultipleObjectSelectionError;
    // public DefaultTableModel model;

    public SObjectSelection() {

        initComponents();
        this.sObjectSelectionError="Please select Object first";
        this.sMultipleObjectSelectionError="Multiple Object selection is not allowed";
        sObjectName = "";
        loadJTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tSObjects = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        bNext = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        lMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tSObjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "API Name", "Object Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tSObjects.setColumnSelectionAllowed(true);
        tSObjects.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tSObjects.setFocusCycleRoot(true);
        tSObjects.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tSObjects.setShowGrid(true);
        tSObjects.setShowHorizontalLines(false);
        tSObjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tSObjectsMouseClicked(evt);
            }
        });
        tSObjects.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tSObjectsKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tSObjectsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tSObjectsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tSObjects);
        tSObjects.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("Step 1: Select Data Object");

        bNext.setText("Next");
        bNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNextActionPerformed(evt);
            }
        });

        bCancel.setText("Cancel");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        lMessage.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bNext)
                        .addGap(18, 18, 18)
                        .addComponent(bCancel))
                    .addComponent(lMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bNext)
                    .addComponent(bCancel))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNextActionPerformed
        // TODO add your handling code here:
        
        if (tSObjects.getSelectedRow() == -1) {
            System.out.println("Inside Row Count -1");
            this.lMessage.setText(sObjectSelectionError);
        } else if (tSObjects.getSelectedRowCount() > 1) {
            this.lMessage.setText(sMultipleObjectSelectionError);
        } else {
            this.bNext.setEnabled(false);
            System.out.println("Inside Else part of Row Count -1");
            DefaultTableModel model = (DefaultTableModel) tSObjects.getModel();

            sObjectName = tSObjects.getValueAt(tSObjects.getSelectedRow(), 0).toString();
            QuerySalesforce.sQueryObject = sObjectName;
            Main.describeGlobalObjectField(sObjectName);
            System.out.println("List SObjectResult:" + listSObjectField);

            Login.sObj.setVisible(false);
            //SQueryFieldSelection sObjField =  new SQueryFieldSelection();
            sField = new SQueryFieldSelection();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            sField.setLocation(dim.width / 2 - sField.getSize().width / 2, dim.height / 2 - sField.getSize().height / 2);
            sField.setTitle("Field Filter List");
            if(!Login.sObj.isShowing() && !sField.isShowing())
                sField.setVisible(true);
            this.bNext.setEnabled(true);
        }
    }//GEN-LAST:event_bNextActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        // TODO add your handling code here:
        // SObjectSelection.dispose();
        try {
            // TODO add your handling code here:
            Main.logout();
        } catch (ConnectionException ex) {
            lMessage.setText(Main.connectionError);
        }
        System.exit(0);
    }//GEN-LAST:event_bCancelActionPerformed

    private void tSObjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tSObjectsMouseClicked
        // TODO add your handling code here:
        sObjectName = tSObjects.getValueAt(tSObjects.getSelectedRow(), 0).toString();
       
        
        System.out.println("onMouseSelection Object Name"+sObjectName);
        columPostn = tSObjects.getSelectedColumn();
        this.bNext.setEnabled(true);
        System.out.println("columPostn:"+columPostn);
    }//GEN-LAST:event_tSObjectsMouseClicked

    private void tSObjectsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tSObjectsKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_tSObjectsKeyTyped

    private void tSObjectsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tSObjectsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tSObjectsKeyPressed

    private void tSObjectsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tSObjectsKeyReleased
        // TODO add your handling code here:
        sObjectName = tSObjects.getValueAt(tSObjects.getSelectedRow(), tSObjects.getSelectedColumn()).toString();
       // System.out.println(String.valueOf(evt.getKeyChar()));
        String sUserTypeChar = String.valueOf(evt.getKeyChar());
        
        DefaultTableModel model = (DefaultTableModel) tSObjects.getModel();
        String sColumnValue = "";
        int selectedRow = -1;//before start
        
       // System.out.println(model.getRowCount());
       // System.out.println("Selected Row:"+tSObjects.getSelectedRow());
        int startRow = selectedRow;
        
        if (selectedRow == model.getRowCount() - 1) {
            startRow = -1;//Go before start
        }
        int newRowNo;
      //  System.out.println("From new function:"+newRowNo);
        newRowNo = salesforce_data_exporter.SearchJtable.searchJTab(tSObjects,String.valueOf(evt.getKeyChar()),tSObjects.getSelectedRow(),tSObjects.getSelectedColumn());
        
            tSObjects.setRowSelectionInterval(newRowNo, newRowNo);
            tSObjects.scrollRectToVisible(new Rectangle(tSObjects.getCellRect(newRowNo, newRowNo, true)));
            sObjectName = tSObjects.getValueAt(tSObjects.getSelectedRow(), tSObjects.getSelectedColumn()).toString();
          
        //for (int row = startRow + 1; row < model.getRowCount(); row++) {
       // sObjectName ="";
       /*for (int row = tSObjects.getSelectedRow() + 1; row < model.getRowCount(); row++) {
            sColumnValue = (String) model.getValueAt(row, tSObjects.getSelectedColumn());
            //System.out.println("Comparing" +sColumnValue+" sObjectName "+sObjectName+" "+ sColumnValue.compareTo(sObjectName));
                if ((sColumnValue.startsWith(sUserTypeChar.toLowerCase()) || sColumnValue.startsWith(sUserTypeChar.toUpperCase()) )) {
               // System.out.println("Inside Loop sColumnValue:"+sColumnValue + " Row No:"+row);
                tSObjects.setRowSelectionInterval(row, row);
                tSObjects.scrollRectToVisible(new Rectangle(tSObjects.getCellRect(row, row, true)));
                sObjectName = tSObjects.getValueAt(tSObjects.getSelectedRow(), tSObjects.getSelectedColumn()).toString();
                return;
                }
        }
        if(sObjectName.isEmpty())
        {
                for (int row = startRow  + 1; row < model.getRowCount(); row++) {
                sColumnValue = (String) model.getValueAt(row, tSObjects.getSelectedColumn());
                //System.out.println("Comparing" +sColumnValue+" sObjectName "+sObjectName+" "+ sColumnValue.compareTo(sObjectName));
                    if ((sColumnValue.startsWith(sUserTypeChar.toLowerCase()) || sColumnValue.startsWith(sUserTypeChar.toUpperCase()) )) {
                    //System.out.println("Inside Loop sColumnValue:"+sColumnValue + " Row No:"+row);
                    tSObjects.setRowSelectionInterval(row, row);
                    tSObjects.scrollRectToVisible(new Rectangle(tSObjects.getCellRect(row, row, true)));
                    sObjectName = tSObjects.getValueAt(tSObjects.getSelectedRow(), tSObjects.getSelectedColumn()).toString();
                    return;
                    }
            }
        }*/
    }//GEN-LAST:event_tSObjectsKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SObjectSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SObjectSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SObjectSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SObjectSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SObjectSelection.setVisible(true);

            }
        });
    }

    public void loadJTable() {
        System.out.println("loadJTable");
        Main.describeGlobalObjects();;
        DefaultTableModel model = (DefaultTableModel) tSObjects.getModel();
        ArrayList<sObjects> list = LisSObjects();

        Object rowData[] = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).sAPIName;
            rowData[1] = list.get(i).sObjectName;
            model.addRow(rowData);
        }

    }

    public class sObjects {

        public String sObjectName;
        public String sAPIName;

        public sObjects(String OName, String APIName) {
            this.sObjectName = OName;
            this.sAPIName = APIName;
        }
    }

    public ArrayList LisSObjects() {
        ArrayList<sObjects> list = new ArrayList<sObjects>();

        sObjects sObInst;
        for (int i = 0; i < Main.listSObject.size(); i++) {
            sObInst = new sObjects(Main.mapSObject.get(Main.listSObject.get(i)), Main.listSObject.get(i));
            list.add(sObInst);
        }

        return list;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bNext;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lMessage;
    private javax.swing.JTable tSObjects;
    // End of variables declaration//GEN-END:variables
}