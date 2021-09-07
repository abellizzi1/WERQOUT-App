package firstProjectDemo.person;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/person")
public class PersonController {

	@GetMapping
	public ArrayList<Person> getPersons() {
		ArrayList<Person> list = new ArrayList<Person>();
		
		list.add(new Person("Angelo", LocalDate.of(2002, 05, 27), "angelo@iastate.edu"));
		
		return list;
	}
	
}
