package by.lesharb.springtest.service;

import java.util.List;

import by.lesharb.springtest.domain.Contact;

public interface ContactService {

	public List<Contact> findAll();

	public List<Contact> findByFirstName(String firstName);

	public List<Contact> findByFirstNameAndLastName(String firstName, String lastName);

}
