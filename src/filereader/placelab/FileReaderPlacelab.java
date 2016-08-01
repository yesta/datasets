package filereader.placelab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import concept.ConceptUtil;
import knowledge.placelab.PlacelabResource;
import activity.Activity;
import activity.ActivityUtil;
import sensor.Sensor;
import sensor.SensorEvent;
import sensor.SensorEventImp;
import sensor.SensorUtil;
import source.placelab.PlacelabInfo;
import filereader.FileReader;

public class FileReaderPlacelab implements FileReader {

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
	public FileReaderPlacelab(final SensorUtil the_sensors,
			final ActivityUtil the_activities) {
		my_sensors = the_sensors;
		my_activities = the_activities;
	}

	@Override
	public List<SensorEvent> readSensorData(String inputSensorFile)
			throws FileNotFoundException {
		Map<Long, Integer> sensordata = new HashMap<Long, Integer>();
		translateOMSensor(sensordata, inputSensorFile + "/type_0.txt",
				my_sensors);
		translateOMSensor(sensordata, inputSensorFile + "/type_11.txt",
				my_sensors);
		translateSensor17(sensordata, inputSensorFile + "/type_17.txt",
				my_sensors);
		translateSensorRFID(sensordata, inputSensorFile + "/type_99.txt",
				my_sensors);
		List<SensorEvent> events = combine(my_sensors, sensordata);
		return events;
	}

	@Override
	public List<SensorEvent> readDairyData(String inputDairyFile)
			throws FileNotFoundException {
		List<SensorEvent> diary = new ArrayList<SensorEvent>();
		Scanner sc = new Scanner(new File(inputDairyFile));
		int startFlag = 0;
		// long timestamp = 0, endTime = 0, time = start.getMillis();
		long act_start = 0, act_end = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (!line.contains("context") && line.contains("ENDTIME")) {
				startFlag = 1;
				act_start = convertTime(line)[0];
				act_end = convertTime(line)[1];
				// System.out.println(new
				// DateTime(act_start).toString()+" - "+new DateTime(act_end));
				// if (act_start > act_end)
				// break;
			}
			if (startFlag > 0 && line.contains("VALUE LABEL=") &&!line.contains("miscellaneous")
					&& line.contains("activity")) {
				// String name = line.substring(line.indexOf("\""),
				// line.indexOf("CATEGORY") - 1);
				String name = line
						.substring(line.indexOf("\""),
								line.indexOf("CATEGORY") - 1).toLowerCase()
						.trim();
				// Activity situId = activities.findActivity(name);
				Activity situ = my_activities.findActivity(name);
				// int situId = activities.findActivity(name).getActId();
				if (situ == null) {
					name = line
							.substring(line.indexOf("SUBCATEGORY=") + 12,
									line.indexOf("/>") - 1).trim()
							.toLowerCase();
					situ = my_activities.findActivity(name);
				}
				if (situ != null) {
					// System.out.println(new
					// DateTime(act_start).toString()+" - "+new
					// DateTime(act_end).toString()+" : "+situ.getActId());
					diary.add(new SensorEventImp(situ.getActId(), act_start,
							act_end));
				} else {
					System.out.println("situ name: " + name);
				}
				// System.out.println(name);
				// if(name.contains("preparing a drink"))
				// System.out.println(" YES preparing "+situId);

				// System.out.println(" act name "+name+" "+situId.getActId());
				// if(startFlag>0 && line.contains("</LABEL>")) startFlag=0;
			}
		}
		return diary;
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

