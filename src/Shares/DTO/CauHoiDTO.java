package Shares.DTO;

public class CauHoiDTO {

    private int id;
    private String cauHoi, cauDung;
    private String cauSai1,cauSai2,cauSai3;

    public CauHoiDTO() {
    }

    public CauHoiDTO(int id, String cauHoi, String cauDung, String cauSai1, String cauSai2, String cauSai3) {
        this.id = id;
        this.cauHoi = cauHoi;
        this.cauDung = cauDung;
        this.cauSai1 = cauSai1;
        this.cauSai2 = cauSai2;
        this.cauSai3 = cauSai3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(String cauHoi) {
        this.cauHoi = cauHoi;
    }

    public String getCauDung() {
        return cauDung;
    }

    public void setCauDung(String cauDung) {
        this.cauDung = cauDung;
    }

    public String getCauSai1() {
        return cauSai1;
    }

    public void setCauSai1(String cauSai1) {
        this.cauSai1 = cauSai1;
    }

    public String getCauSai2() {
        return cauSai2;
    }

    public void setCauSai2(String cauSai2) {
        this.cauSai2 = cauSai2;
    }

    public String getCauSai3() {
        return cauSai3;
    }

    public void setCauSai3(String cauSai3) {
        this.cauSai3 = cauSai3;
    }

   

}
