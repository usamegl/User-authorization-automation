package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.IsteMysql.Util.VeritabaniUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {
	Connection baglanti = null;
	PreparedStatement sorguIfadesi = null;
	ResultSet getirilen = null;
	String sql;

	public LoginController() {
		baglanti = VeritabaniUtil.Baglan();
	}

	@FXML
	private AnchorPane TumSayfa;
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField KullaniciText;

	@FXML
	private TextField SifreText;

	@FXML
	private ComboBox<String> KullaniciTuru;

	@FXML
	private Label Etiket;

	@FXML
	private Button LoginBtn;

	@FXML
    void LoginBtnClick(ActionEvent event) {
       sql="SELECT * FROM kullanici WHERE KullaniciAdi=? AND KullaniciSifre=?";
       try {
    	   sorguIfadesi=baglanti.prepareStatement(sql);
    	   sorguIfadesi.setString(1,KullaniciText.getText().trim());
    	   sorguIfadesi.setString(2,SifreText.getText().trim());
    	   ResultSet getirilen=sorguIfadesi.executeQuery();
    	   if(!getirilen.next()) {
    		   Etiket.setText("Kullanýcý adý veya þifre hatalý");
    	   }
    	   else {
    		   AnchorPane pane1=(AnchorPane) FXMLLoader.load(getClass().getResource("KullaniciForm.fxml"));
    		   TumSayfa.getChildren().setAll(pane1);
    		   Etiket.setText("Giriþ baþarýlý");
    	   }
       } catch(Exception e) {
    	  // System.out.println(e.getMessage().toString());
    	   System.out.println(e.getMessage().toString());
       }

      
    }

	@FXML
	void initialize() {
		String[] liste = { "Yönetici", "Þef", "Misafir" };
		KullaniciTuru.getItems().addAll(liste);
		assert KullaniciText != null : "fx:id=\"KullaniciText\" was not injected: check your FXML file 'Login.fxml'.";
		assert SifreText != null : "fx:id=\"SifreText\" was not injected: check your FXML file 'Login.fxml'.";
		assert KullaniciTuru != null : "fx:id=\"KullaniciTuru\" was not injected: check your FXML file 'Login.fxml'.";
		assert Etiket != null : "fx:id=\"Etiket\" was not injected: check your FXML file 'Login.fxml'.";
		assert LoginBtn != null : "fx:id=\"LoginBtn\" was not injected: check your FXML file 'Login.fxml'.";

	}
}
