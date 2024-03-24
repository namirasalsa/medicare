package telkomedics.dao.edit_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import telkomedics.helper.Database;
import telkomedics.models.DokterGigi;
import telkomedics.models.DokterUmum;

public class EditDokterUmumDao {
    private final Database database = new Database();

    public boolean inputIdDokterEdit(DokterUmum dokterUmum) {
        String query = "SELECT id_dokter_umum FROM data_dokter_umum WHERE id_dokter_umum = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterUmum.getId());
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }

        return false;
    }

    public void editHariNamaDokterBasedOnId(DokterUmum dokterUmum) {
        String query = "UPDATE data_dokter_umum SET hari = ?, nama_dokter = ? WHERE id_dokter_umum = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterUmum.getHari());
            preparedStatement.setString(2, dokterUmum.getNama());
            preparedStatement.setString(3, dokterUmum.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
    }

    public int hariDuplicated(DokterUmum dokterUmum) {
        String query = "SELECT COUNT(*) FROM data_dokter_umum WHERE hari = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, dokterUmum.getHari());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return 0;
    }

    public int namaDuplicated(DokterUmum dokterUmum) {
        String query = "SELECT COUNT(*) FROM data_dokter_umum WHERE nama_dokter = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterUmum.getNama());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
        return 0;
    }

    public void hapusDokterUmum(DokterUmum dokterUmum) {
        String query = "UPDATE data_dokter_umum SET hari = NULL, nama_dokter = NULL WHERE id_dokter_umum = ?";

        try (
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, dokterUmum.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error during data insertion: " + e.getMessage());
        }
    }

    public void editHariDokterGigiBasedOnIdName(DokterGigi dokterGigi) {
        String query = "UPDATE data_dokter_gigi SET hari = ? WHERE nama_dokter = ? and id_dokter_gigi = ?";

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
}

