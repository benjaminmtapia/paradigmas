%=================================

%%  Dominios
%%  mensaje=string
%%  log=string
%%  chatbot
%%  logs=
%%  seed = numero
%%  User = lista

%=================================

%Predicados
%beginDialog (Chatbot, InputLog, Seed, OutputLog).
%sendMessage(Msg, Chatbot, InputLog, Seed, OutputLog).
%endDialog (Chatbot, InputLog, Seed, OutputLog).
%logToStr(Log, StrRep).
%test (User,Chatbot,InputLog,Seed,OutputLog).

%=================================

%Metas(requierimientos funcionales)
%   beginDialog (Chatbot, InputLog, Seed, OutputLog).
%       inicia una conversación con el chatbot y retorna un log con dicha conversacón
%   sendMessage(Msg, Chatbot, InputLog, Seed, OutputLog).
%       predicado que permite charlar con el chatbot y retorna las respuestas de este
%   endDialog (Chatbot, InputLog, Seed, OutputLog).
%       Termina conversación con un chatbot, terminando el log actual y dando la oportunidad a iniciar a uno nuevo
%   logToStr(Log, StrRep).
%       predicado que hace entendible una conversación mostrandola como un string
%   test( User, Chatbot, InputLog, Seed, OutputLog).
%       simula mediante listas predefinidas una conversación con el chatbot

%=================================
%HECHOS

%TDA CHATBOT: se representa mediante 2 hechos: claves y respuestas
%Claves
%Aridad=2
%Se representa como una lista con las posibles preguntas que puede realizar el usuario al Chatbot
%Se usa un auxiliar Claves, para hacer mas facil la consulta
%respuestas
%Aridad=2
%Se representa como una Lista, con sublistas que contienen las respuestas para las posibles preguntas
%Se usa un auxiliar llamado chatbot, para que al momento de consultar se pueda entregar la lista de respuestas de manera mas facil

claves(claves,["inicio","quiero un cafe","latte","tienen cafe?","latte","americano","expresso","cortado","no gracias","si","no","quiero otro","fin"]).
respuestas(chatbot,[
	["Buenos dias, bienvenido a la Cafeteria bot, que se le ofrece?","Hola buenas tardes, en que puedo ayudarle?","Buenas noches, que lleva?"],
	["claro, de cual?","tenemos varios tipos de cafe","revise nuestra carta de cafes"],
	["si, tenemos americano, expresso, cortado, latte","tenemos varios tipos: americano, expresso, cortado y latte","nos quedan: americano, expresso, cortado y latte"],
	["americano, expresso, cortado, latte y express","tenemos desde expresso y americano, hasta latte, 4 tipos","tenemos 4 variedades: latte, americano, expresso y cortado"],
	["ya, un latte, y que mas?","anotado el latte, buena eleccion, lleva algo mas?","sirviendo su latte, desea agregar algo a su orden?"],
	["agregado, quiere otro?","anotado, algo mas?","lo agregue a su pedido, esta todo listo?"], 
	["puedo ofrecerle algo mas?","listo, un cortado, nada mas?","quiere otro cafe?"],
	["algo mas?","anotado, agrega otro?","el expresso es bueno para comenzar el dia","gran eleccion"],
	["esta bien, tenemos su orden lista","estan preparando su orden","su orden esta en proceso"],
	["que mas se le apetece?","de cual?","que otro cafe quiere?"],
	["de acuerdo, enseguida le sirvo","le llevan enseguida","pase al costado a retirar su orden"],
	["claro, cual?","que otro desea agregar?","cual va a querer?"],
	["muchas gracias, que le vaya bien!","gracias por preferirnos, que este bien","le agradecemos su preferencia, vuelva pronto!"]]).

%Listas User para el uso de la función test
%Listas con los respectivos dialogos que realiza el usuario.
listauser(user1,["quiero un cafe","americano","si","latte"]).
listauser(user2,["inicio","quiero","fin"]).
listauser(user3,[]).

