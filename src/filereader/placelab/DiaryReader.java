package filereader.placelab;

import activity.ActivityUtil;

/**
 * read activity from dairy file. Don't use subcategory and pre-input activities.
 * @author juan
 *
 */
public class DiaryReader {
	
	final public static String[] activities = { "entering", "away",
		"undressed", "dressed", "ingredients", "computer", "eating",
		"drink", "phone", "food", "dishes", "adding", "garbage", "reading",
		"toilet", "dishwasher", "hands", "meal", "hygiene", "music", "tv",
		"paperwork", "working", "leisure", "cleaning", "laptop", "laundry" };
	
	public static ActivityUtil getActivities(){
		ActivityUtil au = new ActivityUtil();
		for(String s: activities) {
			au.addActivity(s, null);
		}
		return au;
	}
	
}
