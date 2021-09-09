package firstProjectDemo.person;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "allpersons")
public class PersonController {

	@GetMapping
	public ArrayList<Person> getPersons() {
		ArrayList<Person> list = new ArrayList<Person>();
		
		list.add(new Person("Angelo", LocalDate.of(2002, 05, 27), "angelo@iastate.edu"));
		list.add(new Person("example", LocalDate.of(2001, 02, 24), "example@iastate.edu"));
		
		return list;
	}
	
}
