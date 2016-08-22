package map.paint;

public interface PaintListener {
	public void leftPressed(int x, int y);
	public void rightPressed(int x, int y);
	public void leftReleased(int x, int y);
	public void rightReleased(int x, int y);
	public void leftDragged(int x, int y);
	public void mouseMoved(int x, int y);
	public void destroy();
}
