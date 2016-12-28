package util.mapconst;

public enum MapFieldsOfActivityMessages implements MapFields {
	MSG_ID("msgId", "留言 ID"),
	
	ACTIVITY_ID("activityId", "活動 ID"),
	
	CREATE_USER_ID("createUserId", "建立活動的使用者 ID"),
	
	CREATE_DATE("createDate", "建立活動日期"),
	
	MSG("msg", "留言")
	
	;
	
	private String fieldName;
	private String note;
	
	private MapFieldsOfActivityMessages(String fieldName, String note) {
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
	
	public static MapFieldsOfActivityMessages convert(String fieldName) {
		for (MapFieldsOfActivityMessages e : MapFieldsOfActivityMessages.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}
}
