package mine.edit.component;

import java.awt.Component;

import mine.EnumUtils;
import mine.MineException;
import mine.MineUtils;

public class EditEnumCombo extends TextCombo implements EditComponent {

	private static final long serialVersionUID = -6432889311808048637L;

	private String fieldName;
	
	private Class<?> enu;
	
	public EditEnumCombo(String fieldName, Class<?> enu) {
		this.fieldName = fieldName;
		this.enu = enu;
		super.init(new String[]{"none"}, new String[]{"none"});
	}

	public String getFieldName() {
		return fieldName;
	}

	public Component getComponent() {
		return this;
	}

	public void getData(Object obj) throws MineException {
		MineUtils.setField(obj, fieldName, EnumUtils.callValueOf(enu, getValue()));
	}

	public void setData(Object obj) throws MineException {
		Object value = MineUtils.getField(obj, fieldName);
		setValue(value.toString());
	}
}
