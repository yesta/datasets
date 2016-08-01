package knowledge.tvk;

import concept.ConceptUtil;
import concept.ConceptUtilImp;
import concept.hierarchical.TreeConceptUtilImp;
import knowledge.Resource;

public abstract class TVKResource {

	public static ConceptUtil getObjects() {
		ConceptUtil cu = new TreeConceptUtilImp();
		cu.addConcept("physicalEntity", null);
		cu.addConcept("artefact", "physicalEntity");
		cu.addConcept("article", "artefact");
		cu.addConcept("commodity", "artefact");
		cu.addConcept("consumerGoods", "commodity");
		// cu.addConcept("grocery", "consumerGoods");
		cu.addConcept("durable", "consumerGoods");
		cu.addConcept("appliance", "durable");
		cu.addConcept("homeAppliance", "appliance");
		cu.addConcept("kitchenAppliance", "homeAppliance");
		cu.addConcept("toaster", "kitchenAppliance");
		cu.addConcept("microwave", "kitchenAppliance");
		cu.addConcept("stove", "kitchenAppliance");
		cu.addConcept("fridge", "kitchenAppliance");
		cu.addConcept("freezer", "kitchenAppliance");
		cu.addConcept("whiteGoods", "homeAppliance");
		cu.addConcept("washingmachine", "whiteGoods");
		cu.addConcept("washer", "whiteGoods");
		cu.addConcept("fixture", "artefact");
		cu.addConcept("plumbingFixture", "fixture");
		cu.addConcept("showerDoor", "plumbingFixture");
		cu.addConcept("toilet", "plumbingFixture");
		cu.addConcept("toothbrush", "plumbingFixture");
		cu.addConcept("sink", "plumbingFixture");
		cu.addConcept("bathtub", "plumbingFixture");
		cu.addConcept("swing", "plumbingFixture");
		cu.addConcept("instrumentality", "article");
		cu.addConcept("furnishing", "instrumentality");
		cu.addConcept("furniture", "furnishing");
		cu.addConcept("bedroomFurniture", "furniture");
		cu.addConcept("bed", "bedroomFurniture");
		cu.addConcept("dresser", "bedroomFurniture");
		cu.addConcept("livingroomFurniture", "furniture");
		cu.addConcept("chair", "livingroomFurniture");
		cu.addConcept("keydrawer", "livingroomFurniture");
		cu.addConcept("container", "instrumentality");
		cu.addConcept("bin", "container");
		cu.addConcept("ware", "article");
		cu.addConcept("tableware", "ware");
		cu.addConcept("crockery", "tableware");
		cu.addConcept("flatware", "tableware");
		cu.addConcept("cup", "crockery");
		// cu.addConcept("plate", "flatware");
		cu.addConcept("implement", "instrumentality");
		cu.addConcept("utensil", "implement");
		cu.addConcept("kitchenUtensil", "utensil");
		cu.addConcept("cookingUtensil", "kitchenUtensil");
		cu.addConcept("pan", "cookingUtensil");
		cu.addConcept("grocery", "cookingUtensil");
		cu.addConcept("plate", "cookingUtensil");
		cu.addConcept("structure", "artefact");
		cu.addConcept("obstruction", "structure");
		cu.addConcept("movableBarrier", "obstruction");
		cu.addConcept("door", "movableBarrier");
		cu.addConcept("movableBarrier", "door");
		cu.addConcept("exitDoor", "door");
		cu.addConcept("internalDoor", "door");
		cu.addConcept("window", "movableBarrier");
		return cu;
	}

	public static ConceptUtil getLocations() {
		ConceptUtil cu = new TreeConceptUtilImp();
		{// 0
			cu.addConcept("house", null);
		}
		{// 1
			cu.addConcept("kitchen", "house");
		}
		{// 2
			cu.addConcept("groomingArea", "house");
		}
		{// 3
			cu.addConcept("toilet", "groomingArea");
		}
		{// 3
			cu.addConcept("bathroom", "groomingArea");
		}
		{// 4
			cu.addConcept("bedroom", "house");
		}
		{// 5
			cu.addConcept("livingroom", "house");
		}
		{// 5
			cu.addConcept("fronthall", "livingroom");
		}
		cu.addConcept("balcony", "livingroom");
		cu.addConcept("office", "house");
		return cu;
	}

}
