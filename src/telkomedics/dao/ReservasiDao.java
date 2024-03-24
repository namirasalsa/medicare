package telkomedics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import telkomedics.helper.Database;

import telkomedics.models.Pasien;

public class ReservasiDao {
    private final Database database = new Database();

    public boolean dataPasienIsExist(Pasien pasien) {
        String query = "SELECT * FROM data_mahasiswa WHERE nim = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, pasien.getNim());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return false;
    }

    public boolean duplicateDataPasien(Pasien pasien) {
        String query = "SELECT * FROM data_pasien WHERE nama = ? AND tanggal_pemeriksaan = ? AND jenis_poli = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, pasien.getNama());
            preparedStatement.setDate(2, java.sql.Date.valueOf(pasien.getTanggalPemeriksaan()));
            preparedStatement.setString(3, pasien.getJenisPoli());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return false;
    }

    public DefaultTableModel getReservasi(Pasien pasien) {
        String query = "SELECT nama, no_antrian, jenis_poli, tanggal_pemeriksaan, nama_dokter FROM data_pasien WHERE nim = ?";
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] {"Nama Pasien", "No Antrian", "Nama Poli", "Tanggal Pemeriksaan", "Dokter Terkait"});
    
        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, pasien.getNim());
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nama = resultSet.getString("nama");
                int noAntrian = resultSet.getInt("no_antrian");
                String jenisPoli = resultSet.getString("jenis_poli");
                String tanggalPemeriksaan = resultSet.getString("tanggal_pemeriksaan");
                String namaDokter = resultSet.getString("nama_dokter");
                
                tableModel.addRow(new Object[]{nama, noAntrian, jenisPoli, tanggalPemeriksaan, namaDokter});
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data retrieval: " + e.getMessage());
        }
    
        return tableModel;
    }
    
    public void deleteReservasi(Pasien pasien) {
        String query = "DELETE FROM data_pasien WHERE nama = ? AND tanggal_pemeriksaan = ? AND jenis_poli = ? AND nama_dokter = ?";
    
        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, pasien.getNama());
            preparedStatement.setDate(2, java.sql.Date.valueOf(pasien.getTanggalPemeriksaan()));
            preparedStatement.setString(3, pasien.getJenisPoli());
            preparedStatement.setString(4, pasien.getNamaDokter());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data deletion: " + e.getMessage());
        }
    }
}
