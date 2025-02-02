package estanquero;

import java.util.Random;

public class Productor extends Thread {
	private final Buffer estanco;
	private final Random random = new Random();
	private final int producciones;
	
	
	
	public Productor(Buffer estanco,int producciones) {
		this.estanco=estanco;
		this.producciones=producciones;
		
	}
	
	@Override
	public void run() {
		try {
			int produccionInicial = 0;
			while(produccionInicial < producciones) {
				
				Thread.sleep(1000);
				int ingrediente =random.nextInt(3);
				estanco.colocarIngredientes(ingrediente);
				
				
				 // toda esta parte genera los ingredientes comprobando que sean diferente
				
				
				
				
				produccionInicial++;
				
				
		}
			System.out.println("Estanquero finaliza la produccion");
		
			
		} catch (InterruptedException e) {
			System.out.println("error");
		}
	}
}
