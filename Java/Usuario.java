import java.util.ArrayList;
import java.lang.Math;
class Usuario{
	ArrayList <Integer> notas = new ArrayList<Integer>();

	public void agregarNota(int nota){
		this.notas.add(nota);
	}

	public double promedio(ArrayList<Integer> notas){
		double suma=0;
		for (int i=0 ; i < notas.size() ; i++ ) {
			suma += notas.get(i);
		}

		double promedio;
		promedio = suma / notas.size();
		return promedio;
	}
	public double desviacion(ArrayList<Integer> notas){
		double suma =0;
		double desviacion = 0;
		double promedio = promedio(notas);
		double aux;
		for (int i = 0; i < notas.size() ; i++ ) {
			aux = promedio - notas.get(i);
			suma += Math.pow(aux,2);
		}
		desviacion = suma / notas.size();
		desviacion= Math.pow(desviacion,0.5);
		return desviacion;
	}
	
}