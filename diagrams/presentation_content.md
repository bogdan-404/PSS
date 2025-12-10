# Conținut Prezentare - Design Patterns în Food Delivery System

## SLIDE 1: Introducere

Aplicația noastră reprezintă o platformă completă de livrare de mâncare, dezvoltată folosind Spring Boot pentru backend și React pentru frontend. Sistemul permite clienților să plaseze comenzi de la restaurante, să urmărească statusul comenzilor în timp real și să primească notificări despre progresul livrării.

Proiectul nostru implementează un flux complet de procesare a comenzilor, de la validarea inițială până la livrarea finală. Sistemul gestionează multiple tipuri de comenzi (standard, programate, grup), calculează prețuri dinamice bazate pe distanță, ore de vârf și promoții, procesează plăți prin multiple gateway-uri, calculează rute de livrare și notifică toate părțile implicate în proces.

Pentru a asigura o arhitectură flexibilă, extensibilă și ușor de întreținut, am implementat nouă design patterns fundamentale, organizate în trei categorii: Creational Patterns, Behavioral Patterns și Structural Patterns. Aceste patterns ne permit să gestionăm complexitatea sistemului, să facilităm extinderea funcționalităților și să menținem codul curat și organizat.

---

## SLIDE 2: Creational Patterns

---

## SLIDE 3: Factory Method - Componente și Funcționalitate

Pattern-ul Factory Method este utilizat în sistemul nostru pentru crearea diferitelor tipuri de comenzi. Am implementat interfața OrderFactory care definește metoda abstractă createOrder, iar trei clase concrete implementează această interfață: StandardOrderFactory, ScheduledOrderFactory și GroupOrderFactory.

Fiecare factory este responsabil pentru crearea unui tip specific de comandă, configurând proprietățile corespunzătoare. StandardOrderFactory creează comenzi care sunt procesate imediat, ScheduledOrderFactory creează comenzi programate pentru o dată viitoare, iar GroupOrderFactory gestionează comenzile de grup cu logica specifică acestora.

OrderFactoryProvider joacă rolul de client care selectează factory-ul potrivit în funcție de tipul comenzii solicitate. Acest mecanism ne permite să adăugăm noi tipuri de comenzi în viitor fără a modifica codul existent, ci doar prin crearea unei noi implementări a interfeței OrderFactory.

---

## SLIDE 4: Factory Method - Diagramă și Utilizare

În Figura 1 putem observa structura pattern-ului Factory Method implementat în proiectul nostru. Interfața OrderFactory definește contractul pentru crearea comenzilor, iar cele trei implementări concrete gestionează fiecare tip specific de comandă.

Pattern-ul este utilizat în cadrul CheckoutFacade, unde OrderFactoryProvider primește tipul comenzii din request și returnează factory-ul corespunzător. Acest factory este apoi folosit pentru a crea instanța de Order cu configurația corectă, inclusiv setarea tipului, priorității, opțiunilor de ambalare și a metodei de plată.

Avantajul acestui pattern în contextul nostru este că separăm logica de creare a comenzilor de restul codului, permițând o extensibilitate ușoară. Dacă în viitor dorim să adăugăm un nou tip de comandă, cum ar fi comenzi recurente sau comenzi express, trebuie doar să creăm o nouă implementare a OrderFactory fără a afecta codul existent.

---

## SLIDE 5: Builder - Componente și Funcționalitate

Pattern-ul Builder este implementat pentru construirea obiectelor DeliveryRoute într-un mod flexibil și lizibil. DeliveryRouteBuilder oferă o interfață fluentă care permite setarea parametrilor rutei pas cu pas, folosind metode precum withOrder, withDistanceKm, withEstimatedTimeMinutes și withRouteDetails.

Builder-ul acumulează toate datele necesare pentru construirea rutei și validează că toți parametrii esențiali sunt prezenti înainte de a construi obiectul final prin metoda build. Această abordare ne permite să construim rute complexe într-un mod clar și ușor de înțeles, evitând constructori cu mulți parametri sau obiecte incomplete.

În implementarea noastră, Builder-ul este utilizat în RoutingService, unde datele rutei sunt obținute prin pattern-ul Adapter și apoi folosite pentru a construi obiectul DeliveryRoute final. Această combinație între Adapter și Builder demonstrează cum patterns-urile pot lucra împreună pentru a oferi soluții elegante.

---

## SLIDE 6: Builder - Diagramă și Utilizare

