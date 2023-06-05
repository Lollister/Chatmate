package application;

public class User {
	
	String username;
	int siege;
	int niederlagen;
	int unentschieden;
	
	public User(String ip_username, int ip_siege, int ip_niederlagen, int ip_unentschieden) {
		
		this.username = ip_username;
		this.siege = ip_siege;
		this.unentschieden = ip_unentschieden;
		this.niederlagen = ip_niederlagen;
	}

}
