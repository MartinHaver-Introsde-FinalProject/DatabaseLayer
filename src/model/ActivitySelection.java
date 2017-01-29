package model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import dao.LifeCoachDao;

@Entity
@Table(name = "ActivitySelection")
@NamedQuery(name = "ActivitySelection.findAll", query = "SELECT a FROM ActivitySelection a")
@XmlRootElement(name = "activitySelection")
public class ActivitySelection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idActivitySelection")
	private int idActivitySelection;

	@Column(name = "time")
	private double time;
	
	@OneToOne
	@JoinColumn(name = "idActivity", referencedColumnName = "idActivity", insertable = true, updatable = true)
	private Activity activity;

	@Column(name = "usedCalories")
	private double usedCalories;

	@ManyToOne
	@JoinColumn(name = "idGoal", referencedColumnName = "idGoal")
	private Goal goal;

	@Column(name = "current")
	private int current;
	
	public ActivitySelection() {
		
	}

	public int getIdActivitySelection() {
		return idActivitySelection;
	}

	public void setIdActivitySelection(int idActivitySelection) {
		this.idActivitySelection = idActivitySelection;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getUsedCalories() {
		return usedCalories;
	}

	public void setUsedCalories(double usedCalories) {
		this.usedCalories = usedCalories;
	}

	@XmlTransient
	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public static ActivitySelection getActivitySelectionById(int idActivitySelection) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		ActivitySelection activitySelection = em.find(ActivitySelection.class, idActivitySelection);
		LifeCoachDao.instance.closeConnections(em);
		return activitySelection;
	}

	public static List<ActivitySelection> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<ActivitySelection> list = em.createNamedQuery("ActivitySelection.findAll", ActivitySelection.class)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static ActivitySelection saveActivitySelection(ActivitySelection activitySelection) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(activitySelection);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return activitySelection;
	}

	public static ActivitySelection updateActivitySelection(ActivitySelection activitySelection) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		activitySelection = em.merge(activitySelection);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return activitySelection;
	}

	public static void removeActivitySelection(ActivitySelection activitySelection) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		activitySelection = em.merge(activitySelection);
		em.remove(activitySelection);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
