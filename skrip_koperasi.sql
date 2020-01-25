-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 21, 2019 at 11:11 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `skrip_koperasi`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `id` int(15) NOT NULL,
  `no_anggota` varchar(20) CHARACTER SET utf8 NOT NULL,
  `nama_anggota` varchar(30) CHARACTER SET utf8 NOT NULL,
  `tempat_lahir` varchar(35) CHARACTER SET utf8 NOT NULL,
  `tgl_lahir` date NOT NULL,
  `jenis_kelamin` varchar(15) CHARACTER SET utf8 NOT NULL,
  `alamat` varchar(255) CHARACTER SET utf8 NOT NULL,
  `no_tlp` varchar(15) CHARACTER SET utf8 NOT NULL,
  `simp_pokok` varchar(15) CHARACTER SET utf8 NOT NULL,
  `tgl_masuk` date NOT NULL,
  `status` varchar(15) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`id`, `no_anggota`, `nama_anggota`, `tempat_lahir`, `tgl_lahir`, `jenis_kelamin`, `alamat`, `no_tlp`, `simp_pokok`, `tgl_masuk`, `status`) VALUES
(1, 'AK-01', 'Sudarmadji', 'Jakarta', '1990-09-19', 'Laki-laki', 'Jl. Cege Raya No.36, Jakarta Timur', '081908882637', '300000', '2017-05-14', 'Pegawai'),
(2, 'AK-02', 'Ismanto', 'Bogor', '1990-11-21', 'Laki-laki', 'Jl. Cilangkap Raya No.06', '081299374637', '300000', '2017-05-15', 'Pegawai'),
(3, 'AK-03', 'Dwiki Febriansyah', 'Jakarta', '1989-05-19', 'Laki-laki', 'Jl. Lapangan Tembak Cibubur, Jakarta Timur', '081324563498', '300000', '2017-05-15', 'Pegawai'),
(4, 'AK-04', 'Anik Widyaningsih', 'Yogyakarta', '1985-06-12', 'Perempuan', 'Jl. Citayam Depok No.11, Depok', '081762578356', '300000', '2017-06-21', 'Pegawai'),
(5, 'AK-05', 'Diah', 'Bandung', '1988-12-22', 'Perempuan', 'Jl. Kalisari No.02, Jakarta Timur', '081345672398', '300000', '2017-06-23', 'Pegawai'),
(6, 'AK-06', 'Retno', 'Malang', '1991-05-18', 'Perempuan', 'Jl. Mustika Ratu No.13, Jakarta Timur', '081267877643', '300000', '2017-06-24', 'Honorer'),
(7, 'AK-07', 'Fahmi', 'Jakarta', '1992-10-16', 'Laki-laki', 'Jl. Cibubu II No.27, Jakarta Timur', '087756432897', '300000', '2017-07-01', 'Honorer'),
(8, 'AK-08', 'Cahyo', 'Bogor', '1990-09-21', 'Laki-laki', 'Jl. Kelapa Dua Wetan, Jakarta Timur', '081266754329', '300000', '2017-09-11', 'Honorer');

-- --------------------------------------------------------

--
-- Table structure for table `angsuran`
--

