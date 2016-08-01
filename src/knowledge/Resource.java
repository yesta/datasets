package knowledge;

import concept.ConceptUtil;

public interface Resource {
	/**
	 * get all the objects that sensors can define on
	 * @return
	 */
	ConceptUtil getObjects();
	/**
	 * get all the locations that sensors can define on
	 * @return
	 */
	ConceptUtil getLocations();

}
