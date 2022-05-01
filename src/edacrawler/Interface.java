/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edacrawler;

import static edacrawler.ImageToDisplay.displayImages;
import static edacrawler.ImageToDisplay.displayImagesByFiles;
import static edacrawler.ImageToDisplay.imagesToSave;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author PedroMatias & RodrigoCrispim
 */
public class Interface extends javax.swing.JFrame {
    
    private final JFileChooser openFileChooser;
    private final JFileChooser saveFileChooser;
    private File dir;
    private String absPath;
    
    
    /** Creates new form Interface */
    public Interface() {
        initComponents(); 
        
        createDir(); //cria pasta aonde serão salvas as imagens
        
        //aponta o open e o savefilechooser para o dir e filtra a extensão de nome de ficheiro para ficheiros png
        openFileChooser = new JFileChooser();
        openFileChooser.setCurrentDirectory(dir);
        openFileChooser.setFileFilter(new FileNameExtensionFilter("Imagens (.png)", "png"));
        saveFileChooser = new JFileChooser();
        saveFileChooser.setCurrentDirectory(dir);
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Imagens (.png)", "png"));
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textURL = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSearch = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        boolDomain = new javax.swing.JCheckBox();
        txtDepth = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textURL.setText("http://portal2.ipt.pt");
        textURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textURLActionPerformed(evt);
            }
        });

        txtSearch.setColumns(20);
        txtSearch.setRows(5);
        jScrollPane1.setViewportView(txtSearch);

        jLabel1.setText("URL");

        jLabel2.setText("Texto de busca");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Procurar imagem...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton3.setText("Buscar e guardar...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        boolDomain.setText("Pesquisa no domínio");
        boolDomain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boolDomainActionPerformed(evt);
            }
        });

        txtDepth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        txtDepth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepthActionPerformed(evt);
            }
        });

        jLabel3.setText("Profundidade");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(textURL))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boolDomain))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(boolDomain)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            Logger.getLogger(Interface.class.getName()).log(Level.INFO,"Iniciando");
            
            // Obtenção dos dados da interface
            String url = textURL.getText();
            String searchKey = txtSearch.getText();
            int depth = Integer.parseInt(txtDepth.getSelectedItem().toString());
            boolean ifDomain = boolDomain.isEnabled();
            
            //instancia de crawler com tema de pesquisa e profundidade
            EDACrawler eda = new EDACrawler(url, searchKey, depth);
            //pesquisa recursiva 
            Payload pl = eda.recursiveSearch(ifDomain); 
            //mostra imagens num painel unico
            displayImages(pl);
            
            Logger.getLogger(Interface.class.getName()).log(Level.INFO,"Fim");  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try{
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            
            //abre a janela para podermos escolher uma imagem
            int returnValue = chooser.showOpenDialog(this); 
            //condição para ver se o JFileChooser aprova o returnValue
            if(returnValue == JFileChooser.APPROVE_OPTION){ 
                File [] files = chooser.getSelectedFiles();
                displayImagesByFiles(files);
            } 
        } catch (Exception e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String url = textURL.getText();
        String searchKey = txtSearch.getText();
        int depth = Integer.parseInt(txtDepth.getSelectedItem().toString());
        boolean ifDomain = boolDomain.isSelected();

        //instancia de crawler com tema de pesquisa e profundidade
        EDACrawler eda = new EDACrawler(url, searchKey, depth);
        Payload pl = eda.recursiveSearch(ifDomain);
        displayImages(pl);
        if (!dir.exists()){
            createDir();
        }
        imagesToSave(pl, dir);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void boolDomainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boolDomainActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boolDomainActionPerformed

    private void textURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textURLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textURLActionPerformed

    private void txtDepthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepthActionPerformed
    
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
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interface().setVisible(true);
            }
        });
    }
    
    public final void createDir(){
        try {
            //cria uma nova directoria chamda edaTPimgs
            String OS = System.getProperty("os.name");
            
            //absolutePath com base o sistema operativo
            if (OS.compareTo("Windows")==0) {
                absPath = "c:\\"; 
                Logger.getLogger(Interface.class.getName()).log(Level.CONFIG, null,"SO: Windows");
            }
            else if (OS.compareTo("Linux")==0) {
                absPath = "/home/crispim/Documents/"; 
                Logger.getLogger(Interface.class.getName()).log(Level.CONFIG, null,"SO: Linux");
            }
            else {
                absPath = "c:\\"; 
                Logger.getLogger(Interface.class.getName()).log(Level.WARNING, null,"SO: Não reconhecido");
            }

            dir = new File(absPath.concat("edaTPimgs"));
            dir.mkdir();
            Logger.getLogger(Interface.class.getName()).log(Level.CONFIG, null,"Pasta edaTPimgs criada em: "+absPath);
            
        } catch (Exception e){
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boolDomain;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField textURL;
    private javax.swing.JComboBox<String> txtDepth;
    private javax.swing.JTextArea txtSearch;
    // End of variables declaration//GEN-END:variables

}
