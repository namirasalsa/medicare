package telkomedics.dao.daftar_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import telkomedics.helper.Database;

import telkomedics.models.Pasien;

public class DaftarPasienDao {
    private final Database database = new Database();

    public DefaultTableModel getDaftarPasien(Pasien pasien) {
        String query = "SELECT id, nama, no_antrian, tanggal_pemeriksaan FROM data_pasien WHERE jenis_poli = ?";

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] {"ID", "Nama Pasien", "No Antrian", "Tanggal Pemeriksaan"});

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, pasien.getJenisPoli());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tableModel.addRow(new Object[] {
                        resultSet.getString("id"),
                        resultSet.getString("nama"),
                        resultSet.getInt("no_antrian"),
                        resultSet.getDate("tanggal_pemeriksaan")
                    });
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }

        return tableModel;
    }

    public void deleteDaftarPasien(Pasien pasien) {
        String query = "DELETE FROM data_pasien WHERE id = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, pasien.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
    }

    public void updateNomorAntrian(Pasien pasien) {
        String query = "UPDATE data_pasien SET no_antrian = no_antrian - 1 WHERE no_antrian > ? AND jenis_poli = ? AND tanggal_pemeriksaan = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, pasien.getNoAntrian());
            preparedStatement.setString(2, pasien.getJenisPoli());
            preparedStatement.setString(3, pasien.getTanggalPemeriksaan());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
    }
}
