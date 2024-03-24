package telkomedics.models;

import telkomedics.helper.TelkomedicsException;

public class DokterUmum extends Dokter {
    public String getHari() {
        return super.getHari();
    }

    public String getJadwal() {
        return super.getJadwal();
    }

    public String getNama() {
        return super.getNama();
    }

    public String getJenisPoli() {
        return super.getJenisPoli();
    }

    public String getId() {
        return super.getId();
    }

    @Override
    public void setHari(String hari) throws TelkomedicsException {
        super.setHari(hari);
    }

    @Override
    public void setJadwal(String jadwal) throws TelkomedicsException {
        super.setJadwal(jadwal);
    }

    @Override
    public void setNama(String nama) throws TelkomedicsException {
        super.setNama(nama);
    }

    @Override
    public void setJenisPoli(String jenisPoli) throws TelkomedicsException {
        super.setJenisPoli(jenisPoli);
    }

    @Override
    public void setId(String id) throws TelkomedicsException {
        super.setId(id);
    }
}