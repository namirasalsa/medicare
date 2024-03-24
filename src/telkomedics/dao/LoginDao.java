package telkomedics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import telkomedics.helper.Database;
import telkomedics.helper.TelkomedicsException;

import telkomedics.models.Admin;
import telkomedics.models.Mahasiswa;

public class LoginDao {
    private final Database database = new Database();

    public boolean dataAdminIsExist(Admin admin) {
        String query = "SELECT * FROM data_admin WHERE id = ? AND password = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, admin.getId());
            preparedStatement.setString(2, admin.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return false;
    }

    public boolean dataMahasiswaIsExist(Mahasiswa mahasiswa) {
        String query = "SELECT * FROM data_mahasiswa WHERE nim = ? AND password = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, mahasiswa.getNim());
            preparedStatement.setString(2, mahasiswa.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return false;
    }

    public Mahasiswa namaMhs(Mahasiswa mahasiswa) throws TelkomedicsException {
        String query = "SELECT nama FROM data_mahasiswa WHERE nim = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, mahasiswa.getNim());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mahasiswa.setNama(resultSet.getString("nama"));
                return mahasiswa;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return null;
    }

    public DefaultTableModel getDaftarPengguna() {
        DefaultTableModel daftarPengguna = new DefaultTableModel();
        
        daftarPengguna.setColumnIdentifiers(new String[] {"NIM", "Nama", "No. HP", "Alamat"});

        String query = "SELECT nim, nama, nohp, alamat FROM data_mahasiswa";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                daftarPengguna.addRow(new Object[] {
                    resultSet.getString("nim"),
                    resultSet.getString("nama"),
                    resultSet.getString("nohp"),
                    resultSet.getString("alamat")
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return daftarPengguna;
    }
}
