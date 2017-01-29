package model;

import model.MeasureRange;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import dao.LifeCoachDao;

@Entity
@Table(name = "MeasureDefinition")
@NamedQuery(name = "MeasureDefinition.findAll", query = "SELECT m FROM MeasureDefinition m")
@XmlRootElement(name = "measureDefinition")
public class MeasureDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idMeasureDefinition")
	private int idMeasureDefinition;

	@Column(name = "measureName")
	private String measureName;

	@Column(name = "measureType")
	private String measureType;

	@OneToMany(mappedBy = "measureDefinition")
	private List<MeasureRange> measureRanges;

	public MeasureDefinition() {
	}

	public int getIdMeasureDefinition() {
		return idMeasureDefinition;
	}

	public void setIdMeasureDefinition(int idMeasureDefinition) {
		this.idMeasureDefinition = idMeasureDefinition;
	}

	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	@XmlElementWrapper(name = "measureRanges")
	public List<MeasureRange> getMeasureRanges() {
		return measureRanges;
	}

	public void setMeasureRanges(List<MeasureRange> measureRanges) {
		this.measureRanges = measureRanges;
	}

	public static MeasureDefinition getMeasureDefinitionById(int idMeasureDefinition) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		MeasureDefinition measureDefinition = em.find(MeasureDefinition.class, idMeasureDefinition);
		LifeCoachDao.instance.closeConnections(em);
		return measureDefinition;
	}

	public static List<MeasureDefinition> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<MeasureDefinition> list = em.createNamedQuery("MeasureDefinition.findAll", MeasureDefinition.class)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static MeasureDefinition saveMeasureDefinition(MeasureDefinition measureDefinition) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(measureDefinition);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return measureDefinition;
	}

	public static MeasureDefinition updateMeasureDefinition(MeasureDefinition measureDefinition) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		measureDefinition = em.merge(measureDefinition);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return measureDefinition;
	}

	public static void removeMeasureDefinition(MeasureDefinition measureDefinition) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		measureDefinition = em.merge(measureDefinition);
		em.remove(measureDefinition);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
