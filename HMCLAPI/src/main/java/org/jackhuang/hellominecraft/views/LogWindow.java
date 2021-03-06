/*
 * Copyright 2013 huangyuhui <huanghongxun2008@126.com>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.
 */
package org.jackhuang.hellominecraft.views;

import java.util.Timer;
import org.jackhuang.hellominecraft.C;
import org.jackhuang.hellominecraft.utils.functions.NonFunction;
import org.jackhuang.hellominecraft.utils.DoubleOutputStream;
import org.jackhuang.hellominecraft.utils.LauncherPrintStream;
import org.jackhuang.hellominecraft.utils.StrUtils;
import org.jackhuang.hellominecraft.utils.TextComponentOutputStream;
import org.jackhuang.hellominecraft.utils.Utils;

/**
 *
 * @author huangyuhui
 */
public class LogWindow extends javax.swing.JFrame {

    boolean movingEnd;
    NonFunction<Boolean> listener;
    Runnable terminateGameListener;

    /**
     * Creates new form LogWindow
     */
    public LogWindow() {
        initComponents();

        movingEnd = true;

        setLocationRelativeTo(null);

        TextComponentOutputStream tc = new TextComponentOutputStream(txtLog);
        DoubleOutputStream out = new DoubleOutputStream(tc, System.out);
        System.setOut(new LauncherPrintStream(out));
        DoubleOutputStream err = new DoubleOutputStream(tc, System.err);
        System.setErr(new LauncherPrintStream(err));
    }

    public static LogWindow instance = new LogWindow();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        btnClear = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnCopy = new javax.swing.JButton();
        lblCrash = new javax.swing.JLabel();
        btnMCBBS = new javax.swing.JButton();
        btnTieBa = new javax.swing.JButton();
        btnMCF = new javax.swing.JButton();
        btnTerminateGame = new javax.swing.JButton();
        btnGitHub = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(C.I18N.getString("logwindow.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        txtLog.setEditable(false);
        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        btnClear.setText(C.I18N.getString("ui.button.clear")); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnClose.setText(C.I18N.getString("ui.button.close")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnCopy.setText(C.I18N.getString("ui.button.copy")); // NOI18N
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

        lblCrash.setText(C.I18N.getString("ui.label.crashing")); // NOI18N

        btnMCBBS.setText("MCBBS");
        btnMCBBS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMCBBSActionPerformed(evt);
            }
        });

        btnTieBa.setText(C.I18N.getString("logwindow.tieba")); // NOI18N
        btnTieBa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTieBaActionPerformed(evt);
            }
        });

        btnMCF.setText("Minecraft Forum");
        btnMCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMCFActionPerformed(evt);
            }
        });

        btnTerminateGame.setText(C.I18N.getString("logwindow.terminate_game")); // NOI18N
        btnTerminateGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminateGameActionPerformed(evt);
            }
        });

        btnGitHub.setText("GitHub");
        btnGitHub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGitHubActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnTieBa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMCBBS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMCF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGitHub)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTerminateGame)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCopy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose))
                    .addComponent(lblCrash, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCrash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(btnClose)
                    .addComponent(btnCopy)
                    .addComponent(btnMCBBS)
                    .addComponent(btnTieBa)
                    .addComponent(btnMCF)
                    .addComponent(btnTerminateGame)
                    .addComponent(btnGitHub))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        this.txtLog.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (listener != null && listener.apply() && terminateGameListener != null)
            terminateGameListener.run();
    }//GEN-LAST:event_formWindowClosed

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        Utils.setClipborad(this.txtLog.getText());
    }//GEN-LAST:event_btnCopyActionPerformed

    private void btnMCBBSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMCBBSActionPerformed
        Utils.openLink(C.URL_PUBLISH);
    }//GEN-LAST:event_btnMCBBSActionPerformed

    private void btnTieBaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTieBaActionPerformed
        Utils.openLink(C.URL_TIEBA);
    }//GEN-LAST:event_btnTieBaActionPerformed

    private void btnMCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMCFActionPerformed
        Utils.openLink(C.URL_MINECRAFTFORUM);
    }//GEN-LAST:event_btnMCFActionPerformed

    private void btnTerminateGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminateGameActionPerformed
        if (terminateGameListener != null)
            terminateGameListener.run();
    }//GEN-LAST:event_btnTerminateGameActionPerformed

    private void btnGitHubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGitHubActionPerformed
        Utils.openLink(C.URL_GITHUB);
    }//GEN-LAST:event_btnGitHubActionPerformed

    public void log(String status) {
        String text = txtLog.getText();
        text += status + System.getProperty("line.separator");
        txtLog.setText(text);

        if (movingEnd) {
            int position = text.length();
            txtLog.setCaretPosition(position);
        }
    }

    public void log(String status, Throwable t) {
        log(status);
        log(StrUtils.getStackTrace(t));
    }

    public void setExit(NonFunction<Boolean> exit) {
        this.listener = exit;
    }

    public void setTerminateGame(Runnable l) {
        this.terminateGameListener = l;
    }

    public void clean() {
        txtLog.setText("");
    }

    public boolean getMovingEnd() {
        return movingEnd;
    }

    public void setMovingEnd(boolean b) {
        movingEnd = b;
    }

    @Override
    public void setVisible(boolean b) {
        lblCrash.setVisible(false);
        btnMCBBS.setVisible(false);
        btnTieBa.setVisible(false);
        btnMCF.setVisible(false);
        super.setVisible(b);
    }

    public void showAsCrashWindow(boolean out_date) {
        if (out_date) {
            lblCrash.setVisible(false);
            btnMCBBS.setVisible(false);
            btnTieBa.setVisible(false);
            btnMCF.setVisible(false);
            lblCrash.setText(C.i18n("ui.label.crashing_out_dated"));
        } else {
            lblCrash.setVisible(true);
            btnMCBBS.setVisible(true);
            btnTieBa.setVisible(true);
            btnMCF.setVisible(true);
            lblCrash.setText(C.i18n("ui.label.crashing"));
        }

        super.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnGitHub;
    private javax.swing.JButton btnMCBBS;
    private javax.swing.JButton btnMCF;
    private javax.swing.JButton btnTerminateGame;
    private javax.swing.JButton btnTieBa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCrash;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables
}
