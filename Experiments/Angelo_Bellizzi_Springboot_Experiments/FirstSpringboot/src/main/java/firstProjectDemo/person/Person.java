package firstProjectDemo.person;

import java.time.LocalDate;

public class Person {

	private String name;
	private LocalDate dob;
	private String email;
	
	
	public Person (String name, LocalDate dob, String email)
	{
		this.name = name;
		this.dob = dob;
		this.email = email;
	}
	
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public LocalDate getDob()
	{
		return dob;
	}
	
	public void setDob(LocalDate dob)
	{
		this.dob = dob;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String toString()
	{
		return "Student{" +
				"name='" + name + "'" +
				", email='" + email + "'" +
				", dob=" + dob + "}";
	}
}
