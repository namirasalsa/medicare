package telkomedics.models;

import telkomedics.helper.TelkomedicsException;

public class Dokter {
    private String hari;
    private String jadwal;
    private String nama;
    private String jenisPoli;
    private String id;

    public String getHari() {
        return hari;
    }

    public String getJadwal() {
        return jadwal;
    }

    public String getNama() {
        return nama;
    }

    public String getJenisPoli() {
        return jenisPoli;
    }

    public String getId() {
        return id;
    }

    public void setHari(String hari) throws TelkomedicsException {
        if (hari == null || hari.isEmpty()) {
            throw new TelkomedicsException("hari tidak boleh kosong");
        }
        this.hari = hari;
    }

    public void setJadwal(String jadwal) throws TelkomedicsException {
        if (jadwal == null || jadwal.isEmpty()) {
            throw new TelkomedicsException("jadwal tidak boleh kosong");
        }
        this.jadwal = jadwal;
    }

    public void setNama(String nama) throws TelkomedicsException {
        if (nama == null) {
            throw new TelkomedicsException("nama tidak boleh kosong");
        }
        this.nama = nama;
    }

    public void setJenisPoli(String jenisPoli) throws TelkomedicsException {
        if (jenisPoli == null || jenisPoli.isEmpty()) {
            throw new TelkomedicsException("jenis poli tidak boleh kosong");
        }
        this.jenisPoli = jenisPoli;
    }
    
    public void setId(String id) throws TelkomedicsException {
        if (id == null || id.isEmpty()) {
            throw new TelkomedicsException("id tidak boleh kosong");
        }
        this.id = id;
    }
}