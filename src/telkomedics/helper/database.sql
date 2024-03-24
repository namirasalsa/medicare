-- DROP DATABASE telcomedics;
-- CREATE DATABASE telcomedics;
USE telcomedics;

CREATE TABLE IF NOT EXISTS data_admin (
    id INT PRIMARY KEY,
    nama VARCHAR(20),
    password VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS data_mahasiswa (
	nim VARCHAR(10) PRIMARY KEY,
    nama VARCHAR(30) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nohp VARCHAR(12) NOT NULL,
    alamat VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS data_pasien (
    id INT PRIMARY KEY,
    nim VARCHAR(10),
    nama VARCHAR(30) NOT NULL,
	no_antrian INT NOT NULL,
    tanggal_pemeriksaan DATE NOT NULL,
    jenis_poli ENUM('Poli Umum', 'Poli Gigi') NOT NULL,
    nama_dokter VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS data_dokter_gigi (
	hari ENUM('Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu'),
    jadwal VARCHAR(30),
    nama_dokter VARCHAR(30),
    jenis_poli ENUM('Poli Umum', 'Poli Gigi'),
	id_dokter_gigi INT NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS data_dokter_umum (
	hari ENUM('Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu'),
    jadwal VARCHAR(30),
    nama_dokter VARCHAR(30),
    jenis_poli ENUM('Poli Umum', 'Poli Gigi'),
	id_dokter_umum INT NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS data_dokter (
    nama_dokter VARCHAR(30),
    hari ENUM('Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu'),
    jadwal VARCHAR(30),
    jenis_poli ENUM('Poli Umum', 'Poli Gigi'),
    id_dokter INT NOT NULL PRIMARY KEY
);

INSERT IGNORE INTO data_dokter_gigi (hari, jadwal, nama_dokter, jenis_poli, id_dokter_gigi) VALUES 
	('Senin', '08.00 - 16.30', 'dr. Adzkia', 'Poli Gigi', '1'),
    ('Selasa', '08.00 - 16.30', 'dr. Delfina', 'Poli Gigi', '2'),
    ('Rabu', '08.00 - 16.30', 'dr. Haura', 'Poli Gigi', '3'),
    ('Kamis', '08.00 - 16.30', 'dr. Namira', 'Poli Gigi', '4'),
    ('Jumat', '08.00 - 16.30', 'dr. Salsabilla', 'Poli Gigi', '5');
    
INSERT IGNORE INTO data_dokter_umum (hari, jadwal, nama_dokter, jenis_poli, id_dokter_umum) VALUES 
	('Senin', '08.00 - 16.30', 'dr. Felicia', 'Poli Umum', '6'),
    ('Selasa', '08.00 - 16.30', 'dr. Thalitha', 'Poli Umum', '7'),
    ('Rabu', '08.00 - 16.30', 'dr. Fathin', 'Poli Umum', '8'),
    ('Kamis', '08.00 - 16.30', 'dr. Agisni', 'Poli Umum', '9'),
    ('Jumat', '08.00 - 16.30', 'dr. Zahrra', 'Poli Umum', '10');
    
INSERT IGNORE INTO data_admin (id, nama, password) VALUES
	(1, 'admin', 'admin123');
    
INSERT IGNORE INTO data_dokter (nama_dokter, hari, jadwal, jenis_poli, id_dokter)
    SELECT nama_dokter, hari, jadwal, jenis_poli, id_dokter_gigi
    FROM data_dokter_gigi;

INSERT IGNORE INTO data_dokter (nama_dokter, hari, jadwal, jenis_poli, id_dokter)
    SELECT nama_dokter, hari, jadwal, jenis_poli, id_dokter_umum
    FROM data_dokter_umum;