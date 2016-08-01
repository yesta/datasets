package source.placelab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.joda.time.DateTime;

import activity.Activity;
import activity.ActivityUtil;
import sensor.SensorUtil;
import concept.ConceptUtil;
import filereader.placelab.MetadataReader;
import knowledge.Resource;

public class PlacelabInfo {

	public static String DIRECTORY = "data/placelab/";

	public static String SOURCE_DAIRY_FILE = "/dairy.xml";

	public static String SENSOR_METADATA_FILE = "data/placelab/PLObjects_oct23.xml";

	public static String SERIALISE_METADATA_FILE = "data/placelab/serialise/metadata";

	public static String SERIALISE_SENSOR_EVENTS = "data/placelab/serialise/sensorevents";

	public static String SERIALISE_DAIRY_EVENTS = "data/placelab/serialise2/activityevents";
	
	public static String SERIALISE_DAIRY_EVENTS2 = "data/placelab/serialise2/activityevents_more";

	final public static String[] activities = { "entering", "away",
			"undressed", "dressed", "ingredients", "computer", "eating",
			"drink", "phone", "food", "dishes", "adding", "garbage", "reading",
			"toilet", "dishwasher", "hands", "meal", "hygiene", "music", "tv",
			"paperwork", "working", "leisure", "cleaning", "laptop", "laundry" };

	final public static String[] files = { "08-23", "08-25", "09-05", "09-06",
			"09-08", "09-11", "09-14" };

	final public static DateTime[] start_time = {
			new DateTime(2006, 8, 23, 18, 12, 17, 0),
			new DateTime(2006, 8, 25, 22, 24, 5, 0),
			new DateTime(2006, 9, 5, 18, 29, 38, 0),
			new DateTime(2006, 9, 6, 17, 20, 6, 0),
			new DateTime(2006, 9, 8, 17, 20, 49, 0),
			new DateTime(2006, 9, 11, 17, 37, 22, 0),
			new DateTime(2006, 9, 14, 16, 21, 36, 0) };

	final public static DateTime[] end_time = {
			new DateTime(2006, 8, 23, 23, 59, 24, 0),
			new DateTime(2006, 8, 25, 23, 39, 9, 0),
			new DateTime(2006, 9, 5, 22, 28, 55, 0),
			new DateTime(2006, 9, 6, 23, 53, 58, 0),
			new DateTime(2006, 9, 8, 21, 59, 3, 0),
			new DateTime(2006, 9, 11, 22, 47, 46, 0),
			new DateTime(2006, 9, 14, 23, 59, 10, 0) };

	/**
	 * get all the sensors
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static SensorUtil getSensors(final ConceptUtil locations,
			final ConceptUtil objectTypes, final String directory)
			throws IOException, ClassNotFoundException {
		File f = new File(directory + SERIALISE_METADATA_FILE);
		if (f.exists()) {
			FileInputStream fis = new FileInputStream(directory
					+ SERIALISE_METADATA_FILE);
			ObjectInputStream ois = new ObjectInputStream(fis);
			SensorUtil su = (SensorUtil) ois.readObject();
			fis.close();
			ois.close();
			return su;
		} else {
			return MetadataReader.parseMetaFile(directory+SENSOR_METADATA_FILE,
					locations, objectTypes);
		}
	}

	public static ActivityUtil getActivities(final ConceptUtil locations,
			final ConceptUtil objects) {
		ActivityUtil actUtil = new ActivityUtil();
		int index = 0;
		{
			Activity act = new Activity(index, "phone");
			act.addLocation(locations.findConcept("house").getId());
			act.addObject(objects.findConcept("phoneUtensil").getId());
			// act.addObject(objects.findConcept("furniture").getId());
			actUtil.addActivity(act);
			index++;
		}
		 {//entering or leaving placelab
		 Activity act = new Activity(index, "placelab");
		 act.addLocation(locations.findConcept("house").getId());
		 act.addObject(objects.findConcept("front_door").getId());
		 // act.addObject(objects.findConcept("furniture").getId());
		 actUtil.addActivity(act);
		 index++;
		 }
		{
			Activity act = new Activity(index, "computer");
			act.addLocation(locations.findConcept("entertainingArea").getId());
			act.addObject(objects.findConcept("computer").getId());
			act.addObject(objects.findConcept("electronics").getId());
			act.setPeriod(0, 600);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "TV");
			act.addLocation(locations.findConcept("entertainingArea").getId());
			act.addObject(objects.findConcept("television").getId());
			act.addObject(objects.findConcept("remote_control").getId());
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "hygiene");
			act.addLocation(locations.findConcept("groomingArea").getId());
			act.addObject(objects.findConcept("cabinet").getId());
			act.addObject(objects.findConcept("internal_door").getId());
			act.addObject(objects.findConcept("faucet").getId());
			act.addObject(objects.findConcept("cabinet").getId());
			act.addObject(objects.findConcept("hair_dryer").getId());
			act.addObject(objects.findConcept("toilet").getId());
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "eating");
			act.addLocation(locations.findConcept("diningArea").getId());
			act.addObject(objects.findConcept("eatingUtensil").getId());
			act.addObject(objects.findConcept("food").getId());
			act.addObject(objects.findConcept("grocery").getId());
			act.addObject(objects.findConcept("table").getId());
			act.addObject(objects.findConcept("mug").getId());
			act.addObject(objects.findConcept("bowl").getId());
			act.addObject(objects.findConcept("cutting_board").getId());
			act.addObject(objects.findConcept("cookingIngredient").getId());
			act.addObject(objects.findConcept("food").getId());
			act.addObject(objects.findConcept("cabinet").getId());
			act.addObject(objects.findConcept("closet").getId());
			// act.addObject(objects.findConcept("restingFurniture").getId());
			actUtil.addActivity(act);
			index++;
		}
		// {
		// Activity act = new Activity(index, "garbage").getId());
		// act.addLocation(locations.findConcept("house").getId());
		// act.addObject(objects.findConcept("recycling_bin").getId());
		// act.addObject(objects.findConcept("trash_can").getId());
		// actUtil.addActivity(act);
		// index++;
		// }
		{
			Activity act = new Activity(index, "dressed");
			act.addLocation(locations.findConcept("sleepingArea").getId());
			act.addLocation(locations.findConcept("living_room").getId());
			act.addObject(objects.findConcept("clothes").getId());
			act.addObject(objects.findConcept("closet").getId());
			act.addObject(objects.findConcept("cabinet").getId());
			actUtil.addActivity(act);
			index++;
		}
		return actUtil;
	}
}
