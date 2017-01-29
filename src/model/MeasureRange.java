package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import dao.LifeCoachDao;

@Entity
@Table(name = "MeasureRange")
@NamedQuery(name = "MeasureRange.findAll", query = "SELECT m FROM MeasureRange m")
@XmlRootElement(name = "measureRange")
public class MeasureRange implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idMeasureRange")
	private int idMeasureRange;

	@Column(name = "rangeName")
	private String rangeName;

	@Column(name = "startValue")
	private double startValue;

	@Column(name = "alarmLevel")
	private int alarmLevel;

	@Column(name = "endValue")
	private double endValue;

	@ManyToOne
	@JoinColumn(name = "idMeasureDefinition", referencedColumnName = "idMeasureDefinition", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;

	public MeasureRange() {
	}

	public int getIdMeasureRange() {
		return idMeasureRange;
	}

	public void setIdMeasureRange(int idMeasureRange) {
		this.idMeasureRange = idMeasureRange;
	}

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	public double getStartValue() {
		return startValue;
	}

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public int getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public double getEndValue() {
		return endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	@XmlTransient
	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition measureDefinition) {
		this.measureDefinition = measureDefinition;
	}
	
	public static MeasureRange getMeasureRangeById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		MeasureRange measureRange = em.find(MeasureRange.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return measureRange;
	}

	public static List<MeasureRange> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<MeasureRange> list = em.createNamedQuery("MeasureRange.findAll", MeasureRange.class)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static MeasureRange saveMeasureRange(MeasureRange measureRange) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(measureRange);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return measureRange;
	}

	public static MeasureRange updateMeasureRange(MeasureRange measureRange) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		measureRange = em.merge(measureRange);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return measureRange;
	}

	public static void removeMeasureRange(MeasureRange measureRange) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		measureRange = em.merge(measureRange);
		em.remove(measureRange);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
