package metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;



public class MetierImpl implements Imetier {
	//ajoute une addresse IP dans la base (c'est pas de reservation mais c pour le boutton ajoute)
	@Override
	public void addEmp(Adresse emp){
		// TODO Auto-generated method stub
		Connection conn = DAO.getConn();
		try {
			PreparedStatement ps = conn.prepareStatement("insert into IPman(etablissement,service,IP,MAP,dtr,reserve)" +  
					" VALUES(?,?,?,?,?,?)");
			ps.setString(1,emp.getEtablissement());
			ps.setString(2, emp.getService());
			ps.setString(3, emp.getIP());
			ps.setString(4, emp.getMAP());
			ps.setString(5, emp.getDtr());
			ps.setInt(6, emp.isReserve());
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("IP insert SQL Exception Error : "+e);
		}
		
	}
	//Modifier les information d'une addresse dans la base
	@Override
	public void UpdateEmp(Adresse emp) {
		Connection conn = DAO.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE IPman SET etablissement=? ,service=? ,IP=? ,MAP=?,dtr=?,reserve=? WHERE id=?");
			
			
			ps.setString(1,emp.getEtablissement());
			ps.setString(2, emp.getService());
			ps.setString(3, emp.getIP());
			ps.setString(4, emp.getMAP());
			ps.setString(5, emp.getDtr());
			ps.setInt(6, emp.isReserve());
			ps.setInt(7,emp.getId());
			int rowsUpdated = ps.executeUpdate();
			
			if (rowsUpdated > 0) {
			    System.out.println("An existing IP was updated successfully!");
			}
			
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("IP Update SQL Exception : "+e);
		}
	
	}
	//reservation et liberation des addresses IP dans la base 
	public void Reserve(Adresse emp,int reserve) {
		Connection conn = DAO.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE IPman SET reserve=? WHERE id=?");
			ps.setInt(1, reserve);
			ps.setInt(2,emp.getId());
			int rowsUpdated = ps.executeUpdate();
			
			if (rowsUpdated > 0) {
			    System.out.println("An existing IP was updated successfully!");
			}
			
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("IP Update SQL Exception : "+e);
		}
	
	}
	//suppression definitive de l'adresse 
	@Override
	public void DeleteEmp(Adresse emp) {
		Connection conn = DAO.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM IPman WHERE ID=?");
			
			ps.setInt(1, emp.getId());
			
			ps.execute();
			
			System.out.println("An existing IP was Deleted successfully!");
			
			
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("IP Delete SQL Exception : "+e);
		}
		
	}
//pour la recherche des addresses reserve ou non (pour la generation des deux tables 
	@Override
	public ObservableList<Adresse> getAdresseesParReservation(boolean x) {
		int reservation;
		ObservableList<Adresse> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
		try {
			if(x==true) {
				reservation=1;
			}
			else {
				reservation=0;				
			}
			PreparedStatement ps = conn.prepareStatement("select * from IPman where reserve=?");
			System.out.println("in get");
			ps.setInt(1, reservation);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Adresse emp = new Adresse();
				emp.setId(rs.getInt("id"));
				emp.setEtablissement(rs.getString("etablissement"));
				emp.setService(rs.getString("service"));
				emp.setIP(rs.getString("IP"));
				emp.setMAP(rs.getString("MAP"));
				emp.setDtr(rs.getString("dtr"));
				emp.setReserve(rs.getInt("reserve"));
				emps.add(emp);
			}
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("User and IP Search SQL Exception Error : "+e);
		}
		
		return emps;
	}
