package util.mapconst;

public enum MapFieldsOfUser implements MapFields {
    USER_ID("userId", "使用者 ID"),
	
	EMAIL("email", "使用者註冊時使用的 email"),
	
	PASSWORD("password", "使用者密碼"),
	
	MOBILE_NO("mobileNo", "使用者手機號碼"),
	
	LINE_ID("lineId", "使用者 LINE ID"),
	
	ENABLED("enabled", "是否允許"),

	CREATE_TIME("createTime", "建立時間"),
	
	LAST_SIGN_IN_TIME("lastSigninTime", "上次登入時間");
	
	private String fieldName;
	private String note;
	
	private MapFieldsOfUser(String fieldName, String note) {
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
	
	public static MapFieldsOfUser convert(String fieldName) {
		for (MapFieldsOfUser e : MapFieldsOfUser.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}
}	
