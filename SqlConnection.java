package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class SqlConnection {

	public String logIn(String userID) {//giriþ ekraný kullanýcý þifre eþleþtirme

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		String userPassword = "wrong password";
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				ResultSet rs = st
						.executeQuery("select \"Password\" from public.\"User\" WHERE \"ID\" ='" + userID + "'")) {

			while (rs.next()) {
				userPassword = rs.getString("Password");
				return userPassword;
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return userPassword;
	}

	public boolean InserttoDB(int userID, String Password, String Name, String Surname, int Age) {// yeni kullanýcý
																									// ekleme

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		try (Connection con = DriverManager.getConnection(url, user, password);) {

			String query = " insert into public.\"User\" (\"ID\",\"Password\",\"Name\",\"Surname\",\"Age\")"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, userID);
			preparedStmt.setString(2, Password);
			preparedStmt.setString(3, Name);
			preparedStmt.setString(4, Surname);
			preparedStmt.setInt(5, Age);

			preparedStmt.execute();
		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
		return true;
	}

	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public ObservableList<User> getUsers() {// kullanýcýlarý tabloda gösterme
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		ObservableList<User> UserResult = FXCollections.observableArrayList();
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from public.\"User\"")) {

			while (rs.next()) {
				UserResult.add(new User(rs.getString("id"), rs.getString("password"), rs.getString("name"),
						rs.getString("surname"), rs.getString("age")));
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return UserResult;

	}

	public boolean DeleteFromDB(int userID) {// databaseden kullanýcý silme

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		try (Connection con = DriverManager.getConnection(url, user, password);) {

			String query = " delete from public.\"User\" WHERE \"ID\" = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, userID);
			preparedStmt.execute();

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
		return true;
	}

	public void insertImage(String base64img, int card_id, int index) {// karta fotoðraf ekleme

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		try (Connection con = DriverManager.getConnection(url, user, password);) {

			String query = " insert into public.\"Prints\" (\"card_id\",\"index\",\"base64data\")"
					+ " values (?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, card_id);
			preparedStmt.setInt(2, index);
			preparedStmt.setString(3, base64img);
			preparedStmt.execute();

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public void Checkupdate(int card_id) {// fotoðraf eklenen kartýn check_flagýný 1'e eþitleme

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		try (Connection con = DriverManager.getConnection(url, user, password);) {

			String query = "UPDATE public.\"Cards\" SET check_flag=1 WHERE \"card_id\" = (?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, card_id);
			preparedStmt.execute();

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	public ObservableList<Cards> getCards() {// parmak izi olmayan kartlarý tabloya ekleme
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		ObservableList<Cards> UserResult = FXCollections.observableArrayList();
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from public.\"Cards\" WHERE \"check_flag\" = 0 ")) {

			while (rs.next()) {
				UserResult.add(new Cards(rs.getInt("id"), rs.getInt("card_id"), rs.getString("description"),
						rs.getInt("check_flag")));
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return UserResult;

	}

	public ObservableList<Cards> getCardswithFinger() {// Karta Parmak izi ekleme
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		ObservableList<Cards> UserResult = FXCollections.observableArrayList();
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from public.\"Cards\" WHERE \"check_flag\" = 1 ")) {

			while (rs.next()) {
				UserResult.add(new Cards(rs.getInt("id"), rs.getInt("card_id"), rs.getString("description"),
						rs.getInt("check_flag")));
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return UserResult;

	}

	public boolean CardInsertion(int Card_id, String Description) {// Kart ekleme

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		try (Connection con = DriverManager.getConnection(url, user, password);) {

			String query = " insert into public.\"Cards\" (\"card_id\",\"description\",\"check_flag\")"
					+ " values (?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, Card_id);
			preparedStmt.setString(2, Description);
			preparedStmt.setInt(3, 0);
			preparedStmt.execute();
		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
		return true;
	}

	public String[] getImage(int UserID) {

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		String[] base64img = new String[2];
		try (Connection con = DriverManager.getConnection(url, user, password);) {
			LargeObjectManager lobj = ((org.postgresql.PGConnection) con).getLargeObjectAPI();
			String query = " select \"image\",\"image2\" from public.\"FingerPrint\" WHERE \"ID\" = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, UserID);
			preparedStmt.execute();
			ResultSet rs = preparedStmt.getResultSet();

			while (rs.next()) {
				base64img[0] = rs.getString("image");
				base64img[1] = rs.getString("image2");

				return base64img;
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}
		return base64img;
	}

	public String[] getFingerImage(int card_id) {// Karttaki parmak izini bastýrma

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		String[] base64img = new String[10];
		try (Connection con = DriverManager.getConnection(url, user, password);) {
			LargeObjectManager lobj = ((org.postgresql.PGConnection) con).getLargeObjectAPI();
			String query = " select \"base64data\" from public.\"Prints\" WHERE \"card_id\" = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, card_id);
			preparedStmt.execute();
			ResultSet rs = preparedStmt.getResultSet();
			for (int i = 0; rs.next(); i++) {
				base64img[i] = rs.getString("base64data");
			}
			return base64img;

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(SqlConnection.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}
		return base64img;
	}
}
