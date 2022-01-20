/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Clock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import prueba_oct.ConexSql;

/**
 *
 * @author ICF7030A
 */
public class formulario extends javax.swing.JFrame {

public ResultSet cursor;
public ConexSql oracle;
DefaultTableModel modelo = new DefaultTableModel();
String eleccion;

            
    
    /**
     * Creates new form formulario
     */
    public formulario() {
        initComponents();
        this.oracle = new ConexSql("usuario", "contraseña", "base de datos", "ip:puerto");
    }
    
    
    public void limpia()
    {
    nom_cri.setText("");
        
    }
    
    
    public void consulta(){
    
    
    if (nom_cri.getText().length() == 0)
    {
        
        try {
                 
            //Para establecer el modelo al JTable
            this.modelo.setRowCount(0);
            this.modelo.setColumnCount(0);
            this.con_usu.setModel(modelo);
            this.oracle.conectar();
            this.cursor = this.oracle.buscar("SELECT T1.ID_USUARIO, T1.NOMBRE_USU,T1.ACTIVO, T2.ID_ROL, T2.NOMBRE_ROL FROM \n" +
                    "USUARIO T1, ROL T2 WHERE T1.ID_ROL = T2.ID_ROL ORDER BY T1.ID_USUARIO");
                       
            //Obteniendo la informacion de las columnas que estan siendo consultadas
            ResultSetMetaData rsMd;
        
            rsMd = this.cursor.getMetaData();
        
            //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();
            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= cantidadColumnas; i++) {
             modelo.addColumn(rsMd.getColumnLabel(i));
            }
            
            this.cursor.beforeFirst();
            //Creando las filas para el JTable
            while (this.cursor.next()) {
             Object[] fila = new Object[cantidadColumnas];
             for (int h = 0; h < cantidadColumnas; h++) {
               fila[h]=this.cursor.getObject(h+1);
               
             }
             
             this.modelo.addRow(fila);
            }

            this.cursor.close();
            
         } catch (SQLException ex) {
            Logger.getLogger(formulario.class.getName()).log(Level.SEVERE, null, ex);
        }   
          
}
        
    else
        
        try {
                 
            //Para establecer el modelo al JTable
            this.modelo.setRowCount(0);
            this.modelo.setColumnCount(0);
            this.con_usu.setModel(modelo);
            this.oracle.conectar();
            this.cursor = this.oracle.buscar("SELECT T1.ID_USUARIO, T1.NOMBRE_USU,T1.ACTIVO, T2.ID_ROL, T2.NOMBRE_ROL \n" +
                    "FROM USUARIO T1, ROL T2 WHERE T1.ID_ROL = T2.ID_ROL AND T1.NOMBRE_USU LIKE '%" +  nom_cri.getText().toUpperCase()  + "%' ORDER BY T1.ID_USUARIO ");
                       
            //Obteniendo la informacion de las columnas que estan siendo consultadas
            ResultSetMetaData rsMd;
        
            rsMd = this.cursor.getMetaData();
        
            //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();
            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= cantidadColumnas; i++) {
             modelo.addColumn(rsMd.getColumnLabel(i));
            }
            
            this.cursor.beforeFirst();
            //Creando las filas para el JTable
            while (this.cursor.next()) {
             Object[] fila = new Object[cantidadColumnas];
             for (int h = 0; h < cantidadColumnas; h++) {
               fila[h]=this.cursor.getObject(h+1);
               
             }
             
             this.modelo.addRow(fila);
            }

