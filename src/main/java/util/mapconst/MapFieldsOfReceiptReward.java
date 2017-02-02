package util.mapconst;

public enum MapFieldsOfReceiptReward implements MapFields {
	SECTION("section", "期別"),
	
	REWARD_TYPE("rewardType", "獎別"),
	
	NUMBER("number", "開獎號碼");
	
	private String fieldName;
	private String note;
	
	private MapFieldsOfReceiptReward(String fieldName, String note) {
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
	
	public static MapFieldsOfReceiptReward convert(String fieldName) {
		for (MapFieldsOfReceiptReward e : MapFieldsOfReceiptReward.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}
}