	private void translateOMSensor(Map<Long, Integer> sensor_data,
			final String sensor_file, final SensorUtil sensors)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(sensor_file));
		int count = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			count++;
			// Time: 1156377378820 08/23/2006 23:56:18.820 Channel: 0 ID: 708
			// Type: 0 Data: 0
			String[] split = line.split(" ");
			String sId = split[8].trim();
			int val = Integer.parseInt(split[12].trim());
			if (val > 5) {
				Sensor s = sensors.findSensor(sId);
				if (s != null) {
					// sensor data time is one hour ahead of actual time
					long time = Long.parseLong(split[1].trim()) - 60 * 60 * 1000;
					sensor_data.put(time, s.getId());
				}
			}
		}
		System.out.println("om: " + count);
	}

	// type: 17 - switch
	private void translateSensor17(Map<Long, Integer> synchronisation,
			final String sensor_file, final SensorUtil sensors)
			throws FileNotFoundException {
		Map<String, Integer> id_val = new HashMap<String, Integer>();
		Scanner sc = new Scanner(new File(sensor_file));
		int count = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			count++;
			// Time: 1156371233450 08/23/2006 18:13:53 Computer ID: 0 Sensor ID:
			// E00700001E1F7274
			String[] split = line.split(" ");
			String sId = split[10].trim();
			sId = sId.substring(0, sId.length() - 2);
			int val = (int) Double.parseDouble(split[13].trim());
			val = (val % 200 == 0) ? 0 : 1;
			if (id_val.containsKey(sId)) {
				if (id_val.get(sId) != val) {
					// System.out.println("at: "+line+"\n"+sId+" switch sensor data changes from "+id_val.get(sId)+" to "+val);
					Sensor s = sensors.findSensor(sId);
					if (s != null) {
						long time = Long.parseLong(split[1].trim()) - 5 * 60
								* 60 * 1000;
						synchronisation.put(time, s.getId());
					}
				}
			}
			id_val.put(sId, val);
		}
		System.out.println("17: " + count);
	}

	// type 99: RFID
	private void translateSensorRFID(Map<Long, Integer> synchronisation,
			final String sensor_file, final SensorUtil sensors)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(sensor_file));
		int count = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			count++;
			// type 0: Time: 1156356006841 08/23/2006 18:00:06.841 Channel: 0
			// ID: 297 Type: 0 Data: 0
			String[] split = line.split(" ");
			String sId = split[10].trim();
			Sensor s = sensors.findSensor(sId);
			if (s != null) {
				// rfid sensor data is 5 hours ahead of actual time
				long time = Long.parseLong(split[1].trim()) - 5 * 60 * 60
						* 1000;
				synchronisation.put(time, s.getId());
			}
		}
		System.out.println("rfid: " + count);
	}

	private List<SensorEvent> combine(SensorUtil sensors,
			Map<Long, Integer> original_sensor_data) {
		List<SensorEvent> result = new ArrayList<SensorEvent>();
		Long[] times = new Long[original_sensor_data.size()];
		original_sensor_data.keySet().toArray(times);
		Arrays.sort(times);
		for (int i = 1; i < times.length; i++) {
			if (original_sensor_data.get(times[i - 1]) != original_sensor_data
					.get(times[i])) {
				result.add(new SensorEventImp(original_sensor_data
						.get(times[i]), times[i]));
			}
		}
		return result;
	}

	private long[] convertTime(String input) {
		String[] result = new String[2];
		// SimpleDateFormat dateFormat = new SimpleDateFormat(
		// "MM/dd/yyyy  HH:mm:ss");
		int date = input.indexOf("DATE=");
		result[0] = input.substring(date + 6, date + 16);
		result[1] = input.substring(date + 6, date + 16);
		int starttime = input.indexOf("STARTTIME=");
		result[0] += "  " + input.substring(starttime + 11, input.indexOf("."));
		int endtime = input.indexOf("ENDTIME");
		result[1] += "  " + input.substring(endtime + 9, endtime + 17);
		// System.out.println(result[0]+" - "+result[1]);
		long[] time = new long[2];
		for (int i = 0; i < result.length; i++) {
			// sensorTime = DairyTime + 5 hours;
			try {
				time[i] = new java.util.Date(result[i]).getTime();
			} catch (IllegalArgumentException e) {
				System.out.println(result[i]);
				System.exit(1);
			}
		}
		return time;
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

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		final ConceptUtil locations = PlacelabResource.getLocations();
		final ConceptUtil objects = PlacelabResource.getObjects();
		 SensorUtil su = null;
		
		{
			FileInputStream fis = new FileInputStream(PlacelabInfo.SERIALISE_METADATA_FILE);
			ObjectInputStream ois =new ObjectInputStream(fis);
			su = (SensorUtil)ois.readObject();
			fis.close();
			ois.close();
		}
		{
			FileReaderPlacelab fr = new FileReaderPlacelab(
					su,DiaryReader.getActivities());
//					PlacelabInfo.getActivities(locations, objects));
//			List<SensorEvent> sensorData = new ArrayList<SensorEvent>();
//			for (int i = 0; i < PlacelabInfo.files.length; i++) {
//				sensorData.addAll(fr.readSensorData(PlacelabInfo.DIRECTORY+PlacelabInfo.files[i]));
//				Runtime.getRuntime().freeMemory();
//			}
//			System.out.println(sensorData.size());
//			for(SensorEvent se: sensorData) {
//				System.out.println(se.print());
//			}
//			fr.serialise(sensorData, PlacelabInfo.SERIALISE_SENSOR_EVENTS);
			List<SensorEvent> dairyData = new ArrayList<SensorEvent>();
			for (int i = 0; i < PlacelabInfo.files.length; i++) {
				System.out.println("file: "+PlacelabInfo.DIRECTORY+PlacelabInfo.files[i]+PlacelabInfo.SOURCE_DAIRY_FILE);
				dairyData.addAll(fr.readDairyData(PlacelabInfo.DIRECTORY+PlacelabInfo.files[i]+PlacelabInfo.SOURCE_DAIRY_FILE));
				Runtime.getRuntime().freeMemory();
			}
			System.out.println(dairyData.size());
			for(SensorEvent se: dairyData) {
				System.out.println(se.print());
			}
			fr.serialise(dairyData, PlacelabInfo.SERIALISE_DAIRY_EVENTS2);
		}

	}
}
