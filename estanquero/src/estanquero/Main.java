package estanquero;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numFumadores = 3;
		int producciones = 25;
		int cigarrosTotales = 5;
		
		Buffer estanco = new Buffer(numFumadores,producciones);
		Productor estanquero = new Productor(estanco,producciones);
		estanquero.start();
		
		for(int i=0;i<numFumadores;i++) {
			new Consumidor(i,estanco,cigarrosTotales).start();
		}
	}

}
