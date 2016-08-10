package by.lesharb.springtest.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// Hibernate 3
// @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")

// Hibernate 5 & Java <8
// @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")

// Hibernate 5 & Java = 8
//@Type(type="org.hibernate.type.LocalDateTimeType")

@Entity
@Audited
@Table(name = "contact_audit")
@EntityListeners(AuditingEntityListener.class)
public class ContactAudit {

	private Long id;
	private int version;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private Set<Hobby> hobbies = new HashSet<Hobby>();
	private Set<ContactTelDetail> contactTelDetails = new HashSet<ContactTelDetail>();

	// Audit fields
	private String createdBy;
	private ZonedDateTime createdDate;
	private String lastModifiedBy;
	private ZonedDateTime lastModifiedDate;

	public ContactAudit() {
	}

	public ContactAudit(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public ContactAudit(String firstName, String lastName, Date birthDate, Set<Hobby> hobbies,
			Set<ContactTelDetail> contactTelDetails) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.hobbies = hobbies;
		this.contactTelDetails = contactTelDetails;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@NotAudited
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "contact_audit_hobby_detail", joinColumns = @JoinColumn(name = "CONTACT_ID"), inverseJoinColumns = @JoinColumn(name = "HOBBY_ID"))
	public Set<Hobby> getHobbies() {
		return this.hobbies;
	}

	public void setHobbies(Set<Hobby> hobbies) {
		this.hobbies = hobbies;
	}

	@NotAudited
	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ContactTelDetail> getContactTelDetails() {
		return this.contactTelDetails;
	}

	public void setContactTelDetails(Set<ContactTelDetail> contactTelDetails) {
		this.contactTelDetails = contactTelDetails;
	}

	@CreatedBy
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@CreatedDate
	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@LastModifiedBy
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@LastModifiedDate
	public ZonedDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Transient
	public boolean isNew() {
		if (id == null) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return "Contact - Id: " + id + "\n, First name: " + firstName + "\n, Last name: " + lastName + "\n, Birthday: "
				+ birthDate + "\n, Create by: " + createdBy + "\n, Create date: " + createdDate + "\n, Modified by: "
				+ lastModifiedBy + "\n, Modified date: " + lastModifiedDate;
	}

}
