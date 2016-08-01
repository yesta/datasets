package source.washington;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.joda.time.DateTime;

import activity.Activity;
import activity.ActivityUtil;
import sensor.SensorEvent;
import sensor.SensorEventImp;
import sensor.SensorUtil;
import concept.ConceptUtil;
import filereader.washington.FileReaderWS;

public class WashingtonInfo {

	public static String SOURCE_SENSOR_FILE = "data/washington/washington";

	public static String SOURCE_DAIRY_FILE = "data/washington/washington";

	public static String SERIALISE_ACTIVITIES = "data/washington/serialise/activities";
	
	public static String META_DATA = "data/washington/serialise/meta";

	public static String SERIALISE_SENSOR_EVENTS = "data/washington/serialise/sensorevents";

	public static String SERIALISE_DAIRY_EVENTS = "data/washington/serialise/activityevents";

	public static SensorUtil getSensors(final ConceptUtil locations) {
		SensorUtil su = new SensorUtil(locations);
		su.addSensorWithTypeLocation("p001", "electricity", "house");// electricity
																		// usage
		su.addSensorWithTypeLocation("M043", "event", "hallwayS");
		su.addSensorWithTypeLocation("M044", "event", "bedroomleft");
		su.addSensorWithTypeLocation("M045", "event", "bedroomleft");
		su.addSensorWithTypeLocation("M046", "event", "sleepAreaL");
		su.addSensorWithTypeLocation("M047", "event", "sleepAreaL");
		su.addSensorWithTypeLocation("M048", "event", "workAreaL");
		su.addSensorWithTypeLocation("M049", "event", "workAreaL");
		su.addSensorWithTypeLocation("M050", "event", "bedroomleft");

		su.addSensorWithTypeLocation("M027", "event", "hallwayS");
		su.addSensorWithTypeLocation("M028", "event", "hallwayS");// 10
		su.addSensorWithTypeLocation("M029", "event", "hallwayS");

		su.addSensorWithTypeLocation("M042", "event", "hallwayS");

		su.addSensorWithTypeLocation("M030", "event", "hallwayS");
		su.addSensorWithTypeLocation("M031", "event", "workAreaR");
		su.addSensorWithTypeLocation("M032", "event", "workAreaR");
		su.addSensorWithTypeLocation("M033", "event", "workAreaR");
		su.addSensorWithTypeLocation("M034", "event", "sleepAreaR");
		su.addSensorWithTypeLocation("M035", "event", "sleepAreaR");
		su.addSensorWithTypeLocation("M036", "event", "bedroomright");

		su.addSensorWithTypeLocation("M037", "event", "hallwayS");// 20
		su.addSensorWithTypeLocation("M038", "event", "basin");
		su.addSensorWithTypeLocation("M039", "event", "bath");// 22
		su.addSensorWithTypeLocation("M040", "event", "bath");
		su.addSensorWithTypeLocation("M041", "event", "toilet");

		su.addSensorWithTypeLocation("M001", "event", "livingRoom");
		su.addSensorWithTypeLocation("M002", "event", "livingRoom");
		su.addSensorWithTypeLocation("M003", "event", "livingRoom");
		su.addSensorWithTypeLocation("M004", "event", "livingRoom");
		su.addSensorWithTypeLocation("M005", "event", "livingRoom");
		su.addSensorWithTypeLocation("M006", "event", "tvArea");
		su.addSensorWithTypeLocation("M007", "event", "tvArea");
		su.addSensorWithTypeLocation("M008", "event", "livingRoom");
		su.addSensorWithTypeLocation("M009", "event", "livingRoom");
		su.addSensorWithTypeLocation("M010", "event", "livingRoom");
		su.addSensorWithTypeLocation("M011", "event", "livingRoom");
		su.addSensorWithTypeLocation("M012", "event", "livingRoom");
		su.addSensorWithTypeLocation("M013", "event", "diningArea");
		su.addSensorWithTypeLocation("M014", "event", "diningArea");
		su.addSensorWithTypeLocation("M015", "event", "livingRoom");

		su.addSensorWithTypeLocation("M016", "event", "kitchen");
		su.addSensorWithTypeLocation("M017", "event", "kitchen");
		su.addSensorWithTypeLocation("M018", "event", "kitchen");

		su.addSensorWithTypeLocation("M051", "event", "kitchen");

		su.addSensorWithTypeLocation("M019", "event", "fronthall");
		su.addSensorWithTypeLocation("M020", "event", "fronthall");// 46
		su.addSensorWithTypeLocation("M024", "event", "fronthall");
		su.addSensorWithTypeLocation("M025", "event", "fronthall");

		su.addSensorWithTypeLocation("M026", "event", "stairsF");

		su.addSensorWithTypeLocation("M021", "event", "hallwayS");// 50
		su.addSensorWithTypeLocation("M022", "event", "hallwayS");
		su.addSensorWithTypeLocation("M023", "event", "hallwayS");

		su.addSensorWithTypeLocation("D001", "event", "fronthall");
		su.addSensorWithTypeLocation("D007", "event", "fronthall");
		su.addSensorWithTypeLocation("D002", "event", "livingRoom");
		su.addSensorWithTypeLocation("D011", "event", "kitchen");
		su.addSensorWithTypeLocation("D004", "event", "bedroomleft");
		su.addSensorWithTypeLocation("D003", "event", "bedroombottom");
		su.addSensorWithTypeLocation("D005", "event", "bathroom");
		su.addSensorWithTypeLocation("D006", "event", "toilet");// 60
		su.addSensorWithTypeLocation("D008", "event", "kitchen");
		su.addSensorWithTypeLocation("D009", "event", "kitchen");
		su.addSensorWithTypeLocation("D010", "event", "kitchen");
		su.addSensorWithTypeLocation("D012", "event", "left");
		su.addSensorWithTypeLocation("D014", "event", "kitchen");
		su.addSensorWithTypeLocation("D015", "event", "kitchen");
		su.addSensorWithTypeLocation("D016", "event", "kitchen");
		su.addSensorWithTypeLocation("D013", "event", "livingRoom");

		su.addSensorWithTypeLocation("L001", "event", "bedroomleft");
		su.addSensorWithTypeLocation("L002", "event", "bedroomright");// 70
		su.addSensorWithTypeLocation("L003", "event", "hallwayS");
		su.addSensorWithTypeLocation("L004", "event", "bedroombottom");
		su.addSensorWithTypeLocation("L005", "event", "basin");
		su.addSensorWithTypeLocation("L006", "event", "bath");
		su.addSensorWithTypeLocation("L007", "event", "toilet");
		su.addSensorWithTypeLocation("L008", "event", "livingRoom");
		su.addSensorWithTypeLocation("L009", "event", "fronthall");
		su.addSensorWithTypeLocation("L010", "event", "kitchen");
		su.addSensorWithTypeLocation("L011", "event", "fronthall");

		su.addSensorWithTypeLocation("I011", "event", "livingRoom");
		su.addSensorWithTypeLocation("I012", "event", "livingRoom");
		su.addSensorWithTypeLocation("I006", "event", "kitchen");
		su.addSensorWithTypeLocation("I010", "event", "kitchen");

		return su;
	}

