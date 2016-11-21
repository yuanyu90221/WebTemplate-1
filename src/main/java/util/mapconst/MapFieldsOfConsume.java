package util.mapconst;

public enum MapFieldsOfConsume implements MapFields {
	USER_ID("userId", "使用者 ID"),
	
	CONSUME_DATE("consumeDate", "消費日期"),
	
	TYPE("type", "分類"),
	
	PROD_NAME("prodName", "商品名稱"),
	
	AMOUNT("amount", "消費金額"),
	
	LOTTERY_NO("lotteryNo", "發票號碼"),
	
	PRIZE("prize", "中獎金額"),
	
	IT_GOT("got", "是否中獎");
	
	private String fieldName;
	private String note;
	
	private MapFieldsOfConsume(String fieldName, String note) {
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
	
	public static MapFieldsOfConsume convert(String fieldName) {
		for (MapFieldsOfConsume e : MapFieldsOfConsume.values()) {
			if (e.getFieldName().compareToIgnoreCase(fieldName) == 0) {
				return e;
			}
		}
		return null;
	}
}
