/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.time.DayOfWeek;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Mihailo
 */
public class FrmMovie extends javax.swing.JDialog {
    private DatePicker datePicker;
    
    /**
     * Creates new form FrmMovie
     */
    public FrmMovie(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initDatePicker();
        txtDescription.setWrapStyleWord(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMovieID = new javax.swing.JLabel();
        txtMovieID = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblReleaseDate = new javax.swing.JLabel();
        lblDirector = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        cbDirector = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblScore = new javax.swing.JLabel();
        txtScore = new javax.swing.JTextField();
        btnEnableChanges = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanelDatePicker = new javax.swing.JPanel();
        btnUploadPoster = new javax.swing.JButton();
        lblPoster = new javax.swing.JLabel();
        lblCharactersRemaining = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRoles = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cbActors = new javax.swing.JComboBox<>();
        lblRoleName = new javax.swing.JLabel();
        txtRoleName = new javax.swing.JTextField();
        btnRemoveAllRoles = new javax.swing.JButton();
        btnRemoveActor = new javax.swing.JButton();
        btnAddActor = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMovieGenre = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cbGenres = new javax.swing.JComboBox<>();
        btnRemoveGenre = new javax.swing.JButton();
        btnAddGenre = new javax.swing.JButton();
        btnRemoveAllMovieGenres = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblProduction = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cbProductionCompanies = new javax.swing.JComboBox<>();
        btnRemoveProductionCompany = new javax.swing.JButton();
        btnAddProductionCompany = new javax.swing.JButton();
        btnRemoveAllProductions = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtDuration = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lblMovieID.setText("ID:");

        lblName.setText("Name:");

        lblReleaseDate.setText("Rel. date:");

        lblDirector.setText("Director:");

        lblDescription.setText("Description:");

        cbDirector.setMaximumSize(new java.awt.Dimension(64, 22));
        cbDirector.setPreferredSize(new java.awt.Dimension(64, 22));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(50, 20));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 80));

        txtDescription.setColumns(20);
        txtDescription.setLineWrap(true);
        txtDescription.setRows(5);
        txtDescription.setMaximumSize(new java.awt.Dimension(70, 20));
        txtDescription.setMinimumSize(new java.awt.Dimension(70, 20));
        txtDescription.setPreferredSize(new java.awt.Dimension(64, 84));
        jScrollPane1.setViewportView(txtDescription);
        txtDescription.getAccessibleContext().setAccessibleParent(this);

        btnAdd.setText("Add");

        btnUpdate.setText("Update");
        btnUpdate.setEnabled(false);

        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);

        lblScore.setText("Score:");

        txtScore.setPreferredSize(new java.awt.Dimension(338, 22));

        btnEnableChanges.setText("Enable changes");

        btnCancel.setText("Cancel");

        jPanelDatePicker.setPreferredSize(new java.awt.Dimension(64, 25));
        jPanelDatePicker.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnUploadPoster.setText("Upload poster");
        btnUploadPoster.setEnabled(false);

        lblPoster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblCharactersRemaining.setText("Characters remaining: ");

        tblRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First name", "Last name", "Role name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblRoles);

        jLabel1.setText("Actor:");

        lblRoleName.setText("Role name:");

        btnRemoveAllRoles.setText("Remove all");
        btnRemoveAllRoles.setEnabled(false);

        btnRemoveActor.setText("Remove");
        btnRemoveActor.setEnabled(false);

        btnAddActor.setText("Add");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblRoleName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRoleName, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbActors, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(btnAddActor)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRemoveAllRoles)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveActor)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbActors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnAddActor))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRoleName)
                    .addComponent(txtRoleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveActor)
                    .addComponent(btnRemoveAllRoles))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Actors", jPanel1);

        tblMovieGenre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Genre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblMovieGenre);

        jLabel2.setText("Genre:");

        btnRemoveGenre.setText("Remove");
        btnRemoveGenre.setEnabled(false);

        btnAddGenre.setText("Add");

        btnRemoveAllMovieGenres.setText("Remove all");
        btnRemoveAllMovieGenres.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRemoveAllMovieGenres)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveGenre))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbGenres, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnAddGenre)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbGenres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddGenre))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveGenre)
                    .addComponent(btnRemoveAllMovieGenres))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Genres", jPanel2);

        tblProduction.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Production co. name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblProduction);

        jLabel3.setText("Production co:");

        btnRemoveProductionCompany.setText("Remove");
        btnRemoveProductionCompany.setEnabled(false);

        btnAddProductionCompany.setText("Add");

        btnRemoveAllProductions.setText("Remove all");
        btnRemoveAllProductions.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(cbProductionCompanies, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnAddProductionCompany)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRemoveAllProductions)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveProductionCompany)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbProductionCompanies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddProductionCompany))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveAllProductions)
                    .addComponent(btnRemoveProductionCompany))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Production companies", jPanel3);

        jLabel4.setText("Duration:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblMovieID, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMovieID, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblReleaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jPanelDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                            .addComponent(txtDuration))))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblScore, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDirector, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbDirector, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCharactersRemaining)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 33, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnCancel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnEnableChanges)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnDelete)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnAdd))
                        .addComponent(lblPoster, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnUploadPoster, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMovieID)
                            .addComponent(txtMovieID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDirector)
                            .addComponent(cbDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblScore)
                            .addComponent(txtScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblReleaseDate)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblDescription)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addComponent(lblCharactersRemaining))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jPanelDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPoster, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUploadPoster)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnCancel)
                    .addComponent(btnEnableChanges)
                    .addComponent(btnDelete)
                    .addComponent(btnAdd))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddActor;
    private javax.swing.JButton btnAddGenre;
    private javax.swing.JButton btnAddProductionCompany;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEnableChanges;
    private javax.swing.JButton btnRemoveActor;
    private javax.swing.JButton btnRemoveAllMovieGenres;
    private javax.swing.JButton btnRemoveAllProductions;
    private javax.swing.JButton btnRemoveAllRoles;
    private javax.swing.JButton btnRemoveGenre;
    private javax.swing.JButton btnRemoveProductionCompany;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUploadPoster;
    private javax.swing.JComboBox<Object> cbActors;
    private javax.swing.JComboBox<Object> cbDirector;
    private javax.swing.JComboBox<Object> cbGenres;
    private javax.swing.JComboBox<Object> cbProductionCompanies;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelDatePicker;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCharactersRemaining;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblDirector;
    private javax.swing.JLabel lblMovieID;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPoster;
    private javax.swing.JLabel lblReleaseDate;
    private javax.swing.JLabel lblRoleName;
    private javax.swing.JLabel lblScore;
    private javax.swing.JTable tblMovieGenre;
    private javax.swing.JTable tblProduction;
    private javax.swing.JTable tblRoles;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtMovieID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtRoleName;
    private javax.swing.JTextField txtScore;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnEnableChanges() {
        return btnEnableChanges;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JComboBox<Object> getCbDirector() {
        return cbDirector;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public JLabel getLblDescription() {
        return lblDescription;
    }

    public JLabel getLblMovieId() {
        return lblMovieID;
    }

    public JLabel getLblDirector() {
        return lblDirector;
    }

    public JLabel getLblName() {
        return lblName;
    }

    public JTextArea getTxtDescription() {
        return txtDescription;
    }

    public JTextField getTxtMovieID() {
        return txtMovieID;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public JLabel getLblReleaseDate() {
        return lblReleaseDate;
    }
    
    public DatePicker getReleaseDate() {
        return datePicker;
    }
    
    public JLabel getLblScore() {
        return lblScore;
    }
    
    public JTextField getTxtScore() {
        return txtScore;
    }
    
    public JPanel getJPanelDatePicker() {
        return jPanelDatePicker;
    }

    public JLabel getLblCharactersRemaining() {
        return lblCharactersRemaining;
    }
    
    public void txtDescriptionKeyListener(KeyListener kl) {
        txtDescription.addKeyListener(kl);
    }
    
    public void addBtnAddActionListener(ActionListener actionListener) {
        btnAdd.addActionListener(actionListener);
    }

    public void addBtnEnableChangesActionListener(ActionListener actionListener) {
        btnEnableChanges.addActionListener(actionListener);
    }

    public void addBtnCancelActionListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

    public void addBtnDeleteActionListener(ActionListener actionListener) {
        btnDelete.addActionListener(actionListener);
    }

    public void addBtnUpdateActionListener(ActionListener actionListener) {
          btnUpdate.addActionListener(actionListener);
    }
    
    public void addBtnAddActorActionListener(ActionListener actionListener) {
        btnAddActor.addActionListener(actionListener);
    }
    public void addBtnRemoveActorActionListener(ActionListener actionListener) {
        btnRemoveActor.addActionListener(actionListener);
    }
    
    public void addBtnAddGenreActionListener(ActionListener actionListener) {
        btnAddGenre.addActionListener(actionListener);
    }
    
    public void addBtnRemoveGenreActionListener(ActionListener actionListener) {
        btnRemoveGenre.addActionListener(actionListener);
    }
    
    public void addBtnAddProductionCompanyActionListener(ActionListener actionListener) {
        btnAddProductionCompany.addActionListener(actionListener);
    }
    
    public void addBtnRemoveProductionCompanyActionListener(ActionListener actionListener) {
        btnRemoveProductionCompany.addActionListener(actionListener);
    }
    
    public JButton getBtnAddActor() {
        return btnAddActor;
    }

    public JButton getBtnAddGenre() {
        return btnAddGenre;
    }

    public JButton getBtnAddProductionCompany() {
        return btnAddProductionCompany;
    }

    public JButton getBtnRemoveActor() {
        return btnRemoveActor;
    }

    public JButton getBtnRemoveGenre() {
        return btnRemoveGenre;
    }

    public JButton getBtnRemoveProductionCompany() {
        return btnRemoveProductionCompany;
    }

    public JComboBox<Object> getCbActors() {
        return cbActors;
    }

    public JComboBox<Object> getCbGenres() {
        return cbGenres;
    }

    public JComboBox<Object> getCbProductionCompanies() {
        return cbProductionCompanies;
    }

    public JTable getTblRoles() {
        return tblRoles;
    }

    public JTable getTblMovieGenre() {
        return tblMovieGenre;
    }

    public JTable getTblProduction() {
        return tblProduction;
    }
    
    public JTextField getTxtRoleName() {
        return txtRoleName;
    }

    public JButton getBtnRemoveAllMovieGenres() {
        return btnRemoveAllMovieGenres;
    }

    public JButton getBtnRemoveAllProductions() {
        return btnRemoveAllProductions;
    }

    public JButton getBtnRemoveAllRoles() {
        return btnRemoveAllRoles;
    }

    public JButton getBtnUploadPoster() {
        return btnUploadPoster;
    }
    
    public JLabel getLblPoster() {
        return lblPoster;
    }

    public JTextField getTxtDuration() {
        return txtDuration;
    }
    
    
    public void addBtnRemoveAllRolesActionListener(ActionListener actionListener) {
        btnRemoveAllRoles.addActionListener(actionListener);
    }
    
    public void addBtnRemoveAllMovieGenresActionListener(ActionListener actionListener) {
        btnRemoveAllMovieGenres.addActionListener(actionListener);
    }
    
    public void addBtnRemoveAllProductionsActionListener(ActionListener actionListener) {
        btnRemoveAllProductions.addActionListener(actionListener);
    }
    
    
    public void getTableRolesAddListSelectionListener(ListSelectionListener lsl) {
        tblRoles.getSelectionModel().addListSelectionListener(lsl);
    }
    
    public void getTableMovieGenresAddListSelectionListener(ListSelectionListener lsl) {
        tblMovieGenre.getSelectionModel().addListSelectionListener(lsl);
    }
    
    public void getTableProductionAddListSelectionListener(ListSelectionListener lsl) {
        tblProduction.getSelectionModel().addListSelectionListener(lsl);
    }
    
    public void addBtnUploadPosterActionListener(ActionListener actionListener) {
        btnUploadPoster.addActionListener(actionListener);
    }
    
    private void initDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        datePicker = new DatePicker(dateSettings);
        datePicker.setDateToToday();
        datePicker.setPreferredSize(new Dimension(330, 25));
        jPanelDatePicker.add(datePicker);
    }
}
