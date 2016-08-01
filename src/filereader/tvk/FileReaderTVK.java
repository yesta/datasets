package filereader.tvk;

/**
 * read all the tvk data
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import knowledge.tvk.TVKResource;

import org.joda.time.DateTime;

import concept.ConceptUtil;
import activity.Activity;
import activity.ActivityUtil;
import sensor.SensorEvent;
import sensor.SensorEventImp;
import sensor.SensorUtil;
import source.tvk.HouseAInfo;
import source.tvk.HouseBInfo;
import source.tvk.HouseCInfo;
import filereader.FileReader;

public class FileReaderTVK implements FileReader {
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
	public FileReaderTVK(final SensorUtil the_sensors,
			final ActivityUtil the_activities) {
		my_sensors = the_sensors;
		my_activities = the_activities;
	}

	/**
	 * activity
	 * 
	 * @param inputSensorFile
	 * @return
	 * @throws FileNotFoundException
	 */

	public List<SensorEvent> readSensorData(String inputSensorFile)
			throws FileNotFoundException {
		List<SensorEvent> result = new ArrayList<SensorEvent>();
		Scanner sc = new Scanner(new File(inputSensorFile));
		long last_time = 0;
		int last_aid = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] split = line.split("\t");
			if (line.contains("2008") || line.contains("2009")) {
				long st = getTime(split[0]).getMillis();
				long et = getTime(split[1]).getMillis();
				int act = Integer.parseInt(split[2].trim());
				if (inputSensorFile.contains("houseA")) {
					result.add(new SensorEventImp(my_sensors.findSensor(
							split[2].trim()).getId(), st));
				} else {
					if (last_time == 0) {
						result.add(new SensorEventImp(my_sensors.findSensor(
								split[2].trim()).getId(), st));
					} else if (!(last_aid == act && st - last_time <= 120 * 1000)) {
						result.add(new SensorEventImp(my_sensors.findSensor(
								split[2].trim()).getId(), st));
					}
				}
				if (et - st > 10 * 1000) {
					result.add(new SensorEventImp(my_sensors.findSensor(
							split[2].trim()).getId(), et));
				}
				last_time = et;
				last_aid = act;
			}
		}
		//test
//		System.out.println("test on sensor events: ");
//		for(int i=0; i< 10; i++) {
//			System.out.println(result.get(i).print());
//		}
		return result;
	}

	public List<SensorEvent> readDairyData(String inputDairyFile)
			throws FileNotFoundException {
		List<SensorEvent> result = new ArrayList<SensorEvent>();
		Scanner sc = new Scanner(new File(inputDairyFile));
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.contains("2008") || line.contains("2009")) {
				// 25-Feb-2008 00:22:46 25-Feb-2008 09:34:12 10
				String[] split = line.split("\t");
				DateTime start = getTime(split[0]);
				DateTime end = getTime(split[1]);
				Activity act = my_activities.findActivity(split[2].trim());
				// if (!activities.contains(split[2].trim())) {
				// }
				// find right sensor event whose activity period covers it.
				if (split[2].trim().equals("24")) {
					System.out.println(line);
				}
				if (act != null) {
					result.add(new SensorEventImp(act.getActId(), start
							.getMillis(), end.getMillis()));
				}
			}
		}
		// System.out.println(activities);
//		System.out.println("test on activity events: ");
//		for(int i=0; i< 3; i++) {
//			System.out.println(result.get(i).print());
//		}
		return result;
	}

	/**
	 * process time format in tvk data sets
	 * 
	 * @param a_dateTime
	 * @return
	 */
	private DateTime getTime(final String a_dateTime) {
		if (a_dateTime.contains("-") && a_dateTime.contains(":")) {
			String[] split = a_dateTime.split(" ");
			String[] split0 = split[0].split("-");
			String month = split0[1].toLowerCase().trim();
			int m = 2;
			if (month.equals("feb")) {
				m = 2;
			} else if (month.equals("mar")) {
				m = 3;
			} else if (month.equals("jul")) {
				m = 7;
			} else if (month.equals("aug")) {
				m = 8;
			} else if (month.equals("nov")) {
				m = 11;
			} else if (month.equals("dec")) {
				m = 12;
			}
			String[] split1 = split[1].split(":");
			DateTime dt = new DateTime(Integer.parseInt(split0[2]), m,
					Integer.parseInt(split0[0]), Integer.parseInt(split1[0]),
					Integer.parseInt(split1[1]), Integer.parseInt(split1[2]), 0);
			return dt;
		}
		return null;
	}

	/**
	 * 
	 * @param data
	 * @param exportFile
	 * @throws IOException
	 */
	@Override
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
	public void readSensorData(String inputSensorFile, String exportFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SensorEvent> unserialiseEachEvent(String serialisedFile)
			throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ConceptUtil locations = TVKResource.getLocations();
		ConceptUtil objects = TVKResource.getObjects();
		{
			FileReaderTVK fr = new FileReaderTVK(HouseAInfo.getSensors(
					locations, objects), HouseAInfo.getActivities(locations,
					objects));
			fr.serialise(fr.readSensorData(HouseAInfo.SOURCE_SENSOR_FILE), HouseAInfo.SERIALISE_SENSOR_EVENTS);
			fr.serialise(fr.readDairyData(HouseAInfo.SOURCE_DAIRY_FILE), HouseAInfo.SERIALISE_DAIRY_EVENTS);
		}
//		{
//			FileReaderTVK fr = new FileReaderTVK(HouseBInfo.getSensors(
//					locations, objects), HouseBInfo.getActivities(locations,
//					objects));
//			fr.serialise(fr.readSensorData(HouseBInfo.SOURCE_SENSOR_FILE), HouseBInfo.SERIALISE_SENSOR_EVENTS);
//			fr.serialise(fr.readDairyData(HouseBInfo.SOURCE_DAIRY_FILE), HouseBInfo.SERIALISE_DAIRY_EVENTS);
//		}
//		{
//			FileReaderTVK fr = new FileReaderTVK(HouseCInfo.getSensors(
//					locations, objects), HouseCInfo.getActivities(locations,
//					objects));
//			fr.serialise(fr.readSensorData(HouseCInfo.SOURCE_SENSOR_FILE), HouseCInfo.SERIALISE_SENSOR_EVENTS);
//			fr.serialise(fr.readDairyData(HouseCInfo.SOURCE_DAIRY_FILE), HouseCInfo.SERIALISE_DAIRY_EVENTS);
//		}
	}
}
