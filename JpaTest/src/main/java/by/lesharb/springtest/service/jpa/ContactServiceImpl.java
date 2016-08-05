package by.lesharb.springtest.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.lesharb.springtest.domain.Contact;
import by.lesharb.springtest.service.ContactService;

@Service("jpaContactService")
@Repository
@Transactional
public class ContactServiceImpl implements ContactService {

	 private Log log = LogFactory.getLog(ContactServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Contact> findAll() {
		log.info("Start findAll");
		List<Contact> contacts = em.createNamedQuery("Contact.findAll", Contact.class).getResultList();
		log.info("Finish findAll");
		return contacts;
	}

}
