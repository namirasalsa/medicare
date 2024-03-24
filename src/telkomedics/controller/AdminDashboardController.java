package telkomedics.controller;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import telkomedics.controller.edit_controller.EditDokterGigiController;
import telkomedics.controller.edit_controller.EditDokterUmumController;

import telkomedics.dao.JadwalDokterDao;
import telkomedics.dao.daftar_dao.DaftarPasienDao;
import telkomedics.dao.LoginDao;

import telkomedics.helper.TelkomedicsException;

import telkomedics.models.Pasien;
import telkomedics.models.DokterGigi;
import telkomedics.models.DokterUmum;

import telkomedics.view.AdminDashboard;
import telkomedics.view.FrameEditDokterGigi;
import telkomedics.view.FrameEditDokterUmum;
import telkomedics.view.Login;

import javax.swing.JComboBox;

public class AdminDashboardController {
    private AdminDashboard adminDashboard;

    private DaftarPasienDao daftarPasienDao;
    private JadwalDokterDao jadwalDokterDao;
    private LoginDao loginDao;

    private Pasien pasien;
    private DokterGigi dokterGigi;
    private DokterUmum dokterUmum;

    private JComboBox<String> poli; 
    private DefaultTableModel daftarPasien;
    private DefaultTableModel dataDokterGigi, dataDokterUmum, dataPengguna = new DefaultTableModel();

    public AdminDashboardController(AdminDashboard adminDashboard) {
        this.adminDashboard = adminDashboard;
        adminDashboard.setVisible(true);

        this.daftarPasienDao = new DaftarPasienDao();
        this.jadwalDokterDao = new JadwalDokterDao();
        this.loginDao = new LoginDao();
        
        adminDashboard.logoutButtonListener(new LogoutListener());
        adminDashboard.editDokterGigiButtonListener(new EditDokterGigiListener());
        adminDashboard.editDokterUmumButtonListener(new EditDokterUmumButtonListener());
        adminDashboard.daftarPasienButtonListener(new DaftarPasienListener());
        adminDashboard.hapusButtonListener(new HapusListener());
        adminDashboard.refreshButtonListener(new RefreshListener());

        this.viewDaftarDokter();
        this.viewDaftarPengguna();
        
        adminDashboard.getPoliComboBox().setSelectedIndex(0);
    }

    public void viewDaftarDokter() {
        dataDokterGigi = jadwalDokterDao.getJadwalDokterGigi();
        dataDokterUmum = jadwalDokterDao.getJadwalDokterUmum();
        adminDashboard.getPoliGigiTable().setModel(dataDokterGigi);
        adminDashboard.getPoliUmumTable().setModel(dataDokterUmum);
    }

    public void viewDaftarPengguna() {
        dataPengguna = loginDao.getDaftarPengguna();
        adminDashboard.getTableDataPengguna().setModel(dataPengguna);
    }

    class DaftarPasienListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            poli = adminDashboard.getPoliComboBox();
            pasien = new Pasien();
    
            if (poli.getSelectedItem().equals("Poli Umum")) {
                pasien.setJenisPoli(poli.getSelectedItem().toString());

                daftarPasien = daftarPasienDao.getDaftarPasien(pasien);
                adminDashboard.getTableDaftarPasien().setModel(daftarPasien);
            } else if (poli.getSelectedItem().equals("Poli Gigi")) {
                pasien.setJenisPoli(poli.getSelectedItem().toString());

                daftarPasien = daftarPasienDao.getDaftarPasien(pasien);
                adminDashboard.getTableDaftarPasien().setModel(daftarPasien);
            } else {
                JOptionPane.showMessageDialog(null, "Pilih poli terlebih dahulu!");
            }
        }
    }

    class HapusListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int rowCount = adminDashboard.getTableDaftarPasien().getRowCount();

            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, "Tabel kosong. Tidak ada data yang dapat dihapus.");
                return;
            }

            int row = adminDashboard.getTableDaftarPasien().getSelectedRow();

            if (row >= 0 && row < rowCount) {
                int confirm = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ini?", "Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Pasien pasien = new Pasien();

                    try {
                        pasien.setId(daftarPasien.getValueAt(row, 0).toString());
                        pasien.setNoAntrian(Integer.parseInt(daftarPasien.getValueAt(row, 2).toString()));
                        pasien.setTanggalPemeriksaan(daftarPasien.getValueAt(row, 3).toString());
                        pasien.setJenisPoli(poli.getSelectedItem().toString());
                    } catch (TelkomedicsException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }

                    daftarPasienDao.deleteDaftarPasien(pasien);
                    daftarPasienDao.updateNomorAntrian(pasien);
                    daftarPasien.removeRow(row);
                    JOptionPane.showMessageDialog(null, "Berhasil menghapus data!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu!");
            }
        }
    }

    class LogoutListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Login login = new Login();
                new LoginController(login);
                adminDashboard.dispose();
            } else {
                return;
            }
        }
    }

    class EditDokterGigiListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int selectedRow = adminDashboard.getPoliGigiTable().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu!");
            } else {
                dokterGigi = new DokterGigi();

                try {
                    dokterGigi.setId(adminDashboard.getPoliGigiTable().getValueAt(selectedRow, 0).toString());

                    Object namaDokterObject = adminDashboard.getPoliGigiTable().getValueAt(selectedRow, 1);
                    dokterGigi.setNama((namaDokterObject != null) ? namaDokterObject.toString() : "");

                    // Set other values like hari, jadwal, jenisPoli if needed
                } catch (TelkomedicsException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());                
                }

                FrameEditDokterGigi editDokterGigi = new FrameEditDokterGigi();
                new EditDokterGigiController(editDokterGigi, dokterGigi);
                adminDashboard.dispose();
            }
        }
    }

    class EditDokterUmumButtonListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int selectedRow = adminDashboard.getPoliUmumTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu!");
            } else {
                dokterUmum = new DokterUmum();

                try {
                    Object idObject = adminDashboard.getPoliUmumTable().getValueAt(selectedRow, 0);
                    Object namaObject = adminDashboard.getPoliUmumTable().getValueAt(selectedRow, 1);

                    dokterUmum.setId((idObject != null) ? idObject.toString() : "");
                    dokterUmum.setNama((namaObject != null) ? namaObject.toString() : "");
                } catch (TelkomedicsException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());                
                }

                FrameEditDokterUmum editDokterUmum = new FrameEditDokterUmum();
                new EditDokterUmumController(editDokterUmum, dokterUmum);
                adminDashboard.dispose();
            }
        }
    }

    class RefreshListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            adminDashboard.getTableDaftarPasien().setModel(new DefaultTableModel());

            pasien = new Pasien();

            pasien.setJenisPoli(adminDashboard.getPoliComboBox().getSelectedItem().toString());
            daftarPasien = daftarPasienDao.getDaftarPasien(pasien);
            adminDashboard.getTableDaftarPasien().setModel(daftarPasien);
        }
    }
}
