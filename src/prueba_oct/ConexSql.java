/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba_oct;

import java.sql.*;
import java.util.logging.*;
import javax.swing.*;


public class ConexSql {

    public String usuario;
    public String contra;
    public String base;
    public String host;
    public Statement sta;
    ResultSet res;
    public Connection con;
    public String url;
    public boolean aux;
    
    
    
    public ConexSql(String usuario, String contra, String base, String host) {
        this.con = null;
        this.aux = false;
        this.usuario = usuario;
        this.contra = contra;
        this.base = base;
        this.host = host;
        this.url = "jdbc:oracle:thin:@" + this.host + ":" + this.base;
    }
    
  
    
    public void conectar()
    {
        try
        {
    
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = (Connection)DriverManager.getConnection(this.url, this.usuario, this.contra);
             if (this.con != null)
      {
        
        this.aux = true;
        this.sta = (Statement)this.con.createStatement(this.res.TYPE_SCROLL_SENSITIVE, this.res.CONCUR_UPDATABLE);
      }
            
        }   
         catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Conectarse", JOptionPane.ERROR_MESSAGE);
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexSql.class.getName()).log(Level.SEVERE, null, ex);   
        }
        
    }
    
    
    public String insertar(String query)
    {
        String error = "ok";
        
        try
            {
            this.sta.execute(query);
            } 
        catch (SQLException ex) 
            {
        Logger.getLogger(ConexSql.class.getName()).log(Level.SEVERE, null, ex);
        error = ex.toString();
        System.out.println(error);
            }
        return error;       
    }
    

    public int eliminar(String query)
    {
        int error=1;
        
        try
         {
         this.sta.execute(query);
         
         } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ConexSql.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            error = 0;   
        }
        return error;  
    }

    
    public int modificar(String query)
    {
        int error=1;
        
        try
         {
         this.sta.execute(query);
         
         } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ConexSql.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            error = 0;   
        }
        return error;  
    }
    
    
    public ResultSet buscar(String tabla)
    {  
        try {
            
            this.res = this.sta.executeQuery(tabla);
            this.res.next();
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex );
        }
        
        return this.res;
      
    }
    
    public boolean deconectar()
    {
      boolean b = false;
      
      try
      {
      this.con.close();
      b = true;
      
      } catch (SQLException ex) {
            System.out.println(ex);
        }
        
      return b;
    }
    
    
}