package by.lesharb.springtest.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import by.lesharb.springtest.domain.ContactAudit;
import by.lesharb.springtest.repository.ContactAuditRepository;
import by.lesharb.springtest.service.ContactAuditService;

@Service("contactAuditService")
@Repository
@Transactional
public class ContactAuditServiceImpl implements ContactAuditService {

	@Autowired
	private ContactAuditRepository contactAuditRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional(readOnly=true)
	public List<ContactAudit> findAll() {
		return Lists.newArrayList(contactAuditRepository.findAll());
	}

	@Transactional(readOnly=true)
	public ContactAudit findById(Long id) {
		return contactAuditRepository.findOne(id);
	}

	public ContactAudit save(ContactAudit contact) {
		return contactAuditRepository.save(contact);
	}

	@Transactional(readOnly=true)
	public ContactAudit findAuditByRevision(Long id, int revision) {
		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		return auditReader.find(ContactAudit.class, id, revision);
	}

}
