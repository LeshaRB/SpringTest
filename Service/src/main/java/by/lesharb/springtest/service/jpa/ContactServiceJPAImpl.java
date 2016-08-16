/**
 * Created on Oct 18, 2011
 */
package by.lesharb.springtest.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import by.lesharb.springtest.domain.Contact;
import by.lesharb.springtest.repository.ContactRepository;
import by.lesharb.springtest.service.ContactService;

@Service("contactServiceJPA")
@Repository
@Transactional
public class ContactServiceJPAImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Transactional(readOnly=true)
	public List<Contact> findAll() {
		return Lists.newArrayList(contactRepository.findAll());
	}	
	
	@Transactional(readOnly=true)	
	public List<Contact> findByFirstName(String firstName) {
		return contactRepository.findByFirstName(firstName);
	}

	@Transactional(readOnly=true)	
	public List<Contact> findByFirstNameAndLastName(String firstName,
			String lastName) {
		return contactRepository.findByFirstNameAndLastName(firstName, lastName);
	}

}
