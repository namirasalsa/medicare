package telkomedics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import telkomedics.helper.Database;

public class JadwalDokterDao {
    private final Database database = new Database();

    public DefaultTableModel getJadwalDokterGigi() {
        String query = "SELECT hari, jadwal, nama_dokter, id_dokter_gigi FROM data_dokter_gigi";
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] {"ID Dokter", "Nama Dokter", "Jam Aktif", "Hari Aktif"});
    
        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tableModel.addRow(new Object[] {
                    resultSet.getString("id_dokter_gigi"),
                    resultSet.getString("nama_dokter"),
                    resultSet.getString("jadwal"),
                    resultSet.getString("hari")
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }

        return tableModel;
    }

    public DefaultTableModel getJadwalDokterUmum() {
        String query = "SELECT hari, jadwal, nama_dokter, id_dokter_umum FROM data_dokter_umum";
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] {"ID Dokter", "Nama Dokter", "Jam Aktif", "Hari Aktif"});
    
        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tableModel.addRow(new Object[] {
                    resultSet.getString("id_dokter_umum"),
                    resultSet.getString("nama_dokter"),
                    resultSet.getString("jadwal"),
                    resultSet.getString("hari")
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }

        return tableModel;
    }

    public String getDokterGigi(String hari) {
        String query = "SELECT nama_dokter FROM data_dokter_gigi WHERE hari = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, hari);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nama_dokter");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }

        return "";
    }

    public String getDokterUmum(String hari) {
        String query = "SELECT nama_dokter FROM data_dokter_umum WHERE hari = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, hari);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nama_dokter");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }

        return "";
    }
}