Figura 2 ilustrează structura pattern-ului Builder pentru DeliveryRoute. DeliveryRouteBuilder menține starea internă a tuturor parametrilor necesari și oferă metode chainable care returnează instanța builder-ului, permițând apeluri fluente.

Pattern-ul este folosit în RoutingService, unde după ce obținem datele rutei de la RouteClient (prin Adapter), construim DeliveryRoute folosind builder-ul. Această abordare face codul mult mai lizibil comparativ cu un constructor tradițional care ar necesita mulți parametri opționali.

Builder-ul asigură și validarea datelor, verificând că order, distanceKm și estimatedTimeMinutes sunt prezente înainte de a construi obiectul. Dacă datele lipsesc, se aruncă o excepție clară, prevenind crearea de obiecte incomplete. Acest pattern este deosebit de util în cazul nostru pentru că rutele pot avea parametri opționali precum routeDetails, care pot fi adăugați sau omiși în funcție de nevoi.

---

## SLIDE 7: Abstract Factory - Componente și Funcționalitate

Pattern-ul Abstract Factory este implementat pentru gestionarea diferitelor provideri de plată din sistem. PaymentProviderFactory este factory-ul care creează instanțe de PaymentProvider în funcție de metoda de plată selectată de client.

Am implementat trei provideri de plată concrete: StripePaymentProvider pentru plăți cu cardul prin Stripe, PaypalPaymentProvider pentru plăți prin PayPal, și CashOnDeliveryPaymentProvider pentru plata la livrare. Fiecare provider implementează interfața PaymentProvider, care definește operațiunile comune: authorizePayment, capturePayment, refund și getProviderName.

PaymentProviderFactory menține un map între PaymentMethod enum și instanțele corespunzătoare de PaymentProvider, permițând o selecție rapidă și eficientă a provider-ului potrivit. Factory-ul este inițializat prin dependency injection în Spring, primind toate cele trei implementări concrete și construind map-ul la pornirea aplicației.

---

## SLIDE 8: Abstract Factory - Diagramă și Utilizare

În Figura 3 putem observa structura pattern-ului Abstract Factory pentru sistemul de plăți. PaymentProviderFactory este factory-ul care creează familii de obiecte PaymentProvider, fiecare reprezentând o metodă diferită de procesare a plăților.

Pattern-ul este utilizat în CheckoutFacade când un client plasează o comandă. După calcularea prețului final, sistemul obține provider-ul de plată corespunzător metodei selectate de client și apelează metoda authorizePayment pentru a autoriza plata. Această separare permite adăugarea de noi metode de plată în viitor, cum ar fi Apple Pay sau Google Pay, fără a modifica logica de procesare a comenzilor.

Avantajul acestui pattern în contextul nostru este că abstractizează complet detaliile de implementare ale fiecărui provider de plată. CheckoutFacade nu trebuie să știe cum funcționează Stripe sau PayPal, ci doar să folosească interfața comună PaymentProvider. Acest lucru face codul mult mai testabil, permițând înlocuirea ușoară a provider-ilor reali cu mock-uri în teste.

---

## SLIDE 9: Behavioral Patterns

---

## SLIDE 10: Observer - Componente și Funcționalitate

Pattern-ul Observer este implementat pentru sistemul de notificări al aplicației noastre. OrderEventPublisher joacă rolul de Subject, menținând o listă de observatori (OrderEventListener) și notificându-i când apar evenimente legate de comenzi.

Am implementat trei tipuri de evenimente: OrderEvent ca clasă abstractă de bază, OrderCreatedEvent care este publicat când o comandă nouă este creată, și OrderStatusChangedEvent care este publicat când statusul unei comenzi se schimbă. Aceste evenimente conțin toate informațiile necesare pentru ca observatorii să poată reacționa corespunzător.

Trei clase concrete implementează interfața OrderEventListener: CustomerNotificationListener, RestaurantNotificationListener și CourierNotificationListener. Fiecare listener este responsabil pentru trimiterea notificărilor către categoria corespunzătoare de utilizatori. De exemplu, când o comandă este creată, CustomerNotificationListener trimite o notificare clientului, RestaurantNotificationListener notifică restaurantul, iar CourierNotificationListener așteaptă până când comanda este pregătită pentru livrare.

---

## SLIDE 11: Observer - Diagramă și Utilizare

Figura 4 prezintă structura pattern-ului Observer implementat în sistemul nostru. OrderEventPublisher menține o colecție de OrderEventListener și, când un eveniment apare, iteră prin toți listenerii și îi notifică prin metoda onOrderEvent.

