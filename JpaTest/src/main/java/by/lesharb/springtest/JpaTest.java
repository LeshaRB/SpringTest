package by.lesharb.springtest;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import by.lesharb.springtest.domain.Contact;
import by.lesharb.springtest.service.ContactService;

public class JpaTest {

	private static void listContacts(List<Contact> contacts) {
		System.out.println("");
		System.out.println("Listing contacts without details:");
		for (Contact contact : contacts) {
			System.out.println(contact);
			System.out.println();
		}
	}

	public static void main(String[] args) {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();

		ContactService contactService = ctx.getBean("jpaContactService", ContactService.class);

		List<Contact> contacts = contactService.findAll();
		listContacts(contacts);

		ctx.close();
	}

}
