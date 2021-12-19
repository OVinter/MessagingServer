# Cafenea

Echipa: Tudosoiu Dragos, Deca Sonia, Vinter Ovidiu, Dragan Iulia


### **Tema proiectului**

Tema aleasa de noi pentru proiect este o cafenea cu doi barista care fac cafele cat mai repede posibil, indiferent daca exista sau nu cerere pentru ele. Preparea unei cafele dureaza intre 0 si 2 secunde, iar cei doi barista pun apoi cafeaua pe tejghea, care are un spatiu limitat (poate tine maxim 10 cafele in acelasi timp).
Am folosit Kafka pentru a trimite si receptiona mesaje prin intermediul topic-urilor.
 
In aplicatia noastra avem  2 apps care au fiecare un rol in contextul unei cafenele, si anume:
-	app 1 : Barista
-	app 2 : Client

App 1 are rolul de Barista, iar in aplicatie avem doi barista care pun cafele in paralel pe acelasi topic. Cei 2 barista sunt reprezentati prin threaduri si pot prepara o cafea intr-un interval de timp cuprins intre 0 si 2 secunde, pentru care am folosit un randomizer intre (0,2]. 
Un barista poate face 2 doua actiuni:

-	sa adauge cafele pe tejghea 
-	sa ia cafelele de pe tejghea

Aceste sunt doua acțiuni separate, deci fiecare barista contine doua thread-uri: cel pentru adăugare de cafele pe tejghea și cel pentru luare de cafele de pe tejghea.
Tejgheaua cafenelei este reprezentata de un array cu maxim 10 elemente, populat numai cu cifre de 0 si 1. Cifra 0 reprezinta un loc liber pe tejghea, iar 1 un loc ocupat.


App 2 are rolul de client al cafenelei. Fiecare client este format din main thread-ul aplicației. Clientii pot plasa in paralel comenzi pentru cafele, iar un client poate comanda mai mult de o cafea o data. Clientii comanda pe acelasi topic in paralel. 

Fiecare topic suporta mai multe partitii, acest lucru permite procesarea mai multor mesaje in paralel.

![alt text](https://github.com/OVinter/MessagingServer/blob/main/ArhitecturePCBE.png)