CREATE TABLE `angsuran` (
  `kd_pinjaman` varchar(20) NOT NULL,
  `no_anggota` varchar(20) NOT NULL,
  `kd_bukti` varchar(20) NOT NULL,
  `tgl_angsur` date NOT NULL,
  `lama_pinjaman` varchar(15) NOT NULL,
  `jumlah_angsuran` varchar(30) NOT NULL,
  `total_bayar` varchar(30) NOT NULL,
  `sisa_angsuran` varchar(15) NOT NULL,
  `angsuran_ke` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `angsuran`
--

INSERT INTO `angsuran` (`kd_pinjaman`, `no_anggota`, `kd_bukti`, `tgl_angsur`, `lama_pinjaman`, `jumlah_angsuran`, `total_bayar`, `sisa_angsuran`, `angsuran_ke`) VALUES
('KP-01', 'AK-01', 'AS-01', '2017-09-18', '4', '2000000', '500000', '500000', '1'),
('KP-01', 'AK-01', 'AS-02', '2017-10-18', '4', '2000000', '500000', '1000000', '2'),
('KP-02', 'AK-02', 'AS-03', '2018-03-11', '5', '2500000', '500000', '500000', '1'),
('KP-02', 'AK-02', 'AS-04', '2018-04-11', '5', '2500000', '500000', '1000000', '2'),
('KP-05', 'AK-05', 'AS-05', '2018-08-28', '5', '2000000', '400000', '400000', '1'),
('KP-05', 'AK-05', 'AS-06', '2018-09-28', '5', '2000000', '400000', '800000', '2'),
('KP-04', 'AK-04', 'AS-07', '2018-08-12', '4', '1000000', '250000', '250000', '1'),
('KP-04', 'AK-04', 'AS-08', '2018-09-12', '4', '1000000', '250000', '500000', '2'),
('KP-06', 'AK-06', 'AS-09', '2019-08-19', '4', '2000000', '500000', '500000', '1');

-- --------------------------------------------------------

--
-- Table structure for table `petugas`
--

CREATE TABLE `petugas` (
  `user` varchar(20) CHARACTER SET utf8 NOT NULL,
  `pass` varchar(20) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `petugas`
--

INSERT INTO `petugas` (`user`, `pass`) VALUES
('Admin', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `pinjaman`
--

CREATE TABLE `pinjaman` (
  `no_anggota` varchar(20) NOT NULL,
  `kd_pinjaman` varchar(20) NOT NULL,
  `tgl_pinjaman` date NOT NULL,
  `jumlah_pinjaman` varchar(30) NOT NULL,
  `lama_pinjaman` varchar(10) NOT NULL,
  `potongan_pinjaman` varchar(5) NOT NULL,
  `angsuran` varchar(30) NOT NULL,
  `total_angsuran` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pinjaman`
--

INSERT INTO `pinjaman` (`no_anggota`, `kd_pinjaman`, `tgl_pinjaman`, `jumlah_pinjaman`, `lama_pinjaman`, `potongan_pinjaman`, `angsuran`, `total_angsuran`) VALUES
('AK-01', 'KP-01', '2017-08-22', '2000000', '4', '5', '500000', '1900000'),
('AK-02', 'KP-02', '2018-02-11', '2500000', '5', '5', '500000', '2375000'),
('AK-03', 'KP-03', '2018-06-23', '1500000', '3', '5', '500000', '1425000'),
('AK-04', 'KP-04', '2018-07-12', '1000000', '4', '5', '250000', '950000'),
('AK-05', 'KP-05', '2018-07-28', '2000000', '5', '5', '400000', '1900000'),
('AK-06', 'KP-06', '2019-07-19', '2000000', '4', '5', '500000', '1900000');

-- --------------------------------------------------------

--
-- Table structure for table `simpanan`
--

CREATE TABLE `simpanan` (
  `no_anggota` varchar(20) NOT NULL,
  `kd_transaksi` varchar(20) NOT NULL,
  `tgl_simpanan` date NOT NULL,
  `simp_pokok` varchar(30) NOT NULL,
  `simp_wajib` varchar(30) NOT NULL,
  `total_simpanan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `simpanan`
--

INSERT INTO `simpanan` (`no_anggota`, `kd_transaksi`, `tgl_simpanan`, `simp_pokok`, `simp_wajib`, `total_simpanan`) VALUES
('AK-01', 'KD-01', '2017-05-14', '300000', '50000', '350000'),
('AK-02', 'KD-02', '2017-05-15', '300000', '50000', '350000'),
('AK-03', 'KD-03', '2017-05-15', '300000', '50000', '350000'),
('AK-01', 'KD-04', '2017-06-14', '300000', '50000', '400000'),
('AK-02', 'KD-05', '2017-06-15', '300000', '50000', '400000'),
('AK-03', 'KD-06', '2017-06-15', '300000', '50000', '400000'),
('AK-04', 'KD-07', '2017-06-21', '300000', '50000', '350000'),
('AK-01', 'KD-08', '2019-07-20', '300000', '50000', '450000');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`no_anggota`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `angsuran`
--
ALTER TABLE `angsuran`
  ADD PRIMARY KEY (`kd_bukti`),
  ADD KEY `kd_pinjaman` (`kd_pinjaman`);

--
-- Indexes for table `pinjaman`
--
ALTER TABLE `pinjaman`
  ADD PRIMARY KEY (`kd_pinjaman`),
  ADD KEY `no_anggota` (`no_anggota`);

--
-- Indexes for table `simpanan`
--
ALTER TABLE `simpanan`
  ADD PRIMARY KEY (`kd_transaksi`),
  ADD KEY `no_anggota` (`no_anggota`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `anggota`
--
ALTER TABLE `anggota`
  MODIFY `id` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
