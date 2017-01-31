package resources;

import model.Activity;
import model.ActivitySelection;
import model.Goal;
import model.HealthMeasure;
import model.HealthMeasureHistory;
import model.MeasureDefinition;
import model.Person;
import java.io.IOException;
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
@Path("/database")
public class EHealthResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	//Get API info
	@GET
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getInfo() {
		System.out.println("Getting api information...");
		return "Hello! This database service is part of a project by M.Haver";
	}
	
	// Getting list of people from the database.
	@GET
	@Path("/person")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getAll() {
		System.out.println("Getting list of people...");
		List<Person> people = Person.getAll();
		return people;
	}
	
	// Getting the detail information of a Person identified by idPerson.
	@GET
	@Path("/person/{idPerson}")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Person getPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person with idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		return person;
	}

	// Getting the current Goal of a Person identified by idPerson.
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

	// Getting the list of the Goal histories of a Person identified by idPerson
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

	//Getting the list of Health Measures of a Person identified by idPerson.
	@GET
	@Path("/person/{idPerson}/healthMeasure")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<HealthMeasure> getHealthMeasureOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person's Health Measure who is identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		return person.getHealthMeasures();
	}

	// Getting the list of Health Measure Histories of a Person identified by idPerson.
	@GET
	@Path("/person/{idPerson}/healthMeasureHistory")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<HealthMeasureHistory> getHealthMeasureHistoryOfPersonById(@PathParam("idPerson") int idPerson) {
		System.out.println("Getting Person's Health Measure History who is identified by idPerson = " + idPerson + "...");
		Person person = Person.getPersonById(idPerson);
		return person.getHealthMeasuresHistories();
	}

	//Getting the current Activity Selection of current Goal of a Person identified by idPerson.
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
	
	//Getting the list of activities existing in database.
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
	
	//Getting the detail information of a activity identified by idActivity.
	@GET
	@Path("/activity/{idActivity}")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Activity getActivityById(@PathParam("idActivity") int idActivity) {
		System.out.println("Getting Activity with id = " + idActivity + "...");
		Activity activity = Activity.getActivityById(idActivity);
		return activity;
	}
	
	//Creating new Goal without any Food Selection and Activity Selection for a Person identified by idPerson
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

	//Creating new Health Measure belonging to a Health Measure Type and putting the old Health Measure into Health Measure History for a Person identified by idPerson.
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

	//Creating new Activity Selection for current Goal of a Person
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

	//Updating Activity Selection (time, usedCalories)
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

	//Updating the Goal.
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
	
	// utility method
	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = dateFormat.format(new Date());
		return date;
	}
}
