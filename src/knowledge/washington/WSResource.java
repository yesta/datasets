package knowledge.washington;

import concept.ConceptUtil;
import concept.hierarchical.TreeConceptUtilImp;
import knowledge.Resource;

public class WSResource{

	public static ConceptUtil getObjects() {
		return null;
	}

	public static ConceptUtil getLocations() {
		ConceptUtil cu = new TreeConceptUtilImp();
		cu.addConcept("house", null);
		cu.addConcept("firstFloor", "house");
		cu.addConcept("secondFloor", "house");
		cu.addConcept("livingRoom", "firstFloor");
		cu.addConcept("hallwayF", "firstFloor");
		cu.addConcept("hallwayS", "secondFloor");
		cu.addConcept("diningArea", "livingRoom");
		cu.addConcept("tvArea", "livingRoom");
		cu.addConcept("left", "firstFloor");
		cu.addConcept("fronthall", "left");
		cu.addConcept("stairsF", "left");
		cu.addConcept("stairsS", "secondFloor");
		cu.addConcept("kitchen", "firstFloor");
		cu.addConcept("bedroomleft", "secondFloor");
		cu.addConcept("doorAreaL", "bedroomleft");
		cu.addConcept("sleepAreaL", "bedroomleft");
		cu.addConcept("workAreaL", "bedroomleft");
		cu.addConcept("bedroomright", "secondFloor");
		cu.addConcept("bedroombottom", "secondFloor");
		cu.addConcept("doorAreaR", "bedroomright");
		cu.addConcept("sleepAreaR", "bedroomright");
		cu.addConcept("workAreaR", "bedroomright");
		cu.addConcept("bathroom", "secondFloor");
		cu.addConcept("bathroomDoor", "bathroom");
		cu.addConcept("toilet", "bathroom");
		cu.addConcept("bath", "bathroom");
		cu.addConcept("basin", "bathroom");
		return cu;
	}

}
