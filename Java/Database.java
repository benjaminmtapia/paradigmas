import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
class Database{

	public	String preguntas[]={"inicio",
	"quiero un cafe",
	"tienen cafe?",
	"que cafe tienen?",
	"latte",
	"no gracias",
	"no",
	"si",
	"quiero otro",
	"americano",
	"cortado",
	"expresso",
	"fin"};

	public	String respuestas[][]={{"Buenos dias, bienvenido a la Cafeteria bot, que se le ofrece?","Hola buenas tardes, en que puedo ayudarle?","Buenas noches, que lleva?"},
	{"claro, de cual?","tenemos varios tipos de cafe","revise nuestra carta de cafes"},
	{"americano, expresso, cortado, latte y expresso","tenemos desde expresso y americano, hasta latte, 4 tipos!","tenemos 4 variedades: latte, americano, expresso y cortado"},
	{"si, tenemos americano, expresso, cortado, latte","tenemos varios tipos: americano, expresso, cortado y latte","nos quedan: americano, expresso, cortado y latte"},
	{"ya, un latte, y que mas?","anotado el latte, buena eleccion, lleva algo mas?","sirviendo su latte, desea agregar algo a su orden?"},
	{"esta bien, tenemos su orden lista","estan preparando su orden","su orden esta en proceso"},
	{"de acuerdo, enseguida le sirvo","le llevan enseguida","pase al costado a retirar su orden"}, 
	{"cual quiere?","cual lleva?","de cual?","cual cafe?"},
	{"claro, cual?","que otro desea agregar?","cual va a querer?"},
	{"agregado, quiere otro?","anotado, algo mas?","lo agregue a su pedido, esta todo listo?"},
	{"puedo ofrecerle algo mas?","listo, un cortado, nada mas?","quiere otro cafe?"},
	{"algo mas?","anotado, agrega otro?","el expresso es bueno para comenzar el dia", "gran eleccion"},
	{"muchas gracias, que le vaya bien!","gracias por preferirnos, que este bien","le agradecemos su preferencia, vuelva pronto!"}};

	public int Seed(int posicion){
		/**
		*Transforma un numero para ser usado como semilla para las personalidades del Chatbot
		*@param posicion El numero a transformar aleatoriamente
		*@return resultado El numero ya transformado
		*/
		int resultado;
		resultado= posicion+5;
		resultado= resultado*7;
		resultado= resultado%3;
		return resultado;
		}
	public String mensaje_bienvenida(){
		String mensaje="Bienvenido al programa de Chatbot. Este programa corresponde\na una inteligencia artificial que contiene dialogos predeterminados según una temática. En este caso corresponde a\nuna cafeteria con distintos tipo de cafes,para que usted pueda simular una orden.";
		mensaje= mensaje + "\nUsted puede usar las siguientes funcionalidades que deben comenzar con !:\n!beginDialog\n!endDialog\n!saveLog\nloadLog\n!rate\n!chatbotPerformance\nEscriba !end para terminar el programa\nPara mas informacion leer el manual de usuario adjunto";
		System.out.println(mensaje);
		return mensaje;
	}


	public Log sendMessage(String mensaje, int semilla,Log log){
		/**
		*Funcion sendMessage permite interactuar con el chatbot, recibiendo un mensaje del usuario y entregando una respuesta determinada
		*@param mensaje: pregunta o consulta del usuario
		*@param semilla: numero que indica la personalidad del chatbot
		*@param log: Historial log de conversaciones a cambiar
		*@return log: retorna el log modificado despues de cada interacción con el usuario
		*
		*/

		Database db= new Database();
	//	System.out.println(mensaje);
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDate date1 = currentTime.toLocalDate();
	    int minutos = currentTime.getMinute();
	    int segundos = currentTime.getSecond();
	    int hora= currentTime.getHour();
	    String tiempo;
		String respuesta;
		respuesta="";
		tiempo=String.valueOf(hora);
			tiempo= hora + ":" + String.valueOf(minutos);
			for (int i=0; i < 13 ; i++) {
			
					if(mensaje.equals(db.preguntas[i])){
					//si la pregunta coincide con la base de datos
					int posicion=i;
					int seed= Seed(semilla);
					respuesta=respuestas[posicion][semilla];
					String question = tiempo + " Usuario> "+mensaje;
					String answer = tiempo + " Chatbot>" + respuesta;
					log.agregarLog("\n",log);
					log.agregarLog(question,log);
					log.agregarLog("\n",log);
					log.agregarLog(answer,log);
					System.out.println("Chatbot> "+respuesta);
					return log;
					}

		}
				respuesta="no le entendi, repitame por favor";
				String question = tiempo + " Usuario> " + mensaje;
				String answer = tiempo + " Chatbot>" + respuesta;
				log.agregarLog("\n",log);
				log.agregarLog(question,log);
				log.agregarLog("\n",log);
				log.agregarLog(answer,log);
				System.out.println("Chatbot> "+respuesta);
		return log;
	}
}