//la recherche par etablissement et reservation
	@Override
	public ObservableList<Adresse> getIPByEtablissement(String etab,int reserve){
		ObservableList<Adresse> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from IPman where etablissement like ? and reserve=? ");
			ps.setString(1,"%"+etab+"%");
			ps.setInt(2, reserve);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Adresse emp = new Adresse();
				emp.setId(rs.getInt("id"));
				emp.setEtablissement(rs.getString("etablissement"));
				emp.setService(rs.getString("service"));
				emp.setIP(rs.getString("IP"));
				emp.setMAP(rs.getString("MAP"));
				emp.setDtr(rs.getString("dtr"));
				emps.add(emp);
				}
				
				ps.close();
				
			} catch (Exception e) {
				System.out.println("erroooooooor");
				e.printStackTrace();
			}
			return emps;
				
	}

	//la recherche par id et reservation

	@Override
	public ObservableList<Adresse> RechercheParId(int id,int reserve) {
		ObservableList<Adresse> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from IPman where id= ? and reserve=? ");
			
			
			ps.setInt(1,id);
			ps.setInt(2, reserve);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Adresse emp = new Adresse();
				emp.setId(rs.getInt("id"));
				emp.setEtablissement(rs.getString("etablissement"));
				emp.setService(rs.getString("service"));
				emp.setIP(rs.getString("IP"));
				emp.setMAP(rs.getString("MAP"));
				emp.setDtr(rs.getString("dtr"));
				emps.add(emp);
				}
				
				ps.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return emps;

		
	}
	
//	la rechreche par service et reservation
	@Override
	public ObservableList<Adresse> getIPByService(String service,int reserve) {
		ObservableList<Adresse> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from IPman where service like ? and reserve=?");
			ps.setString(1,"%"+service+"%");
			ps.setInt(2, reserve);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Adresse emp = new Adresse();
				emp.setId(rs.getInt("id"));
				emp.setEtablissement(rs.getString("etablissement"));
				emp.setService(rs.getString("service"));
				emp.setIP(rs.getString("IP"));
				emp.setMAP(rs.getString("MAP"));
				emp.setDtr(rs.getString("dtr"));
				emps.add(emp);
				}
				
				ps.close();
				
			} catch (Exception e) {
				System.out.println("erroooooooor");
				e.printStackTrace();
			}
			return emps;
	}
//	recherche par IP public et reservation

	@Override
	public ObservableList<Adresse> getIPByIP(String IP,int reserve) {
		ObservableList<Adresse> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from IPman where IP like ? and reserve=?");
			ps.setString(1,"%"+IP+"%");
			ps.setInt(2, reserve);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Adresse emp = new Adresse();
				emp.setId(rs.getInt("id"));
				emp.setEtablissement(rs.getString("etablissement"));
				emp.setService(rs.getString("service"));
				emp.setIP(rs.getString("IP"));
				emp.setMAP(rs.getString("MAP"));
				emp.setDtr(rs.getString("dtr"));
				emps.add(emp);
				}
				
				ps.close();
				
			} catch (Exception e) {
				System.out.println("erroooooooor");
				e.printStackTrace();
			}
			return emps;		
	}
//	recherche par MAP et reservation 
	@Override
	public ObservableList<Adresse> getIPByMAP(String MAP,int reserve) {
		ObservableList<Adresse> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from IPman where MAP like ? and reserve=?");
			ps.setString(1,"%"+MAP+"%");
			ps.setInt(2, reserve);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Adresse emp = new Adresse();
				emp.setId(rs.getInt("id"));
				emp.setEtablissement(rs.getString("etablissement"));
				emp.setService(rs.getString("service"));
				emp.setIP(rs.getString("IP"));
				emp.setMAP(rs.getString("MAP"));
				emp.setDtr(rs.getString("dtr"));
				emps.add(emp);
				}
				
				ps.close();
				
			} catch (Exception e) {
				System.out.println("erroooooooor");
				e.printStackTrace();
			}
			return emps;
	}
//	recherche par toutes les mots cle 
	@Override
	public ObservableList<Adresse> getIP(String motcle,int reserve) {
		ObservableList<Adresse> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from IPman where (etablissement like ? or service like ? or IP like ? or MAP like ? or dtR like ?) and reserve=? ");
			for (int i = 1; i < 6; i++) {
				ps.setString(i,"%"+motcle+"%");
			}
				ps.setInt(6, reserve);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Adresse emp = new Adresse();
				emp.setId(rs.getInt("id"));
				emp.setEtablissement(rs.getString("etablissement"));
				emp.setService(rs.getString("service"));
				emp.setIP(rs.getString("IP"));
				emp.setMAP(rs.getString("MAP"));
				emp.setDtr(rs.getString("dtr"));
				emps.add(emp);
				}
				
				ps.close();
				
			} catch (Exception e) {
				System.out.println("erroooooooor");
				e.printStackTrace();
			}
			return emps;
	}
