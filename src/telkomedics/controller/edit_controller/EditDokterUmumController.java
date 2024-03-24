package telkomedics.controller.edit_controller;

import telkomedics.controller.AdminDashboardController;

import telkomedics.dao.edit_dao.EditDokterUmumDao;

import telkomedics.helper.TelkomedicsException;

import telkomedics.models.DokterUmum;

import telkomedics.view.AdminDashboard;
import telkomedics.view.FrameEditDokterUmum;

public class EditDokterUmumController {
    private FrameEditDokterUmum frameEditDokterUmum;
    private EditDokterUmumDao editDokterUmumDao;
    private DokterUmum dokterUmum;

    public EditDokterUmumController(FrameEditDokterUmum frameEditDokterUmum, DokterUmum dokterUmum) {
        this.frameEditDokterUmum = frameEditDokterUmum;
        frameEditDokterUmum.setVisible(true);

        this.editDokterUmumDao = new EditDokterUmumDao();

        this.frameEditDokterUmum.simpanEditButtonListener(new SimpanEditButtonListener());
        this.frameEditDokterUmum.hapusButtonListener(new HapusButtonListener());
        this.frameEditDokterUmum.kembaliButtonListener(new KembaliButtonListener());

        this.frameEditDokterUmum.getSetIdDokter().setText(dokterUmum.getId());
        this.frameEditDokterUmum.getSetNamaDokter().setText(dokterUmum.getNama());
    }

    class SimpanEditButtonListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            dokterUmum = new DokterUmum();

            try {
                dokterUmum.setId(frameEditDokterUmum.getSetIdDokter().getText());
                dokterUmum.setHari(frameEditDokterUmum.getEditHariComboBox().getSelectedItem().toString());
                dokterUmum.setNama(frameEditDokterUmum.getSetNamaDokter().getText());
                String namaDokter = frameEditDokterUmum.getSetNamaDokter().getText();

                if (namaDokter.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Nama dokter tidak boleh kosong!");
                    return;
                }

            } catch (TelkomedicsException e1) {
                javax.swing.JOptionPane.showMessageDialog(null, e1.getMessage());
            }

            if (editDokterUmumDao.inputIdDokterEdit(dokterUmum)) {
                if (editDokterUmumDao.hariDuplicated(dokterUmum) > 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Sudah ada dokter pada hari tersebut");
                    return;
                } else {
                    int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin mengubah data ini?", "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
                        editDokterUmumDao.editHariNamaDokterBasedOnId(dokterUmum);
                        frameEditDokterUmum.dispose();
                        javax.swing.JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
                    } else {
                        return;
                    }
    
                    frameEditDokterUmum.getSetIdDokter().setText("");
                    frameEditDokterUmum.getSetNamaDokter().setText("");
                    
                    AdminDashboard adminDashboard = new AdminDashboard();
                    new AdminDashboardController(adminDashboard);
                    frameEditDokterUmum.dispose();
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "ID Dokter tidak ditemukan!");
            }
        }
    }

    class HapusButtonListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {     
            dokterUmum = new DokterUmum();

            try {
                dokterUmum.setId(frameEditDokterUmum.getSetIdDokter().getText());
            } catch (TelkomedicsException e1) {
                javax.swing.JOptionPane.showMessageDialog(null, e1.getMessage());
            }

            if (editDokterUmumDao.inputIdDokterEdit(dokterUmum)) {
                int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ini?", "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION);
                if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
                    editDokterUmumDao.hapusDokterUmum(dokterUmum);
                    javax.swing.JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                } else {
                    return;
                }

                frameEditDokterUmum.getSetIdDokter().setText("");

                AdminDashboard adminDashboard = new AdminDashboard();
                new AdminDashboardController(adminDashboard);
                frameEditDokterUmum.dispose();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "ID Dokter tidak ditemukan!");
            }
        }
    }

    class KembaliButtonListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            AdminDashboard adminDashboard = new AdminDashboard();
            new AdminDashboardController(adminDashboard);
            frameEditDokterUmum.dispose();
        }
    }
}
