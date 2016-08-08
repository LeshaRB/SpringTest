 package by.lesharb.springtest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import by.lesharb.springtest.domain.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {

	public List<Contact> findByFirstName(String firstName);
	
	public List<Contact> findByFirstNameAndLastName(String firstName, String lastName);	
	
}
