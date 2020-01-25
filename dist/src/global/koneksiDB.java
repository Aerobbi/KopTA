/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author User
 */
public class koneksiDB {
    private static Connection koneksi;
  
  public static Connection ambilKoneksi(){
      if (koneksi == null){
          MysqlDataSource dataKoneksi = new MysqlDataSource();
          dataKoneksi.setURL ("jdbc:mysql://localhost/skrip_koperasi");
          dataKoneksi.setUser ("root");
          dataKoneksi.setPassword("");
          try {
              koneksi = dataKoneksi.getConnection ();
          } catch (SQLException ex) {
              JOptionPane.showMessageDialog (null,"Error Koneksi :"+ ex.getMessage());
          }  
         }
          return koneksi;
  }
  
Connection koneksi() {
        throw new UnsupportedOperationException("Not supported yet.");
        }
}