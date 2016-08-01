package source.tvk;

import activity.ActivityUtil;
import sensor.Sensor;
import sensor.SensorUtil;
import concept.ConceptUtil;

public class HouseCInfo {
	
	public static String SOURCE_SENSOR_FILE = "data/tvk/houseC-sensor.txt";

	public static String SOURCE_DAIRY_FILE = "data/tvk/houseC-activity.txt";

	public static String SERIALISE_SENSOR_EVENTS = "data/tvk/serialise/houseCsensor";

	public static String SERIALISE_DAIRY_EVENTS = "data/tvk/serialise/houseCActivity";

	public static SensorUtil getSensors(final ConceptUtil locations,
			final ConceptUtil objectTypes) {
		SensorUtil su = new SensorUtil(locations, objectTypes);
		su.addSensorWithLocationObj("22", "kitchen", "bin");
		su.addSensorWithLocationObj("35", "bathroom", "bathtub");
		su.addSensorWithLocationObj("36", "bedroom", "dresser");
		su.addSensorWithLocationObj("38", "toilet", "toilet");
		su.addSensorWithLocationObj("39", "bedroom", "bed");
		su.addSensorWithLocationObj("18", "kitchen", "crockery");
		su.addSensorWithLocationObj("23", "kitchen", "plate");
		su.addSensorWithLocationObj("15", "fronthall", "keydrawer");
		su.addSensorWithLocationObj("25", "toilet", "toilet");
		su.addSensorWithLocationObj("16", "bathroom", "swing");
		su.addSensorWithLocationObj("27", "kitchen", "cup");
		su.addSensorWithLocationObj("28", "fronthall", "exitDoor");
		su.addSensorWithLocationObj("29", "bedroom", "internalDoor");
		su.addSensorWithLocationObj("30", "kitchen", "fridge");
		su.addSensorWithLocationObj("5", "bedroom", "bed");
		su.addSensorWithLocationObj("6", "livingroom", "chair");
		su.addSensorWithLocationObj("7", "kitchen", "freezer");
		su.addSensorWithLocationObj("8", "toilet", "toilet");
		su.addSensorWithLocationObj("10", "toilet", "toilet");
		// @TODO CHANGE
		su.addSensorWithLocationObj("11", "bathroom", "swing");
		// su.addSensorWithLocationObj("10", "toilet", "toilet");
		su.addSensorWithLocationObj("13", "kitchen", "crockery");
		su.addSensorWithLocationObj("20", "kitchen", "pan");
		su.addSensorWithLocationObj("21", "kitchen", "microwave");
//		for(Sensor s: su.getSensors()) {
//			System.out.println(s.print());
//		}
		return su;
	}

	public static ActivityUtil getActivities(final ConceptUtil locations,
			final ConceptUtil objectTypes) {
		return HouseBInfo.getActivities(locations, objectTypes);
	}
}
