/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import javax.swing.JOptionPane;
import FileManager.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import Analizador.*;
import Calendarizador.Marcador;
import Micro.*;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URISyntaxException;

/**
 *
 * @author cesar solis
 */
public class mainFrame extends javax.swing.JFrame {

    /**
     * Se inicializan las variables y clases a usar 
     */
    DataTables dt;
    private String _actualPath;
    Sintaxis sintax = new Sintaxis();
    Semantico seman = new Semantico();
    Micro micro = new Micro();
    InstMem mem = new InstMem();
    public boolean compilado = false;
    public boolean archivoCompilado = false;
    public boolean stepReady=false;
    
    
    /***
     *  Metodo encargado de iniciar las tablas donde se almacenan registros,
     *  intrucciones y datos.
     **/
    public mainFrame() {
        this._actualPath = "";
        dt = new DataTables();
        dt.initDataTable();
        dt.initInstructionTable();
        dt.initRegisterTable();
        
        initComponents();
        jLabel1.setText("ISIv1 Simulador");
       
    }

    
    /*
    * Este metodo se encarga de abrir archivos .isi y cargarlos al editor
    * grafico cuando el formato es correcto
    */
      private void cargarFileChooser()
    {
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir...");
        String texto = "";
        int userSelection = fileChooser.showOpenDialog(parentFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                if(fileToLoad.getAbsolutePath().endsWith(".isi")){
                    this._actualPath = null;
                    editorTextPane.setText("");
                    texto = File_TXT.readText(fileToLoad.getAbsolutePath());
                    this._actualPath = fileToLoad.getAbsolutePath();
                    editorTextPane.setText(texto);
                    String path = MessageInterpreter.showActualPath(_actualPath);
                    jTextArea1.setText(path);
                }else{
                    JOptionPane.showMessageDialog(this, "¡Formato de archivo"
                  + " no es compatible!",
                "Cargar", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        
    }
    
      
    /*
    * Este metodo se encarga de abrir archivos .isi y cargarlos al editor
    * grafico cuando el formato es correcto
    */
      private void cargarFileChooserComp()
    {
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir...");
        String texto = "";
        int userSelection = fileChooser.showOpenDialog(parentFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                if(fileToLoad.getAbsolutePath().endsWith(".isi")){
                    this._actualPath = null;
                    editorTextPane.setText("");
                    texto = File_TXT.readText(fileToLoad.getAbsolutePath());
                    this._actualPath = fileToLoad.getAbsolutePath();
                    editorTextPane.setText(texto);
                    editorTextPane.setEnabled(false);
                    String path = MessageInterpreter.showActualPath(_actualPath);
                    jTextArea1.setText(path);
                }else{
                    JOptionPane.showMessageDialog(this, "¡Formato de archivo"
                  + " no es compatible!",
                "Cargar", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        
    }
      
     /*
    * Este metodo se encarga de guardar archivos .isi y 
    */
      private void guardarComoFileChooser(String textoAGuardar)
    {
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como...");
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (fileToSave.getAbsolutePath().endsWith(".isi") &&
                !File_TXT.verifyFilenameExists(fileToSave.getAbsolutePath())) {
                        this._actualPath = fileToSave.getAbsolutePath();
                try {
                    File_TXT.writeText(fileToSave.getAbsolutePath(), textoAGuardar);
                } catch (IOException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                    JOptionPane.showMessageDialog(this, "¡Archivo guardado"
                  + " con éxito!", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                        
            }else if (!fileToSave.getAbsolutePath().endsWith(".isi") &&
                !File_TXT.verifyFilenameExists(fileToSave.getAbsolutePath() + ".isi")){
                this._actualPath = fileToSave.getAbsolutePath() + ".isi";
                try {
                    File_TXT.writeText(fileToSave.getAbsolutePath(), textoAGuardar);
                } catch (IOException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                    JOptionPane.showMessageDialog(this, "¡Archivo guardado"
                  + " con éxito!", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            } else{
                 JOptionPane.showMessageDialog(this, "¡Ya existe archivo "
                  + "con ese nombre!",
                "Guardar como", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
      /***
       * El metodo es el encargado de sobreescribir el archivo ya guardado
       * @param textoAGuardar 
       */
      private void guardarSinFileChooser(String textoAGuardar)
    {
        try {
            File_TXT.writeText(this._actualPath, textoAGuardar);
        } catch (IOException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
            JOptionPane.showMessageDialog(this, "¡Archivo actualizado y"
                    + " guardado!", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
    
    }
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        editorTextPane = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        GUI_tablaDatos = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        GUI_tablaInstrucciones = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        GUI_tablaRegistros = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        LBL_fondo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        numIns = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        editorTextPane.setSize(769, 422);
        editorTextPane.setEditorKit(new javax.swing.text.StyledEditorKit());
        editorTextPane.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane5.setViewportView(editorTextPane);
        TextLineNumber tln = new TextLineNumber( editorTextPane );
        TextWordsColor twc = new TextWordsColor(editorTextPane);
        LinePainter painter = new LinePainter(editorTextPane);
        jScrollPane5.setRowHeaderView( tln );

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Consola");
        jScrollPane1.setViewportView(jTextArea1);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N

        GUI_tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaDatos(),
            new String [] {
                "Address", "Value"
            }
        )    {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if ( col == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaDatos.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(GUI_tablaDatos);

        jTabbedPane1.addTab("Datos", jScrollPane3);

        GUI_tablaInstrucciones.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaInstrucciones(),
            new String [] {
                "Address", "Instruction"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if ( col == 1 && col == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaInstrucciones.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        GUI_tablaInstrucciones.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(GUI_tablaInstrucciones);

        jTabbedPane1.addTab("Instrucciones", jScrollPane4);

        GUI_tablaRegistros.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaRegistros(),
            new String [] {
                "Register", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if (col == 0 && col == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaRegistros.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(GUI_tablaRegistros);

        jTabbedPane1.addTab("Registros", jScrollPane2);

        jButton1.setText("Paso Siguiente");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Ejecutar");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        LBL_fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/fondo.jpg"))); // NOI18N

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("jLabel1");

        numIns.setEnabled(false);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("TeXGyreAdventor", 3, 18)); // NOI18N
        jLabel2.setText("#Instrucción");

        jMenu2.setMnemonic(KeyEvent.VK_N);
        jMenu2.setText("Archivo");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Nuevo");
        jMenuItem1.setText("Nuevo");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);
        KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
        jMenuItem1.setAccelerator(ctrlNKeyStroke);
        jMenu2.add(jSeparator3);

        jMenuItem3.setMnemonic(KeyEvent.VK_O);
        jMenuItem3.setText("Abrir");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);
        KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke("control O");
        jMenuItem3.setAccelerator(ctrlOKeyStroke);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Abrir Compilado");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);
        jMenu2.add(jSeparator5);

        jMenuItem2.setText("Guardar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);
        KeyStroke ctrlAltSKeyStroke = KeyStroke.getKeyStroke("control alt S");
        jMenuItem2.setAccelerator(ctrlAltSKeyStroke);

        jMenuItem8.setMnemonic(KeyEvent.VK_S);
        jMenuItem8.setText("Guardar como");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);
        KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
        jMenuItem8.setAccelerator(ctrlSKeyStroke);
        jMenu2.add(jSeparator4);
        jMenu2.add(jSeparator1);

        jMenuItem10.setText("Salir");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10);
        KeyStroke ctrlAltQKeyStroke = KeyStroke.getKeyStroke("control alt Q");
        jMenuItem10.setAccelerator(ctrlAltQKeyStroke);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ejecutar");

        jMenuItem5.setText("Correr");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);
        KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
        jMenuItem5.setAccelerator(ctrlRKeyStroke);

        jMenuItem6.setText("Compilar");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);
        KeyStroke ctrlAKeyStroke = KeyStroke.getKeyStroke("control A");
        jMenuItem6.setAccelerator(ctrlAKeyStroke);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Debugger");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Ayuda");

        jMenuItem11.setText("Manual ");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        jMenu1.setText("Caledarización");

        jMenuItem7.setText("jMenuItem7");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel1)
                        .addGap(311, 311, 311)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(numIns, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(LBL_fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 1321, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(numIns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(LBL_fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
        // METODO NO SE USA
    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar? "
                + "Cambios que no son guardados se borrarán", 
                "Nuevo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (JOptionPane.YES_OPTION == respuesta){
            this.editorTextPane.setText("");
            this._actualPath = "";
            resetTabs();
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        editorTextPane.setEnabled(true);
        archivoCompilado = false;
        compilado=false;
        cargarFileChooser();
        resetTabs();
        dt.initDataTable();
        dt.initInstructionTable();
        dt.initRegisterTable();
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        MessageInterpreter.showActualPath(_actualPath);
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
         // TODO add your handling code here: Archivo < Guardar
        String textoAGuardar;
        if(!this._actualPath.isEmpty())
        {
           // El texto NO está vacío y se ha abierto o guardado como... 
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea actualizar "
                + "los cambios?\n"
                + "(Cambios que no son guardados se borrarán)", 
                "Guardar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (JOptionPane.YES_OPTION == respuesta){
                textoAGuardar = editorTextPane.getText();
                compilado = false;
                guardarSinFileChooser(textoAGuardar);
                resetTabs();
                dt.initDataTable();
                dt.initInstructionTable();
                dt.initRegisterTable();
                jButton1.setEnabled(false);
                jButton2.setEnabled(false);
            }
        }else{
            textoAGuardar = editorTextPane.getText();
            
            guardarComoFileChooser(textoAGuardar);
        }
        MessageInterpreter.showActualPath(_actualPath);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed

        sintax.flagError = false;
        sintax.line = 0;
        String textoAGuardar = editorTextPane.getText();
        guardarComoFileChooser(textoAGuardar);
        resetTabs();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
         // TODO add your handling code here: Archivo < Cerrar
        String textoAGuardar = "";
        if (this.editorTextPane.getText().isEmpty() && this._actualPath.isEmpty())
        {
            // El texto está vacío y no se ha abierto ni guardado como...
            this.editorTextPane.setText("");
        }else if(!this.editorTextPane.getText().isEmpty() && this._actualPath.isEmpty())
        {
           // El texto NO está vacío y se ha abierto o guardado como... 
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea guardar "
                + "los cambios?\n"
                + "(Cambios que no son guardados se borrarán)", 
                "Cerrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (JOptionPane.YES_OPTION == respuesta){
                textoAGuardar = editorTextPane.getText();
                guardarComoFileChooser(textoAGuardar);
                this.editorTextPane.setText("");
                String path = MessageInterpreter.showActualPath("No current file");
                jTextArea1.setText(path);
            }
        }else {
            textoAGuardar = editorTextPane.getText();
            guardarSinFileChooser(textoAGuardar);
            JOptionPane.showMessageDialog(this, "¡Archivo actualizado y"
                    + " guardado!", "Cerrar", JOptionPane.INFORMATION_MESSAGE);
            this.editorTextPane.setText("");
            String path = MessageInterpreter.showActualPath("No current file");
            jTextArea1.setText(path);
        }
        MessageInterpreter.showActualPath(_actualPath);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // NO SE USA METODO
    }//GEN-LAST:event_jMenu2ActionPerformed

    
    /***
     * Este metodo es el encargado d e estar verificando y actualizando cualquier
     * cambio en las tablas de datos, instrucciones y registros.
     */
    private void updateTabs(){
    
    
     int i=0;
        int temp=0;
        while(i < 16){
            temp = micro.cpu.BR.GetDataA(i);
            dt.UpdateRegisterTablePos(i, temp);
            i++;
        }
        i = 0;
        while(i < 50){
            temp = micro.cpu.DM.GetData(i);
            dt.UpdateDataTablePos(i, temp);
            i++;
        }
        i = 0;
        while(i < 200){
            micro.cpu.IM.i = 0;
            temp=micro.cpu.IM.GetData(i);
            dt.UpdateInstructionTablePos(i, temp);
            i++;
        }
        i=0;
        
        
        //-------------------------------------------------------------------update JTabbed
       GUI_tablaRegistros.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaRegistros(),
            new String [] {
                "Registro", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if (col == 0 && col == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaRegistros.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(GUI_tablaRegistros);
        
        
        
        //-------------------------------
        
        GUI_tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaDatos(),
            new String [] {
                "Direccion", "Valor"
            }
        )    {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if ( col == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaDatos.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(GUI_tablaDatos);

        GUI_tablaInstrucciones.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaInstrucciones(),
            new String [] {
                "Direccion", "Instruccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if ( col == 1 && col == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaInstrucciones.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(GUI_tablaInstrucciones);
        
      
    
    }
    
    /**
     * Desde este metodo se reinician las tablas cuando se requiera
     */
    private void resetTabs(){
    
    
     int i=0;
        int temp=0;
        while(i < 16){
            dt.UpdateRegisterTablePos(i, 0);
            i++;
        }
        i = 0;
        while(i < 50){
            dt.UpdateDataTablePos(i, 0);
            i++;
        }
        i = 0;
        while(i < 50){
            dt.UpdateInstructionTablePos(i, 0);
            i++;
        }
        i=0;
        
        
        //-------------------------------------------------------------------update JTabbed
       GUI_tablaRegistros.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaRegistros(),
            new String [] {
                "Registros", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if (col == 0 && col == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaRegistros.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(GUI_tablaRegistros);
        
        
        
        //-------------------------------
        
        GUI_tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaDatos(),
            new String [] {
                "Direccion", "Valor"
            }
        )    {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if ( col == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaDatos.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(GUI_tablaDatos);

        GUI_tablaInstrucciones.setModel(new javax.swing.table.DefaultTableModel(
            dt.getTablaInstrucciones(),
            new String [] {
                "Direccion", "Instruccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                if ( col == 1 && col == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        GUI_tablaInstrucciones.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(GUI_tablaInstrucciones);
        
      
    
    }
    
    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here: Help < About
        String direccion = "https://drive.google.com/open?id=0Bz5LAWruQBRAa2pLM2pqY3FOTmM";
        try
        {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + direccion);
        }
        catch(Exception err)
        {
            JOptionPane.showMessageDialog(null,"Error: "+err);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   
        /**
         * Aqui se realiza el modo debg paso a paso
         */
        if(stepReady==false){
        
        micro.cpu=new CPU();
        File archivo = null;
                FileReader fr = null;
                BufferedReader br = null;

                try {
                    archivo = new File ("generado.isi");
                    fr = new FileReader (archivo);
                    br = new BufferedReader(fr);

                    String linea;

                    while((linea=br.readLine())!= null){
                        mem.Largo ++;
                    }
                micro.largo=mem.Largo;
                }   
                catch(Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        if( null != fr ){
                            fr.close();
                        }
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                    
                    mem.InstMem();
                    System.out.println("Cargado el generado.isi");

                }
        
        
        
        stepReady=true;
        micro.pc=0;
       
        micro.NextStep();
        updateTabs();
        
        micro.NextStep();
        updateTabs();
        
        micro.NextStep();
        updateTabs();
        
        micro.NextStep();
        updateTabs();
        
        
        //editorTextPane.moveCaretPosition(micro.pc-4);
        
        }
        
        else {
        
        micro.NextStep();
        updateTabs();
        numIns.setText("0");
        if (mem.Largo > Integer.parseInt(numIns.getText())){
            numIns.setText(Integer.toString(micro.pc-3));
        }
        
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
      
        sintax.flagError = false;
        sintax.line = 0;
        if(this._actualPath.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "¡No se ha cargado ningún archivo"
                + " .isi!",
                "Atención", JOptionPane.ERROR_MESSAGE);
            this.compilado = false;
            String msj = MessageInterpreter.mensajeAssembly("No se ha cargado ningún archivo.");
            jTextArea1.setText(msj);
            
        }else{
            if (archivoCompilado == false){
                sintax.leeArchivo(_actualPath);
                if (sintax.flagError){
                    JOptionPane.showMessageDialog(this, "Error en la linea: " + sintax.line,
                        "Error", JOptionPane.ERROR_MESSAGE);
                    String msj = MessageInterpreter.mensajeExecution("No se ha compilado ningún archivo.");
                    jTextArea1.setText(msj);
                }
                else{
                    try {
                        seman.crearArchivo("");
                    } catch (IOException ex) {
                        Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seman.leeArchivo(_actualPath);
                    JOptionPane.showMessageDialog(this, "Archivo compilado exitosamente",
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
                    String msj = MessageInterpreter.mensajeExecution("Archivo compilado.");
                    jTextArea1.setText(msj);
                    compilado = true;
                    resetTabs();
                    dt.initDataTable();
                    dt.initInstructionTable();
                    dt.initRegisterTable();
                }
            }
            else if (archivoCompilado){
                JOptionPane.showMessageDialog(this, "No se puede compilar un archivo " +
                        "compilado!", "Error", JOptionPane.ERROR_MESSAGE);
                    String msj = MessageInterpreter.mensajeExecution("No se ha compilado ningún archivo.");
                    jTextArea1.setText(msj);
            }
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        
        if (this._actualPath.isEmpty() || compilado == false){
            JOptionPane.showMessageDialog(this, "¡No se ha compilado el archivo!",
                "Error", JOptionPane.ERROR_MESSAGE);
            String msj = MessageInterpreter.mensajeExecution("No se ha compilado ningún archivo.");
            jTextArea1.setText(msj);
        }else{

            //Cargar instrucciones aqui
            if (archivoCompilado == false){
                File archivo = null;
                FileReader fr = null;
                BufferedReader br = null;

                try {
                    archivo = new File ("generado.isi");
                    fr = new FileReader (archivo);
                    br = new BufferedReader(fr);

                    String linea;

                    while((linea=br.readLine())!= null){
                        mem.Largo ++;

                    }

                }   
                catch(Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        if( null != fr ){
                            fr.close();
                        }
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                    
                    mem.InstMem();
                    micro.init();
                    System.out.println(mem.Largo);
                    double instCiclo = (double)(mem.Largo)/(double)(mem.Largo + 4);
                    String ciclos = String.valueOf(instCiclo); 
                    double time = (double)(Math.pow(10, -6));
                    System.out.println(time);
                    System.out.println("------------");
                    double insSeg = (double)(mem.Largo + 4) / (double)(Math.pow(10, -6));
                    String msj = MessageInterpreter.mensajeExecution("Se ejecutan "
                            + ciclos + " instrucciones por ciclo" +"\n" +
                        "Se ejecutan " + insSeg + " instrucciones por segundo");
                    jTextArea1.setText(msj);
                    updateTabs();
                    //micro.cpu.BR.print();

                }
            }
            
            else{
                
                
            
            File archivo = null;
                FileReader fr = null;
                BufferedReader br = null;

                try {
                    archivo = new File (_actualPath);
                    fr = new FileReader (archivo);
                    br = new BufferedReader(fr);

                    String linea;

                    while((linea=br.readLine())!= null){
                        mem.Largo ++;

                    }

                }   
                catch(Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        if( null != fr ){
                            fr.close();
                        }
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                    
                    mem.InstMem();
                    micro.init();
                    System.out.println(mem.Largo);
                    double instCiclo = (double)(mem.Largo)/(double)(mem.Largo + 4);
                    String ciclos = String.valueOf(instCiclo); 
                    double time = (double)(Math.pow(10, -6));
                    System.out.println(time);
                    System.out.println("------------");
                    double insSeg = (double)(mem.Largo + 4) / (double)(Math.pow(10, -6));
                    String msj = MessageInterpreter.mensajeExecution("Se ejecutan "
                            + ciclos + " instrucciones por ciclo" +"\n" +
                        "Se ejecutan " + insSeg + " instrucciones por segundo");
                    jTextArea1.setText(msj);
                    updateTabs();

                }        
            } 

        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        if (this._actualPath.isEmpty() || compilado == false){
            JOptionPane.showMessageDialog(this, "¡No se ha compilado el archivo!",
                "Error", JOptionPane.ERROR_MESSAGE);
            String msj = MessageInterpreter.mensajeExecution("No se ha compilado ningún archivo.");
            jTextArea1.setText(msj);
        }else{
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        cargarFileChooserComp();
        MessageInterpreter.showActualPath(_actualPath);
        archivoCompilado = true;
        compilado=true;
        resetTabs();
        dt.initDataTable();
        dt.initInstructionTable();
        dt.initRegisterTable();
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
           micro.cpu=new CPU();
        File archivo = null;
                FileReader fr = null;
                BufferedReader br = null;

                try {
                    archivo = new File ("generado.isi");
                    fr = new FileReader (archivo);
                    br = new BufferedReader(fr);

                    String linea;

                    while((linea=br.readLine())!= null){
                        mem.Largo ++;
                    }
                micro.largo=mem.Largo;
                }   
                catch(Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        if( null != fr ){
                            fr.close();
                        }
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                    
                    mem.InstMem();
                    System.out.println("Cargado el generado.isi");

                }
        micro.pc=0;
       
        micro.NextStep();
        updateTabs();
        
        micro.NextStep();
        updateTabs();
        
        micro.NextStep();
        updateTabs();
        
        micro.NextStep();
        updateTabs();
        int i = 0;    
        try
        {
            
            while(i < mem.Largo +5){
                micro.NextStep();
                updateTabs();
                i++;
                numIns.setText(Integer.toString(micro.pc-3));
                Thread.sleep(500);
                
            }
            
            
        }catch(InterruptedException e){}    
   
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        Marcador marcador = new Marcador();
        marcador.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

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
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable GUI_tablaDatos;
    private javax.swing.JTable GUI_tablaInstrucciones;
    private javax.swing.JTable GUI_tablaRegistros;
    private javax.swing.JLabel LBL_fondo;
    private javax.swing.JTextPane editorTextPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField numIns;
    // End of variables declaration//GEN-END:variables
}
