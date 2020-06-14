package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.IsteMysql.Util.VeritabaniUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.*;

public class KullaniciFormController {
	Connection baglanti = null;
	PreparedStatement sorguIfadesi = null;
	ResultSet getirilen = null;
	String sql = "";

	public KullaniciFormController() {
		baglanti = VeritabaniUtil.Baglan();
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField KullaniciText;

	@FXML
	private TextField TamAdText;

	@FXML
	private ComboBox<String> YetkiCombo;

	@FXML
	private Button EkleBtn;

	@FXML
	private Button GuncelleBtn;

	@FXML
	private Button SilBtn;

	@FXML
	private TableView<Kayitlar> Tablo;
	@FXML
	private TableColumn<Kayitlar, Integer> IDSutun;

	@FXML
	private TableColumn<Kayitlar, String> KullaniciSutun;

	@FXML
	private TableColumn<Kayitlar, String> TamAdSutun;

	@FXML
	private TableColumn<Kayitlar, String> YetkiSutun;

	@FXML
	void EkleBtnClick(ActionEvent event) throws SQLException {
		try {
		TextInputDialog dialog=new TextInputDialog("iste");
		dialog.setTitle("Þifre");
		dialog.setHeaderText("Þifre Tanýmlama");
		dialog.setContentText("Lütfen kayýt edilecek þifreyi giriniz.");
		Optional<String> sonuc=dialog.showAndWait();
		sql="insert into kullanici(KullaniciAdi,KullaniciSifre,KullaniciTamAd,KullaniciYetki) value(?,?,?,?)";
		sorguIfadesi=baglanti.prepareStatement(sql);
		sorguIfadesi.setString(1, KullaniciText.getText().trim());
		sorguIfadesi.setString(2	, sonuc.get().trim());
		sorguIfadesi.setString(3, TamAdText.getText().trim());
		sorguIfadesi.setString(4, YetkiCombo.getSelectionModel().getSelectedItem().trim());
		sorguIfadesi.executeUpdate();
		System.out.println("Ekleme iþlemi tamamlandý.");
		DegerleriGetir();
		

	} catch (Exception e) {
		System.out.println(e.getMessage().toString());
	}
	}

	@FXML
	void GuncelleBtnClick(ActionEvent event) {
		try {
			TextInputDialog dialog = new TextInputDialog("iste");
			dialog.setTitle("Þifre");
			dialog.setHeaderText("Þifre Tanýmlama");
			dialog.setContentText("Lütfen kayýt edilecek þifreyi giriniz ");
			Optional<String> sonuc = dialog.showAndWait();
			int a=Tablo.getSelectionModel().getSelectedItems().get(0).id;	
			sql = "UPDATE kullanici SET KullaniciAdi=?,KullaniciSifre=?,KullaniciTamAd=?,KullaniciYetki=? WHERE KullaniciId=?";	
			sorguIfadesi = baglanti.prepareStatement(sql);
			sorguIfadesi.setString(1, KullaniciText.getText().trim());
			sorguIfadesi.setString(2, sonuc.get().trim());
			sorguIfadesi.setString(3, TamAdText.getText().trim());
			sorguIfadesi.setString(4, YetkiCombo.getSelectionModel().getSelectedItem().trim());
			sorguIfadesi.setString(5, String.valueOf(a).trim());
			sorguIfadesi.executeUpdate();
			System.out.println("Güncelleme iþlemi tamamlandý.");
			DegerleriGetir();
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());	
		}

	}

	@FXML
	void SilBtnClick(ActionEvent event) {
		try {
		int a = Tablo.getSelectionModel().getSelectedItems().get(0).id;

		sql = "DELETE FROM kullanici  WHERE KullaniciId=?";
		sorguIfadesi = baglanti.prepareStatement(sql);

		sorguIfadesi.setString(1, String.valueOf(a).trim());

		sorguIfadesi.executeUpdate();
		System.out.println("Silme iþlemi tamamlandý.");
		DegerleriGetir();
	}catch(

	Exception e)
	{
		System.out.println(e.getMessage().toString());
	}

	}

	@FXML
	void TabloGoster(MouseEvent event) {
		Kayitlar kayit = new Kayitlar();
		kayit = (Kayitlar) Tablo.getItems().get(Tablo.getSelectionModel().getSelectedIndex());
		KullaniciText.setText(kayit.getKullaniciSutun());
		TamAdText.setText(kayit.getTamAdSutun());
		YetkiCombo.setValue(kayit.getYetkiSutun());

	}
	

	public void DegerleriGetir() {
		sql = "SELECT * FROM kullanici";
		ObservableList<Kayitlar> kayitlarListe =FXCollections.observableArrayList();
		try {
			sorguIfadesi = baglanti.prepareStatement(sql);
			ResultSet getirilen = sorguIfadesi.executeQuery();
			while (getirilen.next()) {
				kayitlarListe.add(new Kayitlar(getirilen.getInt("KullaniciId"), getirilen.getString("KullaniciAdi"),
						getirilen.getString("KullaniciTamAd"), getirilen.getString("KullaniciYetki")));
			}
			IDSutun.setCellValueFactory(new PropertyValueFactory<>("id"));
			KullaniciSutun.setCellValueFactory(new PropertyValueFactory<>("KullaniciSutun"));
			TamAdSutun.setCellValueFactory(new PropertyValueFactory<>("TamAdSutun"));
			YetkiSutun.setCellValueFactory(new PropertyValueFactory<>("YetkiSutun"));
			Tablo.setItems(kayitlarListe);

		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		}

	}	

	@FXML
	void initialize() {
		DegerleriGetir();
		String[] liste = { "Yönetici", "Þef", "Misafir" };
		YetkiCombo.getItems().addAll(liste);

		assert KullaniciText != null : "fx:id=\"KullaniciText\" was not injected: check your FXML file 'KullaniciForm.fxml'.";
		assert TamAdText != null : "fx:id=\"TamAdText\" was not injected: check your FXML file 'KullaniciForm.fxml'.";
		assert YetkiCombo != null : "fx:id=\"YetkiCombo\" was not injected: check your FXML file 'KullaniciForm.fxml'.";
		assert EkleBtn != null : "fx:id=\"EkleBtn\" was not injected: check your FXML file 'KullaniciForm.fxml'.";
		assert GuncelleBtn != null : "fx:id=\"GuncelleBtn\" was not injected: check your FXML file 'KullaniciForm.fxml'.";
		assert SilBtn != null : "fx:id=\"SilBtn\" was not injected: check your FXML file 'KullaniciForm.fxml'.";
		assert Tablo != null : "fx:id=\"Tablo\" was not injected: check your FXML file 'KullaniciForm.fxml'.";

	}
}
