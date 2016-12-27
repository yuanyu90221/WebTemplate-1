package util.mapconst;

public enum MapFieldsOfActivity implements MapFields {
	ACTIVITY_ID("activityId", "活動 ID"),
	
	CREATE_USER_ID("createUserId", "建立活動的使用者 ID"),
	
	CREATE_DATE("createDate", "建立活動日期"),
	
	TITLE("title", "活動標題"),
	
	DESC("desc", "活動描述"),
	
	START_DATETIME("startDatetime", "活動開始時間"),
	
	LATITUDE("latitude", "活動緯度"),
	
	LONGITUDE("longitude", "活動經度"),
	
	ATTENDEE_NUM("attendeeNum", "活動預計人數"),
	;
	
	private String fieldName;
	private String note;
	
	private MapFieldsOfActivity(String fieldName, String note) {
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
	
	public static MapFieldsOfActivity convert(String fieldName) {
		for (MapFieldsOfActivity e : MapFieldsOfActivity.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}
}