	/**
	 * retrieve all the activitynames from the diaryFile
	 * 
	 * @param dairyFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ActivityUtil getActivities(String dairyFile,
			final ConceptUtil the_locations) {
		FileReaderWS fr = new FileReaderWS(null, null);
		try {
			ActivityUtil au = fr.getActNames(dairyFile);
			for (Activity act : au.getActivities()) {
				if (act.getActName().contains("Housekeeping")) {
					act.addLocation(the_locations.findConcept("livingRoom")
							.getId());
				} else if (act.getActName().contains("Eating")) {
					act.addLocation(the_locations.findConcept("livingRoom")
							.getId());
				} else if (act.getActName().contains("R1_Sleep")
						|| (act.getActName().contains("R1_Wandering_in_room") || (act
								.getActName().contains("R1_Work")))) {
					act.addLocation(the_locations.findConcept("bedroomleft")
							.getId());
				} else if (act.getActName().contains("R2_Sleep")
						|| (act.getActName().contains("R2_Wandering_in_room") || (act
								.getActName().contains("R2_Work")))) {
					act.addLocation(the_locations.findConcept("bedroomright")
							.getId());
				} else if (act.getActName().contains("Meal_Preparation")) {
					act.addLocation(the_locations.findConcept("kitchen")
							.getId());
				} else if (act.getActName().contains("Watch_TV")) {
					act.addLocation(the_locations.findConcept("tvArea").getId());
				} else if (act.getActName().contains("Personal_Hygiene")) {
					act.addLocation(the_locations.findConcept("bathroom")
							.getId());
				} else if (act.getActName().contains("Bathing")) {
					act.addLocation(the_locations.findConcept("bath").getId());
				} else if (act.getActName().contains("Home")) {
					act.addLocation(the_locations.findConcept("fronthall")
							.getId());
				} else if (act.getActName().contains("Transition")) {
					act.addLocation(the_locations.findConcept("hallwayS").getId());
				}
			}
			return au;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		// Set<String> actNames = new HashSet<String>();
		// Scanner sc = null;
		// // String dairyFile = WashingtonInfo.SOURCE_DAIRY_FILE;
		// try {
		// sc = new Scanner(new File(dairyFile));
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// final int ACTIVITY_INDEX = 3;
		// final String SEPARATOR = "\t";
		// // record the activity id and its status: begin = time; end = null;
		// if (sc != null) {
		// while (sc.hasNextLine()) {
		// String line = sc.nextLine();
		// String[] split = line.split(SEPARATOR);
		// if (split.length == ACTIVITY_INDEX + 1) {
		// String actName = split[ACTIVITY_INDEX];
		// String[] act_parts = actName.split(" ");
		// actNames.add(act_parts[0]);
		// }
		// }
		// }
		// ActivityUtil actUtil = new ActivityUtil();
		// int index = 0;
		// for (String actN : actNames) {
		// System.out.println(index+": "+actN);
		// Activity act = new Activity(index++, actN);
		// actUtil.addActivity(act);
		// }
		// System.out.println("activity number: "+index);
		// FileOutputStream fos = new FileOutputStream(SERIALISE_ACTIVITIES);
		// ObjectOutputStream oos = new ObjectOutputStream(fos);
		// oos.writeObject(obj);
	}

	public static ActivityUtil getActivities(ConceptUtil the_locations) {
		ActivityUtil actUtil = new ActivityUtil();
		int index = 0;
		{// 0
			Activity act = new Activity(index++, "Housekeeping");
			act.addLocation(the_locations.findConcept("livingRoom").getId());
			actUtil.addActivity(act);
		}
		{// 1
			Activity act = new Activity(index++, "Eating");
			act.addLocation(the_locations.findConcept("diningArea").getId());
			actUtil.addActivity(act);
		}
		{// 3
			Activity act = new Activity(index++, "R1_Sleep");
			act.addLocation(the_locations.findConcept("bedroomleft").getId());
			actUtil.addActivity(act);
		}
		{// 4
			Activity act = new Activity(index++, "R2_Sleep");
			act.addLocation(the_locations.findConcept("bedroomright").getId());
			actUtil.addActivity(act);
		}
		{// 6
			Activity act = new Activity(index++, "R1_Wandering_in_room");
			act.addLocation(the_locations.findConcept("bedroomleft").getId());
			actUtil.addActivity(act);
		}

		{// 7
			Activity act = new Activity(index++, "R2_Wandering_in_room");
			act.addLocation(the_locations.findConcept("bedroomright").getId());
			actUtil.addActivity(act);
		}
		{// 8
			Activity act = new Activity(index++, "R1_Work");
			act.addLocation(the_locations.findConcept("bedroomleft").getId());
			actUtil.addActivity(act);
		}
		{// 9
			Activity act = new Activity(index++, "R2_Work");
			act.addLocation(the_locations.findConcept("bedroomright").getId());
			actUtil.addActivity(act);
		}
		{// 10
			Activity act = new Activity(index++, "Meal_Preparation");
			act.addLocation(the_locations.findConcept("kitchen").getId());
			actUtil.addActivity(act);
		}
		{// 12
			Activity act = new Activity(index++, "Watch_TV");
			act.addLocation(the_locations.findConcept("tvArea").getId());
			actUtil.addActivity(act);
		}
		{// 11
			Activity act = new Activity(index++, "Personal_Hygiene");
			act.addLocation(the_locations.findConcept("bathroom").getId());
			// act.addLocation(the_locations.findConcept("bath").getId());
			actUtil.addActivity(act);
		}

		{// 5
			Activity act = new Activity(index++, "Bathing");
			act.addLocation(the_locations.findConcept("bath").getId());
			actUtil.addActivity(act);
		}
		{// 2
			Activity act = new Activity(index++, "Home");
			act.addLocation(the_locations.findConcept("fronthall").getId());
			actUtil.addActivity(act);
		}

		return actUtil;
	}
}
