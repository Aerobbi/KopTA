/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static global.koneksiDB.ambilKoneksi;
import global.koneksiDB;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.ButtonGroup;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author User
 */
public class FromAnggota extends javax.swing.JInternalFrame {
    
    String tglmasuk;
    String birth;
    /**
     * Creates new form FromAnggota
     */
    public FromAnggota() {
        initComponents();
        isiTabel();
        buttonGroup();
        autoSerial();
        
        jTable1.setAutoCreateRowSorter(true);
    }
    
    private void reset (){
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jDateChooser1.setDate(null);
        jRadioButton1.setSelected(true);
        jTextField6.setText("");
        jTextField5.setText("");
        jTextField4.setText("");
        jDateChooser2.setDate(null);
        jComboBox1.setSelectedItem("Pegawai");
        jLabel11.setText("");
        jTextField7.setText("");
        tanggalAwal.setDate(null);
        tanggalAkhir.setDate(null);
    }
    
    private void autoSerial (){
        String queryAuto = "SELECT MAX(RIGHT(no_anggota, 2)) AS id FROM anggota";
        Statement statement;
        Connection connection;
        try {
            connection = koneksiDB.ambilKoneksi();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryAuto);
            while (rs.next()){
                if (rs.first() == false){
                jTextField1.setText("AK-01");
                } else{
                    rs.last();
                    int setSerial = rs.getInt(1)+1;
                    String id = String.valueOf(setSerial);
                    int serialNext = id.length();
                    for (int a= 0; a < 1 - serialNext; a++){
                    id = "01" + id;
                    }
                jTextField1.setText("AK-0" + id);
                }
            }
        } catch (Exception ex){
                System.out.println(ex);
    }
    }
    
    private void isiTabel(){
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        String query = "SELECT * FROM anggota";
        Statement statement;
        Connection connection;
        try{
            connection = koneksiDB.ambilKoneksi();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                String No = rs.getString("no_anggota");
                String Nama = rs.getString("nama_anggota");
                String Tempat = rs.getString("tempat_lahir");
                String TglLahir = rs.getString("tgl_lahir");
                String JnsKelamin = rs.getString("jenis_kelamin");
                String Alamat = rs.getString("alamat");
                String NoTlp = rs.getString("no_tlp");
                String SimpPokok = rs.getString("simp_pokok");
                String TglMasuk = rs.getString("tgl_masuk");
                String Status = rs.getString("status");
                String[] data = new String[] {No,Nama,Tempat,TglLahir,JnsKelamin,Alamat,NoTlp,SimpPokok,TglMasuk,Status};
                tableModel.addRow(data);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this,"ERROR :"+ e.getMessage());
        }
    }
    
    private void cari(){
        if(jTextField7.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Isikan data Pencarian dengan kata kunci Nomor Anggota, Nama Anggota atau Tanggal Masuk");
        } else {
            try {
                Connection connection;
                connection = koneksiDB.ambilKoneksi();
                Statement st = connection.createStatement();
                String cari = jTextField7.getText();
                ResultSet rs = st.executeQuery ("SELECT * FROM anggota WHERE no_anggota LIKE '%"+cari+
                "%' OR nama_anggota LIKE '%"+cari+"%' OR tgl_masuk LIKE '%"+cari+"%'");
                while(rs.next()){
                    String JnsKelamin = rs.getString("jenis_kelamin");
                    if (JnsKelamin.equals("Laki-laki")){
                    jRadioButton1.setSelected(true);
                    } else if (JnsKelamin.equals("Perempuan")) {
                    jRadioButton2.setSelected(true);
                    }
                    jTextField1.setText(rs.getString("no_anggota"));
                    jTextField2.setText(rs.getString("nama_anggota"));
                    jTextField3.setText(rs.getString("tempat_lahir"));
                    jDateChooser1.setDate(rs.getDate("tgl_lahir"));
                    jTextField6.setText(rs.getString("alamat"));
                    jTextField5.setText(rs.getString("no_tlp"));
                    jTextField4.setText(rs.getString("simp_pokok"));
                    jDateChooser2.setDate(rs.getDate("tgl_masuk"));
                    jComboBox1.setSelectedItem(rs.getString("status"));
                }
                jLabel11.setText("Data ditemukan!");
                jLabel11.setForeground(Color.green);
                if (jTextField2.getText().isEmpty()){
                jLabel11.setText("Data tidak ditemukan");
                jLabel11.setForeground(Color.red);
                JOptionPane.showMessageDialog(this, "Gunakan kata kunci Nomor Anggota, Nama Anggota atau Tanggal Masuk", "Ada Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                System.out.println(ex);
                }
            }    
        } 
    
    private void tambah(){
        String queryInsert = "INSERT INTO anggota (no_anggota, nama_anggota, tempat_lahir, tgl_lahir, jenis_kelamin, alamat, no_tlp, simp_pokok, tgl_masuk, status) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Connection connection;
        PreparedStatement statement;
        String jenisKelamin = null;
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        if(jRadioButton1.isSelected()){
            jenisKelamin = jRadioButton1.getText();
        }if(jRadioButton2.isSelected()){
            jenisKelamin = jRadioButton2.getText();
        }
        try {
            connection = koneksiDB.ambilKoneksi();
            statement = connection.prepareStatement (queryInsert);
            statement.setString (1, jTextField1.getText());
            statement.setString (2, jTextField2.getText());
            statement.setString (3, jTextField3.getText());
            statement.setString (4, (birth));
            statement.setString (5, (jenisKelamin));
            statement.setString (6, jTextField6.getText());
            statement.setString (7, jTextField5.getText());
            statement.setString (8, jTextField4.getText());
            statement.setString (9, (tglmasuk));
            statement.setString (10, (String) jComboBox1.getSelectedItem());
            int hasilQuery = statement.executeUpdate();
            if (hasilQuery == 1){        
                jLabel11.setText("Selamat, Data Anggota Berhasil di Tambahkan!");
                jLabel11.setForeground(Color.green);
            }
    } catch (Exception ex) {
        jLabel11.setText("Lihat! Data Anggota belum terisi dengan benar");
        jLabel11.setForeground(Color.red);
        System.out.println(ex);
    }
    }
    
    private void edit() {
        String queryEdit = "UPDATE anggota SET nama_anggota = ?, tempat_lahir = ?, tgl_lahir = ?, jenis_kelamin = ?, alamat = ?, no_tlp = ?, simp_pokok = ?, tgl_masuk = ?, status = ? WHERE no_anggota = ?  ";
        Connection connection;
        PreparedStatement statement;
        String jenisKelamin = null;
        if(jRadioButton1.isSelected()){
            jenisKelamin = jRadioButton1.getText();
        }if(jRadioButton2.isSelected()){
            jenisKelamin = jRadioButton2.getText();
        }
        try {
            connection = koneksiDB.ambilKoneksi();
            statement = connection.prepareStatement (queryEdit);
            
            statement.setString(10, jTextField1.getText());
            statement.setString(1, jTextField2.getText());
            statement.setString(2, jTextField3.getText());
            statement.setString (3, (birth));
            statement.setString(4, (jenisKelamin));
            statement.setString(5, jTextField6.getText());
            statement.setString(6, jTextField5.getText());
            statement.setString(7, jTextField4.getText());
            statement.setString(8, (tglmasuk));
            statement.setString(9, (String) jComboBox1.getSelectedItem());
            int hasilQuery = statement.executeUpdate();
            if (hasilQuery == 1){        
                jLabel11.setText("Yes, Data Anggota Berhasil di Perbarui!");
                jLabel11.setForeground(Color.green);
            }
            else {
                jLabel11.setText("Kesalahan! Mohon cek kembali pengisian anda!!!");
                jLabel11.setForeground(Color.red);
            }
    } catch (Exception ex) {
        System.out.println(ex);
    }
    }
    
    private void hapus (){
        String queryDelete = "DELETE FROM anggota WHERE no_anggota =?";
        Connection connection;
        PreparedStatement statement;
        try{
            
            int response = JOptionPane.showConfirmDialog(this, "Benar-benar ingin menghaspusnya?", "PERINGATAN!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);
            
            switch (response) {
                case JOptionPane.YES_OPTION:
                    connection = koneksiDB.ambilKoneksi();
                    statement = connection.prepareStatement (queryDelete);
                    statement.setString (1, jTextField1.getText());
                    int hasilQuery = statement.executeUpdate();
                    if (jTextField2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih untuk dihapus");
                    }
                    if (hasilQuery ==1){
                        JOptionPane.showMessageDialog(this, "Data Terhapus");
                        jLabel11.setText("Data sudah terhapus!");
                        jLabel11.setForeground(Color.red);
                    }   break;
                case JOptionPane.NO_OPTION:
                    JOptionPane.showMessageDialog(this, "Dibatalkan");
                    break;
                case JOptionPane.CLOSED_OPTION:
                    JOptionPane.showMessageDialog(this, "CLOSED");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void refresh () {
    DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
    int baris = tableModel.getRowCount();
        for (int i = 0;0 < baris; i++){
            tableModel.removeRow(0);
        }
        isiTabel();
    }
    
    private void buktiAnggota (){
        try {
            String path="src/Laporan/buktiAnggota.jasper";
            HashMap parameter = new HashMap();
            parameter.put("namaAnggota",jTextField2.getText());   
            JasperPrint print = JasperFillManager.fillReport(path,parameter,ambilKoneksi());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
                                JOptionPane.showMessageDialog(rootPane,"Data belum dipilih");
                                System.out.println(ex);
                }
    }
    
    private void laporan () {
        Connection connection;
        try {
            connection = koneksiDB.ambilKoneksi();
            String jd = "src/Laporan/lapAng.jasper";
            HashMap parameter = new HashMap();
            parameter.put("tgl1", tanggalAwal.getDate());
            parameter.put("tgl2", tanggalAkhir.getDate());
            JasperPrint print = JasperFillManager.fillReport(jd,parameter,connection);
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
                                JOptionPane.showMessageDialog(rootPane,"Gagal membuat Laporan Data Anggota");
                                System.out.println(ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        tanggalAwal = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        tanggalAkhir = new com.toedter.calendar.JDateChooser();
        jButton7 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setTitle("Data Anggoa");
        setPreferredSize(new java.awt.Dimension(982, 520));

        jPanel2.setName(""); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DATA ANGGOTA");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jLabel1.setIconTextGap(2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("No. Anggota");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Nama Anggota");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Tempat, Tgl Lahir");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Jenis Kelamin");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton1.setText("Laki-laki");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton2.setText("Perempuan");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Tanggal Masuk");

        jDateChooser2.setDateFormatString("yyyy-MM-dd");
        jDateChooser2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Simpanan Pokok");

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Status");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pegawai", "Honorer" }));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("No. Telepon");

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Alamat");

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField6KeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/page_add.png"))); // NOI18N
        jButton1.setText("TAMBAH");
        jButton1.setIconTextGap(5);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/report_edit.png"))); // NOI18N
        jButton2.setText("EDIT");
        jButton2.setIconTextGap(5);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/page_delete.png"))); // NOI18N
        jButton3.setText("HAPUS");
        jButton3.setIconTextGap(5);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/page_refresh.png"))); // NOI18N
        jButton4.setText("REFRESH");
        jButton4.setIconTextGap(5);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/report_go.png"))); // NOI18N
        jButton5.setText("BUKTI");
        jButton5.setIconTextGap(5);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jTextField2)
                            .addComponent(jTextField1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(jTextField5)))
                    .addComponent(jTextField6))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jRadioButton1)
                        .addComponent(jRadioButton2)
                        .addComponent(jLabel6))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField7.setToolTipText("kata kunci nomor anggota, nama anggota atau tanggal masuk");
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/search.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Anggota", "Nama Anggota", "Tempat Lahir", "Tanggal Lahir", "Jenis Kelamin", "Alamat", "No. Telepon", "Simpanan Pokok", "Tanggal Masuk", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Laporan Data Anggota", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Dari Tanggal");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tanggalAwal.setDateFormatString("yyyy-MM-dd");
        tanggalAwal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("s/d Tanggal");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tanggalAkhir.setDateFormatString("yyyy-MM-dd");
        tanggalAkhir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/report_go.png"))); // NOI18N
        jButton7.setText("CETAK");
        jButton7.setIconTextGap(2);
        jButton7.setMargin(new java.awt.Insets(2, 12, 2, 14));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggalAwal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tanggalAkhir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tanggalAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tanggalAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(248, 248, 248)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGroup(){
        ButtonGroup bg = new ButtonGroup();
        bg.add(jRadioButton1);
        bg.add(jRadioButton2);
    }
    
    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        if(jDateChooser1.getDate()!=null){
            SimpleDateFormat Format=new SimpleDateFormat("yyyy-MM-dd");
            birth = Format.format(jDateChooser1.getDate());
        }
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if(jDateChooser2.getDate()!=null){
            SimpleDateFormat Format=new SimpleDateFormat("yyyy-MM-dd");
            tglmasuk = Format.format(jDateChooser2.getDate());
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    @SuppressWarnings("static-access")
    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
            jTextField2.requestFocus();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    @SuppressWarnings("static-access")
    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
            jTextField3.requestFocus();
        }
    }//GEN-LAST:event_jTextField2KeyPressed

    @SuppressWarnings("static-access")
    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
        jDateChooser1.requestFocus();
        }
    }//GEN-LAST:event_jTextField3KeyPressed

    @SuppressWarnings("static-access")
    private void jTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
        jTextField5.requestFocus();
        }
    }//GEN-LAST:event_jTextField6KeyPressed

    @SuppressWarnings("static-access")
    private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
        jComboBox1.requestFocus();
        }
    }//GEN-LAST:event_jTextField5KeyPressed

    @SuppressWarnings("static-access")
    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
        jDateChooser2.requestFocus();
        }
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        jLabel11.setText("");
        try {
                int row = jTable1.rowAtPoint(evt.getPoint());

                String NoAnggota = jTable1.getValueAt(row, 0).toString();
                String NamaAnggota = jTable1.getValueAt(row, 1).toString();
                String TempatLahir = jTable1.getValueAt(row, 2).toString();
                String TglLahir = jTable1.getValueAt(row, 3).toString();
                String JnsKelamin = jTable1.getValueAt(row, 4).toString();
                String Alamat = jTable1.getValueAt(row, 5).toString();
                String NoTlp = jTable1.getValueAt(row, 6).toString();
                String SimpPokok = jTable1.getValueAt(row, 7).toString();
                String TglMasuk = jTable1.getValueAt(row, 8).toString();
                String Status = jTable1.getValueAt(row, 9).toString();
                
                if (JnsKelamin.equals("Laki-laki")) {
                jRadioButton1.setSelected(true);
                } else {
                jRadioButton2.setSelected(true);
                }
                java.util.Date date1 = null;
                try {
                date1 = new SimpleDateFormat ("yyyy-MM-dd").parse(TglLahir);
                } catch (ParseException ex) {
                System.out.println (ex);
                }
                java.util.Date date2 =null;
                try {
                date2 = new SimpleDateFormat ("yyyy-MM-dd").parse(TglMasuk);
                } catch (ParseException ex){
                System.out.println (ex);
                }

                jTextField1.setText(String.valueOf(NoAnggota));
                jTextField2.setText(String.valueOf(NamaAnggota));
                jTextField3.setText(String.valueOf(TempatLahir));
                jDateChooser1.setDate(date1);
                jTextField6.setText(String.valueOf(Alamat));
                jTextField5.setText(String.valueOf(NoTlp));
                jTextField4.setText(String.valueOf(SimpPokok));
                jDateChooser2.setDate(date2);
                jComboBox1.setSelectedItem(Status);
            } catch (Exception e){
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tambah();
        refresh();
        autoSerial();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        edit();
        refresh();
        autoSerial();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        hapus();
        autoSerial();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        refresh();
        reset();
        autoSerial();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Locale locale = new Locale ("id", "ID");
        Locale.setDefault(locale); 
        buktiAnggota();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
        jButton6ActionPerformed(evt);
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Locale locale = new Locale ("id", "ID");
        Locale.setDefault(locale);
        laporan();
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private com.toedter.calendar.JDateChooser tanggalAkhir;
    private com.toedter.calendar.JDateChooser tanggalAwal;
    // End of variables declaration//GEN-END:variables
}
