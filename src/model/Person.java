package model;

import model.HealthMeasure;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import dao.LifeCoachDao;

import java.util.List;

@Entity
@Table(name = "Person")
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
@XmlRootElement(name = "person")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int idPerson;

	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;

	@Column(name = "birthdate")
	private String birthdate;

	@Column(name = "username")
	private String username;

	@Column(name = "sex")
	private int sex;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<HealthMeasure> healthMeasures;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<HealthMeasureHistory> healthMeasuresHistories;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Goal> goals;

	public Person() {
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@XmlElementWrapper(name = "healthMeasures")
	public List<HealthMeasure> getHealthMeasures() {
		return healthMeasures;
	}

	public void setHealthMeasures(List<HealthMeasure> healthMeasures) {
		this.healthMeasures = healthMeasures;
	}

	@XmlElementWrapper(name = "healthMeasuresHistories")
	public List<HealthMeasureHistory> getHealthMeasuresHistories() {
		return healthMeasuresHistories;
	}

	public void setHealthMeasuresHistories(List<HealthMeasureHistory> healthMeasuresHistories) {
		this.healthMeasuresHistories = healthMeasuresHistories;
	}

	@XmlElementWrapper(name = "goals")
	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public static Person getPersonById(int idPerson) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Person person = em.find(Person.class, idPerson);
		LifeCoachDao.instance.closeConnections(em);
		return person;
	}

	public static List<Person> getAll() {
		System.out.println("--> Initializing Entity manager...");
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		System.out.println("--> Querying the database for all the people...");
		List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
		System.out.println("--> Closing connections of entity manager...");
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static Person savePerson(Person person) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(person);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return person;
	}

	public Person updatePerson(Person person) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		person = em.merge(person);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return person;
	}

	public static void removePerson(Person person) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		person = em.merge(person);
		em.remove(person);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
