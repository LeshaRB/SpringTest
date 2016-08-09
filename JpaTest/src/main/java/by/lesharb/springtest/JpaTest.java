package by.lesharb.springtest;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import by.lesharb.springtest.domain.Contact;
import by.lesharb.springtest.domain.ContactAudit;
import by.lesharb.springtest.service.ContactAuditService;
import by.lesharb.springtest.service.ContactService;

public class JpaTest {

	private static void listContactsAudit(List<ContactAudit> contacts) {
		System.out.println("");
		System.out.println("Listing contacts without details:");
		for (ContactAudit contact : contacts)
			System.out.println(contact);
	}

	private static void listContacts(List<Contact> contacts) {
		System.out.println("");
		System.out.println("Listing contacts without details:");
		for (Contact contact : contacts)
			System.out.println(contact);
	}

	public static void main(String[] args) {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();

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

		// Add new contact
		System.out.println("Add new contact");
		ContactAudit contact = new ContactAudit();
		contact.setFirstName("Michael");
		contact.setLastName("Jackson");
		contact.setBirthDate(new Date());
		contactServiceAudit.save(contact);
		contactsAudit = contactServiceAudit.findAll();
		listContactsAudit(contactsAudit);

		// Find by id
		contact = contactServiceAudit.findById(1l);
		System.out.println("");
		System.out.println("Contact with id 1:" + contact);
		System.out.println("");

		// Update contact
		System.out.println("Update contact");
		contact.setFirstName("Tom");
		contactServiceAudit.save(contact);
		contactsAudit = contactServiceAudit.findAll();
		listContactsAudit(contactsAudit);

		// Find audit record by revision
		ContactAudit oldContact = contactServiceAudit.findAuditByRevision(1l, 1);
		System.out.println("");
		System.out.println("Old Contact with id 1 and rev 1:" + oldContact);
		System.out.println("");
		oldContact = contactServiceAudit.findAuditByRevision(1l, 2);
		System.out.println("");
		System.out.println("Old Contact with id 1 and rev 2:" + oldContact);
		System.out.println("");

		ctx.close();
	}

}
