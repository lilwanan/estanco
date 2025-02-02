package estanquero;

public class Consumidor extends Thread {
	private final int idFumador;
	private final Buffer estanco;
	
	private final int finalFumar;
	
	public Consumidor(int idFumador,Buffer estanco,int finalFumar) {
		this.idFumador=idFumador;
		this.estanco = estanco;
		
		this.finalFumar=finalFumar;
	}
	
	@Override
	public void run() {
		int cigarrosFumados =0;
		while(cigarrosFumados < finalFumar) {
			estanco.peticionIngredientes(idFumador);
			estanco.cogerIngredientes(idFumador);
			cigarrosFumados++;
		}
		System.out.println("Fumador " + idFumador + " termino de fumar");
	}
}