            this.cursor.close();
            this.nom_cri.setText("");
            
         } catch (SQLException ex) {
            Logger.getLogger(formulario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public void pasar(){
        
        this.com_rol.removeAllItems();
        this.rd_si.setSelected(false);
        this.rd_no.setSelected(false);
        this.edi_usu.setEnabled(true);
        this.eli_usu.setEnabled(true);
        this.gu_usu.setEnabled(false);
        this.com_rol.setEnabled(true);
        this.nom_usu.setEnabled(true);
        this.rd_si.setEnabled(true);
        this.rd_no.setEnabled(true);
        
        agrega_rol();
        int pas = this.con_usu.getSelectedRow();
        this.id_usu.setText(con_usu.getValueAt(pas, 0).toString());
        this.nom_usu.setText(con_usu.getValueAt(pas, 1).toString());
        this.com_rol.setSelectedItem(con_usu.getValueAt(pas, 4).toString());
        
        
       
        if (con_usu.getValueAt(pas, 2).equals("SI"))
        {
        this.rd_si.setSelected(true);
        }
        else
        this.rd_no.setSelected(true);        
        
        
    }
    
    
    public void crear(){
        
        this.id_usu.setText("");
        this.nom_usu.setText("");
        this.Activo.clearSelection();
        agrega_rol();
        this.gu_usu.setEnabled(true);
        this.edi_usu.setEnabled(false);
        this.eli_usu.setEnabled(false);
        this.com_rol.setEnabled(true);
        this.nom_usu.setEnabled(true);
        this.rd_si.setEnabled(true);
        this.rd_no.setEnabled(true);
        
    }
    
   
    
    public void validar()
    {
        
            
        
        
        
    }
    
    
   public void agrega_rol()
   {
   try {
         
         
         this.com_rol.removeAllItems();
         this.oracle.conectar();
         this.cursor = this.oracle.buscar("SELECT DISTINCT NOMBRE_ROL FROM ROL ORDER BY NOMBRE_ROL ");
         this.cursor.beforeFirst();
      
         while(this.cursor.next()){   
         com_rol.addItem(this.cursor.getString("NOMBRE_ROL"));
          
   }
     
  
     
   this.cursor.close();
   this.oracle.deconectar();
         
     }
     catch (SQLException ex) {
         
     }
   }
    
   
   
   public void guardar()
   {
       
       if (this.nom_usu.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Se debe ingresar un nombre válido." ,"Validación",JOptionPane.ERROR_MESSAGE);
        }
        else if (this.com_rol.getSelectedItem().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Se debe ingresar un rol válido." ,"Validación",JOptionPane.ERROR_MESSAGE);
        }
        else if (rd_si.isSelected()== false && rd_no.isSelected()==false)
        {
        JOptionPane.showMessageDialog(null, "Se seleccionar si el usuario está activo o no." ,"Validación",JOptionPane.ERROR_MESSAGE);
        }
        else
        {

       if (rd_si.isSelected()) {
       eleccion = "SI";
       }
       else if (rd_no.isSelected())
       {
       eleccion = "NO";
       }
    
    try {
        
        this.oracle.conectar();
        this.cursor = this.oracle.buscar("SELECT COUNT(*) FROM USUARIO WHERE NOMBRE_USU ='" + this.nom_usu.getText()  + "'" );
        
        String valor = (this.cursor.getString(1));
        int con_v = Integer.parseInt(valor);
        
        if(con_v > 0) 
        {
        
        JOptionPane.showMessageDialog(null, "El usuario " + this.nom_usu.getText() + " ya existe en el sistema." ,"Crea usuario",JOptionPane.ERROR_MESSAGE);
        }
        
        else
        {
        
            this.oracle.insertar("INSERT INTO USUARIO (ID_USUARIO,ID_ROL,NOMBRE_USU,ACTIVO ) VALUES (NULL,\n" + 
              "NULL" + ",'" + this.nom_usu.getText().toUpperCase() + "','" + eleccion + "')" );
       
       this.oracle.modificar("UPDATE USUARIO SET ID_ROL = (SELECT ID_ROL FROM ROL WHERE NOMBRE_ROL = '" + this.com_rol.getSelectedItem() + "')\n" + ""
               + "WHERE ID_ROL IS NULL");
       
       
       
       JOptionPane.showMessageDialog(null, "Se agregó el usuario " + this.nom_usu.getText() + "." ,"Crea usuario",JOptionPane.INFORMATION_MESSAGE);
       
       this.nom_usu.setText("");
       this.Activo.clearSelection();
       this.rd_si.setEnabled(false);
       this.rd_no.setEnabled(false);
       this.com_rol.setEnabled(false);
       this.com_rol.removeAllItems();
       this.gu_usu.setEnabled(false);
       
       
       consulta();
       this.oracle.deconectar();
            
        }
            
        
    } catch (SQLException ex) {
        Logger.getLogger(formulario.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        }  
       
   }
   
   
   public void eliminar()
   {
   
       this.oracle.conectar();
       this.oracle.eliminar("DELETE FROM USUARIO WHERE ID_USUARIO =" + this.id_usu.getText());
       
       
      
       consulta();
   
       this.oracle.deconectar();
       
       JOptionPane.showMessageDialog(null, "Se eliminó el usuario " + this.nom_usu.getText() + " con el id " + this.id_usu.getText() + "." ,"Elimina usuario",JOptionPane.INFORMATION_MESSAGE);
       
       this.id_usu.setText("");
       this.nom_usu.setText("");
       this.Activo.clearSelection();
       this.rd_no.setEnabled(false);
       this.rd_si.setEnabled(false);
       this.nom_usu.setEnabled(false);
       this.edi_usu.setEnabled(false);
       this.eli_usu.setEnabled(false);
       com_rol.setEnabled(false);
       this.com_rol.removeAllItems();
       
       
   }
   
   
   
   public void editar()
   {    
       
       if (this.nom_usu.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Se debe ingresar un nombre válido." ,"Validación",JOptionPane.ERROR_MESSAGE);
        }
        else if (this.com_rol.getSelectedItem().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Se debe ingresar un rol válido." ,"Validación",JOptionPane.ERROR_MESSAGE);
        }
        else if (rd_si.isSelected()== false && rd_no.isSelected()==false)
        {
        JOptionPane.showMessageDialog(null, "Se seleccionar si el usuario está activo o no." ,"Validación",JOptionPane.ERROR_MESSAGE);
        }
        else
        {

       if (rd_si.isSelected()) {
       eleccion = "SI";
       }
       else if (rd_no.isSelected())
       {
       eleccion = "NO";
       }
       
       
       this.oracle.conectar();
       this.oracle.modificar("UPDATE USUARIO SET NOMBRE_USU = '" + this.nom_usu.getText() + "', ACTIVO = '" + eleccion + "', ID_ROL = (SELECT ID_ROL FROM ROL WHERE NOMBRE_ROL = '" + this.com_rol.getSelectedItem() + "') WHERE ID_USUARIO =" + this.id_usu.getText());
       consulta();
       this.oracle.deconectar();
       
       JOptionPane.showMessageDialog(null, "Se actualazó el usuario " + this.nom_usu.getText() + " con el id " + this.id_usu.getText() + "." ,"Actualiza usuario",JOptionPane.INFORMATION_MESSAGE);
       
       this.id_usu.setText("");
       this.nom_usu.setText("");
       this.Activo.clearSelection();
       this.rd_no.setEnabled(false);
       this.rd_si.setEnabled(false);
       this.nom_usu.setEnabled(false);
       this.edi_usu.setEnabled(false);
       this.eli_usu.setEnabled(false);
       com_rol.setEnabled(false);
       this.com_rol.removeAllItems();
   
   } 
   
   }
   
       
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Activo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cons_usu = new javax.swing.JButton();
        lim_usu = new javax.swing.JButton();
        lbl_1 = new javax.swing.JLabel();
        nom_cri = new javax.swing.JTextField();
        lbl_6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        crea_usu1 = new javax.swing.JButton();
        lbl_7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        con_usu = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        gu_usu = new javax.swing.JButton();
        edi_usu = new javax.swing.JButton();
        eli_usu = new javax.swing.JButton();
        lbl_8 = new javax.swing.JLabel();
        lbl_2 = new javax.swing.JLabel();
        id_usu = new javax.swing.JTextField();
        lbl_3 = new javax.swing.JLabel();
        nom_usu = new javax.swing.JTextField();
        lbl_4 = new javax.swing.JLabel();
        com_rol = new javax.swing.JComboBox<>();
        lbl_5 = new javax.swing.JLabel();
        rd_si = new javax.swing.JRadioButton();
        rd_no = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión de usuarios");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cons_usu.setText("Consultar");
        cons_usu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cons_usuActionPerformed(evt);
            }
        });

        lim_usu.setText("Limpiar");
        lim_usu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lim_usuActionPerformed(evt);
            }
        });

        lbl_1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_1.setText("Nombre:");

        lbl_6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_6.setText("Parametros de búsqueda");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbl_1)
                .addGap(18, 18, 18)
                .addComponent(nom_cri)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cons_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lim_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cons_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lim_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_1)
                    .addComponent(nom_cri, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        crea_usu1.setText("Crear");
        crea_usu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crea_usu1ActionPerformed(evt);
            }
        });

        lbl_7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_7.setText("Lista de usuarios");

        con_usu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                con_usuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(con_usu);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_7)
                            .addComponent(crea_usu1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lbl_7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(crea_usu1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        gu_usu.setText("Guardar");
        gu_usu.setEnabled(false);
        gu_usu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gu_usuActionPerformed(evt);
            }
        });

        edi_usu.setText("Editar");
        edi_usu.setEnabled(false);
        edi_usu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edi_usuActionPerformed(evt);
            }
        });

        eli_usu.setText("Eliminar");
        eli_usu.setEnabled(false);
        eli_usu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eli_usuActionPerformed(evt);
            }
        });

        lbl_8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_8.setText("Información de usuarios");

        lbl_2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_2.setText("ID:");

        id_usu.setEnabled(false);

        lbl_3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_3.setText("Nombre:");

        nom_usu.setEnabled(false);
        nom_usu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nom_usuKeyTyped(evt);
            }
        });

        lbl_4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_4.setText("Rol:");

        com_rol.setEnabled(false);

        lbl_5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbl_5.setText("Activo:");

        Activo.add(rd_si);
        rd_si.setText("Si");
        rd_si.setEnabled(false);
        rd_si.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rd_siMouseClicked(evt);
            }
        });

        Activo.add(rd_no);
        rd_no.setText("No");
        rd_no.setEnabled(false);
        rd_no.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rd_noMouseClicked(evt);
            }
        });
        rd_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_noActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbl_2)
                        .addGap(54, 54, 54)
                        .addComponent(id_usu))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbl_3)
                        .addGap(18, 18, 18)
                        .addComponent(nom_usu))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbl_4)
                        .addGap(47, 47, 47)
                        .addComponent(com_rol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(gu_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edi_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(eli_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_8)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lbl_5)
                                .addGap(25, 25, 25)
                                .addComponent(rd_si)
                                .addGap(18, 18, 18)
                                .addComponent(rd_no)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_8)
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gu_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edi_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eli_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_2)
                    .addComponent(id_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nom_usu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_4)
                    .addComponent(com_rol, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_5)
                    .addComponent(rd_si)
                    .addComponent(rd_no))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lim_usuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lim_usuActionPerformed
        limpia();
    }//GEN-LAST:event_lim_usuActionPerformed

    private void eli_usuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eli_usuActionPerformed
        eliminar();
    }//GEN-LAST:event_eli_usuActionPerformed

    private void cons_usuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cons_usuActionPerformed
        consulta();
    }//GEN-LAST:event_cons_usuActionPerformed

    private void con_usuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_con_usuMouseClicked
        pasar();
    }//GEN-LAST:event_con_usuMouseClicked

    private void crea_usu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crea_usu1ActionPerformed
        crear();
    }//GEN-LAST:event_crea_usu1ActionPerformed

    private void gu_usuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gu_usuActionPerformed
        guardar();
    }//GEN-LAST:event_gu_usuActionPerformed

    private void rd_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_noActionPerformed
        
    }//GEN-LAST:event_rd_noActionPerformed

    private void rd_siMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rd_siMouseClicked
        
    }//GEN-LAST:event_rd_siMouseClicked

    private void rd_noMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rd_noMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rd_noMouseClicked

    private void edi_usuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edi_usuActionPerformed
        editar();
    }//GEN-LAST:event_edi_usuActionPerformed

    private void nom_usuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nom_usuKeyTyped
        int key = evt.getKeyChar();
        
        boolean mayus = key >= 65 && key <= 90; 
        boolean minus = key >= 97 && key <= 122;
        boolean espacio = key == 32;
        
        
        if (!(mayus || espacio || minus ))
        {
        evt.consume();
        }
        
        
        
    }//GEN-LAST:event_nom_usuKeyTyped

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
            java.util.logging.Logger.getLogger(formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formulario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.ButtonGroup Activo;
    private javax.swing.JComboBox<String> com_rol;
    private javax.swing.JTable con_usu;
    private javax.swing.JButton cons_usu;
    private javax.swing.JButton crea_usu1;
    private javax.swing.JButton edi_usu;
    private javax.swing.JButton eli_usu;
    private javax.swing.JButton gu_usu;
    private javax.swing.JTextField id_usu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_1;
    private javax.swing.JLabel lbl_2;
    private javax.swing.JLabel lbl_3;
    private javax.swing.JLabel lbl_4;
    private javax.swing.JLabel lbl_5;
    private javax.swing.JLabel lbl_6;
    private javax.swing.JLabel lbl_7;
    private javax.swing.JLabel lbl_8;
    private javax.swing.JButton lim_usu;
    private javax.swing.JTextField nom_cri;
    private javax.swing.JTextField nom_usu;
    private javax.swing.JRadioButton rd_no;
    private javax.swing.JRadioButton rd_si;
    // End of variables declaration//GEN-END:variables
}
