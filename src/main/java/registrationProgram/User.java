package registrationProgram;

import java.util.ArrayList;
import java.io.Serializable;

public abstract class User implements Serializable{
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	
	//constructor
	public User() {
		this.firstName = "";
		this.lastName = "";
		this.username = "";
		this.password = "";
	}
	
	public User(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	
	//define getters and setters
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	//method to get full name
	public String getFullName() {
		String fullName = firstName + " " + lastName;
		return fullName;
	}
	
	//method to view name, id, current enrollment, and max capacity of all courses
	public abstract void viewAllCourses(ArrayList<Course> courseDirectory); 
}
