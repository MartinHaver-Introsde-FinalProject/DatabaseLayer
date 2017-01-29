package model;

import model.MeasureDefinition;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import dao.LifeCoachDao;
import javax.persistence.OneToOne;

@Entity
@Table(name = "HealthMeasure")
@NamedQuery(name = "HealthMeasure.findAll", query = "SELECT l FROM HealthMeasure l")
@XmlRootElement(name = "healthMeasure")
public class HealthMeasure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idHealthMeasure")
	private int idHealthMeasure;

	@OneToOne
	@JoinColumn(name = "idMeasureDefinition", referencedColumnName = "idMeasureDefinition", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;

	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	@Column(name = "value")
	private double value;

	public HealthMeasure() {
	}

	public int getIdHealthMeasure() {
		return idHealthMeasure;
	}

	public void setIdHealthMeasure(int idHealthMeasure) {
		this.idHealthMeasure = idHealthMeasure;
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

	public void setPerson(Person person) {
		this.person = person;
	}

	public static HealthMeasure getHealthMeasureById(int idHealthMeasure) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasure healthMeasure = em.find(HealthMeasure.class, idHealthMeasure);
		LifeCoachDao.instance.closeConnections(em);
		return healthMeasure;
	}

	public static List<HealthMeasure> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<HealthMeasure> list = em.createNamedQuery("HealthMeasure.findAll", HealthMeasure.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static HealthMeasure saveHealthMeasure(HealthMeasure healthMeasure) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(healthMeasure);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return healthMeasure;
	}

	public static HealthMeasure updateHealthMeasure(HealthMeasure healthMeasure) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		healthMeasure = em.merge(healthMeasure);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return healthMeasure;
	}

	public static void removeHealthMeasure(HealthMeasure healthMeasure) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		healthMeasure = em.merge(healthMeasure);
		em.remove(healthMeasure);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
