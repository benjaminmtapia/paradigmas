import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.io.*;
import java.lang.Math;

class Chatbot{

	Database db= new Database();
	ArrayList <Integer> notas = new ArrayList<Integer>();

	private Log beginDialog(Log log, int semilla){
		/**
		*Funcion beginDialog permite iniciar conversación con un chatbot
		*@param log: log con los registros del chatbot donde se almacenaran las conversaciones
		*@param semilla: numero que da a indicar la personalidad del chatbot
		*@return log: historial de conversaciones actualizado
		*/

		LocalDateTime currentTime = LocalDateTime.now();
		LocalDate date1 = currentTime.toLocalDate();
	      int minutos = currentTime.getMinute();
	      int segundos = currentTime.getSecond();
	      int hora= currentTime.getHour();
		  int seed=1;
		  String inicio="";
		
			if (hora>=0 || hora<=7){
				inicio=db.respuestas[0][2];
			}
			else if(hora >=7 || hora<=12 ){
				 inicio=db.respuestas[0][0];
			}
			else if(hora >=12 && hora < 20 ){
				 inicio=db.respuestas[0][1];
			}
			else if(hora > 19 && hora <= 23){
			 inicio=db.respuestas[0][2];
			}
		String conversacion=log.getLog();
		int largo_conversacion=conversacion.length();
			if(largo_conversacion!=0){
				endDialog(semilla,log);
				//return log;
			}

		String mensaje="Chatbot>";
		
		mensaje=mensaje.concat(inicio);

		System.out.println(mensaje);
		String tiempo;
		tiempo=String.valueOf(hora);
		tiempo= hora + ":" + String.valueOf(minutos);
		String question = tiempo + " Usuario> !beginDialog\n";
		String answer= tiempo + " Chatbot> ";
		log.agregarLog(question,log);
		log.agregarLog(answer,log);
		log.agregarLog(inicio,log);
		return log;
			
	}
	private void endDialog(int seed, Log log){
		/**
		*Funcion endDialog que permite terminar una conversación con el chatbot agregando un delimitador especial
		*@param seed: semilla de la personalidad del chatbot
		*@param log: historial que lleva en cuenta la conversacion actual con el chatbot
		*/
		db.sendMessage("fin",seed,log);
	}
	
	public int promedio(ArrayList<Integer> notas){
		int suma=0;
		for (int i=0 ; i < notas.size() ; i++ ) {
			suma += notas.get(i);
		}

		int promedio;
		promedio = suma / notas.size();
		return promedio;
	}
	public double desviacion(ArrayList<Integer> notas){
		int suma =0;
		double desviacion = 0;
		int promedio = promedio(notas);
		int aux;
		for (int i = 0; i < notas.size() ; i++ ) {
			aux = promedio - notas.get(i);
			suma += Math.pow(aux,2);
		}
		desviacion = suma / notas.size();
		desviacion= Math.pow(desviacion,0.5);
		return desviacion;
	}

	private void rate(int notaChatbot, int notaUsuario, Log log, Usuario user){
		/**
		*Funcion rate: permite evaluar tanto al usuario como al chatbot
		*@param notaChatbot: nota que el usuario le da al chatbot
		*@param notaUsuario: nota que el usuario se da a si mismo como autoevaluacion
		*@param log: Historial de conversaciones actuales
		*/
		String usuario="Nota usuario: ";
		String chatbot="Nota Chatbot: ";
		usuario+=String.valueOf(notaUsuario);
		chatbot+=String.valueOf(notaChatbot);
		this.notas.add(notaChatbot);
		user.agregarNota(notaUsuario);
		log.agregarLog(usuario,log);
		log.agregarLog("\n",log);
		log.agregarLog(chatbot,log);
	}
	public ArrayList<Integer> getNotas(){
		return notas;
	}
	private void chatbotPerformance(Usuario user){
	//	ArrayList <Integer> notas;
	//	notas = getNotas();
		double desviacionUsuario = user.desviacion(user.notas);
		double promedioUsuario = user.promedio(user.notas);
		double desviacionChatbot = desviacion(notas);
		int promedioChatbot = promedio(notas);
		System.out.println("Promedio usuario: "+ promedioUsuario + "\n");
		System.out.println("Promedio chatbot: "+ promedioChatbot + "\n");
		System.out.println("Desviacion estandar usuario: "+ desviacionUsuario + "\n");
		System.out.println("Desviacion estandar chatbot : "+ desviacionChatbot + "\n");

	}

	public void develop(){
		/**
		*Funcion develop pone a andar la mecánica de un chatbot, donde se presentan todas sus funcionalidades
		*/
		Log log= new Log();
		Usuario user= new Usuario();
		boolean verificador=true;
		db.mensaje_bienvenida();
		int seed;
		String partes[];
		Scanner sc= new Scanner(System.in);
		int semilla=0;
		while(verificador==true){

			System.out.println("Usuario> ");
			String entrada=sc.nextLine();
			String orden= entrada.substring(0,1);
			if(entrada.contains("!beginDialog")){
					if(entrada.contains(" ")){
						partes=entrada.split(" ");
						String part1=partes[0];
						String part2=partes[1];
						seed=db.Seed(Integer.parseInt(part2));
						semilla=seed;
					}
					else{
					seed=999;
					semilla=seed;
					}
					semilla = seed;
					log=beginDialog(log,seed);
				}
			else if(orden.equals("!")){
				//ES UN COMANDO
				if(entrada.equals("!saveLog")){
					log.saveLog(log.getLog());
				}
				else if(entrada.contains("!loadLog")){
					partes=entrada.split(" ");
					log.loadLog(partes[1],log);
				}
				else if(entrada.equals("!endDialog")){
					endDialog(semilla,log);
				}
				else if(entrada.equals("!chatbotPerformance")){
					chatbotPerformance(user);
				}
				else if(entrada.contains("!rate")){
				partes=entrada.split(" ");
				String notaChatbot=partes[1];
				String notaUsuario=partes[2];
				int usuario=Integer.parseInt(notaUsuario);
				int chatbot=Integer.parseInt(notaChatbot);
				rate(chatbot,usuario,log,user);
			}
			else if(entrada.contains("!end")){
				verificador=false;
			}
			/*
			else{
				System.out.println("Chatbot> Ese comando no esta entre las funciones");
				log.agregarLog("Chatbot> Ese comando no esta entre las funciones",log);
			}
			*/
		}

		else{

			//interaccion directa
				log=db.sendMessage(entrada,semilla,log);
			}
			
		}
	
	}

}