package telkomedics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import telkomedics.helper.Database;
import telkomedics.models.Mahasiswa;

public class SignupDao {
    private final Database database = new Database();

    public void insertData(Mahasiswa mahasiswa) {
        String query = "INSERT INTO data_mahasiswa (nim, nama, password, nohp, alamat) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, mahasiswa.getNim());
            preparedStatement.setString(2, mahasiswa.getNama());
            preparedStatement.setString(3, mahasiswa.getPassword());
            preparedStatement.setString(4, mahasiswa.getNoHp());
            preparedStatement.setString(5, mahasiswa.getAlamat());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
    }

    public boolean isNimExist(Mahasiswa mahasiswa) {
        String query = "SELECT COUNT(*) FROM data_mahasiswa WHERE nim = ?";
        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, mahasiswa.getNim());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error checking NIM existence: " + e.getMessage());
        }
        return false;
    }
}
