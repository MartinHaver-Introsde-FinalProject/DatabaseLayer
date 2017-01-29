package resources;

import model.Activity;
import model.ActivitySelection;
import model.FoodSelection;
import model.Goal;
import model.HealthMeasure;
import model.HealthMeasureHistory;
import model.MeasureDefinition;
import model.Person;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless
@LocalBean
@Path("/api")
public class EHealthResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	/*
	 * Getting api information.
	 * 
	 * URL: http://localhost:8080/introsde.local-database-service/api
	 * 
	 * GET: OK
	 */
	
	@GET
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getInfo() {
		System.out.println("Getting api information...");
		return "Hello! This is Local database service by M.Haver";
	}
	

	/*
	 * Getting list of people existing in database.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/person")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getAll() {
		System.out.println("Getting list of people...");
		List<Person> people = Person.getAll();
		return people;
	}

	/*
	 * Getting the detail information of a Person identified by idPerson.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/person/{idPerson}")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person with idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		return person;
	}

	/*
	 * Getting the current Goal of a Person identified by idPerson.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goal
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/person/{idPerson}/goal")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Goal getCurrentGoalOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting current Person's Goal who is identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		for (Goal goal : person.getGoals()) {
			if (goal.getCurrent() == 1) {
				return goal;
			}
		}
		return null;
	}

	/*
	 * Getting the list of the Goal histories of a Person identified by idPerson
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goalHistory
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/person/{idPerson}/goalHistory")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Goal> getGoalHistoryOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person's Goal history who is identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		List<Goal> goals = new ArrayList<Goal>();
		for (Goal goal : person.getGoals()) {
			if (goal.getCurrent() == 0) {
				goals.add(goal);
			}
		}
		return goals;
	}

	/*
	 * Getting the list of Health Measures of a Person identified by idPerson.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/healthMeasure
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/person/{idPerson}/healthMeasure")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<HealthMeasure> getHealthMeasureOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person's Health Measure who is identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		return person.getHealthMeasures();
	}

	/*
	 * Getting the list of Health Measure Histories of a Person identified by idPerson.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/healthMeasureHistory
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/person/{idPerson}/healthMeasureHistory")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<HealthMeasureHistory> getHealthMeasureHistoryOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person's Health Measure History who is identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		return person.getHealthMeasuresHistories();
	}

	/*
	 * Getting the current Food Selection of current Goal of a Person identified by idPerson.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goal/foodSelection
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/person/{idPerson}/goal/foodSelection")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public FoodSelection getFoodSelectionOfCurrentGoalOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting the current Food Selection of current Goal of a Person identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		for (Goal goal : person.getGoals()) {
			if (goal.getCurrent() == 1) {
				for (FoodSelection foodSelection : goal.getFoodSelections()) {
					if (foodSelection.getCurrent() == 1) {
						return foodSelection;
					}
				}
			}
		}
		return null;
	}

	/*
	 * Getting the current Activity Selection of current Goal of a Person identified by idPerson.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goal/activitySelection
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/person/{idPerson}/goal/activitySelection")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ActivitySelection getActivitySelectionOfCurrentGoalOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting the current Activity Selection of current Goal of a Person identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		for (Goal goal : person.getGoals()) {
			if (goal.getCurrent() == 1) {
				for (ActivitySelection activitySelection : goal.getActivitySelections()) {
					if (activitySelection.getCurrent() == 1) {
						return activitySelection;
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * Getting the list of activities existing in database.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/activity
	 * 
	 * GET: OK
	 */
	
	
	@GET
	@Path("/activity")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Activity> getAll(@Context UriInfo ui) {
		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		List<Activity> activities = null;
		if (queryParams.size() != 0) {
			String activityType = queryParams.getFirst("activityType");
			System.out.println("Getting list of activities with activity type = " + activityType + "...");
			activities = new ArrayList<Activity>();
			for (Activity activity : Activity.getAll()) {
				if (activity.getActivityType().equalsIgnoreCase(activityType))
					activities.add(activity);
			}
		} else {
			System.out.println("Getting list of activities...");
			activities = Activity.getAll();
		}
		return activities;
	}
	
	/*
	 * Getting the detail information of a activity identified by idActivity.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/activity/1
	 * 
	 * GET: OK
	 */

	@GET
	@Path("/activity/{idActivity}")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Activity getActivityById(@PathParam("idActivity") int idActivity) {
		System.out.println("Getting Activity with id = " + idActivity + "...");
		Activity activity = Activity.getActivityById(idActivity);
		return activity;
	}
	
	/*
	 * Creating new Goal without any Food Selection and Activity Selection for a Person identified by idPerson
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goal
	 * 
	 * POST: OK
	 * 
	 * { "idGoal": 0, "goalName": "Lose weight 0.5 kg per week 3", "current": 1,
	 * "idealWeight": 65, "date": "06-12-2016", "idealBmi": 22,
	 * "shavedCalories": null, "foodSelections": null, "activitySelections":
	 * null }
	 */
	
	@POST
	@Path("/person/{idPerson}/goal")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveGoalForPerson(@PathParam("idPerson") int idPerson, Goal goal) throws IOException {
		System.out.println( "Creating a new Goal with empty Food Selection and Activity Selection for Person who is identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		for (Goal g : person.getGoals()) {
			if (g.getCurrent() == 1) {
				g.setCurrent(0);
			}
		}
		goal.setPerson(person);
		person.getGoals().add(goal);
		person = person.updatePerson(person);
		return person;
	}

	/*
	 * Creating new Health Measure belonging to a Health Measure Type and putting the old Health Measure into Health Measure History for a Person identified by idPerson.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/healthMeasure/weight
	 * 
	 * POST: OK
	 *
	 * { "idHealthMeasure": 1, "value": "68", "measureDefinition": null }
	 */

	@POST
	@Path("/person/{idPerson}/healthMeasure/{healthMeasureType}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveHealthMeasureForPerson(@PathParam("idPerson") int idPerson, @PathParam("healthMeasureType") String healthMeasureType, HealthMeasure healthMeasure) throws IOException {
		System.out.println("Creating new Health Measure for person with id = " + idPerson + " and with Health Measure Type = " + healthMeasureType + "...");
		Person person = Person.getPersonById(idPerson);

		for (HealthMeasure hm : person.getHealthMeasures()) {
			if (hm.getMeasureDefinition().getMeasureName().equalsIgnoreCase(healthMeasureType)) {
				HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();
				healthMeasureHistory.setIdHealthMeasureHistory(0);
				healthMeasureHistory.setMeasureDefinition(hm.getMeasureDefinition());
				healthMeasureHistory.setPerson(person);
				healthMeasureHistory.setTimestamp(this.getDateTime());
				healthMeasureHistory.setValue(hm.getValue());
				person.getHealthMeasuresHistories().add(healthMeasureHistory);
				person.getHealthMeasures().remove(hm);
				break;
			}
		}

		for (MeasureDefinition measureDefinition : MeasureDefinition.getAll()) {
			if (measureDefinition.getMeasureName().equalsIgnoreCase(healthMeasureType)) {
				healthMeasure.setMeasureDefinition(measureDefinition);
				break;
			}
		}
		healthMeasure.setPerson(person);
		person.getHealthMeasures().add(healthMeasure);
		person = person.updatePerson(person);
		return person;
	}

	/*
	 * Creating new Food Selection for current Goal of a Person
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goal/foodSelection
	 * 
	 * POST: OK
	 * 
	 * { "idFoodSelection": 0, "label": "Thai Chicken Salad", "image":
	 * "https://www.edamam.com/web-img/c5c/c5c51c310b92f9f47c5a34ce7af6328b.jpg",
	 * "calories": 956, "weight": 637, "ingredients":
	 * "1: Little gem lettuce leaves and cucumber batons to serve (20.0);2: 2 garlic cloves , finely chopped (6.0);3: A small handful mint and coriander leaves (7.98);4: 3.0 tbsp lime juice (46.03094);5: 1 stalk lemon grass , woody outer leaves removed, finely chopped (20.0);6: 2 red chilli , finely chopped (0.0);7: 100.0ml chicken stock (101.4456);8: 400.0g minced chicken (buy minced or whizz chicken thigh fillets in a food processor) (400.0);9: A thumb-sized piece root ginger , grated (0.001);10: 2.0 tbsp fish sauce (36.0);"
	 * , "current": 1 }
	 */

	@POST
	@Path("/person/{idPerson}/goal/foodSelection")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveGoalForPerson(@PathParam("idPerson") int idPerson, FoodSelection foodSelection)
			throws IOException {
		System.out.println("Creating new food selection for person with id = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		for (Goal goal : person.getGoals()) {
			if (goal.getCurrent() == 1) {
				for (FoodSelection fs : goal.getFoodSelections()) {
					if (fs.getCurrent() == 1) {
						fs.setCurrent(0);
					}
				}
				foodSelection.setGoal(goal);
				goal.getFoodSelections().add(foodSelection);
			}
		}
		person = person.updatePerson(person);
		return person;
	}

	/*
	 * Creating new Activity Selection for current Goal of a Person
	 *
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goal/activitySelection
	 * 
	 * POST: OK
	 * 
	 * { "idActivity": 4, "activityName": "Running, 5 mph 12 min/mile",
	 * "caloriesPerHour": "455", "activityType": "Running" }
	 */

	@POST
	@Path("/person/{idPerson}/goal/activitySelection")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveGoalForPerson(@PathParam("idPerson") int idPerson, Activity activity) throws IOException {
		System.out.println("Creating new Activity Selection for current Goal of a Person with id = " + idPerson
				+ " and with activity id = " + activity.getIdActivity() + "...");
		Person person = Person.getPersonById(idPerson);
		for (Goal goal : person.getGoals()) {
			if (goal.getCurrent() == 1) {
				for (ActivitySelection fs : goal.getActivitySelections()) {
					if (fs.getCurrent() == 1) {
						fs.setCurrent(0);
					}
				}

				ActivitySelection activitySelection = new ActivitySelection();
				activitySelection.setActivity(activity);
				activitySelection.setCurrent(1);
				activitySelection.setGoal(goal);
				activitySelection.setIdActivitySelection(0);
				activitySelection.setTime(0); // two hour do this activity
				activitySelection.setUsedCalories(0); // two hour * rate of
														// calories to be
														// used in one hour
				goal.getActivitySelections().add(activitySelection);

			}
		}
		person = person.updatePerson(person);
		return person;
	}

	/*
	 * Updating Activity Selection (time, usedCalories)
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goal/activitySelection
	 * 
	 * PUT: OK
	 * 
	 * { "time": 2, "usedCalories": 1000 }
	 */

	@PUT
	@Path("/person/{idPerson}/goal/activitySelection")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person saveGoalForPerson(@PathParam("idPerson") int idPerson, ActivitySelection activitySelection)
			throws IOException {
		System.out.println("Updating a current Activity Selection of a current Goal of a Person with id = " + idPerson
				+ " and with Activity Selection id = " + activitySelection.getIdActivitySelection() + "...");
		Person person = Person.getPersonById(idPerson);
		for (Goal goal : person.getGoals()) {
			if (goal.getCurrent() == 1) {
				for (ActivitySelection fs : goal.getActivitySelections()) {
					if (fs.getCurrent() == 1) {
						fs.setTime(activitySelection.getTime());
						fs.setUsedCalories(activitySelection.getUsedCalories());
					}
				}
			}
		}
		person = person.updatePerson(person);
		return person;
	}

	/*
	 * Updating the Goal.
	 * 
	 * http://localhost:8080/introsde.local-database-service/api/person/1/goal
	 * 
	 * PUT: OK
	 * 
	 * { "shavedCalories": 600 }
	 */

	@PUT
	@Path("/person/{idPerson}/goal")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person updateGoalForPerson(@PathParam("idPerson") int idPerson, Goal goal) throws IOException {
		System.out.println("Updating used calories for current Goal of a Person with id = " + idPerson);
		Person person = Person.getPersonById(idPerson);
		for (Goal tmGoal : person.getGoals()) {
			if (tmGoal.getCurrent() == 1) {
				tmGoal.setShavedCalories(goal.getShavedCalories());
			}
		}
		person = person.updatePerson(person);
		return person;
	}
	
	
	@GET
	@Path("/database")
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String generateDatabase() {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	    	File file = new File("lifecoach.sqlite");
	    	if (file.exists()) {
	    		file.delete();
	    	}
	    	
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:lifecoach.sqlite");
	      System.out.println("Opened database successfully");
			stmt = c.createStatement();
			String sql = "DROP TABLE IF EXISTS \"Activity\";"
					+ "CREATE TABLE \"Activity\" (\"idActivity\" INTEGER PRIMARY KEY  NOT NULL ,\"activityName\" TEXT,\"caloriesPerHour\" DOUBLE DEFAULT (null) ,\"activityType\" TEXT);"
					+ "INSERT INTO \"Activity\" VALUES(1,'Jogging, general',390,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(2,'Jogging in place',455,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(3,'Jogging on a mini-tramp',228,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(4,'Running, 5 mph 12 min/mile',455,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(5,'Running, 5.2 mph 11.5 min/mile',520,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(6,'Running, 6 mph 10 min/mile',585,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(7,'Running, 6.7 mph 9 min/mile',650,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(8,'Running, 7 mph 8.5 min/mile',683,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(9,'Running, 7.5 mph 8 min/mile',748,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(10,'Running, 8 mph 7.5 min/mile',813,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(11,'Running, 8.6 mph 7 min/mile',845,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(12,'Running, 9 mph 6.5 min/mile',910,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(13,'Running, 10 mph 6 min/mile',975,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(14,'Running, 10.9 mph 5.5 min/mile',1105,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(15,'Running up stairs',910,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(16,'Running, on a track, team practice',585,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(17,'Running, training, pushing a wheelchair',455,'Running');"
					+ "INSERT INTO \"Activity\" VALUES(18,'Bicycling, BMX or mountain',488,'Bicycling');"
					+ "INSERT INTO \"Activity\" VALUES(19,'Bicycling, leisure Slower than 10 mph, to work or for pleasure',195,'Bicycling');"
					+ "INSERT INTO \"Activity\" VALUES(20,'Bicycling, general',455,'Bicycling');"
					+ "INSERT INTO \"Activity\" VALUES(21,'Bicycling, leisure, slow, light effort',325,'Bicycling');"
					+ "INSERT INTO \"Activity\" VALUES(22,'Bicycling, leisure, moderate effort',455,'Bicycling');"
					+ "INSERT INTO \"Activity\" VALUES(23,'Bicycling, racing or leisure, fast, vigorous effort',585,'Bicycling');"
					+ "INSERT INTO \"Activity\" VALUES(24,'Bicycling, racing very fast from 16 to 19 mph not drafting, or faster than 19 mph drafting',715,'Bicycling');"
					+ "INSERT INTO \"Activity\" VALUES(25,'Bicycling, racing extremely fast > 20 mph',975,'Bicycling');"
					+ "INSERT INTO \"Activity\" VALUES(26,'Bicycling, stationary, moderate effort',390,'Conditioning exercise');"
					+ "INSERT INTO \"Activity\" VALUES(27,'Calisthenics, heavy, vigorous effort including pushups, situps, pullups, jumping jacks',455,'Conditioning exercise');"
					+ "INSERT INTO \"Activity\" VALUES(28,'Rowing, stationary, moderate effort',390,'Conditioning exercise');"
					+ "INSERT INTO \"Activity\" VALUES(29,'Ballet or modern, twist, jazz, tap, jitterbug',247,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(30,'Aerobic, general',358,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(31,'Aerobic, step using a 6 to 8 inch step',488,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(32,'Aerobic, step using a 10 to 12 inch step',585,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(33,'Aerobic, low impact',260,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(34,'Aerobic, high impact',390,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(35,'General dancing including Greek, Middle Eastern, hula, flamenco, belly, and swing dancing',228,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(36,'Ballroom, dancing fast',293,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(37,'Ballroom, fast Disco, folk, square, line dancing, Irish step dancing, polka, contra, country',228,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(38,'Ballroom, slow Waltz, foxtrot, slow dancing, samba, tango, 19th century, mambo, chacha',130,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(39,'Traditional American Indian dancing including Anishinaabe Jingle Dancing',293,'Dancing');"
					+ "INSERT INTO \"Activity\" VALUES(40,'Archery (non-hunting)',170,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(41,'Badminton, competitive',480,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(42,'Badminton, social single and doubles, general',238,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(43,'Basketball, game',476,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(44,'Basketball, non-game, general',340,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(45,'Basketball, officiating',480,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(46,'Billiards',102,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(47,'Bowling',136,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(48,'Boxing, punching bag',340,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(49,'Football, touch, flag, general',476,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(50,'Tennis, general',408,'Sports');"
					+ "INSERT INTO \"Activity\" VALUES(51,'Climbing hills',408,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(52,'Walking, less than 2.0 mph, level ground, strolling, very slow',68,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(53,'Walking, 2.0 mph, level, slow pace, firm surface',102,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(54,'Walking, 2.5 mph, firm surface',136,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(55,'Walking, 2.5 mph, downhill',122,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(56,'Walking, 3.0 mph, level, moderate pace, firm surface',156,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(57,'Walking, 3.5 mph, level, brisk, firm surface, walking for exercise',190,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(58,'Walking, 3.5 mph, uphill',340,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(59,'Walking, 4.0 mph, level, firm surface, very brisk pace',272,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(60,'Walking, 4.5 mph, level, firm surface, very, very brisk',360,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(61,'Walking, 5.0 mph',476,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(62,'Walking, to work or class',204,'Walking');"
					+ "INSERT INTO \"Activity\" VALUES(63,'Swimming laps, freestyle, fast, vigorous effort',612,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(64,'Swimming laps, freestyle, slow, moderate or light effort',408,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(65,'Swimming, backstroke, general',408,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(66,'Swimming, breaststroke, general',612,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(67,'Swimming, butterfly, general',680,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(68,'Swimming, crawl, fast (75 yards/minute), vigorous effort',680,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(69,'Swimming, crawl, slow (50 yards/minute), moderate or light effort',476,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(70,'Swimming, lake, ocean, river',340,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(71,'Swimming, treading water, fast vigorous effort',612,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(72,'Swimming, treading water, moderate effort, general',204,'Water activities');"
					+ "INSERT INTO \"Activity\" VALUES(73,'Treadmill, fairly light effort (RPE 11)',456,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(74,'Treadmill, somewhat hard effort (RPE 13)',612,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(75,'Treadmill, hard effort (RPE 15)',741,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(76,'Cycle ergometry, fairly light effort (RPE 11)',320,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(77,'Cycle ergometry, somewhat hard effort (RPE 13)',408,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(78,'Cycle ergometry, hard effort (RPE 15)',503,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(79,'Rowing ergometry, fairly light effort (RPE 11)',415,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(80,'Rowing ergometry, somewhat hard effort (RPE 13)',503,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(81,'Rowing ergometry, hard effort (RPE 15)',619,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(82,'Stair stepper, fairly light effort (RPE 11)',415,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(83,'Stair stepper, somewhat hard effort (RPE 13)',524,'Indoor exercise machines');"
					+ "INSERT INTO \"Activity\" VALUES(84,'Stair stepper, hard effort (RPE 15)',626,'Indoor exercise machines');"
					+ "DROP TABLE IF EXISTS \"ActivitySelection\";"
					+ "CREATE TABLE \"ActivitySelection\" (\"idActivitySelection\" INTEGER PRIMARY KEY  NOT NULL ,\"current\" INTEGER,\"time\" TEXT,\"usedCalories\" DOUBLE DEFAULT (null) ,\"idGoal\" INTEGER, \"idActivity\" INTEGER);"
					+ "DROP TABLE IF EXISTS \"FoodSelection\";"
					+ "CREATE TABLE \"FoodSelection\" (\"idFoodSelection\" INTEGER PRIMARY KEY  NOT NULL ,\"label\" TEXT,\"image\" TEXT,\"calories\" DOUBLE DEFAULT (null) ,\"weight\" DOUBLE DEFAULT (null) ,\"ingredients\" TEXT,\"idGoal\" INTEGER,\"current\" INTEGER);"
					+ "DROP TABLE IF EXISTS \"Goal\";"
					+ "CREATE TABLE \"Goal\" (\"idGoal\" INTEGER PRIMARY KEY  NOT NULL ,\"goalName\" TEXT,\"idPerson\" INTEGER,\"current\" INTEGER,\"idealBmi\" DOUBLE DEFAULT (null) ,\"idealWeight\" DOUBLE DEFAULT (null) ,\"date\" TEXT DEFAULT (null) ,\"shavedCalories\" DOUBLE DEFAULT (null) );"
					+ "DROP TABLE IF EXISTS \"HealthMeasure\";"
					+ "CREATE TABLE \"HealthMeasure\" (\"idHealthMeasure\" INTEGER PRIMARY KEY  DEFAULT (null) ,\"idMeasureDefinition\" INTEGER DEFAULT ('NULL') ,\"idPerson\" INTEGER DEFAULT ('NULL') ,\"value\" DOUBLE DEFAULT (null) );"
					+ "INSERT INTO \"HealthMeasure\" VALUES(1,1,1,90);"
					+ "INSERT INTO \"HealthMeasure\" VALUES(2,2,1,1.65);"
					+ "INSERT INTO \"HealthMeasure\" VALUES(3,3,1,54);"
					+ "INSERT INTO \"HealthMeasure\" VALUES(4,4,1,33.057851239669425);"
					+ "INSERT INTO \"HealthMeasure\" VALUES(5,5,1,1666.25);"
					+ "DROP TABLE IF EXISTS \"HealthMeasureHistory\";"
					+ "CREATE TABLE \"HealthMeasureHistory\" (\"idHealthMeasureHistory\" INTEGER PRIMARY KEY  DEFAULT (null) ,\"idPerson\" INTEGER DEFAULT ('NULL') ,\"idMeasureDefinition\" INTEGER DEFAULT ('NULL') ,\"value\" DOUBLE DEFAULT (null) ,\"timestamp\" TEXT DEFAULT ('NULL') );"
					+ "DROP TABLE IF EXISTS \"MeasureDefinition\";"
					+ "CREATE TABLE \"MeasureDefinition\" (\"idMeasureDefinition\" INTEGER PRIMARY KEY  DEFAULT (null) ,\"measureName\" TEXT DEFAULT ('NULL') ,\"measureType\" TEXT DEFAULT ('NULL') );"
					+ "INSERT INTO \"MeasureDefinition\" VALUES(1,'weight','double');"
					+ "INSERT INTO \"MeasureDefinition\" VALUES(2,'height','double');"
					+ "INSERT INTO \"MeasureDefinition\" VALUES(3,'age','integer');"
					+ "INSERT INTO \"MeasureDefinition\" VALUES(4,'bmi','double');"
					+ "INSERT INTO \"MeasureDefinition\" VALUES(5,'bmr','double');"
					+ "DROP TABLE IF EXISTS \"MeasureRange\";"
					+ "CREATE TABLE \"MeasureRange\" (\"idMeasureRange\" INTEGER PRIMARY KEY  DEFAULT (null) ,\"idMeasureDefinition\" INTEGER DEFAULT ('NULL') ,\"rangeName\" TEXT DEFAULT ('NULL') ,\"startValue\" DOUBLE DEFAULT (null) ,\"endValue\" DOUBLE DEFAULT (null) ,\"alarmLevel\" INTEGER DEFAULT ('NULL') );"
					+ "INSERT INTO \"MeasureRange\" VALUES(1,4,'Overweight',25.01,30,1);"
					+ "INSERT INTO \"MeasureRange\" VALUES(2,4,'Obesity',30.01,NULL,2);"
					+ "INSERT INTO \"MeasureRange\" VALUES(3,4,'Normal weight',20.01,25,0);"
					+ "INSERT INTO \"MeasureRange\" VALUES(4,4,'Underweight',NULL,20,1);"
					+ "DROP TABLE IF EXISTS \"Person\";"
					+ "CREATE TABLE \"Person\" (\"idPerson\" INTEGER PRIMARY KEY ,\"firstname\" TEXT DEFAULT ('NULL') ,\"lastname\" TEXT DEFAULT ('NULL') ,\"birthdate\" TEXT DEFAULT ('NULL') ,\"username\" TEXT DEFAULT ('NULL') ,\"sex\" INTEGER DEFAULT (null) );"
					+ "INSERT INTO \"Person\" VALUES(1,'Chuck','Norris','1945-01-01 00:00:00','chuck.norris',1);"
					+ "INSERT INTO \"Person\" VALUES(2,'Pallino','Pinco','1982-06-08 18:00:00','pinco',1);"
					+ "INSERT INTO \"Person\" VALUES(3,'Pappo','Pippo','1990-01-01 00:00:00','pippo',0);"
					+ "DROP TABLE IF EXISTS \"SEQUENCE\";"
					+ "CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT NUMBER(19), PRIMARY KEY (SEQ_NAME));"
					+ "INSERT INTO \"SEQUENCE\" VALUES('SEQ_GEN',4);";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      return (e.getClass().getName() + ": " + e.getMessage());
	    }
	    return ("Table created successfully");
	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = dateFormat.format(new Date());
		return date;
}
	
}
