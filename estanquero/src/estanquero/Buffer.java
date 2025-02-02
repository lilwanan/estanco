package estanquero;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Buffer {
	
	private final Semaphore semaforoMesa = new Semaphore(1); // control de la mesa a la que los clientes acceden
	private final Semaphore semaforoProduccion = new Semaphore(0); // contro de produccion del estanquero
	private final Semaphore[] fumadores; // array de fumadores 
	private final Semaphore produccionFinalizada = new Semaphore(0);
	
	private int[]ingredientes = {-1,-1,-1};
	private int colaFumadores =0;
	private int produccionesRestantes;
	private final Random random = new Random();
	
	
	public Buffer(int totalFumadores, int produccionesRestantes) {
		this.fumadores = new Semaphore[totalFumadores];
		this.produccionesRestantes=produccionesRestantes;
		
		for(int i=0; i< totalFumadores;i++) { // crea cada semaforo y lo añade al array de semaforos, esta condicion regula para que se creen mas fumadores 
			fumadores[i] = new Semaphore(0);
		}
	}
	
	public void peticionIngredientes(int idfumador) {
		try {
			semaforoMesa.acquire();
			if(produccionesRestantes<=0) {
				semaforoMesa.release();
				return;
			}
			System.out.println("El fumador " + idfumador + " está solicitando ingredientes" );
			semaforoProduccion.release(); // avisa al estanquero que hace falta mas ingredientes
			semaforoMesa.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void colocarIngredientes(int ingrediente) {
		try {
			semaforoProduccion.acquire();
			if(produccionesRestantes<=0) {
				produccionFinalizada.release();
				return;
			}
			
			 // espera a que se solicite algun ingrediente
			semaforoMesa.acquire(); // espera a que mesa este
			
			while(ingredientes[0] == ingrediente || ingredientes[1]==ingrediente || ingredientes[2]==ingrediente) {
					ingrediente = random.nextInt(3);
				
			}
			for(int i =0;i<3;i++) {
				if(ingredientes[i] == -1) {
					ingredientes[i] = ingrediente;
					
					System.out.println("Estanquero coloca: " + nombreIngrediente(ingrediente));
					
					System.out.println("Ingrediente " + nombreIngrediente(ingrediente) + " colocado en la posición " + i);
					
					
					
					break;
				}
				
			}
			
			System.out.println(ingredientes[0]);
			System.out.println(ingredientes[1]);
			System.out.println(ingredientes[2]);
			
			if(ingredientes[0]!=-1 && ingredientes[1]!=-1 && ingredientes[2]!=-1)
				for(int i = 0 ;i<fumadores.length;i++) {
					fumadores[i].release();
					
				
				}
			
			
			
			produccionesRestantes--;
			
			semaforoMesa.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void cogerIngredientes(int idfumador) {
		try {
			if(produccionesRestantes <=0) {
				produccionFinalizada.acquire();
				return;
			}
			fumadores[idfumador].acquire();
			semaforoMesa.acquire();
			while(colaFumadores != idfumador) {
				semaforoMesa.release();
				Thread.sleep(10000);
				semaforoMesa.acquire();
			}
			if(ingredientes[0]!=-1 && ingredientes[1]!=-1 && ingredientes[2]!=-1) {
				System.out.println("Fumador " + idfumador + " toma los ingredientes");
				System.out.println(idfumador + " esta fumando");
				Thread.sleep(3000);
				System.out.println(idfumador + " termino de fumar");
				// ahora cuando termine el yonki cogeria los ingredientes pa volver a fumar
				ingredientes[0]=-1;
				ingredientes[1]=-1;
				ingredientes[2]=-1;
				semaforoProduccion.release(); // produce un nuevo ingrediente
				colaFumadores = (colaFumadores+1)%3;
				
				
			}
			
			
			semaforoMesa.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String nombreIngrediente(int ingr) {
		// TODO Auto-generated method stub
		switch(ingr) {
			case 0: 
				return "Papel";
			case 1:
				return "Tabaco";
			case 2:
				return "Cerillas";
			default:
				return "Desconocido";
		}
		
	}
	
	
}
