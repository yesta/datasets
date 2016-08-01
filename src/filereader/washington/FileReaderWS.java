package filereader.washington;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import knowledge.washington.WSResource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import concept.ConceptUtil;
import sensor.Sensor;
import sensor.SensorEvent;
import sensor.SensorEventImp;
import sensor.SensorUtil;
import source.washington.WashingtonInfo;
import activity.Activity;
import activity.ActivityUtil;
import filereader.FileReader;

public class FileReaderWS implements FileReader {

	final protected static String SEPARATOR = "\t";
	final protected static int TIME_INDEX = 0;
	final protected static int SENSOR_ID_INDEX = 1;
	final protected static int SENSOR_VAL_INDEX = 2;
	final protected static int ACTIVITY_INDEX = 3;

	/**
	 * sensor knowledge
	 */
	private SensorUtil my_sensors;

	/**
	 * activity knowledge
	 */
	private ActivityUtil my_activities;

	/**
	 * constructor with sensor and activity knowledge
	 * 
	 * @param the_sensors
	 * @param the_activities
	 */
	public FileReaderWS(final SensorUtil the_sensors,
			final ActivityUtil the_activities) {
		my_sensors = the_sensors;
		my_activities = the_activities;
	}

	@Override
	public List<SensorEvent> readSensorData(String inputSensorFile)
			throws FileNotFoundException {
		List<SensorEvent> result = new ArrayList<SensorEvent>();
		Scanner sc = new Scanner(new File(inputSensorFile));
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] split = line.split(SEPARATOR);
			// System.out.println(split[SENSOR_VAL_INDEX]+":"+(split[SENSOR_VAL_INDEX].contains("OFF")));
			if (!split[SENSOR_VAL_INDEX].contains("OFF")) {
				// System.out.println(line);
				int sensor_id = -1;
				if (my_sensors.findSensor(split[SENSOR_ID_INDEX]) != null) {
					sensor_id = my_sensors.findSensor(split[SENSOR_ID_INDEX])
							.getId();
				}
				if (sensor_id >= 0) {
					DateTime sensor_time = processTime(split[TIME_INDEX]);
					result.add(new SensorEventImp(sensor_id, sensor_time
							.getMillis(), sensor_time.getMillis()));
				}
			}
		}
		return result;
	}

	protected static DateTime processTime(String a_time) {
		String[] date_time = a_time.split(" ");
		int year = Integer.parseInt(date_time[0].substring(0, 4));
		int month = Integer.parseInt(date_time[0].substring(5, 7));
		int date = Integer.parseInt(date_time[0].substring(8, 10));
		int hour = Integer.parseInt(date_time[1].substring(0, 2));
		int min = Integer.parseInt(date_time[1].substring(3, 5));
		int second = Integer.parseInt(date_time[1].substring(6, 8));
		return new DateTime(year, month, date, hour, min, second, 0,
				DateTimeZone.UTC);
	}

	/**
	 * initialise a map to record the status of each activity map<act id,
	 * begin/end>
	 * 
	 * @param the_actSize
	 * @return
	 */
	private Map<Integer, DateTime> actStatus(final int the_actSize) {
		Map<Integer, DateTime> actId_status = new HashMap<Integer, DateTime>();
		for (int i = 0; i < the_actSize; i++) {
			actId_status.put(i, null);
		}
		return actId_status;
	}

	public ActivityUtil getActNames(final String diary_file)
			throws FileNotFoundException {
		Set<String> names = new HashSet<String>();
		Scanner sc = new Scanner(new File(diary_file));
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.contains("begin") || line.contains("end")) {
				// System.out.println("got it: "+line);
				String act = getActName(line);
				if (act.contains("_"))
					names.add(act);
				// System.out.println("act name: "+act);
			}
		}
		ActivityUtil au = new ActivityUtil();
		int index = 0;
		for (String n : names) {
			System.out.println(index + ": " + n);
			au.addActivity(new Activity(index, n));
			index++;
		}
		return au;
	}

	private String getActName(final String line) {
		String act_name;
		String[] split = line.split(SEPARATOR);
		// DateTime time = processTime(split[TIME_INDEX]);
		if (split.length >= ACTIVITY_INDEX + 1) {
			act_name = split[ACTIVITY_INDEX];
		} else {
			try {
				// System.out.println("contains space rather than tab");
				act_name = split[SENSOR_VAL_INDEX].trim().split(" ")[1].trim()
						.isEmpty() ? split[SENSOR_VAL_INDEX].trim().split(" ")[2]
						: split[SENSOR_VAL_INDEX].trim().split(" ")[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				// System.err.println("catch: " + line);
				act_name = split[SENSOR_VAL_INDEX];
			}
		}
		if (act_name.contains("begin") || act_name.contains("end")) {
			act_name = act_name.split(" ")[0];
		}
		// if (!act_name.contains("R")) {
		// System.out.println(line);
		// }
		return act_name;
	}

	@Override
	public List<SensorEvent> readDairyData(String inputDairyFile)
			throws FileNotFoundException {
		List<SensorEvent> result = new ArrayList<SensorEvent>();
		Scanner sc = new Scanner(new File(inputDairyFile));
		// record the activity id and its status: begin = time; end = null;
		Map<Integer, DateTime> actId_status = actStatus(my_activities
				.getActivities().size());
		Random rd = new Random();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.contains("begin") || line.contains("end")) {
				String[] split = line.split(SEPARATOR);
				DateTime time = processTime(split[TIME_INDEX]);
				String act_name = getActName(line);
				// find activity
				int actId = -1;
				for (Activity act : my_activities.getActivities()) {
					if (act_name.contains(act.getActName())) {
						actId = act.getActId();
						break;
					}
				}
				if (actId < 0) {
					System.out.println("invalid name: " + act_name + " line: "
							+ line);
					// for (int i = 0; i < split.length; i++) {
					// System.out.println(i + ": " + split[i]);
					// }
					// String[] names =
					// split[SENSOR_VAL_INDEX].trim().split(" ");
					// for (int i = 0; i < names.length; i++) {
					// System.out.println(i + ": " + names[i]);
					// }
					// System.out.println("split again: " + act_name);
					// if (act_name.contains("begin") ||
					// act_name.contains("end")) {
					// act_name = act_name.split(" ")[0];
					// System.out.println("split again from begin: "
					// + act_name);
					// }
				}
				if (actId_status.containsKey(actId)) {
					if (actId_status.get(actId) != null) {
						// actId has started
						result.add(new SensorEventImp(actId, actId_status.get(
								actId).getMillis(), time.getMillis()));
						actId_status.put(actId, null);
					} else {
						actId_status.put(actId, time);
					}
				}
				// } else {
				// if (rd.nextInt(10) > 8)
				// System.out.println("no act: " + line);
			}
		}
		System.out.println("activities: " + result.size());
		for (SensorEvent se : result) {
			System.out.println(my_activities.findActivity(se.getSensorId())
					.getActName()
					+ ": "
					+ new DateTime(se.getStartTime()).toString()
					+ " - "
					+ new DateTime(se.getEndTime()).toString());
		}
		return result;
	}

	public void serialise(List<SensorEvent> data, final String exportFile)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(exportFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(data);
		oos.flush();
		oos.close();
		fos.close();
	}

	@Override
	public List<SensorEvent> unserialise(String serialisedFile)
			throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(serialisedFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		return (List<SensorEvent>) ois.readObject();
	}

	@Override
	public void readSensorData(String inputSensorFile, String exportFile)
			throws Exception {
		Scanner sc = new Scanner(new File(inputSensorFile));
		FileOutputStream fos = new FileOutputStream(new File(exportFile));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		int count = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] split = line.split(SEPARATOR);
			// for(int i=0; i<split.length; i++) {
			// System.out.println(i+" : "+split[i]);
			// }
			if (!split[SENSOR_VAL_INDEX].contains("OFF")) {
				// System.out.println(line);
				int sensor_id = -1;
				if (my_sensors.findSensor(split[SENSOR_ID_INDEX]) != null) {
					sensor_id = my_sensors.findSensor(split[SENSOR_ID_INDEX])
							.getId();
				}
				if (sensor_id >= 0) {
					// System.out.println("Add");
					DateTime sensor_time = processTime(split[TIME_INDEX]);
					oos.writeObject(new SensorEventImp(sensor_id, sensor_time
							.getMillis(), sensor_time.getMillis()));
					if (count++ % 1000 == 0) {
						oos.flush();
						oos.reset();
						// System.out.println(new
						// DateTime(sensor_time).toString());
					}
					// } else {
					// System.out.println(line);
				}
			}
		}
		System.out.println("sensor events: " + count);
		oos.close();
		fos.close();
	}

	@Override
	public List<SensorEvent> unserialiseEachEvent(String serialisedFile)
			throws IOException, ClassNotFoundException {
		List<SensorEvent> result = new ArrayList<SensorEvent>();
		FileInputStream fis = new FileInputStream(new File(serialisedFile));
		ObjectInputStream ois = new ObjectInputStream(fis);
		boolean stop = false;
		while (!stop) {
			try {
				result.add((SensorEvent) ois.readObject());
				Runtime.getRuntime().freeMemory();
			} catch (EOFException e) {
				stop = true;
			}
		}
		ois.close();
		fis.close();
		return result;
	}

	public static void main(String[] args) throws Exception {
		// final String line = "2009-08-24 00:00:00.000009	M046	OFF";
		// String[] split = line.split(SEPARATOR);
		// for(int i=0; i<split.length; i++) {
		// System.out.println(i+" : "+split[i]);
		// }
		// ActivityUtil acts = WashingtonInfo
		// .getActivities(WashingtonInfo.SOURCE_DAIRY_FILE);
		// System.out.println(acts.findActivity(3).getActName());
		ConceptUtil locations = WSResource.getLocations();
		// WashingtonInfo.getActivities(WashingtonInfo.SOURCE_DAIRY_FILE);

		SensorUtil sensors = WashingtonInfo.getSensors(locations);
		for (Sensor s : sensors.getSensors()) {
			System.out.println(s.getId() + "\t" + s.getName() + "\t"
					+ locations.findConcept(s.getLocation()).getName());
		}

		ActivityUtil activities = WashingtonInfo.getActivities(
				WashingtonInfo.SOURCE_DAIRY_FILE, locations);

		for (Activity a : activities.getActivities()) {
			System.out.println(a.getActName() + ": "
					+ locations.findConcept(a.getLocation().get(0)).getName());
		}

		 {
		 FileOutputStream fos = new FileOutputStream(
		 WashingtonInfo.META_DATA);
		 ObjectOutputStream oos = new ObjectOutputStream(fos);
		 oos.writeObject(locations);
		 oos.writeObject(sensors);
		 oos.writeObject(activities);
		 oos.close();
		 fos.close();
		 }
		 FileReaderWS fr = new FileReaderWS(sensors, activities);
		
		  fr.readSensorData(WashingtonInfo.SOURCE_SENSOR_FILE,
		  WashingtonInfo.SERIALISE_SENSOR_EVENTS);
		 fr.serialise(fr.readDairyData(WashingtonInfo.SOURCE_DAIRY_FILE),
		 WashingtonInfo.SERIALISE_DAIRY_EVENTS);

	}
}