Pattern-ul este utilizat în CheckoutFacade după ce o comandă este salvată cu succes. Sistemul publică un OrderCreatedEvent care conține comanda creată, iar toți listenerii înregistrați primesc acest eveniment și execută acțiunile corespunzătoare. De asemenea, când statusul unei comenzi se schimbă (de exemplu, de la PENDING la PREPARING sau de la ON_THE_WAY la DELIVERED), se publică un OrderStatusChangedEvent.

Avantajul acestui pattern este că permite o decuplare completă între obiectul care generează evenimentele (OrderEventPublisher) și obiectele care reacționează la acestea (listenerii). Putem adăuga noi tipuri de listeneri fără a modifica codul existent, de exemplu un listener care trimite email-uri sau unul care actualizează statistici. Această flexibilitate face sistemul mult mai extensibil și ușor de întreținut.

---

## SLIDE 12: Strategy - Componente și Funcționalitate

Pattern-ul Strategy este implementat pentru calculul dinamic al prețurilor în funcție de diferite criterii. Interfața PricingStrategy definește metoda apply care primește prețul de bază și comanda, și returnează prețul ajustat.

Am implementat trei strategii concrete de preț: DistanceBasedPricingStrategy care adaugă o taxă bazată pe distanța de livrare, PeakHourPricingStrategy care aplică un supliment în orele de vârf (11:00-14:00 și 18:00-21:00), și PromotionalPricingStrategy care aplică reduceri pentru anumite tipuri de comenzi, cum ar fi comenzile de grup.

PricingService joacă rolul de Context, menținând o listă cu toate strategiile disponibile și aplicându-le secvențial asupra prețului de bază. Fiecare strategie primește prețul rezultat din strategia anterioară și îl ajustează conform logicii sale specifice. Această abordare permite combinarea mai multor strategii pentru a calcula prețul final.

---

## SLIDE 13: Strategy - Diagramă și Utilizare

În Figura 5 putem observa structura pattern-ului Strategy pentru sistemul de prețuri. Fiecare strategie implementează interfața PricingStrategy și definește propria logică de calcul a prețului, fără a cunoaște existența celorlalte strategii.

Pattern-ul este utilizat în PricingService, care aplică toate strategiile disponibile secvențial. Sistemul pornește cu prețul de bază calculat din prețurile meniului, apoi aplică strategia pentru distanță, apoi strategia pentru orele de vârf, și în final strategia promoțională. Rezultatul final este un preț care reflectă toate aceste ajustări.

Avantajul acestui pattern în contextul nostru este că permite adăugarea de noi strategii de preț fără a modifica codul existent. De exemplu, putem adăuga o strategie pentru reduceri sezoniere, pentru programe de loialitate, sau pentru comenzi de volum mare. Fiecare strategie este independentă și poate fi testată izolat, iar PricingService rămâne simplu și ușor de înțeles.

---

## SLIDE 14: Chain of Responsibility - Componente și Funcționalitate

Pattern-ul Chain of Responsibility este implementat pentru validarea comenzilor înainte de procesare. OrderValidationHandler este clasa abstractă care definește structura handler-ilor din lanț, menținând o referință către următorul handler și oferind metoda validate care procesează request-ul și îl transmite mai departe.

Am implementat patru handleri concrete: AddressValidator care verifică că clientul are o adresă validă de livrare, PaymentDetailsValidator care validează că metoda de plată este specificată, ItemsAvailabilityValidator care verifică că toate produsele solicitate sunt disponibile în stoc, și FraudCheckValidator care efectuează verificări anti-fraud, cum ar fi respingerea comenzilor cu cantități suspicioase de mari.

Handlerii sunt legați într-un lanț în configurația Spring, unde fiecare handler setează următorul handler prin metoda setNext. Când o comandă trebuie validată, se apelează validate pe primul handler, care execută propria validare și, dacă aceasta reușește, transmite request-ul următorului handler. Dacă orice validare eșuează, se aruncă o ValidationException și procesul se oprește.

---

## SLIDE 15: Chain of Responsibility - Diagramă și Utilizare

Figura 6 ilustrează structura pattern-ului Chain of Responsibility pentru validarea comenzilor. Fiecare handler extinde OrderValidationHandler și implementează metoda doValidate cu logica sa specifică de validare.

Pattern-ul este utilizat în CheckoutFacade la începutul procesului de plasare a comenzii. Înainte de a crea comanda sau de a procesa plata, sistemul validează request-ul prin lanțul de validare. Dacă toate validările trec, procesul continuă. Dacă orice validare eșuează, comanda este respinsă și clientul primește un mesaj de eroare clar care indică ce anume nu este valid.

