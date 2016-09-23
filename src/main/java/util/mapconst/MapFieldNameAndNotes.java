package util.mapconst;

public enum MapFieldNameAndNotes {
	USER_ID("userId", "使用者 ID"),
	
	EMAIL("email", "使用者註冊時使用的 email"),
	
	PASSWORD("password", "使用者密碼"),
	
	MOBILE_NO("mobileNo", "使用者手機號碼"),
	
	LINE_ID("lineId", "使用者 LINE ID"),
	
	LAST_SIGN_IN_TIME("lastSigninTime", "上次登入時間"),
	
	CREATE_TIME("createTime", "建立時間"),
	
	ROLE("role", "角色")
	
	;
	
	private String fieldName;
	private String note;
	
	private MapFieldNameAndNotes(String fieldName, String note) {
		this.fieldName = fieldName;
		this.note = note;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public static MapFieldNameAndNotes convert(String fieldName) {
		for (MapFieldNameAndNotes e : MapFieldNameAndNotes.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}
}
