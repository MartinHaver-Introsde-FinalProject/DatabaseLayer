package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;

import dao.LifeCoachDao;

@Entity
@Table(name = "Activity")
@NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a")
@XmlRootElement(name = "activity")
public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "idActivity")
	private int idActivity;
	
	@Column(name = "activityName")
	private String activityName;
	
	@Column(name = "caloriesPerHour")
	private double caloriesPerHour;
	
	@Column(name = "activityType")
	private String activityType;
	
	public Activity() {
		
	}

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public double getCaloriesPerHour() {
		return caloriesPerHour;
	}

	public void setCaloriesPerHour(double caloriesPerHour) {
		this.caloriesPerHour = caloriesPerHour;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	public static Activity getActivityById(int idActivity) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Activity activity = em.find(Activity.class, idActivity);
		LifeCoachDao.instance.closeConnections(em);
		return activity;
	}

	public static List<Activity> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static Activity saveActivity(Activity activity) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(activity);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return activity;
	}

	public static Activity updateActivity(Activity activity) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		activity = em.merge(activity);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return activity;
	}

	public static void removeActivity(Activity activity) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		activity = em.merge(activity);
		em.remove(activity);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
