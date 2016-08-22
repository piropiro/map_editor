package mine.edit.component;

import mine.MineException;
import mine.MineUtils;

public class EditIntSliderArray extends EditIntSlider implements EditComponent {

	private static final long serialVersionUID = -6432889311808048637L;
	
	private int index;
	
	public EditIntSliderArray(String fieldName, int index) {
		super(fieldName);
		this.index = index;
	}
	
	@Override
	public void getData(Object obj) throws MineException {
		String fieldName = super.getFieldName();
		int[] ints = (int[])MineUtils.getField(obj, fieldName);
		ints[index] = getValue();
		MineUtils.setField(obj, fieldName, ints);
	}

	@Override
	public void setData(Object obj) throws MineException {
		String fieldName = super.getFieldName();
		Object value = MineUtils.getField(obj, fieldName);
		int[] ints = (int[]) value;
		setValue(ints[index]);
	}

}
