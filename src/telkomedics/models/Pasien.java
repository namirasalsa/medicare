package telkomedics.models;

import telkomedics.helper.TelkomedicsException;

public class Pasien {
    private String id;
    private String nim;
    private String nama;
    private int noAntrian;
    private String tanggalPemeriksaan;
    private String jenisPoli;
    private String namaDokter;

    public String getId() {
        return id;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public int getNoAntrian() {
        return noAntrian;
    }

    public String getTanggalPemeriksaan() {
        return tanggalPemeriksaan;
    }

    public String getJenisPoli() {
        return jenisPoli;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setId(String id) throws TelkomedicsException {
        if (id == null || id.isEmpty()) {
            throw new TelkomedicsException("id tidak boleh kosong");
        }
        this.id = id;
    }

    public void setNim(String nim) throws TelkomedicsException {
        if (nim == null || nim.isEmpty()) {
            throw new TelkomedicsException("NIM tidak boleh kosong");
        }

        if (nim.length() != 10) {
            throw new TelkomedicsException("NIM harus 10 digit");
        }

        this.nim = nim;
    }

    public void setNama(String nama) throws TelkomedicsException {
        if (nama == null || nama.isEmpty()) {
            throw new TelkomedicsException("Nama tidak boleh kosong");
        }

        this.nama = nama;
    }

    public void setNoAntrian(int noAntrian) {
        this.noAntrian = noAntrian;
    }

    public void setTanggalPemeriksaan(String tanggalPemeriksaan) {
        this.tanggalPemeriksaan = tanggalPemeriksaan;
    }

    public void setJenisPoli(String jenisPoli) {
        this.jenisPoli = jenisPoli;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }
}
