package by.lesharb.springtest.repository;

import org.springframework.data.repository.CrudRepository;

import by.lesharb.springtest.domain.ContactAudit;

public interface ContactAuditRepository extends CrudRepository<ContactAudit, Long> {

}
