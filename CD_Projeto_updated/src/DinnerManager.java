/**
 *
 * @author joao-alegria
 */
public class DinnerManager {

	public static void main(String[] args) {
		int M = 7;
		int N = 3;

		Order order = new Order(M, N, "C:\\Users\\joaop\\Desktop");
		Kitchen kitchen = new Kitchen(order);
		Bar bar = new Bar(order);
		Table table = new Table(order);

		Chef chefe = new Chef(kitchen, bar, order);
		Waiter empregado = new Waiter(kitchen, bar, table, order);
		Student[] estudantes = new Student[7];
		for (int i = 0; i < M; i++) {
			estudantes[i] = new Student(bar, table, order, i + 1);
		}
		chefe.start();
		empregado.start();
		for (int i = 0; i < M; i++) {
			estudantes[i].start();
		}
		try {
			chefe.join();
			empregado.join();
		} catch (InterruptedException e) {
			System.err.println("Caught Exception: " + e.getMessage());
			System.exit(1);
		}
		for (int t = 0; t < M; t++) {
			try {
				estudantes[t].join();
			} catch (Exception e) {
				System.err.println("Caught Exception: " + e.getMessage());
				System.exit(1);
			}
		}
		System.out.println("FIM!");
		order.closeLog();
	}

}
