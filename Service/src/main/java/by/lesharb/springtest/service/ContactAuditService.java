package by.lesharb.springtest.service;

import java.util.List;

import by.lesharb.springtest.domain.ContactAudit;

public interface ContactAuditService {

	public List<ContactAudit> findAll();
	
	public ContactAudit findById(Long id);
	
	public ContactAudit save(ContactAudit contact);	
	
	public ContactAudit findAuditByRevision(Long id, int revision);
	
}
