package source.tvk;

import activity.Activity;
import activity.ActivityUtil;
import sensor.SensorUtil;
import concept.ConceptUtil;

/**
 * 
 * @author juanye
 * 
 */
public class HouseAInfo {

	public static String SOURCE_SENSOR_FILE = "data/tvk/houseA-sensor.txt";

	public static String SOURCE_DAIRY_FILE = "data/tvk/houseA-activity.txt";

	public static String SERIALISE_SENSOR_EVENTS = "data/tvk/serialise/houseAsensor";

	public static String SERIALISE_DAIRY_EVENTS = "data/tvk/serialise/houseAActivity";

	/**
	 * get all the sensors
	 */
	public static SensorUtil getSensors(final ConceptUtil locations,
			final ConceptUtil objectTypes) {
		SensorUtil su = new SensorUtil(locations, objectTypes);
		su.addSensorWithLocationObj("1", "kitchen", "microwave");
//		su.addSensorWithLocationObj("5", "bathroom", "showerDoor");
		su.addSensorWithLocationObj("5", "bathroom", "plumbingFixture");
		su.addSensorWithLocationObj("6", "toilet", "plumbingFixture");
		su.addSensorWithLocationObj("7", "kitchen", "cup");
		su.addSensorWithLocationObj("8", "kitchen", "fridge");
		su.addSensorWithLocationObj("9", "kitchen", "plate");
		su.addSensorWithLocationObj("12", "fronthall", "exitDoor");
		su.addSensorWithLocationObj("13", "kitchen", "washer");
		su.addSensorWithLocationObj("14", "toilet", "toilet");
		su.addSensorWithLocationObj("17", "kitchen", "freezer");
		su.addSensorWithLocationObj("18", "kitchen", "pan");
		su.addSensorWithLocationObj("20", "house", "washingmachine");
		su.addSensorWithLocationObj("23", "kitchen", "grocery");
		su.addSensorWithLocationObj("24", "bedroom", "internalDoor");
		return su;
	}

	public static ActivityUtil getActivities(final ConceptUtil locations,
			final ConceptUtil objects) {
		ActivityUtil actUtil = new ActivityUtil();
		int index = 0;
		{
			Activity act = new Activity(index, "1");
			act.addLocation(locations.findConcept("fronthall").getId());
			act.addObject(objects.findConcept("exitDoor").getId());
//			act.setPeriod(3600,Integer.MAX_VALUE);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "4");
			act.addLocation(locations.findConcept("toilet").getId());
			act.addObject(objects.findConcept("showerDoor").getId());
			act.addObject(objects.findConcept("toilet").getId());
			act.setPeriod(0, 600);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "5");
			act.addLocation(locations.findConcept("bathroom").getId());
			act.addObject(objects.findConcept("showerDoor").getId());
			act.addObject(objects.findConcept("swing").getId());
			act.addObject(objects.findConcept("bathtub").getId());
			act.setPeriod(180, 1800);
			act.setStartTime(6, 17);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "10");
			act.addLocation(locations.findConcept("bedroom").getId());
			act.addObject(objects.findConcept("door").getId());
			act.addObject(objects.findConcept("bed").getId());
			// added for faulty sensor detection
//			act.setPeriod(5 * 60, Integer.MAX_VALUE);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "13");
			act.addLocation(locations.findConcept("kitchen").getId());
			act.addObject(objects.findConcept("fridge").getId());
			act.addObject(objects.findConcept("plate").getId());
//			act.setPeriod(0, 600);
			act.setStartTime(7, 12);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "15");
			act.addLocation(locations.findConcept("kitchen").getId());
			act.addObject(objects.findConcept("microwave").getId());
			act.addObject(objects.findConcept("pan").getId());
//			act.setPeriod(120, 3600);
			act.setStartTime(17, 22);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "17");
			act.addLocation(locations.findConcept("kitchen").getId());
			act.addObject(objects.findConcept("cup").getId());
			act.addObject(objects.findConcept("fridge").getId());
			actUtil.addActivity(act);
			index++;
		}
		System.out.println(index+" : "+ actUtil.getActivities().size());
		return actUtil;
	}

}
