package metier;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String rule;
	
	public User() {
		super();
	}

	public User( String username, String password, String rule) {
		super();
		this.username = username;
		this.password = password;
		this.rule = rule;
	}
	public User(int id, String username, String password, String rule) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.rule = rule;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
	
}
