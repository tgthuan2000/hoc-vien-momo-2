USE [hoc_vien_momo]
GO
/****** Object:  Table [dbo].[CauHinh]    Script Date: 12/15/2021 5:07:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CauHinh](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[SoLuongCauHoi] [int] NOT NULL,
	[DiemTranDau] [int] NULL,
	[ThoiGian] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CauHoi]    Script Date: 12/15/2021 5:07:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CauHoi](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CauHoi] [varchar](255) NULL,
	[CauDung] [varchar](255) NULL,
	[CauSai1] [varchar](255) NULL,
	[CauSai2] [varchar](255) NULL,
	[CauSai3] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NguoiDung]    Script Date: 12/15/2021 5:07:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NguoiDung](
	[Username] [varchar](255) NOT NULL,
	[Password] [varchar](255) NOT NULL,
	[TenNguoiDung] [nvarchar](255) NOT NULL,
	[GioiTinh] [bit] NOT NULL,
	[NgaySinh] [datetime] NOT NULL,
	[TongTran] [int] NOT NULL,
	[TongTranThang] [int] NOT NULL,
	[ChuoiThangMax] [int] NOT NULL,
	[ChuoiThuaMax] [int] NOT NULL,
	[ChuoiThang] [int] NOT NULL,
	[ChuoiThua] [int] NOT NULL,
	[TrangThaiChuoi] [bit] NOT NULL,
	[TongDiem] [int] NOT NULL,
	[DiemIQ] [int] NOT NULL,
	[Block] [bit] NOT NULL,
 CONSTRAINT [PK_NguoiDung] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[CauHinh] ON 

INSERT [dbo].[CauHinh] ([Id], [SoLuongCauHoi], [DiemTranDau], [ThoiGian]) VALUES (1, 5, 10, 10)
SET IDENTITY_INSERT [dbo].[CauHinh] OFF
GO
SET IDENTITY_INSERT [dbo].[CauHoi] ON 

INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (1, N'Luoi thuong nhay cam voi vi nao nhat?', N'Dang', N'Chua', N'Cay', N'Ngot')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (2, N'Qua dia cau co may dai duong?', N'4', N'2', N'5', N'3')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (3, N'Loai ca voi thuong song o moi truong nao?', N'Dai duong', N'Song, ho', N'Sa mac', N'Rung ram')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (4, N'Mat troi moc o huong nao?', N'Dong', N'Nam', N'Tay', N'Bac')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (5, N'So luong quoc gia la thanh vien chinh thuc cua Lien hop quoc?', N'195', N'200', N'100', N'175')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (6, N'Se ra sao neu mot nguoi tat tho trong vong 20 phut?', N'Tu vong', N'Buon non', N'Chong mat', N'Tieu chay')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (7, N'Giong cho Corgi co nguon goc tu nuoc nao?', N'Anh', N'Mi', N'Uc', N'Siberia')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (8, N'Cau vong thuong co bao nhieu mau?', N'7', N'3', N'5', N'1')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (9, N'Du ai noi nga noi nghieng, long ta van vung nhu kieng may chan?', N'3', N'4', N'5', N'2')
INSERT [dbo].[CauHoi] ([Id], [CauHoi], [CauDung], [CauSai1], [CauSai2], [CauSai3]) VALUES (10, N'Mon an nao la mon an dac trung cua Viet Nam?', N'Pho', N'Hamberger', N'Pizza', N'Boreto')
SET IDENTITY_INSERT [dbo].[CauHoi] OFF
GO
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'beahan@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Bea', 1, CAST(N'2000-05-05T00:00:00.000' AS DateTime), 20, 15, 9, 4, 3, 0, 0, 800, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'koepp3@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Koe', 0, CAST(N'1998-01-02T00:00:00.000' AS DateTime), 14, 10, 8, 3, 2, 1, 0, 600, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'lilly35@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Lily', 0, CAST(N'2000-05-11T00:00:00.000' AS DateTime), 20, 14, 10, 3, 3, 0, 0, 800, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'mcdermott@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'MC', 0, CAST(N'1999-07-06T00:00:00.000' AS DateTime), 8, 5, 4, 1, 0, 1, 0, 350, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'ngandoan@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Đoàn Thị Kim Ngân', 0, CAST(N'2000-08-20T00:00:00.000' AS DateTime), 10, 7, 6, 1, 2, 0, 0, 400, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'ngandoan110520@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Đoàn Ngân', 1, CAST(N'2021-12-09T00:00:00.000' AS DateTime), 15, 6, 3, 2, 0, 2, 0, 500, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'ngandoan11052000@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Đoàn Ngân', 1, CAST(N'2021-12-02T00:00:00.000' AS DateTime), 20, 8, 7, 1, 0, 3, 0, 700, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'tgthuan2000@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Trần Gia Thuận', 1, CAST(N'2000-08-20T00:00:00.000' AS DateTime), 17, 12, 10, 3, 3, 1, 0, 600, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'thanghd@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Hồ Đức Thắng', 1, CAST(N'2000-08-20T00:00:00.000' AS DateTime), 18, 15, 14, 1, 5, 2, 0, 650, 0, 0)
INSERT [dbo].[NguoiDung] ([Username], [Password], [TenNguoiDung], [GioiTinh], [NgaySinh], [TongTran], [TongTranThang], [ChuoiThangMax], [ChuoiThuaMax], [ChuoiThang], [ChuoiThua], [TrangThaiChuoi], [TongDiem], [DiemIQ], [Block]) VALUES (N'toy33@gmail.com', N'e10adc3949ba59abbe56e057f20f883e', N'Toy', 1, CAST(N'2000-08-06T00:00:00.000' AS DateTime), 18, 13, 7, 2, 0, 2, 0, 100, 0, 0)
GO
