package filereader.placelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import knowledge.placelab.PlacelabResource;
import concept.ConceptUtil;
import sensor.Sensor;
import sensor.SensorUtil;
import source.placelab.PlacelabInfo;

public class MetadataReader {

	private static String rename(String nameWithSpace) {
		String new_name = "";
		if (nameWithSpace.contains(" ")) {
			String[] parts = nameWithSpace.split(" ");
			for (int i = 0; i < parts.length; i++) {
				new_name += parts[i];
				if (i != parts.length - 1) {
					new_name += "_";
				}
			}
		}
		return new_name;
	}

	private static String removeQuote(String nameWithSpace) {
		if (nameWithSpace.endsWith("\"")) {
			String name = nameWithSpace
					.substring(0, nameWithSpace.length() - 1);
			return name;
		} else {
			return nameWithSpace;
		}
	}

	public static SensorUtil parseMetaFile(final String file,
			final ConceptUtil locations, final ConceptUtil objectTypes)
			throws FileNotFoundException {
		SensorUtil sensors = new SensorUtil(locations, objectTypes);
		Scanner sc = new Scanner(new File(file));
		// Set<String> objects = new HashSet<String>();
		int typeId = -1;
		String id = "";
		String loc = "";
		String obj = "";
		String contains = "";
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			// if (line.contains("OM")) {
			// typeId = 0;
			// } else if (line.contains("SWITCH")) {
			// typeId = 17;
			// } else if (line.contains("RFID")) {
			// typeId = 99;
			// } else if (line.contains("MOTION")) {
			// typeId = 11;
			// }
			if (line.contains("RFID")) {
				typeId = 99;
			} else if (line.contains("MOTION")) {
				typeId = 11;
			} else if (line.contains("OM")) {
				typeId = 0;
			} else if (line.contains("SWITCH")) {
				 typeId = 17;
			}
			if (typeId >= 0 && line.contains("ID id=")) {
				id = line.substring(line.indexOf("\"") + 1,
						line.indexOf("/>") - 1).trim();
				id = removeQuote(id);
			}
			if (typeId >= 0 && typeId != 11 && line.contains("OBJECT text=")) {
				obj = line.substring(line.indexOf("\"") + 1,
						line.indexOf("/>") - 2).trim();
				obj = removeQuote(obj);
				if (obj.contains(" ")) {
					obj = rename(obj);
				}
			}
			if (typeId >= 0 && line.contains("LOCATION text=")) {
				loc = line.substring(line.indexOf("\"") + 1,
						line.indexOf("/>") - 1).trim();
				loc = removeQuote(loc);
				if (loc.contains(" ")) {
					loc = rename(loc);
				}
			}
			if (typeId >= 0 && contains.isEmpty()
					&& line.contains("CONTAINS text=")) {
				contains = line.substring(line.indexOf("\"") + 1,
						line.indexOf("/>") - 1).trim();
				contains = removeQuote(contains);
				if (contains.contains(" ")) {
					contains = rename(contains);
				}
				if (objectTypes.findConcept(contains) != null) {
					contains = objectTypes.findConcept(contains).getName();
				} else {
					contains = "";
				}
			}
			if (typeId >= 0 && line.contains("</SENSOR>")) {
				// System.out.println(id + " " + loc + " " + obj);
				if (!obj.contains("simulate")) {
					if (!contains.isEmpty()) {
						sensors.addSensorWithLocationObj(id, loc, contains);
					} else if (obj.contains("door")||typeId == 11 || obj.contains("left")
							|| obj.contains("right") || obj.contains("kitchen")) {
						sensors.addSensorWithLocationObj(id, loc, "internal_door");
					} else {
						sensors.addSensorWithLocationObj(id, loc, obj);
					}
				}
				// objects.add(obj);
				typeId = -1;
				id = "";
				loc = "";
				obj = "";
				contains = "";
			}
		}
		// System.out.println(objects.size() + "\t" + objects);
//		for (Sensor s : sensors.getSensors()) {
//			System.out.println(s.getId() + " " + s.getName() + " "
//					+ objectTypes.findConcept(s.getObject()).getName() + " "
//					+ locations.findConcept(s.getLocation()).getName());
//		}
//		System.out.println("total sensors: "+count);
		return sensors;
	}

//	public static void getMetaData(final String input_file,
//			final String serialisation_file) throws IOException {
//		ConceptUtil ou = Objects.getResources();
//		// for(Concept c: ou.getConcepts()) {
//		// c.print();
//		// }
//		ConceptUtil lu = Locations.getLocations();
//		// for(Concept c: lu.getConcepts()) {
//		// c.print();
//		// }
//		SensorUtil su = MetadataReader.parseMetaFile(input_file, lu, ou);
//		// for(Sensor s: su.getSensors()) {
//		// s.print();
//		// }
//		FileOutputStream fos = new FileOutputStream(serialisation_file);
//		ObjectOutputStream oos = new ObjectOutputStream(fos);
//		oos.writeObject(su);
//		oos.close();
//	}

	public static void main(String[] args) throws IOException {
		
		final ConceptUtil locations = PlacelabResource.getLocations();
		final ConceptUtil objects = PlacelabResource.getObjects();
		
		SensorUtil su = parseMetaFile(PlacelabInfo.SENSOR_METADATA_FILE, locations, objects);
		FileOutputStream fos = new FileOutputStream(PlacelabInfo.SERIALISE_METADATA_FILE);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(su);
		oos.close();
		fos.close();
		System.out.println(su.getSensors().size());
//		getMetaData(
//				"../../workspace-learning/semanticmining/data/placelab/PLObjects_oct23.xml",
//				"data/placelab/metadata");
//				"../faultysensors/data/placelab/metadata");
	}
}