%================================
%CLAUSULAS DE HORN

	% Semilla: permite consultar un numero "aleatorio" que se pide en los parametros de las clausulas principales
	%			sus valores van de 0 a 2, debido a que existen hasta 3 respuestas distintas para cada pregunta.
	% Seed: Numero de entrada.
	% X: Valor calculado a entregar.
	% Modo de uso: 
	%	semilla(5,3).
	%	X=1.

	semilla(Seed,X):-A is (Seed+8), X is mod(A,3).

	% IndexOf: Consulta por la posición de un elemento de una Lista. Se usa en las clausulas principales.
	% [H|_]: Lista de Entrada, donde se encuentra el elemento a buscar.
	% X: Elemento x de la lista.
	% Index: Posición de el elemento X
	% Ejemplo de uso:
	%	Indexof([1,2,3,4],2,X).
	%	X=1.
	%	Indexof([1,2,3,4],X,3).
	%	X=4.
	% Tipo de Recursión: Recursión Lineal

	indexOf([H|_], H, 0). 
	indexOf([_|T], X, Index):-
	indexOf(T, X, Index1), 
	Index is Index1+1. 

	% getMessage: Consulta para obtener una respuesta ante una determinada clave, se usa para las clausulas principales.
	% Mensaje: String, ingresado por el usuario ante el cual el Chatbot debe responder.
	% Seed: Int, semilla para generar aleatoriedad.
	% Pos: Int, posición para usar en la lista de respuestas.
	% Respuesta: String, elemento de la lista de respuestas a entregar por Chatbot.
	% Ejemplo:
	%	getMessage("inicio",5,1,Ans).
	%	Ans="Buenos días que se le ofrece"
	%	getMessage("quiero un cafe",5,3,Ans).
	%	Ans="claro de cual?""

	getMessage(_,Seed,Pos,Respuesta):-
		respuestas(chatbot,X),
		indexOf(X,Respuestas,Pos),
		indexOf(Respuestas,Respuesta,Seed).
	% beginDialog: Clausula para iniciar una conversación con un chatbot.
	% Chatbot: Atomo auxiliar de consulta para el TDA
	% InputLog: String, log de entrada para acumular las conversaciones.
	% Seed: Int, numero para generar aleatoriedad de conversaciones.
	% OutputLog:String, log de salida con la nueva conversacion.

	getGreeting(Hour,Pos):-
   Hour >= 0, Hour < 7, !, Pos=2;
   Hour >= 7, Hour =< 12, !, Pos=0 ;
   Hour > 12, Hour < 20, !,Pos=1;
   Hour > 20, Hour =< 23, !, Pos=2.
	


	beginDialog(Chatbot,InputLog,Seed,OutputLog):-
	get_time(T),
	convert_time(T,_,_,_,Hour,Minute,_,_),
	string_concat(InputLog,"*___*\n",Log),
	atomics_to_string([Hour,":",Minute," Chatbot: "], "", B), %hora_minuto chatbot:
	string_concat(Log,B,C), %C= *___*\n hora:minuto Chatbot: 
	getGreeting(Hour,A),
	semilla(Seed,_),
	getMessage(Chatbot,A,0,Ans),
	string_concat(C,Ans,D),
	string_concat(D,"\n",OutputLog);
	OutputLog="".

	% sendMessage: Clausula para enviar mensajes y mantener una conversacion con el Chatbot.
	% Message: String, dialogo ingresado por el usuario
	% Chatbot: Atomo auxiliar de consulta para el TDA
	% InputLog: String, log de entrada para acumular las conversaciones.
	% Seed: Int, numero para generar aleatoriedad de conversaciones.
	% OutputLog: String, log de salida con la nueva conversacion.
	sendMessage(Message,Chatbot,InputLog,Seed,OutputLog):-
		get_time(T),
		convert_time(T,_,_,_,Hour,Minute,_,_),
		semilla(Seed,S),
		claves(claves,A),%se obtienen todas las preguntas
		atomics_to_string([Hour,":",Minute],"",B), %hora para el usuario
		string_concat(B, " User: ",C), %hora:minuto user:
		string_concat(C,Message,Pregunta),
		string_concat(Pregunta," \n",Usuario),
		string_concat(InputLog,Usuario,Log),
		%ahora la respuesta
		indexOf(A,Message,Posicion),
		getMessage(Chatbot,S,Posicion,Ans),
		atomics_to_string([Hour,":",Minute," Chatbot: "], "", D), %hora:minuto chatbot: 
		string_concat(D,Ans,E), %hora:minuto Chatbot: Respuesta
		string_concat(E,"\n",Final),
		string_concat(Log,Final,OutputLog);
		OutputLog="no entendi bien, puede repetirme lo que quiso decir?".
		%B es la clave donde esta la pregunta

	% endDialog: Clausula para terminar una conversación con un chatbot.
	% Chatbot: Atomo auxiliar de consulta para el TDA
	% InputLog: String, log de entrada para acumular las conversaciones.
	% Seed: Int, numero para generar aleatoriedad de conversaciones.
	% OutputLog: String, log de salida con la conversacion.
	
endDialog(Chatbot,InputLog,Seed,OutputLog):-

	get_time(T),
	convert_time(T,_,_,_,Hour,Minute,_,_),
	string_concat(InputLog,"",Log),
	atomics_to_string([Hour,":",Minute," Chatbot: "], "", B), %hora_minuto chatbot:
	string_concat(Log,B,C), %C= *___*\n hora:minuto Chatbot: 
	semilla(Seed,A),
	getMessage(Chatbot,A,12,Ans),
	string_concat(C,Ans,D),
	string_concat(D,"\n",OutputLog);
	OutputLog="".


	test2([],Chatbot,InputLog,Seed,OutputLog):-endDialog(Chatbot,InputLog,Seed,OutputLog).
	test2([A|B],Chatbot,InputLog,Seed,OutputLog):-
		sendMessage(A,Chatbot,InputLog,Seed,Mensaje),
		test2(B,Chatbot,Mensaje,Seed,OutputLog).

	test(User,Chatbot,InputLog,Seed,OutputLog):-listauser(User,X),
		beginDialog(Chatbot,InputLog,Seed,Salida),
		test2(X,Chatbot,Salida,Seed,OutputLog).

	logToStr(Log,StrRep):-string_concat(Log,"",StrRep),
		write(StrRep);write("").
%%WRITE nl
%log to str write(texto \n)
%================================
