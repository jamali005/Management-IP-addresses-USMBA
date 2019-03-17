package metier;

public class Adresse {

	private int id;
	private String etablissement;
	private String service;	
	private String IP;
	private String MAP;
	private String dtr;	
	private int reserve;
	public Adresse() {
		super();
	}
	
	public Adresse(int id, String etablissement, String service, String iP, String mAP, String dtr, int reserve) {
		super();
		this.id = id;
		this.etablissement = etablissement;
		this.service = service;
		IP = iP;
		MAP = mAP;
		this.dtr = dtr;
		this.reserve = reserve;
	}

	public Adresse(String etablissement, String service, String iP, String mAP, String dtr, 
			 int reserve) {
		super();
		this.etablissement = etablissement;
		this.service = service;
		IP = iP;
		MAP = mAP;
		this.dtr = dtr;
		this.reserve = reserve;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEtablissement() {
		return etablissement;
	}
	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getMAP() {
		return MAP;
	}
	public void setMAP(String mAP) {
		MAP = mAP;
	}
	public String getDtr() {
		return dtr;
	}
	public void setDtr(String dtr) {
		this.dtr = dtr;
	}
	public int isReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}	
	
	
	

	

}
