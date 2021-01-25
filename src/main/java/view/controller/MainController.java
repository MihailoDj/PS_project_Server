/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import controller.Controller;
import domain.UserStatistics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.TableColumnModel;
import view.coordinator.ServerFormCoordinator;
import view.form.FrmMain;
import view.form.component.table.UserStatisticsTableModel;
import view.form.component.table.UserStatsticsTableModelRenderer;

/**
 *
 * @author Mihailo
 */
public class MainController {
    private final FrmMain frmMain;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addWindowListener();
        addActionListener();
    }

    public void openForm() {
        frmMain.setTitle("Main form");
        frmMain.setLocationRelativeTo(null);
        
        setUpTableuserStatistics();
        
        frmMain.setVisible(true);
        frmMain.setExtendedState( JFrame.MAXIMIZED_BOTH);
    }
    
    private void addWindowListener() {
        frmMain.frmMainWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Controller.getInstance().stopServer();
            }
            
        });
    }

    private void addActionListener() {
        frmMain.btnStartServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().startServer();
                frmMain.getBtnStartServer().setEnabled(false);
                frmMain.getBtnStopServer().setEnabled(true);
                frmMain.getLblServerStatus().setText("SERVER IS RUNNING");
                frmMain.getLblServerStatus().setForeground(Color.green.darker());
            }
        });
        frmMain.btnStopServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().stopServer();
                frmMain.getBtnStartServer().setEnabled(true);
                frmMain.getBtnStopServer().setEnabled(false);
                frmMain.getLblServerStatus().setText("SERVER IS CLOSED");
                frmMain.getLblServerStatus().setForeground(Color.red);
            }
        });
        frmMain.jmiNewMovieAddActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductNewActionPerformed(evt);
            }

            private void jmiProductNewActionPerformed(java.awt.event.ActionEvent evt) {
                ServerFormCoordinator.getInstance().openMovieForm();
            }
        });
        frmMain.jmiViewAllMoviesActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductShowAllActionPerformed(evt);
            }

            private void jmiProductShowAllActionPerformed(java.awt.event.ActionEvent evt) {
                ServerFormCoordinator.getInstance().openViewAllMoviesForm();
            }
        });
        frmMain.jmiNewDirectorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmiNewDirectorActionPerformed(evt);
            }
            
            private void jmiNewDirectorActionPerformed(ActionEvent evt) {
                ServerFormCoordinator.getInstance().openDirectorForm();
            }
        });
        frmMain.jmiViewAllDirectorsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmiViewAllDirectorsActionPerformed(evt);
            }
            
            private void jmiViewAllDirectorsActionPerformed(ActionEvent evt) {
                ServerFormCoordinator.getInstance().openViewAllDirectorsForm();
            }
        });
        frmMain.jmiNewActorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmiNewActorActionPerformed(evt);
            }
            
            private void jmiNewActorActionPerformed(ActionEvent evt) {
                ServerFormCoordinator.getInstance().openActorForm();
            }
        });
        frmMain.jmiViewAllActorsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmiViewAllActorsActionPerformed(evt);
            }
            
            private void jmiViewAllActorsActionPerformed(ActionEvent evt) {
                ServerFormCoordinator.getInstance().openViewAllActorsForm();
            }
        });
    }
    
    public FrmMain getFrmMain() {
        return frmMain;
    }
    
    public void setUpTableuserStatistics() {
        try {
            List<UserStatistics> userStats = Controller.getInstance().selectUserStatistics();
            
            UserStatisticsTableModel ustm = new UserStatisticsTableModel(userStats);
            frmMain.getTblUserStatistics().setModel(ustm);
            
            
            TableColumnModel tcm = frmMain.getTblUserStatistics().getColumnModel();

            tcm.getColumn(2).setCellRenderer(new UserStatsticsTableModelRenderer());
            frmMain.getTblUserStatistics().setAutoCreateRowSorter(true);
            frmMain.getTblUserStatistics().getTableHeader().setResizingAllowed(false);

            frmMain.getTblUserStatistics().setRowHeight(30);
            tcm.getColumn(0).setPreferredWidth(15);
            tcm.getColumn(1).setPreferredWidth(150);
            tcm.getColumn(2).setPreferredWidth(100);
            tcm.getColumn(3).setPreferredWidth(100);
            tcm.getColumn(4).setPreferredWidth(100);
            tcm.getColumn(5).setPreferredWidth(150);
            tcm.getColumn(6).setPreferredWidth(35);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
