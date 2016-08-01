package filereader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import sensor.SensorEvent;

public interface FileReader {
	/**
	 * read sensor data from an input file
	 * @param inputSensorFile source sensor data file
	 * @return a list of sensor events
	 * @throws FileNotFoundException 
	 */
	List<SensorEvent> readSensorData(final String inputSensorFile) throws FileNotFoundException;
	
	/**
	 * read dairy data from an input file 
	 * @param inputDairyFile source dairy data file
	 * @return a list of activity events
	 * @throws FileNotFoundException 
	 */
	List<SensorEvent> readDairyData(final String inputDairyFile) throws FileNotFoundException;
	
	/**
	 * serialise read events into an export file.
	 * @param data
	 * @throws IOException 
	 */
	void serialise(final List<SensorEvent> data, final String exportFile) throws IOException;
	/**
	 * read sensor events from a serialised file.
	 * @param serialisedFile
	 * @return
	 * @throws ClassNotFoundException 
	 */
	List<SensorEvent> unserialise(final String serialisedFile) throws IOException, ClassNotFoundException;
	/**
	 * read in each sensor event and serailse in the export file
	 * @param inputSensorFile
	 * @param exportFile
	 * @throws Exception 
	 */
	void readSensorData(final String inputSensorFile, final String exportFile) throws Exception;
	/**
	 * read each sensor event from a serialised file.
	 * @param serialisedFile
	 * @return
	 * @throws ClassNotFoundException 
	 */
	List<SensorEvent> unserialiseEachEvent(final String serialisedFile) throws IOException, ClassNotFoundException;
	
}
