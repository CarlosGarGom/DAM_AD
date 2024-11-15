package singletonHilos;

public class PrincipalMultiHilo {


	static class Hilo1 implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Singleton singleton = Singleton.getInstance("Rojo");
			System.out.println("singleton de color:"+singleton.getColor());
		}
		
	}
	static class Hilo2 implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Singleton singleton = Singleton.getInstance("Rojo");
			System.out.println("singleton de color:"+singleton.getColor());
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Thread hilo1 = new Thread(new Hilo1());
			Thread hilo2= new Thread(new Hilo2());
	}

}
