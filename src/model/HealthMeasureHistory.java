package model;

import model.Person;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import dao.LifeCoachDao;

@Entity
@Table(name = "HealthMeasureHistory")
@NamedQuery(name = "HealthMeasureHistory.findAll", query = "SELECT h FROM HealthMeasureHistory h")
@XmlRootElement(name = "healthMeasureHistory")
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idHealthMeasureHistory")
	private int idHealthMeasureHistory;
	
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	@Column(name = "value")
	private double value;

	@Column(name = "timestamp")
	private String timestamp;

	@ManyToOne
	@JoinColumn(name = "idMeasureDefinition", referencedColumnName = "idMeasureDefinition")
	private MeasureDefinition measureDefinition;

	public HealthMeasureHistory() {
	}

	public int getIdHealthMeasureHistory() {
		return idHealthMeasureHistory;
	}

	public void setIdHealthMeasureHistory(int idHealthMeasureHistory) {
		this.idHealthMeasureHistory = idHealthMeasureHistory;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person param) {
		this.person = param;
	}
	
	public static HealthMeasureHistory getHealthMeasureHistoryById(int idHealthMeasureHistory) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory healthMeasureHistory = em.find(HealthMeasureHistory.class, idHealthMeasureHistory);
		LifeCoachDao.instance.closeConnections(em);
		return healthMeasureHistory;
	}

	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<HealthMeasureHistory> list = em
				.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory healthMeasureHistory) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(healthMeasureHistory);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return healthMeasureHistory;
	}

	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory healthMeasureHistory) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		healthMeasureHistory = em.merge(healthMeasureHistory);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return healthMeasureHistory;
	}

	public static void removeHealthMeasureHistory(HealthMeasureHistory healthMeasureHistory) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		healthMeasureHistory = em.merge(healthMeasureHistory);
		em.remove(healthMeasureHistory);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
