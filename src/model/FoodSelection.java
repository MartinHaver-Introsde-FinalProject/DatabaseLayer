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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import dao.LifeCoachDao;

@Entity
@Table(name = "FoodSelection")
@NamedQuery(name = "FoodSelection.findAll", query = "SELECT f FROM FoodSelection f")
@XmlRootElement(name = "foodSelection")
public class FoodSelection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idFoodSelection")
	private int idFoodSelection;

	@Column(name = "label")
	private String label;

	@Column(name = "image")
	private String image;

	@Column(name = "calories")
	private double calories;

	@Column(name = "weight")
	private double weight;

	@Column(name = "ingredients")
	private String ingredients;

	@ManyToOne
	@JoinColumn(name = "idGoal", referencedColumnName = "idGoal")
	private Goal goal;

	@Column(name = "current")
	private int current;
	
	public FoodSelection() {
		
	}

	public int getIdFoodSelection() {
		return idFoodSelection;
	}

	public void setIdFoodSelection(int idFoodSelection) {
		this.idFoodSelection = idFoodSelection;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
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

	public static FoodSelection getFoodSelectionById(int idFoodSelection) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		FoodSelection foodSelection = em.find(FoodSelection.class, idFoodSelection);
		LifeCoachDao.instance.closeConnections(em);
		return foodSelection;
	}

	public static List<FoodSelection> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<FoodSelection> list = em.createNamedQuery("FoodSelection.findAll", FoodSelection.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static FoodSelection saveFoodSelection(FoodSelection foodSelection) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(foodSelection);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return foodSelection;
	}

	public static FoodSelection updateFoodSelection(FoodSelection foodSelection) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		foodSelection = em.merge(foodSelection);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return foodSelection;
	}

	public static void removeFoodSelection(FoodSelection foodSelection) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		foodSelection = em.merge(foodSelection);
		em.remove(foodSelection);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