Avantajul acestui pattern este că permite adăugarea de noi validări fără a modifica handlerii existenți. De exemplu, putem adăuga un handler care verifică restricțiile de zonă de livrare sau unul care validează voucher-urile de reducere. Fiecare validare este izolată în propriul handler, ceea ce face codul mult mai ușor de înțeles, testat și întreținut. De asemenea, ordinea validărilor poate fi schimbată ușor prin reconfigurarea lanțului.

---

## SLIDE 16: Structural Patterns

---

## SLIDE 17: Decorator - Componente și Funcționalitate

Pattern-ul Decorator este implementat pentru calculul prețurilor cu opțiuni suplimentare. Interfața PriceCalculator definește metoda calculatePrice, iar BasePriceCalculator este implementarea de bază care calculează prețul sumând prețurile tuturor produselor din comandă.

Am implementat trei decoratori concrete: PriorityDeliveryPriceDecorator care adaugă o taxă pentru livrare prioritară, ExtraPackagingPriceDecorator care adaugă o taxă pentru ambalare suplimentară pe fiecare produs, și InsurancePriceDecorator care adaugă o taxă de asigurare calculată ca procent din prețul de bază.

Fiecare decorator menține o referință către un PriceCalculator pe care îl înfășoară. Când calculatePrice este apelat, decoratorul apelează mai întâi metoda pe calculatorul înfășurat pentru a obține prețul de bază, apoi aplică propria logică de ajustare. Această abordare permite înlănțuirea mai multor decoratori pentru a aplica multiple ajustări de preț.

---

## SLIDE 18: Decorator - Diagramă și Utilizare

În Figura 7 putem observa structura pattern-ului Decorator pentru calculul prețurilor. Decoratorii implementează aceeași interfață PriceCalculator ca și componenta de bază, permițând înlocuirea transparentă și înlănțuirea decoratorilor.

Pattern-ul este utilizat în PricingService după aplicarea strategiilor de preț. În funcție de opțiunile selectate de client (livrare prioritară, ambalare suplimentară, asigurare), sistemul înfășoară BasePriceCalculator cu decoratorii corespunzători. De exemplu, dacă clientul alege atât livrare prioritară cât și asigurare, se creează un InsurancePriceDecorator care înfășoară un PriorityDeliveryPriceDecorator care înfășoară BasePriceCalculator.

Avantajul acestui pattern este că permite adăugarea de noi opțiuni de preț fără a modifica codul existent. Putem adăuga un decorator pentru ambalare ecologică, pentru livrare în weekend, sau pentru orice altă opțiune viitoare. Fiecare decorator este independent și poate fi combinat cu alții, oferind o flexibilitate maximă în calculul prețurilor. Pattern-ul Decorator lucrează împreună cu pattern-ul Strategy pentru a oferi un sistem complet de calcul al prețurilor.

---

## SLIDE 19: Adapter - Componente și Funcționalitate

Pattern-ul Adapter este implementat pentru integrarea cu servicii externe de calculare a rutelor. Interfața RouteClient definește metoda getRoute care primește originea și destinația și returnează un obiect RouteData cu distanța, timpul estimat și detaliile rutei.

DummyRouteClientAdapter este implementarea concretă care simulează calcularea rutelor fără a face apeluri reale la API-uri externe. În producție, acest adapter ar putea fi înlocuit cu GoogleMapsRouteAdapter sau cu orice alt adapter care implementează interfața RouteClient și se conectează la un serviciu real de rutare.

RoutingService joacă rolul de Client care folosește interfața RouteClient fără a cunoaște detaliile de implementare. Service-ul obține adresele restaurantului și clientului din comandă, apelează getRoute pe RouteClient, și folosește datele returnate pentru a construi DeliveryRoute folosind pattern-ul Builder.

---

## SLIDE 20: Adapter - Diagramă și Utilizare

Figura 8 prezintă structura pattern-ului Adapter pentru sistemul de rutare. DummyRouteClientAdapter adaptează interfața unui serviciu extern de rutare (care în cazul nostru este simulat) la interfața RouteClient așteptată de RoutingService.

Pattern-ul este utilizat în RoutingService când o comandă necesită calcularea unei rute de livrare. Service-ul nu trebuie să știe dacă folosim Google Maps, OpenStreetMap, sau un serviciu simulat - el doar apelează metoda getRoute pe interfața RouteData și primește datele necesare. Această abstractizare permite schimbarea provider-ului de rutare fără a modifica RoutingService.

