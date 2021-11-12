# MessagingServer

Echipa: Tudosoiu Dragos, Deca Sonia, Vinter Ovidiu, Dragan Iulia


### **Tema proiectului**
Un server care poate fi folosit de aplicatii pentru a comunica intre ele prin mesaje. Prin intermediul serverului pot fi efectuate trei tipuri de activitati:
-	trimitere de mesaje
- receptie de mesaje
-	administrare

Vom folosi Kafka pentru a trimite si receptiona mesaje prin intermediul a doua tipuri de resurse oferite de server:
-	message queues
-	topics

In aplicatia noastra vom avea un server, o baza de date si 3 apps care au fiecare un rol in contextul unui coffee shop, si anume:
-	app 1 : furnizorul pentru coffee shop app
-	app 2 : coffee shop app
-	app 3 : client coffee shop app

Pentru fiecare app vom avea un thread nou (terminal). 

Cozile de mesaje pot tine un numar maxim de mesaje la un moment dat, iar accesul lor se face dupa princpiul FIFO. Fiecare mesaj specifica intr-un antet destinatarul mesajului. Daca un program nu este destinatar al mesajului nu il va prelua. Preluarea unui mesaj implica scoaterea lui din coada.

Resursele de tip Topic permit publicarea de mesaje care pot fi citite de un numar nelimitat de clienti. Serverul trimite resurse de tip topic catre toate cele 3 apps.
-	app 1: raspunde cererii de la coffee shop
-	app 2: cere furnizorului anumite produse; spune clientilor meniul de la coffee shop
-	app 3: mai multi clienti, daca un client cere un anumit produs de maim ult de 10 ori se va
trimite un mesaj de broadcast “high demand”

