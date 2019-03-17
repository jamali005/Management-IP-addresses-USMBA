package metier;


import javafx.collections.ObservableList;

public interface Imetier {
	public void addEmp(Adresse emp);
	public void UpdateEmp(Adresse emp);
	public void DeleteEmp(Adresse emp);
	public ObservableList<Adresse> getAdresseesParReservation(boolean x);
	public ObservableList<Adresse> getIPByEtablissement(String etab,int reserve);
	public ObservableList<Adresse> getIPByService(String service,int reserve);
	public ObservableList<Adresse> getIPByIP(String IP,int reserve);
	public ObservableList<Adresse> getIPByMAP(String MAP,int reserve);
	public ObservableList<Adresse> getIP(String motcle,int reserve);

	public void addUser(User user);
	public ObservableList<User> getAllUser();
	public ObservableList<String> getAllIPNotreserved();
	public ObservableList<Adresse> RechercheParId(int id,int reserve);
	public ObservableList<Adresse> getAllIP();
	

}