Avantajul acestui pattern în contextul nostru este că permite testarea ușoară a sistemului fără a face apeluri reale la API-uri externe, și facilitează schimbarea provider-ului de rutare în viitor. Dacă decidem să trecem de la Google Maps la un alt serviciu, trebuie doar să creăm un nou adapter care implementează RouteClient, fără a modifica restul codului. Adapter-ul lucrează împreună cu Builder-ul pentru a construi obiectul DeliveryRoute final.

---

## SLIDE 21: Facade - Componente și Funcționalitate

Pattern-ul Facade este implementat prin CheckoutFacade, care oferă o interfață simplificată pentru procesarea completă a comenzilor. Facade-ul ascunde complexitatea subsistemului care include validare, creare de comenzi, calcul de prețuri, procesare de plăți, calcul de rute, și publicare de evenimente.

CheckoutFacade orchestrează toate aceste procese într-o singură metodă placeOrder care primește un OrderCreationRequest și returnează Order-ul procesat. În interior, facade-ul coordonează mai multe componente: OrderValidationHandler pentru validare, OrderFactoryProvider pentru crearea comenzii, PricingService pentru calculul prețului, PaymentProviderFactory pentru procesarea plății, RoutingService pentru calculul rutei, OrderEventPublisher pentru notificări, și multiple repository-uri pentru persistența datelor.

Această abordare simplifică semnificativ utilizarea sistemului. Clientii facade-ului (cum ar fi OrderController) nu trebuie să cunoască toate detaliile de implementare ale fiecărui subsistem. Ei doar apelează placeOrder și primesc rezultatul, fără a trebui să gestioneze manual fiecare pas al procesului.

---

## SLIDE 22: Facade - Diagramă și Utilizare

Figura 9 ilustrează structura pattern-ului Facade și relațiile sale cu toate subsistemele. CheckoutFacade acționează ca o interfață unificată care ascunde complexitatea a peste zece componente diferite.

Pattern-ul este utilizat în OrderController, unde endpoint-ul pentru plasarea comenzilor apelează simplu metoda placeOrder a CheckoutFacade. Facade-ul gestionează întregul flux: validează comanda folosind Chain of Responsibility, creează comanda folosind Factory Method, calculează ruta folosind Adapter și Builder, calculează prețul folosind Strategy și Decorator, procesează plata folosind Abstract Factory, și publică evenimente folosind Observer.

Avantajul acestui pattern este că oferă o interfață simplă și clară pentru o funcționalitate complexă, reducând cuplarea între client și subsisteme. Dacă în viitor modificăm logica internă a unui subsistem (de exemplu, adăugăm o nouă validare sau schimbăm modul de calcul al prețurilor), clientii facade-ului nu trebuie modificați. Facade-ul demonstrează excelent cum multiple design patterns pot lucra împreună pentru a crea o arhitectură puternică și flexibilă.

---

## SLIDE 23: Concluzie

Implementarea acestor nouă design patterns în proiectul nostru de food delivery a adus beneficii semnificative în termeni de calitate a codului, flexibilitate și mentenabilitate. Patterns-urile Creational (Factory Method, Builder, Abstract Factory) ne-au permis să gestionăm crearea obiectelor complexe într-un mod organizat și extensibil, permițând adăugarea de noi tipuri de comenzi, metode de plată și moduri de construire a rutelor fără a modifica codul existent.

Patterns-urile Behavioral (Observer, Strategy, Chain of Responsibility) au facilitat comunicarea între componente, calculul dinamic al prețurilor și validarea flexibilă a comenzilor. Observer-ul a permis o decuplare completă între generarea evenimentelor și reacția la acestea, Strategy-ul a oferit flexibilitate în calculul prețurilor, iar Chain of Responsibility a permis adăugarea de noi validări fără a afecta cele existente.

Patterns-urile Structural (Decorator, Adapter, Facade) au simplificat interacțiunea cu sisteme externe, au permis adăugarea de funcționalități la calculul prețurilor, și au oferit o interfață simplă pentru un sistem complex. Facade-ul, în special, demonstrează puterea combinației de patterns, orchestrând toate celelalte patterns într-un flux coerent și ușor de utilizat.

În concluzie, utilizarea acestor design patterns nu a fost doar o demonstrație teoretică, ci o alegere practică care ne-a permis să construim un sistem robust, extensibil și ușor de întreținut. Patterns-urile au facilitat testarea, au redus cuplarea între componente, și au făcut codul mult mai lizibil și organizat. Această arhitectură ne permite să extindem sistemul în viitor cu noi funcționalități fără a compromite stabilitatea și calitatea codului existent.

