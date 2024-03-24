package telkomedics.models;

import telkomedics.helper.TelkomedicsException;

public class Admin extends Pengguna {
    private String id;
    private String nama;
    private String password;

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setId(String id) throws TelkomedicsException {
        if (id == null || id.isEmpty()) {
            throw new TelkomedicsException("ID tidak boleh kosong");
        }
        this.id = id;
    }

    @Override
    public void setNama(String nama) throws TelkomedicsException {
        if (nama == null || nama.isEmpty()) {
            throw new TelkomedicsException("Nama tidak boleh kosong");
        }
        this.nama = nama;
    }

    @Override
    public void setPassword(String password) throws TelkomedicsException {
        if (password == null || password.isEmpty()) {
            throw new TelkomedicsException("Password tidak boleh kosong");
        }
        this.password = password;
    }
}
