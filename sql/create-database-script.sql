-- quanlibanve.Ga definition

CREATE TABLE `Ga` (
  `MaGa` varchar(25) NOT NULL,
  `TenGa` varchar(50) NOT NULL,
  `VungMien` varchar(50) NOT NULL,
  PRIMARY KEY (`MaGa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.KhachHang definition

CREATE TABLE `KhachHang` (
  `MaKhachHang` int(11) NOT NULL AUTO_INCREMENT,
  `HoTen` varchar(100) NOT NULL,
  `SoDienThoai` varchar(11) NOT NULL,
  `ThoiGianDangKy` datetime NOT NULL,
  `LaKhachHangThanThiet` int(11) NOT NULL,
  PRIMARY KEY (`MaKhachHang`),
  UNIQUE KEY `UC_KhachHang` (`MaKhachHang`,`SoDienThoai`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4;


-- quanlibanve.KhuyenMai definition

CREATE TABLE `KhuyenMai` (
  `MaKhuyenMai` varchar(50) NOT NULL,
  `PhanTramGiamGia` double NOT NULL DEFAULT 0,
  `GhiChu` varchar(255) DEFAULT NULL,
  `ThoiGianBatDau` datetime NOT NULL,
  `ThoiGianKetThuc` datetime NOT NULL,
  PRIMARY KEY (`MaKhuyenMai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.LoaiKhoang definition

CREATE TABLE `LoaiKhoang` (
  `MaLoaiKhoang` varchar(25) NOT NULL,
  `TenLoaiKhoang` varchar(50) NOT NULL,
  PRIMARY KEY (`MaLoaiKhoang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.NhanVien definition

CREATE TABLE `NhanVien` (
  `MaNhanVien` int(11) NOT NULL AUTO_INCREMENT,
  `HoTen` varchar(100) NOT NULL,
  `GioiTinh` tinyint(1) NOT NULL,
  `SoDienThoai` varchar(11) NOT NULL,
  `NgayDangKy` date NOT NULL DEFAULT curdate(),
  `NgaySinh` date NOT NULL,
  `TrangThai` int(11) NOT NULL DEFAULT 1,
  `MatKhau` varchar(100) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `VaiTro` varchar(25) NOT NULL,
  PRIMARY KEY (`MaNhanVien`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;


-- quanlibanve.Tau definition

CREATE TABLE `Tau` (
  `MaTau` varchar(50) NOT NULL,
  `TenTau` varchar(50) NOT NULL,
  PRIMARY KEY (`MaTau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.HoaDon definition

CREATE TABLE `HoaDon` (
  `MaHoaDon` varchar(100) NOT NULL,
  `ThoiGianTaoHoaDon` datetime NOT NULL DEFAULT curtime(),
  `GhiChu` varchar(255) DEFAULT NULL,
  `VAT` double NOT NULL,
  `MaKhachHang` int(11) NOT NULL,
  `MaNhanVien` int(11) NOT NULL,
  `MaKhuyenMai` varchar(50) DEFAULT NULL,
  `TongTien` double NOT NULL,
  `TamTinh` double NOT NULL,
  `TongTienGiam` double NOT NULL,
  PRIMARY KEY (`MaHoaDon`),
  KEY `FK_HoaDon_KhachHang` (`MaKhachHang`),
  KEY `FK_HoaDon_NhanVien` (`MaNhanVien`),
  KEY `FK_HoaDon_KhuyenMai` (`MaKhuyenMai`),
  CONSTRAINT `FK_HoaDon_KhachHang` FOREIGN KEY (`MaKhachHang`) REFERENCES `KhachHang` (`MaKhachHang`),
  CONSTRAINT `FK_HoaDon_KhuyenMai` FOREIGN KEY (`MaKhuyenMai`) REFERENCES `KhuyenMai` (`MaKhuyenMai`),
  CONSTRAINT `FK_HoaDon_NhanVien` FOREIGN KEY (`MaNhanVien`) REFERENCES `NhanVien` (`MaNhanVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.LichSuTraVe definition

CREATE TABLE `LichSuTraVe` (
  `MaLichSuTraVe` int(11) NOT NULL AUTO_INCREMENT,
  `ThoiGianTraVe` datetime NOT NULL DEFAULT curtime(),
  `GhiChu` varchar(255) DEFAULT NULL,
  `MaKhachHang` int(11) NOT NULL,
  `MaVe` varchar(25) NOT NULL,
  PRIMARY KEY (`MaLichSuTraVe`),
  KEY `FK_LichSuTraVe_KhachHang` (`MaKhachHang`),
  KEY `FK_LichSuTraVe_Ve` (`MaVe`),
  CONSTRAINT `FK_LichSuTraVe_KhachHang` FOREIGN KEY (`MaKhachHang`) REFERENCES `KhachHang` (`MaKhachHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.TiepNhanYeuCauDoiVe definition

CREATE TABLE `TiepNhanYeuCauDoiVe` (
  `MaYeuCau` int(11) NOT NULL AUTO_INCREMENT,
  `ThoiGianTiepNhan` datetime NOT NULL DEFAULT curtime(),
  `ThoiGianYeuCauDoi` datetime NOT NULL,
  `GhiChu` varchar(255) DEFAULT NULL,
  `MaKhachHang` int(11) NOT NULL,
  `MaNhanVien` int(11) NOT NULL,
  `MaToa` varchar(50) DEFAULT NULL,
  `TenToa` varchar(50) DEFAULT NULL,
  `MaTau` int(11) DEFAULT NULL,
  PRIMARY KEY (`MaYeuCau`),
  KEY `FK_TiepNhanYeuCauDoiVe_KhachHang` (`MaKhachHang`),
  KEY `FK_TiepNhanYeuCauDoiVe_NhanVien` (`MaNhanVien`),
  CONSTRAINT `FK_TiepNhanYeuCauDoiVe_KhachHang` FOREIGN KEY (`MaKhachHang`) REFERENCES `KhachHang` (`MaKhachHang`),
  CONSTRAINT `FK_TiepNhanYeuCauDoiVe_NhanVien` FOREIGN KEY (`MaNhanVien`) REFERENCES `NhanVien` (`MaNhanVien`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;


-- quanlibanve.ToaTau definition

CREATE TABLE `ToaTau` (
  `MaToa` varchar(100) NOT NULL,
  `TenToa` varchar(100) DEFAULT NULL,
  `MaTau` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`MaToa`),
  KEY `ToaTau_Tau_FK` (`MaTau`),
  CONSTRAINT `ToaTau_Tau_FK` FOREIGN KEY (`MaTau`) REFERENCES `Tau` (`MaTau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.Tuyen definition

CREATE TABLE `Tuyen` (
  `MaTuyen` varchar(25) NOT NULL,
  `NgayTaoTuyen` date NOT NULL,
  `MaGaDi` varchar(25) NOT NULL,
  `MaGaDen` varchar(25) NOT NULL,
  `GiaNiemYet` double NOT NULL,
  PRIMARY KEY (`MaTuyen`),
  KEY `FK_Tuyen_GaDi` (`MaGaDi`),
  KEY `FK_Tuyen_GaDen` (`MaGaDen`),
  CONSTRAINT `FK_Tuyen_GaDen` FOREIGN KEY (`MaGaDen`) REFERENCES `Ga` (`MaGa`),
  CONSTRAINT `FK_Tuyen_GaDi` FOREIGN KEY (`MaGaDi`) REFERENCES `Ga` (`MaGa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.Chuyen definition

CREATE TABLE `Chuyen` (
  `MaChuyen` varchar(25) NOT NULL,
  `ThoiGianKhoiHanh` datetime NOT NULL,
  `ThoiGianDen` datetime NOT NULL,
  `MaTuyen` varchar(25) NOT NULL,
  `MaTau` varchar(25) NOT NULL,
  PRIMARY KEY (`MaChuyen`),
  KEY `FK_Chuyen_Tuyen` (`MaTuyen`),
  KEY `Chuyen_Tau_FK` (`MaTau`),
  CONSTRAINT `Chuyen_Tau_FK` FOREIGN KEY (`MaTau`) REFERENCES `Tau` (`MaTau`),
  CONSTRAINT `FK_Chuyen_Tuyen` FOREIGN KEY (`MaTuyen`) REFERENCES `Tuyen` (`MaTuyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.Khoang definition

CREATE TABLE `Khoang` (
  `MaKhoang` varchar(100) NOT NULL,
  `TenKhoang` varchar(100) NOT NULL,
  `MaToa` varchar(25) NOT NULL,
  `MaLoaiKhoang` varchar(100) NOT NULL,
  PRIMARY KEY (`MaKhoang`),
  KEY `FK_Khoang_LoaiKhoang` (`MaLoaiKhoang`),
  KEY `Khoang_ToaTau_FK` (`MaToa`),
  CONSTRAINT `FK_Khoang_LoaiKhoang` FOREIGN KEY (`MaLoaiKhoang`) REFERENCES `LoaiKhoang` (`MaLoaiKhoang`),
  CONSTRAINT `Khoang_ToaTau_FK` FOREIGN KEY (`MaToa`) REFERENCES `ToaTau` (`MaToa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.Slot definition

CREATE TABLE `Slot` (
  `MaSlot` varchar(100) NOT NULL,
  `TinhTrang` int(11) NOT NULL,
  `MaKhoang` varchar(100) NOT NULL,
  `SoSlot` int(11) NOT NULL,
  PRIMARY KEY (`MaSlot`),
  KEY `FK_Slot_Khoang` (`MaKhoang`),
  CONSTRAINT `Slot_Khoang_FK` FOREIGN KEY (`MaKhoang`) REFERENCES `Khoang` (`MaKhoang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.Ve definition

CREATE TABLE `Ve` (
  `MaVe` varchar(100) NOT NULL,
  `MaKhachHang` int(11) NOT NULL,
  `MaTuyen` varchar(25) NOT NULL,
  `MaTau` varchar(25) NOT NULL,
  `MaSlot` varchar(100) NOT NULL,
  PRIMARY KEY (`MaVe`),
  KEY `FK_Ve_KhachHang` (`MaKhachHang`),
  KEY `FK_Ve_HoaDon` (`MaTuyen`),
  KEY `FK_Ve_Tau` (`MaTau`),
  KEY `FK_Ve_Slot` (`MaSlot`),
  CONSTRAINT `FK_Ve_HoaDon` FOREIGN KEY (`MaTuyen`) REFERENCES `Tuyen` (`MaTuyen`),
  CONSTRAINT `FK_Ve_KhachHang` FOREIGN KEY (`MaKhachHang`) REFERENCES `KhachHang` (`MaKhachHang`),
  CONSTRAINT `FK_Ve_Slot` FOREIGN KEY (`MaSlot`) REFERENCES `Slot` (`MaSlot`),
  CONSTRAINT `FK_Ve_Tau` FOREIGN KEY (`MaTau`) REFERENCES `Tau` (`MaTau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- quanlibanve.ChiTietHoaDon definition

CREATE TABLE `ChiTietHoaDon` (
  `MaChiTietHoaDon` int(11) NOT NULL AUTO_INCREMENT,
  `MaHoaDon` varchar(100) NOT NULL,
  `MaVe` varchar(100) NOT NULL,
  PRIMARY KEY (`MaChiTietHoaDon`),
  KEY `FK_ChiTietHoaDon_HoaDon` (`MaHoaDon`),
  KEY `ChiTietHoaDon_FK` (`MaVe`),
  CONSTRAINT `ChiTietHoaDon_FK` FOREIGN KEY (`MaVe`) REFERENCES `Ve` (`MaVe`),
  CONSTRAINT `FK_ChiTietHoaDon_HoaDon` FOREIGN KEY (`MaHoaDon`) REFERENCES `HoaDon` (`MaHoaDon`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;


