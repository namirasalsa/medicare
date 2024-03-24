package telkomedics.dao.edit_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import telkomedics.helper.Database;

import telkomedics.models.DokterGigi;

public class EditDokterGigiDao {
    private final Database database = new Database();

    public boolean inputIdDokterEdit(DokterGigi dokterGigi) {
        String query = "SELECT id_dokter_gigi FROM data_dokter_gigi WHERE id_dokter_gigi = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterGigi.getId());
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }

        return false;
    }

    public void editHariNamaDokterBasedOnId(DokterGigi dokterGigi) {
        String query = "UPDATE data_dokter_gigi SET hari = ?, nama_dokter = ? WHERE id_dokter_gigi = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterGigi.getHari());
            preparedStatement.setString(2, dokterGigi.getNama());
            preparedStatement.setString(3, dokterGigi.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
    }

    public int hariDuplicated(DokterGigi dokterGigi) {
        String query = "SELECT COUNT(*) FROM data_dokter_gigi WHERE hari = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterGigi.getHari());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return 0;
    }

    public int namaDuplicated(DokterGigi dokterGigi) {
        String query = "SELECT COUNT(*) FROM data_dokter_gigi WHERE nama_dokter = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterGigi.getNama());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return 0;
    }

    public void hapusDokterGigi(DokterGigi dokterGigi) {
        String query = "UPDATE data_dokter_gigi SET hari = NULL, nama_dokter = NULL WHERE id_dokter_gigi = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterGigi.getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
    }
}
