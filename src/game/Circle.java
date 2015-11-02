/**
 * 
 */
package game;

/**
 * 圆形
 * 
 * @author hjn
 * 
 */
public class Circle extends AbstractShape {

	/** 半径 */
	private int radius;

	public Circle(int px, int py, int radius) {
		super(px, py);
		this.radius = radius;
	}
    /**
     * 跟别的图像是否有重叠
     */
	@Override
	public boolean collision(IShape other) {
		if (other instanceof Circle) {
			Circle otherCircle = (Circle) other;
			return (this.radius + otherCircle.radius) * (this.radius + otherCircle.radius) >= ((this.x - otherCircle.x) * (this.x - otherCircle.x) + (this.y - otherCircle.y) * (this.y - otherCircle.y));
			
		}else if(other instanceof Rectangle){
			return other.collision(this);
		}
		return false;
	}

	/**
	 * 是否包含某个点
	 */
	@Override
	public boolean contains(int px, int py) {
		return this.radius * radius - ((this.x - px) * (this.x - px) + (this.y - py) * (this.y - py)) >= 0;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public Circle copy() {
		return new Circle(radius, radius, radius);
	}

	@Override
	public void moveTo(int px, int py) {
		this.x = px;
		this.y = py;
	}
}
