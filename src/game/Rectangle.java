/**
 * 
 */
package game;

/**
 * 矩形
 * 
 * @author hjn
 * 
 */
public class Rectangle extends AbstractShape {

	/** 矩形的顶点 */
	private int[][] vertex;

	public Rectangle(int px, int py, int[][] vertex) {
		super(px, py);
		this.vertex = vertex;
	}

	/**
	 * 是否有重叠，圆心到矩形6条边的距离小于圆的半径并且圆心不在矩形内那么这个矩形跟这个圆重叠
	 */
	@Override
	public boolean collision(IShape other) {
		try {
			if (other instanceof Circle) {
				Circle circle = (Circle) other;
				boolean fla = this.getLength(circle.x, circle.y, vertex[0][0],
						vertex[0][1], vertex[1][0], vertex[1][1])
						- circle.getRadius() < 0;
				if (fla) {
					return true;
				}
				boolean fla2 = this.getLength(circle.x, circle.y, vertex[0][0],
						vertex[0][1], vertex[2][0], vertex[2][1])
						- circle.getRadius() < 0;
				if (fla2) {
					return true;
				}
				boolean fla3 = this.getLength(circle.x, circle.y, vertex[0][0],
						vertex[0][1], vertex[3][0], vertex[3][1])
						- circle.getRadius() < 0;
				if (fla3) {
					return true;
				}
				boolean fla4 = this.getLength(circle.x, circle.y, vertex[1][0],
						vertex[1][1], vertex[2][0], vertex[2][1])
						- circle.getRadius() < 0;
				if (fla4) {
					return true;
				}
				boolean fla5 = this.getLength(circle.x, circle.y, vertex[1][0],
						vertex[1][1], vertex[3][0], vertex[3][1])
						- circle.getRadius() < 0;
				if (fla5) {
					return true;
				}
				boolean fla6 = this.getLength(circle.x, circle.y, vertex[2][0],
						vertex[2][1], vertex[3][0], vertex[3][1])
						- circle.getRadius() < 0;
				if (fla6) {
					return true;
				}
				boolean fla7 = this.contains(circle.x, circle.y);
				if (fla7) {
					return true;
				}
			} else if (other instanceof Rectangle) {
				Rectangle otherRectangle = (Rectangle) other;
				return this.collisionOBB(otherRectangle);
			}
		} catch (Exception e) {
			// 数组下标越界
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * OBB矩形碰撞检测
	 * @param rectangle 矩形2
	 * @return
	 */
	private boolean collisionOBB(Rectangle rectangle){
		int[][] vertex2=rectangle.vertex;
		/*矩形2相邻两条边的向量*/
		int wx1=vertex2[0][0]-vertex2[1][0];
		int wy1=vertex2[0][1]-vertex2[1][1];
		int wx2=vertex2[1][0]-vertex2[2][0];
		int wy2=vertex2[1][1]-vertex2[2][1];
		/*两个矩形中心点连接向量*/
		int centerX=(vertex2[0][0]+vertex2[2][0])/2-(vertex[0][0]+vertex[2][0])/2;
		int centerY=(vertex2[0][0]+vertex2[2][0])/2-(vertex[0][0]+vertex[2][0])/2;
		/*矩形一第一条边的向量*/
		int x11=vertex[0][0]-vertex[1][0];
		int y11=vertex[0][1]-vertex[1][1];
		/*矩形一在第一条边的投影*/
		double le1=Math.sqrt(x11*x11+y11*y11);
		/*矩形2相邻两条边在矩形1第一条边上的投影,例如projection211表示第2个矩形的第1条边在矩形1上第1条边的投影*/
		double projection2111=this.getProjection(wx1, wy1, x11, y11);
		double projection2211=this.getProjection(wx2, wy2, x11, y11);
		/*中心点连接向量的投影*/
		double centerProjection1=this.getProjection(centerX, centerY, x11, y11);
		/*两个矩形投影之和*/
		double total=projection2111+projection2211+le1;
		/*如果中心点向量投影大于矩形投影之和的一半那肯定没有碰撞*/
		if(centerProjection1>total/2){
			return false;
		}
		
		int x12=vertex[1][0]-vertex[2][0];
		int y12=vertex[1][1]-vertex[2][1];
		double le2=Math.sqrt(x12*x12+y12*y12);
		double projection2112=this.getProjection(wx1, wy1, x12, y12);
		double projection2212=this.getProjection(wx2, wy2, x12, y12);
		double centerProjection2=this.getProjection(centerX, centerY, x12, y12);
		if(centerProjection2>(projection2112+projection2212+le2)/2){
			return false;
		}
		/*反过来矩形1在矩形2相邻两条边的投影的一半跟中心点向量的投影大小对比,不一一写注释了*/
		int wx11=vertex[0][0]-vertex[1][0];
		int wy11=vertex[0][1]-vertex[1][1];
		int wx12=vertex[1][0]-vertex[2][0];
		int wy12=vertex[1][1]-vertex[2][1];
		
		int x21=vertex2[1][0]-vertex2[2][0];
		int y21=vertex2[1][1]-vertex2[2][1];
		double le3=Math.sqrt(x21*x21+y21*y21);
		double projection1121=this.getProjection(wx11, wy11, x21, y21);
		double projection1221=this.getProjection(wx12, wy12, x21, y21);
		double centerProjection3=this.getProjection(centerX, centerY, x21, y21);
		if(centerProjection3>(projection1121+projection1221+le3)/2){
			return false;
		}
		
		int x22=vertex2[1][0]-vertex2[2][0];
		int y22=vertex2[1][1]-vertex2[2][1];
		double le4=Math.sqrt(x22*x22+y22*y22);
		double projection1122=this.getProjection(wx11, wy11, x22, y22);
		double projection1222=this.getProjection(wx12, wy12, x22, y22);
		double centerProjection4=this.getProjection(centerX, centerY, x22, y22);
		if(centerProjection4>(projection1122+projection1222+le4)/2){
			return false;
		}
		return true;
	}
	/**
	 * 求向量1在向量2的投影
	 * @param x1 向量1的x坐标
	 * @param y1 向量1的y坐标
	 * @param x2 向量2的x坐标
	 * @param y2 向量2的y坐标
	 * @return 投影的绝对值
	 */
	private double getProjection(int x1,int y1,int x2,int y2){
		double t=(x1*x2+y1*y2)/(Math.sqrt(x1*x1+y1*y1)*Math.sqrt(x2*x2+y2*y2));
		double length=Math.sqrt(x1*x1+y1*y1)*t;
		return Math.abs(length);
	}
	
	/**
	 * 是否包含某一点
	 */
	@Override
	public boolean contains(int px, int py) {
		double l = this.getLength(px, py, vertex[0][0], vertex[0][1],
				vertex[1][0], vertex[1][1]);
		double l1 = this.getLength(px, py, vertex[1][0], vertex[1][1],
				vertex[2][0], vertex[2][1]);
		double l2 = this.getLength(px, py, vertex[2][0], vertex[2][1],
				vertex[3][0], vertex[3][1]);
		double l3 = this.getLength(px, py, vertex[3][0], vertex[3][1],
				vertex[0][0], vertex[0][1]);
		double total = l1 + l2 + l3 + l;
		double width = this.getLength(vertex[0][0], vertex[0][1], vertex[1][0],
				vertex[1][1]);
		double height = this.getLength(vertex[3][0], vertex[3][1],
				vertex[0][0], vertex[0][1]);
		return total == (width + height);
	}

	/* 两点间的距离 */
	private double getLength(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	/**
	 * 点到线段的距离
	 * 
	 * @param x
	 *            点x坐标
	 * @param y
	 *            点y坐标
	 * @param x1
	 *            线段顶点1x坐标
	 * @param y1
	 *            线段顶点1y坐标
	 * @param x2
	 *            线段顶点2x坐标
	 * @param y2
	 *            线段顶点2y坐标
	 * @return
	 */
	private double getLength(int x, int y, int x1, int y1, int x2, int y2) {
		double cross = (x2 - x1) * (x - x1) + (y2 - y1) * (y - y1);
		if (cross <= 0) {
			return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
		}
		double d2 = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
		if (cross >= d2) {
			return Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
		}
		double r = cross / d2;
		double px = x1 + (x2 - x1) * r;
		double py = y1 + (y2 - y1) * r;
		return Math.sqrt((x - px) * (x - px) + (py - y) * (py - y));

	}

	public int[][] getVertex() {
		return vertex;
	}

	public void setVertex(int[][] vertex) {
		this.vertex = vertex;
	}

	@Override
	public Rectangle copy() {
		return new Rectangle(x, y, vertex);
	}

	@Override
	public void moveTo(int px, int py) {

		int vx = px - this.x;
		int vy = py - this.y;

		int[][] copyOfVertex = new int[this.vertex.length][2];
		for (int i = 0; i < this.vertex.length; i++) {
			copyOfVertex[i][0] = vx + this.vertex[i][0];
			copyOfVertex[i][1] = vy + this.vertex[i][1];
		}

		this.x = px;
		this.y = py;
	}

}
