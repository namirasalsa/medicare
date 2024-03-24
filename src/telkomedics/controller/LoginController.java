package telkomedics.controller;

import javax.swing.JOptionPane;

import telkomedics.dao.LoginDao;

import telkomedics.helper.TelkomedicsException;

import telkomedics.models.Admin;
import telkomedics.models.Mahasiswa;

import telkomedics.view.AdminDashboard;
import telkomedics.view.Login;
import telkomedics.view.PasienDashboard;
import telkomedics.view.Signup;

public class LoginController {
    private Login login;
    private LoginDao loginDao;
    private Admin admin;
    private Mahasiswa mahasiswa;

    public LoginController(Login login) {
        this.login = login;
        login.setVisible(true);

        this.loginDao = new LoginDao();

        login.loginButtonListener(new LoginListener());
        login.signupButtonListener(new SignupListener());
    }

    class LoginListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (login.getRole().getSelectedItem().equals("Admin")) {
                admin = new Admin();

                try {
                    admin.setId(login.getInputId().getText());
                    admin.setPassword(new String(login.getInputPassword().getPassword()));
                } catch (TelkomedicsException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }

                if (loginDao.dataAdminIsExist(admin)) {
                    AdminDashboard adminDashboard = new AdminDashboard();
                    new AdminDashboardController(adminDashboard);
                    login.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak dapat menemukan data admin!");
                }
            } else if (login.getRole().getSelectedItem().equals("Pasien")) {
                mahasiswa = new Mahasiswa();

                try {
                    mahasiswa.setId(login.getInputId().getText());
                    mahasiswa.setPassword(new String(login.getInputPassword().getPassword()));
                } catch (TelkomedicsException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }

                if (loginDao.dataMahasiswaIsExist(mahasiswa)) {
                    PasienDashboard pasienDashboard = new PasienDashboard();
                    try {
                        new PasienDashboardController(pasienDashboard, mahasiswa);
                    } catch (TelkomedicsException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());                    
                    }
                    login.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak dapat menemukan data pasien!");
                    //set text field kosong
                    login.getInputId().setText("");
                    login.getInputPassword().setText("");
                    
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih role terlebih dahulu!");
            }
        }
    }

    class SignupListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            //apabila role = admin maka signup button diset visible false
            if(login.getRole().getSelectedItem().equals("Admin")){
                login.getSignupButton().setEnabled(false);
            } else{
                Signup signup = new Signup();
                new SignupController(signup);
                login.dispose();
            }



        }
    }
}
