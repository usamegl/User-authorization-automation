package application;

public class Kayitlar {
	int id;
	private String KullaniciSutun;
	private String TamAdSutun;
	private String YetkiSutun;

	public int getId() {
		return id;
	}

	public void setId(int Id) {
		this.id = Id;
	}

	public String getKullaniciSutun() {
		return KullaniciSutun;

	}

	public void setKullaniciSutun(String KullaniciSutun) {
		this.KullaniciSutun = KullaniciSutun;
	}
	public String getTamAdSutun() {
		return TamAdSutun;

	}

	public void setTamAdSutun(String TamAdSutun) {
		this.TamAdSutun = TamAdSutun;
	}
	public String getYetkiSutun() {
		return YetkiSutun;

	}

	public void setYetkiSutun(String YetkiSutun) {
		this.YetkiSutun = YetkiSutun;
	}
    Kayitlar(){
    	
    }
    Kayitlar(int id,String KullaniciSutun,String TamAdSutun,String YetkiSutun) {
    	this.id=id;
    	this.KullaniciSutun=KullaniciSutun;
    	this.TamAdSutun=TamAdSutun;
    	this.YetkiSutun=YetkiSutun;
    }
}
