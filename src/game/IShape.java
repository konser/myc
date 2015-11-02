
package game;

/**
 * @author hjn
 *
 */
public interface IShape {

	int getX();
	
	int getY();
	
	boolean collision(IShape other);
	
	boolean contains(int px, int py);
	
	IShape copy();
	
	void moveTo(int px, int py);
	
}
