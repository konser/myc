package game;

public class Test {
	public static void main(String[] args) {
		test();
	}
	// 根据两点可以获取向量
	// 根据一点可以获得
	// 根据两点获取

	public static void test() {
		Circle c1 = new Circle(0, 0, 71);
		Circle c2 = new Circle(0, 76, 2);
		/* 圆是否包含圆 */
		System.out.println("圆包含圆" + c1.collision(c2));

		/* 矩形的4个点 */
		int[][] in = new int[4][2];
		in[0][0] = 0;
		in[0][1] = 100;

		in[1][0] = 100;
		in[1][1] = 0;

		in[2][0] = 200;
		in[2][1] = 100;

		in[3][0] = 100;
		in[3][1] = 200;

		Rectangle rectangle1 = new Rectangle(100, 100, in);
		/* 矩形是否包含圆 */
		System.out.println("矩形是否包含圆:" + (rectangle1.collision(c1)));
		/* 圆包含矩形 */
		System.out.println("圆包含矩形:" + (c1.collision(rectangle1)));
		/* 矩形形包含点 */
		System.out.println("矩形形包含点:" + rectangle1.contains(55, 49));

		/* 矩形的4个点 */
		int[][] in2 = new int[4][2];
		in2[0][0] = 0;
		in2[0][1] = 44;

		in2[1][0] = 0;
		in2[1][1] = 0;

		in2[2][0] = 44;
		in2[2][1] = 0;

		in2[3][0] = 44;
		in2[3][1] = 44;

		Rectangle rectangle2 = new Rectangle(24, 24, in2);
		long start = System.currentTimeMillis();
		rectangle2.collision(rectangle1);
		for (int i = 0; i < 100000; i++) {
			// c1.collision(c2);
			// c1.collision(rectangle1);
			rectangle2.collision(rectangle1);
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start));
		System.out.println("矩形包含矩形:" + rectangle2.collision(rectangle1));
	}
}
