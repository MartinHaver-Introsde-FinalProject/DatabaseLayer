package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import dao.LifeCoachDao;

@Entity
@Table(name = "Goal")
@NamedQuery(name = "Goal.findAll", query = "SELECT p FROM Goal p")
@XmlRootElement(name = "goal")
public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "idGoal")
	private int idGoal;
	
	@Column(name = "goalName")
	private String goalName;
	
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;
	
	@Column(name = "current")
	private int current;
	
	@Column(name = "idealWeight")
	private double idealWeight;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "idealBmi")
	private double idealBmi;
	
	@Column(name = "shavedCalories")
	private double shavedCalories;
	
	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<FoodSelection> foodSelections;
	
	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ActivitySelection> activitySelections;
	
	public Goal() {
		
	}
	
	public int getIdGoal() {
		return idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public double getIdealWeight() {
		return idealWeight;
	}

	public void setIdealWeight(double idealWeight) {
		this.idealWeight = idealWeight;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getIdealBmi() {
		return idealBmi;
	}

	public void setIdealBmi(double idealBmi) {
		this.idealBmi = idealBmi;
	}

	public double getShavedCalories() {
		return shavedCalories;
	}

	public void setShavedCalories(double shavedCalories) {
		this.shavedCalories = shavedCalories;
	}

	@XmlElementWrapper(name = "foodSelections")
	public List<FoodSelection> getFoodSelections() {
		return foodSelections;
	}

	public void setFoodSelections(List<FoodSelection> foodSelections) {
		this.foodSelections = foodSelections;
	}

	@XmlElementWrapper(name = "activitySelections")
	public List<ActivitySelection> getActivitySelections() {
		return activitySelections;
	}

	public void setActivitySelections(List<ActivitySelection> activitySelections) {
		this.activitySelections = activitySelections;
	}

	public static Goal getGoalById(int idGoal) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Goal goal = em.find(Goal.class, idGoal);
		LifeCoachDao.instance.closeConnections(em);
		return goal;
	}

	public static List<Goal> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<Goal> list = em
				.createNamedQuery("Goal.findAll", Goal.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static Goal saveGoal(Goal goal) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(goal);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return goal;
	}

	public static Goal updateGoal(Goal goal) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		goal = em.merge(goal);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return goal;
	}

	public static void removeGoal(Goal goal) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		goal = em.merge(goal);
		em.remove(goal);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}

}
