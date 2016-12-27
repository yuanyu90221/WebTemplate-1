package util.mapconst;

public enum MapFieldsOfJoinActivities implements MapFields {
	USER_ID("userId", "參與活動的使用者 ID"),

	ACTIVITY_ID("activityId", "活動 ID"),
	;

	private String fieldName;
	private String note;
	
	private MapFieldsOfJoinActivities(String fieldName, String note) {
		this.fieldName = fieldName;
		this.note = note;
	}
	
	@Override
	public String getFieldName() {
		return this.fieldName;
	}
	
	@Override
	public String getNote() {
		return this.note;
	}
	
	public static MapFieldsOfJoinActivities convert(String fieldName) {
		for (MapFieldsOfJoinActivities e : MapFieldsOfJoinActivities.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}

}
