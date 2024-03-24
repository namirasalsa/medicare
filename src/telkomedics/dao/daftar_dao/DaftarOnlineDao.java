package telkomedics.dao.daftar_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import telkomedics.helper.Database;

import telkomedics.models.Pasien;

public class DaftarOnlineDao {
    private final Database database = new Database();

    public void insertDataDaftar(Pasien pasien) {
        String query = "INSERT INTO data_pasien (id, nim, nama, no_antrian, tanggal_pemeriksaan, jenis_poli, nama_dokter) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, Integer.parseInt(pasien.getId()));
            preparedStatement.setString(2, pasien.getNim());
            preparedStatement.setString(3, pasien.getNama());
            preparedStatement.setInt(4, pasien.getNoAntrian());
            preparedStatement.setDate(5, java.sql.Date.valueOf(pasien.getTanggalPemeriksaan()));
            preparedStatement.setString(6, pasien.getJenisPoli());
            preparedStatement.setString(7, pasien.getNamaDokter());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
    }

    public int countExistingPatients(Pasien pasien) {
        String query = "SELECT COUNT(*) FROM data_pasien WHERE tanggal_pemeriksaan = ? AND jenis_poli = ?";
        int existingPatients = 0;

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(pasien.getTanggalPemeriksaan()));
            preparedStatement.setString(2, pasien.getJenisPoli());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    existingPatients = resultSet.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during counting existing patients: " + e.getMessage());
        }

        return existingPatients;
    }

    public int idPasien() {
        String query = "SELECT MAX(id) FROM data_pasien";
        int id = 0;

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during counting existing patients: " + e.getMessage());
        }

        return id;
    }

    public boolean dataPasienIsExist(Pasien pasien) {
        String query = "SELECT COUNT(*) FROM data_pasien WHERE tanggal_pemeriksaan = ? AND nama = ?";
        int existingPatients = 0;

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(pasien.getTanggalPemeriksaan()));
            preparedStatement.setString(2, pasien.getNama());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    existingPatients = resultSet.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during counting existing patients: " + e.getMessage());
        }

        return existingPatients > 0;
    }
}
