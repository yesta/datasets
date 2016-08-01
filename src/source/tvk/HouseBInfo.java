package source.tvk;

import activity.Activity;
import activity.ActivityUtil;
import sensor.SensorUtil;
import concept.ConceptUtil;

public class HouseBInfo {

	public static String SOURCE_SENSOR_FILE = "data/tvk/houseB-sensor.txt";

	public static String SOURCE_DAIRY_FILE = "data/tvk/houseB-activity.txt";

	public static String SERIALISE_SENSOR_EVENTS = "data/tvk/serialise/houseBsensor";

	public static String SERIALISE_DAIRY_EVENTS = "data/tvk/serialise/houseBActivity";

	public static SensorUtil getSensors(final ConceptUtil locations,
			final ConceptUtil objectTypes) {
		SensorUtil su = new SensorUtil(locations, objectTypes);
		su.addSensorWithLocationObj("1", "bathroom", "internalDoor");
		su.addSensorWithLocationObj("3", "kitchen", "fridge");
		su.addSensorWithLocationObj("5", "kitchen", "grocery");
		su.addSensorWithLocationObj("6", "toilet", "toilet");
		su.addSensorWithLocationObj("7", "fronthall", "exitDoor");
		su.addSensorWithLocationObj("9", "kitchen", "plate");
		su.addSensorWithLocationObj("10", "bedroom", "internalDoor");
		su.addSensorWithLocationObj("12", "bedroom", "bed");
		su.addSensorWithLocationObj("13", "bedroom", "bed");
		su.addSensorWithLocationObj("14", "kitchen", "crockery");
		su.addSensorWithLocationObj("15", "kitchen", "stove");
		su.addSensorWithLocationObj("16", "bedroom", "bed");
		su.addSensorWithLocationObj("18", "bedroom", "dresser");
		su.addSensorWithLocationObj("19", "bathroom", "bathtub");
		// piano chair
		su.addSensorWithLocationObj("20", "livingroom", "chair");
		su.addSensorWithLocationObj("21", "bathroom", "sink");
		// chair server
		su.addSensorWithLocationObj("22", "livingroom", "chair");
		su.addSensorWithLocationObj("24", "balcony", "internalDoor");
		su.addSensorWithLocationObj("25", "house", "window");
		su.addSensorWithLocationObj("26", "kitchen", "toaster");
		su.addSensorWithLocationObj("27", "kitchen", "microwave");
		su.addSensorWithLocationObj("28", "kitchen", "internalDoor");
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
			act.addObject(objects.findConcept("keydrawer").getId());
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "4");
			act.addLocation(locations.findConcept("toilet").getId());
			act.addObject(objects.findConcept("door").getId());
			act.addObject(objects.findConcept("toilet").getId());
			act.setPeriod(0, 300);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "5");
			act.addLocation(locations.findConcept("bathroom").getId());
			act.addObject(objects.findConcept("door").getId());
			act.addObject(objects.findConcept("swing").getId());
			act.addObject(objects.findConcept("bathtub").getId());
			act.setPeriod(180, 1800);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "6");
			act.addLocation(locations.findConcept("groomingArea").getId());
			act.addObject(objects.findConcept("toothbrush").getId());
			act.setPeriod(150, 900);
			act.setStartTime(6, 10);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "10");
			act.addLocation(locations.findConcept("bedroom").getId());
			act.addObject(objects.findConcept("door").getId());
			act.addObject(objects.findConcept("bed").getId());
			act.setPeriod(60, 24*3600);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "13");
			act.addLocation(locations.findConcept("kitchen").getId());
			act.addObject(objects.findConcept("fridge").getId());
			act.addObject(objects.findConcept("plate").getId());
			act.setStartTime(7, 12);
			act.setPeriod(120, 1800);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "15");
			act.addLocation(locations.findConcept("kitchen").getId());
			act.addObject(objects.findConcept("microwave").getId());
			act.addObject(objects.findConcept("pan").getId());
			act.setPeriod(120, 6000);
			act.setStartTime(16, 22);
			actUtil.addActivity(act);
			index++;
		}
		{
			Activity act = new Activity(index, "17");
			act.addLocation(locations.findConcept("kitchen").getId());
			act.addObject(objects.findConcept("cup").getId());
			act.addObject(objects.findConcept("fridge").getId());
			actUtil.addActivity(act);
			act.setPeriod(0, 300);
			index++;
		}
		return actUtil;
	}
}
