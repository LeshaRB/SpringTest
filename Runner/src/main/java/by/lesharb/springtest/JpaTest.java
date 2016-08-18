package by.lesharb.springtest;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import by.lesharb.springtest.config.AppConfig;
import by.lesharb.springtest.domain.Contact;
import by.lesharb.springtest.domain.ContactAudit;
import by.lesharb.springtest.service.ContactAuditService;
import by.lesharb.springtest.service.ContactService;

public class JpaTest {
	// private static Log log = LogFactory.getLog(JpaTest.class);
	private static Logger log1 = LoggerFactory.getLogger(JpaTest.class);

	private static void listContactsAudit(List<ContactAudit> contacts) {
		// log.info("");
		log1.info("");
		// log.info("Listing contacts without details:");
		log1.info("Listing contacts without details:");
		for (ContactAudit contact : contacts) {
			// log.info(contact);
			log1.info(contact.toString());
		}
	}

	private static void listContacts(List<Contact> contacts) {
		// log.info("");
		log1.info("");
		// log.info("Listing contacts without details:");
		log1.info("Listing contacts without details:");
		for (Contact contact : contacts) {
			// log.info(contact);
			log1.info(contact.toString());
		}
	}

	public static void main(String[] args) {

		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		// GenericXmlApplicationContext ctx = new
		// GenericXmlApplicationContext();
		// ctx.load("classpath:app-context.xml");
		// ctx.refresh();

		ContactService contactService = ctx.getBean("contactService", ContactService.class);

		List<Contact> contacts = contactService.findAll();
		listContacts(contacts);

		ContactService contactServiceJPA = ctx.getBean("contactServiceJPA", ContactService.class);

		List<Contact> contactsJPA = contactServiceJPA.findAll();
		listContacts(contactsJPA);

		contactsJPA = contactServiceJPA.findByFirstName("Clarence");
		listContacts(contactsJPA);

		contactsJPA = contactServiceJPA.findByFirstNameAndLastName("Lesha", "Labotsky");
		listContacts(contactsJPA);

		ContactAuditService contactServiceAudit = ctx.getBean("contactAuditService", ContactAuditService.class);

		List<ContactAudit> contactsAudit = contactServiceAudit.findAll();
		listContactsAudit(contactsAudit);

		// Add new contact System.out.println("Add new contact");
		ContactAudit contact = new ContactAudit();
		contact.setFirstName("Michael");
		contact.setLastName("Jackson");
		contact.setBirthDate(new Date());
		contactServiceAudit.save(contact);
		contactsAudit = contactServiceAudit.findAll();
		listContactsAudit(contactsAudit);

		// Find by id contact = contactServiceAudit.findById(1l);
		// log.info("");
		log1.info("");
		// log.info("Contact with id 1:" + contact);
		log1.info("Contact with id 1:" + contact);
		// log.info("");
		log1.info("");

		// Update contact System.out.println("Update contact");
		contact.setFirstName("Tom");
		contactServiceAudit.save(contact);
		contactsAudit = contactServiceAudit.findAll();
		listContactsAudit(contactsAudit);

		// Find audit record by revision ContactAudit oldContact =
		ContactAudit oldContact = contactServiceAudit.findAuditByRevision(1l, 1);
		// log.info("");
		log1.info("");
		// log.info("Old Contact with id 1 and rev 1:" + oldContact);
		log1.info("Old Contact with id 1 and rev 1:" + oldContact);
		// log.info("");
		log1.info("");
		oldContact = contactServiceAudit.findAuditByRevision(1l, 2);
		// log.info("");
		log1.info("");
		// log.info("Old Contact with id 1 and rev 2:" + oldContact);
		log1.info("Old Contact with id 1 and rev 2:" + oldContact);
		// log.info("");
		log1.info("");

		ctx.close();
	}

}
