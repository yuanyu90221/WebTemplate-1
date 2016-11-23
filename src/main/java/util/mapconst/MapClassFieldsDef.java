package util.mapconst;

import java.util.HashMap;
import java.util.Map;

public enum MapClassFieldsDef {
	USER("User", MapFieldsOfUser.values()),
	USER_ROLE("UserRole", MapFieldsOfUserRole.values()),
	PRODUCT("Product", MapFieldsOfProduct.values()),
	CONSUME("Consume", MapFieldsOfConsume.values()),
	RECEIPT_REWARD("ReceiptReward", MapFieldsOfReceiptReward.values())
	;
	
	private String className;
	private Map<String, String> mapFieldNameAndNotes = new HashMap<>();
	
	private MapClassFieldsDef(String className, MapFields[] mapFields) {
		this.className = className;
		if (mapFields != null) {
			for (MapFields e : mapFields) {
				mapFieldNameAndNotes.put(e.getFieldName(), e.getNote());
			}
		}
	}
	
	private String getClassName() {
		return this.className;
	}
	
	public String getNote(String fieldName) {
		return mapFieldNameAndNotes.get(fieldName);
	}
	
	public static MapClassFieldsDef convert(String className) {
		for (MapClassFieldsDef e : MapClassFieldsDef.values()) {
			if (e.getClassName().compareToIgnoreCase(className) == 0) {
				return e;
			}
		}
		return null;
	}
}
