/*
 * 作成日: 2003/12/28
 *
 */
package map.paint;

/**
 * @author k-saito
 *
 */
public class CatchData {

	private int x;
	private int y;
	private int[][] data;
	
	public CatchData(){
	}
	
	public CatchData(int x, int y, int[][] data){
		setX(x);
		setY(y);
		setData(data);
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getWidth(){
		return data[0].length;
	}
	
	public int getHeight(){
		return data.length;
	}

	/**
	 * @return
	 */
	public int[][] getData() {
		return (int[][])data.clone();
	}

	/**
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param is
	 */
	public void setData(int[][] is) {
		data = (int[][])is.clone();
	}

	/**
	 * @param i
	 */
	public void setX(int i) {
		x = i;
	}

	/**
	 * @param i
	 */
	public void setY(int i) {
		y = i;
	}

}
