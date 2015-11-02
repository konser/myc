package udp.test;

public class T3 {
	public static void main(String[] args) {
		for(int i=0;i<100;i++) {
			if(i%2 == 0) {
				System.out.println("i%2 == 0=>" + i);
			} else {
				System.out.println("i%2 <> 0=>" + i);
			}
		}
	}
}