//recherche de toutes les addresses non reserver 
	@Override
	public ObservableList<String> getAllIPNotreserved() {
		ObservableList<String> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select IP from IPman where reserve=0 ");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {

				emps.add(rs.getString("IP"));
				}
				
				ps.close();
				
			} catch (Exception e) {
				System.out.println("erroooooooor");
				e.printStackTrace();
			}
			return emps;
	}
//recherche de toutes les addresses avec leur information
	@Override
	public ObservableList<Adresse> getAllIP() {
		ObservableList<Adresse> emps = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from IPman ");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Adresse emp = new Adresse();
				emp.setId(rs.getInt("id"));System.out.println(rs.getInt("id"));
				emp.setEtablissement(rs.getString("etablissement"));
				emp.setService(rs.getString("service"));
				emp.setIP(rs.getString("IP"));
				emp.setMAP(rs.getString("MAP"));
				emp.setDtr(rs.getString("dtr"));
				emps.add(emp);
				}
				
				ps.close();
				
			} catch (Exception e) {
				System.out.println("erroooooooor");
				e.printStackTrace();
			}
			return emps;
	}
// verification d ecriture l'addresse IP et MAP 
	public static boolean isValidIP(String ipAddr){
		String IPADDRESS_PATTERN = 
		        "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ipAddr);
		if (matcher.find()) {
			if(ipAddr.equals(matcher.group()))
		    return true;
			else return false;
		} else{
		    return false;
		}
	}
	
	
	
//	##################### USER MANAGMENT ###################
	@Override
	public ObservableList<User> getAllUser() {
		ObservableList<User> Users = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("SELECT * FROM USER");
				ResultSet rs = ps.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setId(rs.getInt("ID"));System.out.println(rs.getInt("ID"));
				user.setRule(rs.getString("Rule"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				Users.add(user);
			}
			ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Users;
	}

	@Override	
	public void addUser(User user){
		// TODO Auto-generated method stub
		Connection conn = DAO.getConn();
		try {
			PreparedStatement ps = conn.prepareStatement("insert into User (username,password,Rule)" +  
					" VALUES(?,?,?)");
			ps.setString(1,user.getUsername());
			ps.setString(2,user.getPassword());
			ps.setString(3, user.getRule());
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("User insert SQL Exception Error : "+e);
		}
		
	}
	
	public void UpdateUser(User user) {
		Connection conn = DAO.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE User SET username=? ,password=? ,Rule=? WHERE id=?");
			
			
			ps.setString(1,user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRule());
			ps.setInt(4,user.getId());
			int rowsUpdated = ps.executeUpdate();
			
			if (rowsUpdated > 0) {
			    System.out.println("An existing Adresse was updated successfully!");
			}
			
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("User Update SQL Exception : "+e);
		}
	
	}
	public void DeleteUser(User user) {
		Connection conn = DAO.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM User WHERE ID=?");
			
			ps.setInt(1, user.getId());
			
			ps.execute();
			
			System.out.println("An existing User was Deleted successfully!");
			
			
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("User Delete SQL Exception : "+e);
		}
		
	}
	public ObservableList<User> RechercheParRole(String role) {
		ObservableList<User> users = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("SELECT * FROM USER WHERE RULE LIKE ?");
			
			
			ps.setString(1, "%"+role+"%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("Password"));
				user.setRule(rs.getString("Rule"));
				users.add(user);
				}
				
				ps.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return users;
		
	}
	public ObservableList<User> RechercheParUsername(String username) {
		ObservableList<User> users = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from user where username like ?");
			
			
			ps.setString(1, "%"+username+"%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("Password"));
				user.setRule(rs.getString("Rule"));
				users.add(user);
				}
				
				ps.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return users;
		
	}
	public ObservableList<User> RechercheParMotCle(String motcle) {
		ObservableList<User> users = FXCollections.observableArrayList();
		Connection conn = DAO.getConn();
		
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement("select * from User where username like ? or password like ? or Rule like ? or id=? ");
			for (int i = 1; i < 5; i++) {
				ps.setString(i,"%"+motcle+"%");
			}
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getString("Rule"));
				users.add(user);
				}
				
				ps.close();
				
			} catch (Exception e) {
				System.out.println("erroooooooor");
				e.printStackTrace();
			}
			return users;
	}
}
