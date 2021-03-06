USE [hoc_vien_momo]
GO
/****** Object:  Trigger [dbo].[TRG_UPDATE]    Script Date: 12/15/2021 4:44:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [dbo].[TRG_UPDATE] on [dbo].[NguoiDung] after update AS
BEGIN
	 DECLARE @thang INT = (SELECT NguoiDung.ChuoiThang FROM NguoiDung, inserted WHERE NguoiDung.Username = inserted.Username),
			 @thangMax INT = (SELECT NguoiDung.ChuoiThangMax FROM NguoiDung, inserted WHERE NguoiDung.Username = inserted.Username),
			 @thua INT = (SELECT NguoiDung.ChuoiThua FROM NguoiDung, inserted WHERE NguoiDung.Username = inserted.Username),
			 @thuaMax INT = (SELECT NguoiDung.ChuoiThuaMax FROM NguoiDung, inserted WHERE NguoiDung.Username = inserted.Username);
	  
	  IF (@thang > @thangMax)
	  BEGIN
	   UPDATE NguoiDung SET ChuoiThangMax = @thang FROM NguoiDung 
	   JOIN inserted ON NguoiDung.Username = inserted.Username
	  END

	 IF (@thua > @thuaMax)
	  BEGIN
	   UPDATE NguoiDung SET ChuoiThuaMax = @thua FROM NguoiDung 
	   JOIN inserted ON NguoiDung.Username = inserted.Username
	  END
END