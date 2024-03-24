package telkomedics.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import telkomedics.dao.JadwalDokterDao;
import telkomedics.dao.ReservasiDao;
import telkomedics.dao.LoginDao;
import telkomedics.dao.daftar_dao.DaftarOnlineDao;
import telkomedics.dao.daftar_dao.DaftarPasienDao;

import telkomedics.helper.TelkomedicsException;

import telkomedics.models.Mahasiswa;
import telkomedics.models.Pasien;

import telkomedics.view.Login;
import telkomedics.view.PasienDashboard;

import java.util.Calendar;

public class PasienDashboardController {
    private PasienDashboard pasienDashboard;

    private DaftarOnlineDao daftarOnlineDao;
    private ReservasiDao reservasiDao;
    private JadwalDokterDao jadwalDokterDao;
    private DaftarPasienDao daftarPasienDao;
    private LoginDao loginDao;

    private Pasien pasien;

    private Date date = null;
    private DefaultTableModel reservasiModel, dataDokterGigi, dataDokterUmum = new DefaultTableModel();
    
    public PasienDashboardController(PasienDashboard pasienDashboard, Mahasiswa mahasiswa) throws TelkomedicsException {
        this.pasienDashboard = pasienDashboard;
        pasienDashboard.setVisible(true);

        this.reservasiDao = new ReservasiDao();
        this.daftarOnlineDao = new DaftarOnlineDao();
        this.jadwalDokterDao = new JadwalDokterDao();
        this.daftarPasienDao = new DaftarPasienDao();
        this.loginDao = new LoginDao();
        
        pasienDashboard.setNim(mahasiswa);
        pasienDashboard.setNama(loginDao.namaMhs(mahasiswa));
        
        pasienDashboard.logoutButtonListener(new LogoutListener());
        pasienDashboard.daftarButtonListener(new DaftarListener());
        pasienDashboard.refreshButtonListener(new RefreshListener());
        pasienDashboard.batalReservasiButtonListener(new BatalReservasiListener());
        pasienDashboard.tableReservasiClickedListener(new TableReservasiListener());

        pasien = new Pasien();
        pasien.setNim(pasienDashboard.getNim().getText());
        pasien.setNama(pasienDashboard.getNama().getText());
        
        this.viewReservasi(pasien);
        this.viewDaftarDokter();
    }

    public void viewReservasi(Pasien pasien) {
        reservasiModel = reservasiDao.getReservasi(pasien);
        pasienDashboard.getTableReservasi().setModel(reservasiModel);
    }

    public void viewDaftarDokter() {
        dataDokterGigi = jadwalDokterDao.getJadwalDokterGigi();
        dataDokterUmum = jadwalDokterDao.getJadwalDokterUmum();
        pasienDashboard.getPoliGigiTable().setModel(dataDokterGigi);
        pasienDashboard.getPoliUmumTable().setModel(dataDokterUmum);
    }

    class TableReservasiListener extends java.awt.event.MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            int row = pasienDashboard.getTableReservasi().getSelectedRow();
    
