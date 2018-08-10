import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.io.*;
class Log{	
	/**
	*Atributo log como string vacio
	*/
	String log="";
	public void setLog(String conversacion){
		/**
		*Funcion setLog que trabaja como setter de la clase y cambia el atributo log
		*@param conversacion: string con la conversacion actual 
		*/
		this.log=conversacion;
	}
	public String getLog(){
		/**
		*funcion getLog que retorna el Log del objeto log actual
		*/
		//System.out.println(log);
		return log;
	}
	public Log agregarLog(String dialogo, Log log){
		/**
		*agregarLog usa el setter de la clase para ir agregando un string que suele ser un dialogo al log de la conversacion
		*@param dialogo: string a agregar al log de la conversacion
		*@param log: log al que se le agregaran las conversaciones
		*@return log: retorna el log ya cambiado
		*/
	String resultado=log.getLog();
	resultado=resultado.concat(dialogo);
	log.setLog(resultado);
	return log;
	}

	public int log_length(String conversacion){
		/**
		*entrega el largo de una conversacion para efectos del metodo beginDialog de la clase Chatbot
		*@param conversacion: string completo de la conversación en curso del log
		*@return largo: largo del string de la conversacion
		*/
		int largo;
		largo=conversacion.length();
		return largo;
	}
	
	public void saveLog(String log){
		/**
		*saveLog se encarga de guardar el log en un archivo de texto con nombre en formato de fecha y hora
		*@param log: conversacion a guardar
		*/
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDate date1 = currentTime.toLocalDate();

	      int minutos = currentTime.getMinute();
	      int segundos = currentTime.getSecond();
	      int hora= currentTime.getHour();
	      int year= currentTime.getYear();
		  int day = calendar.get(Calendar.DATE);
	      int month= currentTime.getMonthValue();
	      String nombreArchivo;
	      nombreArchivo=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
	      nombreArchivo+="-"+String.valueOf(hora)+"-"+String.valueOf(minutos)+"-"+String.valueOf(segundos)+".log";

		
		FileWriter fichero = null;
		PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(nombreArchivo);
            pw = new PrintWriter(fichero);
            pw.println(log);
                

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	public Log loadLog(String nombreArchivo, Log log){
		/**
		* loadLog carga el log de un archivo de texto y permite seguir la conversacion desde alli
		* @param nombreArchivo: nombre del archivo con el log a cargar
		* @param log: log con la conversacion actual en curso donde se le agregara el log del archivo de texto
		* @return log: log actualizado con la conversacion del log del archivo de texto
		*/
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (nombreArchivo);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null){
                  	log.agregarLog(linea,log);
                  	log.agregarLog("\n",log);
                     System.out.println(linea);}
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
      return log;
	}
}