            if (row >= 0) {
                pasien = new Pasien();
                pasien.setNoAntrian(Integer.parseInt(reservasiModel.getValueAt(row, 1).toString()));
        
                String[] jam = {
                    "08.00", "08.30", "09.00", "09.30", "10.00", "10.30", "11.00", 
                    "11.30", "13.30", "14.00", "14.30", "15.00", "15.30", "16.00", "16.30"
                };
        
                if (pasien.getNoAntrian() > 0 && pasien.getNoAntrian() <= jam.length) {
                    pasienDashboard.setJamPoliLabel(jam[pasien.getNoAntrian() - 1]);
                } else {
                    JOptionPane.showMessageDialog(null, "Nomor Antrian tidak valid!");
                }
            } else {
                pasienDashboard.setJamPoliLabel("-- : --");
            }
        }
    }

    class RefreshListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            pasienDashboard.getTableReservasi().setModel(new DefaultTableModel());
            pasien = new Pasien();
            try {
                pasien.setNim(pasienDashboard.getNim().getText());
            } catch (TelkomedicsException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());            
            }

            viewReservasi(pasien);
        }
    }
    
    class LogoutListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Login login = new Login();
                new LoginController(login);
                pasienDashboard.dispose();
            } else {
                return;
            }
        }
    }

    class DaftarListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            String tahun = pasienDashboard.getTahunComboBox().getSelectedItem().toString();
            String bulan = pasienDashboard.getBulanComboBox().getSelectedItem().toString();
            String tanggal = pasienDashboard.getTanggalComboBox().getSelectedItem().toString();

            pasien = new Pasien();

            try {
                pasien.setNim(pasienDashboard.getNim().getText());
                pasien.setNama(pasienDashboard.getInputPasien().getText());
                pasien.setJenisPoli(pasienDashboard.getButtonGroup().getSelection().getActionCommand());
                pasien.setTanggalPemeriksaan(tahun + "-" + bulan + "-" + tanggal);
                pasien.setNamaDokter(getDokterTerkait(getHari(pasien.getTanggalPemeriksaan()), pasien.getJenisPoli()));
            } catch (TelkomedicsException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());            
            }

            if (!reservasiDao.dataPasienIsExist(pasien)) {
                JOptionPane.showMessageDialog(null, "NIM tidak terdaftar!");
                return;
            }

            if (reservasiDao.duplicateDataPasien(pasien)) {
                JOptionPane.showMessageDialog(null, "Anda sudah mendaftar pada tanggal tersebut di poli yang sama!");
                return;
            }

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = formatter.parse(pasien.getTanggalPemeriksaan());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            
            if (date.before(new Date())) {
                JOptionPane.showMessageDialog(null, "Tanggal yang dipilih tidak valid!");
                return;
            }

            if (pasien.getNamaDokter().equals("")) {
                JOptionPane.showMessageDialog(null, "Dokter tidak tersedia pada hari tersebut!");
                return;
            }

            if (daftarOnlineDao.dataPasienIsExist(pasien)) {
                JOptionPane.showMessageDialog(null, "Anda sudah mendaftar pada tanggal tersebut!");
                return;
            }

            int currentMaxId = daftarOnlineDao.idPasien();

            if (currentMaxId == 0) {
                int id = 1;
                try {
                    pasien.setId(Integer.toString(id));
                } catch (TelkomedicsException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            } else {
                int id = currentMaxId + 1;
                try {
                    pasien.setId(Integer.toString(id));
                } catch (TelkomedicsException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }

            int existingPatients = daftarOnlineDao.countExistingPatients(pasien);
            pasien.setNoAntrian(existingPatients + 1);

            if (pasien.getNoAntrian() > 15) {
                JOptionPane.showMessageDialog(null, "Nomor antrian sudah penuh!");
                return;
            }

            daftarOnlineDao.insertDataDaftar(pasien);
            JOptionPane.showMessageDialog(null, "Pendaftaran berhasil! Nomor Antrian Anda: " + pasien.getNoAntrian());
            
        }

        private String getHari(String tanggal) {
            String[] tanggalSplit = tanggal.split("-");
            int tahun = Integer.parseInt(tanggalSplit[0]);
            int bulan = Integer.parseInt(tanggalSplit[1]);
            int tanggalInt = Integer.parseInt(tanggalSplit[2]);

            String[] hari = {
                "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"
            };
            
            Calendar calendar = Calendar.getInstance();
            calendar.set(tahun, bulan - 1, tanggalInt);
            int hariInt = calendar.get(Calendar.DAY_OF_WEEK);
            return hari[hariInt - 1];
        }

        private String getDokterTerkait(String hari, String jenisPoli) {
            String dokter = "";
            if (jenisPoli.equals("Poli Gigi")) {
                dokter = jadwalDokterDao.getDokterGigi(hari);
            } else if (jenisPoli.equals("Poli Umum")) {
                dokter = jadwalDokterDao.getDokterUmum(hari);
            }
            return dokter;
        }
    }

    class BatalReservasiListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin membatalkan reservasi?", "Batal Reservasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int row = pasienDashboard.getTableReservasi().getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu!");
                } else {
                    pasien = new Pasien();

                    try {
                        pasien.setNama(reservasiModel.getValueAt(row, 0).toString());
                        pasien.setNoAntrian(Integer.parseInt(reservasiModel.getValueAt(row, 1).toString()));
                        pasien.setJenisPoli(reservasiModel.getValueAt(row, 2).toString());
                        pasien.setTanggalPemeriksaan(reservasiModel.getValueAt(row, 3).toString());
                        pasien.setNamaDokter(reservasiModel.getValueAt(row, 4).toString());
                    } catch (TelkomedicsException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());                    
                    }

                    reservasiDao.deleteReservasi(pasien);
                    daftarPasienDao.updateNomorAntrian(pasien);
                    reservasiModel.removeRow(row);

                    JOptionPane.showMessageDialog(null, "Berhasil membatalkan reservasi!");
                }
            } else {
                return;
            }
        }
    }
}