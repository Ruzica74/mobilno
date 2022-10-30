package com.example.mymobapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Sight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {City.class, Sight.class}, version=1, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {


    public abstract DaoSight getDaoSight();
    public abstract DaoCity getDaoCity();

    private static MyAppDatabase myAppDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized MyAppDatabase getInstance(Context context) {
        if (null == myAppDatabase) {
            myAppDatabase = buildDatabaseInstance(context);
        }
        return myAppDatabase;
    }

    private static MyAppDatabase buildDatabaseInstance(Context context) {
        MyAppDatabase db =Room.databaseBuilder(context,
                        MyAppDatabase.class,
                        Constants.DB_NAME)
                //.addCallback(sRoomDatabaseCallback)
                .allowMainThreadQueries().build();
        new InsertTask().execute();
        return db;
    }

    public  void cleanUp(){
        myAppDatabase = null;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            System.out.println("db callback prije taska");

            //new InsertTask().execute();

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                DaoSight daos= myAppDatabase.getDaoSight();
                daos.deleteAll();
                System.out.println("db oncreate");

                DaoCity daoc= myAppDatabase.getDaoCity();
                daoc.deleteAll();

                /*Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);*/
                City city1=new City("Bri", "Bray", "Bray is a coastal town in north County Wicklow, Ireland. It is situated about twenty kilometres (12 mi) south of Dublin city centre on the east coast. It has a population of 32,600 making it the ninth largest urban area within Ireland (at the 2016 census). Bray is home to Ardmore Studios, and some light industry is located in the town, with some business and retail parks on its southern periphery. Commuter links between Bray and Dublin are provided by rail, Dublin Bus and the M11 and M50 motorways. Small parts of the town's northern outskirts are in County Dublin. \n" +
                        "Originally developed as a planned resort town in the 19th century, Bray's popularity as a seaside resort was serviced by the Dublin and Kingstown Railway, which was extended to Bray in 1854. During the late 20th century, the town's use as a resort declined when foreign travel became an option for holiday-makers. However, day-trippers continued to come to Bray during the summer months.\n", "Bri je primorski grad u severnom okrugu Viklou u Irskoj. Nalazi se oko dvadeset kilometara (12 milja) južno od centra Dablina na istočnoj obali. Ima 32.600 stanovnika, što ga čini devetim najvećim urbanim područjem u Irskoj (na popisu iz 2016.). Bri je dom Ardmor studija, a deo lake industrije se nalazi u gradu, sa nekim poslovnim i maloprodajnim parkovima na njegovoj južnoj periferiji. Prigradske veze između Brija i Dablina obezbeđuju se železnicom, Dablinskim autobusom i autoputevima M11 i M50. Mali delovi severne periferije grada su u okrugu Dablin.\n" +
                        "Prvobitno razvijen kao planirano letovalište u 19. veku, popularnost Breja kao primorskog odmarališta opsluživala je železnica Dablina i Kingstauna, koja je proširena na Bri 1854. Tokom kasnog 20. veka, upotreba grada kao odmarališta je opala kada su strani putovanja su postala opcija za turiste. Međutim, izletnici su nastavili da dolaze u Bri tokom letnjih meseci.\n", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0b/09/49/14/photo2jpg.jpg?w=700&h=500&s=1", "https://foodandwine.ie/uploads/article/2019/7/2667/bray_beach_GettyImages-908528082_main.jpg","https://www.myirelandtour.com/images/ap/wicklow/bray/bray-town/bray-town-001,lightbox-singular/bray-town-001,ar_unchanged,w_1732,h_1154,.jpg", "https://www.youtube.com/watch?v=kPx-kWh-THk", "","");
                daoc.insertCity(city1);
                System.out.println("gradovi: "+daoc.getCities());

            });
        }
    };

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        //private WeakReference<AddNoteActivity> activityReference;
        //private Note note;

        // only retain a weak reference to the activity
        InsertTask() {
            super();
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            DaoSight daos= myAppDatabase.getDaoSight();
            daos.deleteAll();
            System.out.println("db oncreate");

            DaoCity daoc= myAppDatabase.getDaoCity();
            daoc.deleteAll();

            City city1=new City("Bri", "Bray", "Bray is a coastal town in north County Wicklow, Ireland. It is situated about twenty kilometres (12 mi) south of Dublin city centre on the east coast. It has a population of 32,600 making it the ninth largest urban area within Ireland (at the 2016 census). Bray is home to Ardmore Studios, and some light industry is located in the town, with some business and retail parks on its southern periphery. Commuter links between Bray and Dublin are provided by rail, Dublin Bus and the M11 and M50 motorways. Small parts of the town's northern outskirts are in County Dublin. \n" +
                    "Originally developed as a planned resort town in the 19th century, Bray's popularity as a seaside resort was serviced by the Dublin and Kingstown Railway, which was extended to Bray in 1854. During the late 20th century, the town's use as a resort declined when foreign travel became an option for holiday-makers. However, day-trippers continued to come to Bray during the summer months.\n", "Bri je primorski grad u severnom okrugu Viklou u Irskoj. Nalazi se oko dvadeset kilometara (12 milja) južno od centra Dablina na istočnoj obali. Ima 32.600 stanovnika, što ga čini devetim najvećim urbanim područjem u Irskoj (na popisu iz 2016.). Bri je dom Ardmor studija, a deo lake industrije se nalazi u gradu, sa nekim poslovnim i maloprodajnim parkovima na njegovoj južnoj periferiji. Prigradske veze između Brija i Dablina obezbeđuju se železnicom, Dablinskim autobusom i autoputevima M11 i M50. Mali delovi severne periferije grada su u okrugu Dablin.\n" +
                    "Prvobitno razvijen kao planirano letovalište u 19. veku, popularnost Breja kao primorskog odmarališta opsluživala je železnica Dablina i Kingstauna, koja je proširena na Bri 1854. Tokom kasnog 20. veka, upotreba grada kao odmarališta je opala kada su strani putovanja su postala opcija za turiste. Međutim, izletnici su nastavili da dolaze u Bri tokom letnjih meseci.\n", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0b/09/49/14/photo2jpg.jpg?w=700&h=500&s=1", "https://foodandwine.ie/uploads/article/2019/7/2667/bray_beach_GettyImages-908528082_main.jpg","https://www.myirelandtour.com/images/ap/wicklow/bray/bray-town/bray-town-001,lightbox-singular/bray-town-001,ar_unchanged,w_1732,h_1154,.jpg", "https://www.youtube.com/watch?v=kPx-kWh-THk", "","");
            City city2=new City("Dablin", "Dublin",
                    "Dublin is the capital and largest city of Ireland. On a bay at the mouth of the River Liffey, it is in the province of Leinster, bordered on the south by the Dublin Mountains, a part of the Wicklow Mountains range. At the 2016 census, it had a population of 1,173,179, while the population of County Dublin as a whole was 1,347,359, and the Greater Dublin Area was 1,904,806. \n" +
                            "A settlement was established in the area by the Gaels during or before the 7th century, followed by the Vikings. As the Kingdom of Dublin grew, it became Ireland's principal settlement by the 12th century Anglo-Norman invasion of Ireland.[17] The city expanded rapidly from the 17th century and was briefly the second largest in the British Empire and sixth largest in Western Europe after the Acts of Union in 1800. Following independence in 1922, Dublin became the capital of the Irish Free State, renamed Ireland in 1937.\n",
                    "Dablin je glavni i najveći grad Irske. U zalivu na ušću rijeke Lifi, nalazi se u provinciji Lenster, na jugu graniči sa Dablinskim planinama, delom planinskog lanca Viklou. Na popisu iz 2016. godine imala je 1.173.179 stanovnika, dok je u okrugu Dablin u celini bilo 1.347.359 stanovnika, a područje Velikog Dablina 1.904.806.\n" +
                            "\n" +
                            "Naselje su osnovali na tom području Geli tokom ili pre 7. veka, a zatim Vikinzi. Kako je Kraljevina Dablin rasla, postala je glavno naselje Irske nakon anglo-normanske invazije na Irsku u 12. veku. Grad se brzo širio od 17. veka i nakratko je bio drugi po veličini u Britanskoj imperiji i šesti po veličini u Zapadnoj Evropi nakon Akta Unije 1800. godine. Nakon sticanja nezavisnosti 1922. godine, Dablin je postao glavni grad Irske slobodne države, preimenovana u Irsku 1937. godine.\n",
                    "http://t3.gstatic.com/licensed-image?q=tbn:ANd9GcT3gfaKbCl9h0gplrhrZXatTan7HLbmofmvo7IuBGogeInMOS-P0NHgmMIparFgho9s",
                    "https://upload.wikimedia.org/wikipedia/commons/4/42/Samuel_Beckett_Bridge_At_Sunset_Dublin_Ireland_%2897037639%29_%28cropped%29.jpeg",
                    "https://cdn.audleytravel.com/3625/2589/79/1322689-grafton-street-dublin.jpg",
                    "https://www.youtube.com/watch?v=LcKnx7I97yk&ab_channel=Expedia ","","");
            City city3=new City("Drogeda", "Drogheda", "Drogheda is an industrial and port town in County Louth on the east coast of Ireland, 56 km (35 mi) north of Dublin. It is located on the Dublin–Belfast corridor on the east coast of Ireland, mostly in County Louth but with the south fringes of the town in County Meath, 49 km (30 mi) north of Dublin. Drogheda has a population of approximately 41,000 inhabitants (2016), making it the eleventh largest settlement by population in all of Ireland, and the largest town in the country by both population and area. It is the last bridging point on the River Boyne before it enters the Irish Sea. The UNESCO World Heritage Site of Newgrange is located 8 km (5.0 mi) west of the town.\n" +
                    "Drogheda was founded as two separately administered towns in two different territories: Drogheda-in-Meath (i.e. the Lordship and Liberty of Meath, from which a charter was granted in 1194) and Drogheda-in-Oriel (or 'Uriel', as County Louth was then known). In 1412 these two towns were united, and Drogheda became a county corporate, styled as \"the County of the Town of Drogheda\". Drogheda continued as a county borough until the establishment of county councils under the Local Government (Ireland) Act 1898, which saw all of Drogheda, including a large area south of the Boyne, become part of an extended County Louth. \n",
                    "Drogeda je industrijski i lučki grad u okrugu Laut na istočnoj obali Irske, 56 km (35 milja) severno od Dablina. Nalazi se na koridoru Dablin-Belfast na istočnoj obali Irske, uglavnom u okrugu Laut, ali sa južnim rubovima grada u okrugu Mit, 49 km (30 milja) severno od Dablina. Drogeda ima populaciju od približno 41.000 stanovnika (2016), što ga čini jedanaestim naseljem po broju stanovnika u celoj Irskoj, i najvećim gradom u zemlji i po broju stanovnika i po površini. To je poslednja tačka premošćavanja na reci Bojn pre nego što uđe u Irsko more. Njugrejndž, koji je pod zaštitom UNESCO-a, nalazi se 8 km (5,0 milja) zapadno od grada.\n" +
                            "Drogeda je osnovana kao dva grada sa odvojenom upravom na dve različite teritorije: Drogeda-in-Mit (tj. Gospodstvo i sloboda Mita, od koje je povelja dodeljena 1194.) i Drogeda-in-Orijel (ili 'Urijel', kao okrug Laut je tada bio poznat). Godine 1412. ova dva grada su ujedinjena, a Drogeda je postala okružna kompanija, stilizovana kao „Okrug grada Drogede“. Drogeda je nastavila kao okrug okruga sve do uspostavljanja okružnih saveta prema Zakonu o lokalnoj upravi (Irska) iz 1898. godine, kojim je cela Drogeda, uključujući veliko područje južno od Bojna, postala deo proširenog okruga Laut. \n",
                    "https://www.discoverboynevalley.ie/sites/default/files/inline-images/Drogheda%20by%20Copter%20View%20Ireland%20resized_5_0.jpg","https://www.drogheda.ie//assets/image-cache/uploads/images/BackgroundImages/scotchhall.73388036.jpg",
                    "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0d/fc/1b/24/drogheda.jpg?w=700&h=500&s=1 ", "https://www.youtube.com/watch?v=UDIAKA4mvf8&ab_channel=ExploreIreland",
                    "", "");
            City city4=new City("Galvej","Galway", "Galway is a city in the West of Ireland, in the province of Connacht, which is the county town of County Galway. It lies on the River Corrib between Lough Corrib and Galway Bay, and is the sixth most populous city on the island of Ireland and the fourth most populous in the Republic of Ireland, with a population at the 2022 census of 83,456. \n" +
                    "Located near an earlier settlement, Galway grew around a fortification built by the King of Connacht in 1124. A municipal charter in 1484 allowed citizens of the by then walled city to form a council and mayoralty. Controlled largely by a group of merchant families, the Tribes of Galway, the city grew into a trading port. Following a period of decline, as of the 21st century, Galway is a tourist destination known for festivals and events including the Galway Arts Festival. \n",
                    "Galvej je grad na zapadu Irske, u provinciji Konaht, koja je grad okruga Galvej. Leži na rijeci Korib između Lou Koriba i zaliva Galvej, i šesti je grad po broju stanovnika na ostrvu Irska i četvrti po broju stanovnika u Republici Irskoj, sa populacijom od 83.456 stanovnika na popisu iz 2022.\n" +
                            "Smješten u blizini ranijeg naselja, Galvej je rastao oko utvrđenja koje je izgradio kralj Konahta 1124. Opštinska povelja iz 1484. dozvoljavala je građanima do tada opasanog grada da formiraju veće i gradonačelništvo. Uglavnom kontrolisan od strane grupe trgovačkih porodica, plemena Galveja, grad je prerastao u trgovačku luku. Nakon perioda opadanja, od 21. veka, Galvej je turistička destinacija poznata po festivalima i događajima, uključujući Galvai Arts Festival.\n",
                    "https://cdn-bnpeg.nitrocdn.com/UXTheotNaGNnRFESvxcowQoeVERbBvHk/assets/static/optimized/rev-6dd8c8a/wp-content/uploads/2019/03/Things-To-Do-In-Galway-1024x768.jpg",
                    "https://images.ireland.com/media/Images/galway-day-trips/b73254c641a44e77806a03b897f43cf2.jpg",
                    "https://assets-eu-01.kc-usercontent.com/aa24ba70-9a12-01ae-259b-7ef588a0b2ef/b3ae44d7-17bf-4242-b636-4a3512dc43e4/header-eglinton-canal-galway-city.jpg ",
                    "https://www.youtube.com/watch?v=pFNGWfshvgA&ab_channel=Expedia", "", "");
            City city5=new City("Kilkeni", "Kilkenny", "Kilkenny is a city in County Kilkenny, Ireland. It is located in the South-East Region and in the province of Leinster. It is built on both banks of the River Nore. The 2016 census gave the total population of Kilkenny as 26,512. \n" +
                    "Kilkenny is a tourist destination, and its environs include historic buildings such as Kilkenny Castle, St Canice's Cathedral and round tower, Rothe House, Shee Alms House, Black Abbey, St. Mary's Cathedral, Kilkenny Town Hall, St. Francis Abbey, Grace's Castle, and St. John's Priory. Kilkenny is also known for its craft and design workshops, the Watergate Theatre, public gardens and museums. Annual events include Kilkenny Arts Festival, the Cat Laughs comedy festival and music at the Kilkenny Roots Festival. \n" +
                    "Kilkenny began with an early 6th-century ecclesiastical foundation within the Kingdom of Ossory. Following the Norman invasion of Ireland, Kilkenny Castle and a series of walls were built to protect the burghers of what became a Norman merchant town. William Marshall, Lord of Leinster, gave Kilkenny a charter as a town in 1207. By the late 13th century, Kilkenny was under Hiberno-Norman control. The Statutes of Kilkenny, passed at Kilkenny in 1367, aimed to curb the decline of the Hiberno-Norman Lordship of Ireland. In 1609, King James I of England granted Kilkenny a Royal Charter, giving it the status of a city. Following the Irish Rebellion of 1641, the Irish Catholic Confederation, also known as the \"Confederation of Kilkenny\", was based in Kilkenny and lasted until the Cromwellian conquest of Ireland in 1649. From 1840 onwards, Kilkenny has not been administered as a city under local government law, but the Local Government Reform Act 2014 provides for \"the continued use of the description city\".\n",
                    "Kilkeni je grad u okrugu Kilkeni u Irskoj. Nalazi se u jugoistočnom regionu i u provinciji Leinster. Izgrađen je na obe obale reke Nor. Popis iz 2016. dao je ukupan broj stanovnika Kilkenija 26.512.\n" +
                            "Kilkeni je turistička destinacija, a njegovo okruženje obuhvata istorijske građevine kao što su zamak Kilkeni, katedrala Svete Kanice i okrugli toranj, Rothe House, Shee Alms House, Black Abbei, Katedrala Svete Marije, Gradska vijećnica Kilkenni, Abbei St Francis, Grace's Castle , i Priorat Svetog Jovana. Kilkeni je takođe poznat po svojim zanatskim i dizajnerskim radionicama, Votergejt teatru, javnim baštama i muzejima. Godišnji događaji uključuju Kilkenni Arts Festival, Festival komedije Cat Laughs i muziku na Kilkenni Roots Festivalu.\n" +
                            "Kilkeni je počeo sa crkvenim osnovama iz ranog 6. veka unutar Kraljevine Osorija. Nakon normanske invazije na Irsku, zamak Kilkeni i niz zidova izgrađeni su da bi zaštitili građanstvo onoga što je postalo normanski trgovački grad. Vilijam Maršal, lord od Lenstera, dao je Kilkeniju povelju kao grad 1207. Do kasnog 13. veka Kilkeni je bio pod Hiberno-Normanskom kontrolom. Statut Kilkenija, donesen u Kilkenniju 1367. godine, imao je za cilj da obuzda opadanje Hiberno-normanskog gospodstva Irske. Godine 1609. engleski kralj Džejms I dodelio je Kilkeniju Kraljevsku povelju, dajući mu status grada. Nakon irske pobune 1641. godine, Irska katolička konfederacija, poznata i kao „Kilkenijska konfederacija“, bila je sa sedištem u Kilkeniju i trajala je do Kromvelovog osvajanja Irske 1649. Od 1840. nadalje, Kilkeni nije bio pod upravom grada pod zakon o lokalnoj upravi, ali Zakon o reformi lokalne samouprave iz 2014. predviđa „nastavak korišćenja opisnog grada“.\n",
                    "https://www.planetware.com/photos-large/IRL/ireland-kilkenny-castle.jpg",
                    "https://assets-eu-01.kc-usercontent.com/aa24ba70-9a12-01ae-259b-7ef588a0b2ef/81571b12-ed9d-46bb-8059-a2f979d88c2e/header-kilkenny-castle-kilkenny-city-county-kilkenny.jpg",
                    "https://media.istockphoto.com/photos/historic-buildings-in-kilkenny-ireland-picture-id1283413368?k=20&m=1283413368&s=612x612&w=0&h=9C7f1RBCxkZkJ-K3f13_aFskTUG0A_jVDuK4fkfy56g=",
                    "https://www.youtube.com/watch?v=IMin48SK01g ",
                    "", "");
            City city6=new City("Kinsejl", "Kinsale",
                    "Kinsale is a historic port and fishing town in County Cork, Ireland. Located approximately 25 km (16 mi) south of Cork City on the southeast coast near the Old Head of Kinsale, it sits at the mouth of the River Bandon, and has a population of 5,281 (as of the 2016 census) which increases in the summer when tourism peaks.\n" +
                            "Kinsale is a holiday destination for both Irish and overseas tourists. The town is known for its restaurants, including the Michelin-starred Bastion restaurant, and holds a number of annual gourmet food festivals. \n" +
                            "As a historically strategic port town, Kinsale's notable buildings include Desmond Castle (associated with the Earls of Desmond and also known as the French Prison) of c. 1500, the 17th-century pentagonal bastion fort of James Fort on Castlepark peninsula, and Charles Fort, a partly restored star fort of 1677 in nearby Summercove. Other historic buildings include the Church of St Multose (Church of Ireland) of 1190, St John the Baptist (Catholic) of 1839, and the Market House of c. 1600. Kinsale is in the Cork South-West (Dáil Éireann) constituency, which has three seats.\n",
                    "Kinsejl je istorijska luka i ribarski grad u okrugu Kork u Irskoj. Nalazi se otprilike 25 km (16 milja) južno od grada Kork na jugoistočnoj obali u blizini Old Head of Kinsale, nalazi se na ušću reke Bandon i ima populaciju od 5.281 (prema popisu iz 2016.) koja se povećava u leto kada je turizam vrhunac.\n" +
                            "Kinsale je destinacija za odmor i za irske i za inostrane turiste. Grad je poznat po svojim restoranima, uključujući restoran Bastion sa Michelin zvezdom, i održava niz godišnjih festivala gurmanske hrane.\n" +
                            "Kao istorijski strateški lučki grad, značajne zgrade Kinsalea uključuju zamak Desmond (povezan sa grofovima Dezmonda i poznat i kao francuski zatvor) iz c. 1500, petougaona tvrđava bastiona Džejms tvrđave iz 17. veka na poluostrvu Kaslpark i tvrđava Čarls, delimično obnovljena zvezdana tvrđava iz 1677. u obližnjem Sammerkovu. Ostale istorijske građevine uključuju crkvu Svetog Multose (Crkva Irske) iz 1190. godine, Svetog Jovana Krstitelja (katoličkog) iz 1839. godine i pijacu iz c. 1600. Kinsejl je u izbornoj jedinici Cork South-Vest (Dail Eireann), koja ima tri mesta.\n",
                    "https://upload.wikimedia.org/wikipedia/commons/e/ea/Milk_Market_Cafe%2C_Kinsale_-_geograph.org.uk_-_1995637.jpg ",
                    "https://www.planetware.com/wpimages/2020/11/ireland-kinsale-things-to-do-take-historic-stroll-local-guides.jpg",
                    "https://images.ireland.com/media/Images/old-head-of-kinsale/9cae91ed047a47d19599b34020079dee.jpg ",
                    "https://www.youtube.com/watch?v=gITxLqaJy8k ", "", "");
            City city7=new City("Kob", "Cobh", "Cobh, known from 1849 until 1920 as Queenstown, is a seaport town on the south coast of County Cork, Ireland. With a population of around 13,000 inhabitants, Cobh is on the south side of Great Island in Cork Harbour and home to Ireland's only dedicated cruise terminal. Tourism in the area draws on the maritime and emigration legacy of the town.\n" +
                    "Facing the town are Spike and Haulbowline islands. On a high point in the town stands St Colman's, the cathedral church of the diocese of Cloyne. It is one of the tallest buildings in Ireland, standing at 91.4 metres (300 ft).\n",
                    "Kob, poznat od 1849. do 1920. kao Kvinstaun, je morski lučki grad na južnoj obali okruga Kork u Irskoj. Sa populacijom od oko 13.000 stanovnika, Cobh se nalazi na južnoj strani Velikog ostrva u luci Kork i dom je jedinog namenskog terminala za krstarenje u Irskoj. Turizam u ovoj oblasti oslanja se na pomorsko i emigraciono nasleđe grada.\n" +
                            "Nasuprot gradu su ostrva Spike i Haulbovline. Na visokoj tački u gradu stoji St Colman's, katedralna crkva biskupije Klojn. To je jedna od najviših zgrada u Irskoj, visoka 91,4 metara (300 stopa).\n",
                    "https://www.telegraph.co.uk/content/dam/Travel/Cruise/Port-guides/Cobh-cathedral-sunset-Getty-CROP.jpg",
                    "https://assets-eu-01.kc-usercontent.com/aa24ba70-9a12-01ae-259b-7ef588a0b2ef/f9a4d6bc-3329-41e2-abed-88a67308b3f8/Spike-Island-Cobh-Cork.jpg ",
                    "https://www.irishcentral.com/uploads/article/1387/cobh_co_cork___getty.jpg?t=1660123531 ",
                    "https://www.youtube.com/watch?v=j8tHwt4Q1N4 ", "", "");
            City city8=new City("Kork", "Cork", "Cork  is the second largest city in Ireland and third largest city by population on the island of Ireland. It is located in the south-west of Ireland, in the province of Munster. Following an extension to the city's boundary in 2019, its population is over 222,000. \n" +
                    "The city centre is an island positioned between two channels of the River Lee which meet downstream at the eastern end of the city centre, where the quays and docks along the river lead outwards towards Lough Mahon and Cork Harbour, one of the largest natural harbours in the world. \n" +
                    "Originally a monastic settlement, Cork was expanded by Viking invaders around 915. Its charter was granted by Prince John in 1185. Cork city was once fully walled, and the remnants of the old medieval town centre can be found around South and North Main streets. The city's cognomen of \"the rebel city\" originates in its support for the Yorkist cause in the Wars of the Roses. Corkonians sometimes refer to the city as \"the real capital\", a reference to its opposition to the Anglo-Irish Treaty in the Irish Civil War.\n",
                    "Kork je drugi po veličini grad u Irskoj i treći po broju stanovnika na ostrvu Irska. Nalazi se na jugozapadu Irske, u provinciji Manster. Nakon proširenja granice grada 2019. godine, njegova populacija je preko 222.000.\n" +
                            "Centar grada je ostrvo pozicionirano između dva kanala reke Li koji se sastaju nizvodno na istočnom kraju centra grada, gde kejevi i pristaništa duž reke vode ka Lough Mahonu i luci Kork, jednoj od najvećih prirodnih luka u svet.\n" +
                            "Prvobitno monaško naselje, Kork su proširili vikinški osvajači oko 915. Njegovu povelju je dao princ Džon 1185. Grad Kork je nekada bio potpuno ograđen zidinama, a ostaci starog srednjovekovnog centra grada mogu se naći oko južne i severne glavne ulice. Gradski kognomen „pobunjeničkog grada“ potiče od njegove podrške Jorkističkom cilju u Ratovima ruža. Korkonijani grad ponekad nazivaju „pravom prestonicom“, što se odnosi na njegovo protivljenje anglo-irskom sporazumu u irskom građanskom ratu.\n",
                    "https://assets-eu-01.kc-usercontent.com/aa24ba70-9a12-01ae-259b-7ef588a0b2ef/4313c5a7-630f-44a5-8f7e-a7fc6b6e148d/header-cobh-cathedral-cork.jpg",
                    "https://img.delicious.com.au/_db_tuZz/del/2019/07/cork-ireland-110336-1.jpg",
                    "https://images.ireland.com/media/Images/Cork/de4a0df0e2fb42a6bbc1cf28fc6a305a.jpg",
                    "https://www.youtube.com/watch?v=DnGAUNnQLxQ&ab_channel=WhereToNext-Europe", "", "");
            City city9=new City("Sligo", "Sligo", "Sligo is a coastal seaport and the county town of County Sligo, Ireland, within the western province of Connacht. With a population of approximately 20,000 in 2016, it is the largest urban centre in the county, with Sligo Borough District constituting 61% (38,581) of the county's population of 63,000. \n" +
                    "Sligo is a commercial and cultural centre situated on the west coast of Ireland. Its surrounding coast and countryside, as well as its connections to the poet W. B. Yeats, have made it a tourist destination.\n",
                    "Sligo je obalna luka i grad okruga Sligo, Irska, u zapadnoj provinciji Konaht. Sa populacijom od oko 20.000 u 2016. godini, to je najveći urbani centar u okrugu, a okrug Sligo Borough čini 61% (38.581) od 63.000 stanovnika okruga.\n" +
                            "Sligo je komercijalni i kulturni centar koji se nalazi na zapadnoj obali Irske. Njegova okolna obala i krajolik, kao i veze sa pesnikom V. B. Ieatsom, učinili su ga turističkom destinacijom.\n",
                    "https://i0.wp.com/www.sligo.ie/wp-content/uploads/slider-home-benbulben.jpg?resize=1500%2C630&ssl=1",
                    "https://i2-prod.irishmirror.ie/incoming/article26717095.ece/ALTERNATES/s1200c/1_sligo-town-centre-stock-generic.jpg",
                    "https://images.lovin.ie/uploads/2021/09/29164806/disc-sligo.jpg",
                    "https://www.youtube.com/watch?v=k30C5Y9bDDA", "", "");
            City city10=new City("Voterford", "Waterford", "Waterford is a city in County Waterford in the south-east of Ireland. It is located within the province of Munster. The city is situated at the head of Waterford Harbour. It is the oldest and the fifth most populous city in Ireland. It is the ninth most populous settlement on the island of Ireland. Waterford City and County Council is the local government authority for the city. According to the 2016 Census, 53,504 people live in the city, with a wider metropolitan population of 82,963.\n" +
                    "Today, Waterford is known for Waterford Crystal, a legacy of the city's former glassmaking industry. Glass, or crystal, was manufactured in the city from 1783 until early 2009 when the factory there was shut down after the receivership of Waterford Wedgwood plc. The Waterford Crystal visitor centre in the Viking Quarter, under new owners, opened in June 2010, after the intervention of Waterford City Council and Waterford Chamber of Commerce, and resumed production. Waterford is also known for being the starting point of Ryanair's first flight, a 14-seat Embraer Bandeirante turboprop aircraft flying between Waterford and London Gatwick Airport.\n",
                    "Voterford je grad u okrugu Voterford na jugoistoku Irske. Nalazi se u okviru provincije Minster. Grad se nalazi na čelu luke Voterford. To je najstariji i peti grad po broju stanovnika u Irskoj. To je deveto naselje po broju stanovnika na ostrvu Irska. Gradsko i okružno veće Voterford je organ lokalne uprave grada. Prema popisu stanovništva iz 2016. godine, u gradu živi 53.504 stanovnika, sa širim gradskim stanovništvom od 82.963.\n" +
                            "Danas je Voterford poznat po Voterford kristalu, nasleđu nekadašnje gradske industrije stakla. Staklo, ili kristal, proizvodilo se u gradu od 1783. do početka 2009. kada je tamošnja fabrika zatvorena nakon što je Voterford Vedgvud plc pripao. Centar za posetioce Voterford Cristal u četvrti Viking, pod novim vlasnicima, otvoren je u junu 2010. godine, nakon intervencije Gradskog veća Voterforda i Privredne komore Voterforda, i nastavio proizvodnju. Voterford je takođe poznat po tome što je bio polazna tačka za prvi let Rajanera, turbopropelerskog aviona Embraer Bandeirante sa 14 sedišta koji leti između Voterforda i londonskog aerodroma Getvik.\n",
                    "https://www.irishcentral.com/uploads/article/116658/Waterford_Harbour_GettyImages-639645094.jpg?t=1648630959",
                    "https://img.resized.co/wlrfm_com/eyJkYXRhIjoie1widXJsXCI6XCJodHRwczpcXFwvXFxcL2ltZy53bHJmbS5jb21cXFwvcHJvZFxcXC91cGxvYWRzXFxcL2NpdHktbGl2ZS1jb21wLTEwMjR4NjcxLmpwZ1wiLFwid2lkdGhcIjo3OTUsXCJoZWlnaHRcIjo1OTYsXCJkZWZhdWx0XCI6XCJodHRwczpcXFwvXFxcL2V1LWNlbnRyYWwtMS5saW5vZGVvYmplY3RzLmNvbVxcXC9wcGx1cy5pbWcud2xyZm0uY29tXFxcL3Byb2RcXFwvdXBsb2Fkc1xcXC8zMDAteC0yNTAlNDAzeC5qcGdcIixcIm9wdGlvbnNcIjp7XCJvdXRwdXRcIjpcIndlYnBcIn19IiwiaGFzaCI6IjIyZTgzNGVhYjEwYjYyODc0NmNjNDM0M2UxNzUzNGM2YjFhMzI5YmYifQ==/waterford-is-the-best-place-to-live-in-ireland.jpg",
                    "https://www.waterfordcouncil.ie/images/homepage4-sliders/1.jpg",
                    "https://www.youtube.com/watch?v=YaeUCHw_kNE&ab_channel=POPtravel ", "", "");
            daoc.insertCity(city1);
            daoc.insertCity(city2);
            daoc.insertCity(city3);
            daoc.insertCity(city4);
            daoc.insertCity(city5);
            daoc.insertCity(city6);
            daoc.insertCity(city7);
            daoc.insertCity(city8);
            daoc.insertCity(city9);
            daoc.insertCity(city10);

            Sight sight1=new Sight("Zamak Blarni ", "Blarney Castle", "Blarney Castle is a medieval stronghold in Blarney, near Cork, Ireland. Though earlier fortifications were built on the same spot, the current keep was built by the MacCarthy of Muskerry dynasty, a cadet branch of the Kings of Desmond, and dates from 1446. The Blarney Stone is among the machicolations of the castle. \n" +
                    "Historic Blarney Castle is famous for its stone, which legend tells has the power of conferring eloquence on all who kiss it.\n" +
                    "Blarney Castle’s gardens are one of the most visited in Ireland. The 60 acres are a joy to explore. Visit the prehistoric Fern Garden, a deadly Poison Garden, and the magical Rock Close with its ancient Yew trees and druidic stones. Make a wish on the famous wishing steps while the waterfall cascades alongside you.\n" +
                    "Stroll by the lake, river side and woodlands or in the dazzlingly beautiful arboretums. The gardens are constantly changing, and evolving environment and each visit is a new experience and don’t forget to kiss the famous Blarney Stone which has the traditional power of conferring eloquence on all who kiss it.\n" +
                    "The word Blarney was introduced into the English language by Queen Elizabeth I and is described as pleasant talk, intended to deceive without offending. The stone is set in the wall below the battlements, and to kiss it, one has to lean backwards (grasping an iron railing) from the parapet walk!\n",
                    "Zamak Blarni je srednjovekovno uporište u Blarniju, blizu Korka, u Irskoj. Iako su ranije utvrđenja izgrađena na istom mestu, sadašnju tvrđavu je sagradila dinastija MacCarthi od Muskerrija, kadetska grana kraljeva Dezmonda, i datira iz 1446. Kamen Blarni je među mahikolacijama zamka.\n" +
                            "Istorijski zamak Blarni poznat je po svom kamenu, za koji legenda kaže da ima moć da pruži elokvenciju svima koji ga ljube.\n" +
                            "Vrtovi zamka Blarni su jedan od najposećenijih u Irskoj. 60 hektara je zadovoljstvo istražiti. Posetite praistorijsku baštu paprati, smrtonosnu otrovnu baštu i magičnu stenu Klozu sa svojim drevnim stablima tise i druidskim kamenjem. Poželite želju na čuvenim stepenicama želja dok vodopad kaskade pored vas.\n" +
                            "Prošetajte pored jezera, obale reke i šuma ili u blistavo lepim arboretumima. Bašte se stalno menjaju i okruženje se razvija i svaka poseta je novo iskustvo i ne zaboravite da poljubite čuveni Blarni kamen koji ima tradicionalnu moć da pruži elokvenciju svima koji ga ljube.\n" +
                            "Reč Blarni je u engleski jezik uvela kraljica Elizabeta I i opisana je kao prijatan razgovor sa namerom da obmane bez uvrede. Kamen je postavljen u zid ispod ograde, a da biste ga poljubili, potrebno je nagnuti se unazad (hvatajući se za gvozdenu ogradu) sa parapetne šetnice!\n",
                    false, "", "", "https://blarneycastle.ie/wp-content/uploads/2022/06/blarney_castle_pic.jpg",
                    "https://www.gardensofireland.org/resources/images/directory/listing/9/gallery/1006.JPG",
                    "https://www.irelandbeforeyoudie.com/wp-content/uploads/2021/04/blarney-facts-1024x512.jpg");
            Sight sight2=new Sight("Dablinski zamak", "Dublin Castle", "Dublin Castle is one of the most important buildings in Irish history.\n" +
                    "From 1204. until 1922. it was the seat of English, and later British rule in Ireland. During that time, it served principally as a residence for the British monarch’s Irish representative, the Viceroy of Ireland, and as a ceremonial and administrative centre. The Castle was originally developed as a medieval fortress under the orders of King John of England. It had four corner towers linked by high curtain walls and was built around a large central enclosure. Constructed on elevated ground once occupied by an earlier Viking settlement, the old Castle stood approximately on the site of the present Upper Castle Yard. It remained largely intact until April 1684, when a major fire caused severe damage to much of the building. Despite the extent of the fire, parts of the medieval and Viking structures survived and can still be explored by visitors today.\n" +
                    "\n" +
                    "Following the fire, a campaign of rebuilding in the late-seventeenth and eighteenth centuries saw the Castle transformed from a medieval bastion into a Georgian palace. The new building included a suite of grand reception rooms known as the State Apartments. These palatial spaces accommodated the Viceroy and were the focus of great state occasions. During the early months of each year, the Viceroy, and occasionally the visiting British monarch, played host to a series of entertainments in the State Apartments. Known as the ‘season’, these festivities included state balls, banquets and regal ceremonies for members of the aristocracy. In the early nineteenth century the Castle was enhanced by the addition of the Chapel Royal in the Lower Castle Yard. This magnificent Gothic Revival structure, bristling with pinnacles on the outside and rich with ornamental features within, provided a place of worship for the viceregal household. It remains one of the architectural highlights of Georgian Dublin today.\n",
                    "Dablinski zamak je jedna od najvažnijih građevina u irskoj istoriji.\n" +
                            "Od 1204. do 1922. godine bio je sedište engleske, a kasnije britanske vlasti u Irskoj. Za to vreme služio je uglavnom kao rezidencija irskog predstavnika britanskog monarha, vicekralja Irske, i kao ceremonijalni i administrativni centar. Zamak je prvobitno razvijen kao srednjovekovna tvrđava po naređenju engleskog kralja Džona. Imao je četiri ugaone kule povezane visokim zidovima i bio je izgrađen oko velikog centralnog ograđenog prostora. Izgrađen na uzvišenju koju je nekada zauzimalo ranije vikinško naselje, stari zamak je stajao otprilike na mestu današnjeg Gornjeg dvorišta. Ostao je uglavnom netaknut sve do aprila 1684. godine, kada je veliki požar naneo ozbiljnu štetu velikom delu zgrade. Uprkos obimu požara, delovi srednjovekovnih i vikinških građevina su preživeli i posetioci ih i danas mogu istražiti.\n" +
                            "\n" +
                            "Nakon požara, kampanja obnove kasnog sedamnaestog i osamnaestog veka dovela je do toga da je zamak pretvoren iz srednjovekovnog bastiona u gruzijsku palatu. Nova zgrada je uključivala skup velikih prijemnih soba poznatih kao Državni stanovi. Ovi raskošni prostori su smestili vicekralja i bili su fokus velikih državnih prilika. Tokom prvih meseci svake godine, vicekralj, a povremeno i britanski monarh koji je bio u poseti, bio je domaćin nizu zabava u Državnim apartmanima. Poznate kao 'sezona', ove svečanosti su uključivale državne balove, bankete i kraljevske ceremonije za pripadnike aristokratije. Početkom devetnaestog veka zamak je poboljšan dodavanjem Kraljevske kapele u dvorištu Donjeg zamka. Ova veličanstvena građevina gotičkog preporoda, spolja puna vrhova i bogata ukrasnim elementima iznutra, predstavljala je mesto obožavanja vicekraljevskog domaćinstva. To je i danas jedan od arhitektonskih vrhunaca gruzijskog Dablina.\n",
                    false, "", "", "https://seda.college/blog/wp-content/uploads/2018/05/dublin-castle-1024x768.jpg",
                    "https://twotravelingtexans.com/wp-content/uploads/2018/07/castle-3274586_1920-1.jpg",
                    "https://heritageireland.ie/assets/uploads/2020/03/Dublin-Castle-throne-room.jpg");
            Sight sight3=new Sight("Dvorac Ešford", "Castle Ashford", "The castle was built in 1228. on the site of a former monastery by the Anglo-Norman House of Burke. After more than three and a half centuries, the castle passed into the hands of a new owner after a fierce struggle. In 1589, it was occupied by Sir Richard Bingham, who additionally fortified it. In 1670. or 1678, Dominic Brown, of the Brown family, received this estate by special royal grant for use. In 1715, the Brown family founded the Ashford estate here and built a hunting lodge in the style of a 17th-century French castle. The two-headed eagles that can still be seen on the roof of this castle are the coat of arms of the Brown family. At the end of the 18th century, a branch of this family lived in the castle. At the beginning of the 19th century, it was recorded that a certain Thomas Ellwood, representative of the Brown family in Ashford, lived in the castle in 1814. The estate was bought in 1852. by Sir Benjamin Guinness. He extended the building with two large wings in the Victorian style. He also expanded the estate to 26,000 hectares, built new roads and planted thousands of trees. In 1939, the castle was sold to Noel Hugard, who turned the castle into a hotel. In 1970, John Mulcahy became the new owner of the hotel. He completely renovated it, and also expanded it by adding a new wing and building a golf course in the early 1970s. A group of American investors, of Irish origin, bought the property in 1985. and then sold it in 2007 for €50 million to real estate dealer and investor Gary Barrett and his family. In September 2012, Ashford was named the best resort and hotel in Ireland, and the third best in Europe.",
                    "Dvorac je sagrađen 1228. godine na lokaciji nekadašnjeg manastira od strane anglo-normanske kuće Burk. Posle više od tri i po veka dvorac  je prešao u ruke novog vlasnika nakon žestoke borbe. Njega je 1589. godine zauzeo ser Ričard Bingham, koji ga je dodatno utvrdio. Dominik Braun, iz Braun porodice, dobio je 1670. ili 1678. godine ovo imanje posebnom kraljevskom darovnicom na korišćenje. Porodica Braun je 1715. godine ovde osnovala Ašford imanje i tu je sagradila lovački dom u stilu francuskog zamka iz XVII veka. Dvoglavi orlovi koji se i danas mogu videti na krovu ovog dvorca predstavljaju grb porodice Braun. Krajem XVIII veka ogranak ove porodice živeo je u dvorcu. Početkom XIX veka zabeleženo je da je izvesni Tomas Elvud, zastupnik porodice Braun u Ašfordu, živeo u zamku 1814. godine. Imanje je 1852. godine kupio ser Bendžamin Ginis. On je proširio zdanje sa dva velika krila u viktorijanskom stilu. Takođe, on je proširio imanje na 26.000 hektara, izgradio je nove puteve i zasadio je na hiljade stabala. Dvorac je 1939. godine prodat Noelu Hugardu koji je dvorac pretvorio u hotel. Novi vlasnik hotela je 1970. godine postao Džon Mulkahi. On je izvršio njegovu potpunu obnovu, a takođe ga je i proširio dodavanjem novog krila i izgradnjom golf terena početkom sedamdesetih godina prošlog veka. Grupa američkih investitora, irskog porekla, kupila je imanje 1985. godine, a zatim i prodala 2007. godine, za 50 miliona evra dileru nekretnina i investitoru Geriju Beretu i njegovoj porodici. U septembru 2012. godine Ašford je proglašen najboljim letovalištem i hotelom u Irskoj, i trećim najboljim u Evropi.",
                    false, "", "", "https://www.avvio.com/wp-content/uploads/2019/09/ASH-190314-Famous-Guests-Credit-Red-Carnation-Hotels2.jpg",
                    "https://mapasymochila.com/wp-content/uploads/2021/01/Ashford-Castle-3.jpg",
                    "https://media.tatler.com/photos/6225f6f91de4ad23c0ed580d/4:3/w_1520,h_1140,c_limit/Ashford%20Castle%20Oak%20Hall-production_digital.jpg");
            Sight sight4=new Sight("Moherske litice", "The Cliffs of Moher", "On the west coast of Ireland, there is one of the country's most prominent coastal features: the Cliffs of Moher (Irish: Aillte Mhothair), 8 km long and up to 213 meters high at its highest point.\n" +
                    "At this location there was once a gigantic delta that disappeared approximately 320 million years ago, leaving behind a place of incredible beauty, at the same time mystical, cold, and for those who are afraid of heights... terrifying. It is estimated that around 30,000 birds live on the cliffs (20 species), and they are just one of the reasons why more than a million tourists visit this spectacular landscape every year.\n" +
                    "The Cliffs of Moher have risen to the top of Ireland's attractions list in recent years, thanks to their numerous appearances in films such as The Princess Bride and Harry Potter and the Half-Blood Prince, as well as their appearance in music videos by popular bands such as Maroon 5 and Westlife.\n",
                    "Na zapadnoj obali Irske nalazi se jedno od najistaknutijih obalnih obilježja ove države: Moherske litice (irski: Aillte Mhothair), dugačke 8 km, te visoke čak do 213 metatra na svojoj najvišoj tački.\n" +
                            "Na ovoj lokaciji se nekada nalazila gigantska delta koja je nestala prije otprilike 320 miliona godina, ostavljajući iza sebe mjesto nevjerojatne ljepote, u isto vrijeme mistično, hladno, te za one koji se boje visine… zastrašujuće. Procjenjuje se da oko 30.000 ptica živi na liticama (20 vrsta), a one su samo jedan od razloga zašto više od milion turista godišnje posjeti ovaj spektakularni krajolik.\n" +
                            "Moherske litice su posljednjih godina dospjele na sami vrh popisa atrakcija u Irskoj, a tome je doprinjelo i njihovo brojno pojavljivanje u filmovima kao što su The Princess Bride i Harry Potter and the Half-Blood Prince, kao i pojavljivanje u video spotovima popularnih bendova poput Maroon 5 i Westlife.\n",
                    false, "", "", "https://www.fodors.com/wp-content/uploads/2019/03/shutterstock_717478675.jpg",
                    "https://www.cliffsofmoher.ie/wp-content/uploads/2022/08/Cliffs-of-Moher-drone5.jpg",
                    "https://expertvagabond.com/wp-content/uploads/obriens-tower-cliffs-moher-900x600.jpg.webp");
            Sight sight5=new Sight("Ginis skladište", "Guinness Storehouse ", "Guinness Storehouse is a tourist attraction at St. James's Gate Brewery in Dublin, Ireland. Since opening in 2000, it has received over twenty million visitors. \n" +
                    "The Storehouse covers seven floors surrounding a glass atrium shaped in the form of a pint of Guinness. The ground floor introduces the beer's four ingredients (water, barley, hops and yeast), and the brewery's founder, Arthur Guinness. Other floors feature the history of Guinness advertising and include an interactive exhibit on responsible drinking. The seventh floor houses the Gravity Bar with views of Dublin and where visitors may drink a pint of Guinness included in the price of admission.\n" +
                    "The building in which the Storehouse is located was constructed in 1902 as a fermentation plant for the St. James's Gate Brewery (yeast is added to the brew). It was designed in the style of the Chicago School of Architecture and was the first multi-storey steel-framed building to be constructed in Ireland. The building was used continuously as the fermentation plant of the Brewery until its closure in 1988, when a new fermentation plant was completed near the River Liffey. \n" +
                    "In 1997, it was decided to convert the building into the Guinness Storehouse, replacing the Guinness Hop Store as the Brewery's visitor centre. The redesign of the building was undertaken by the UK-based design firm Imagination in conjunction with the Dublin-based architects firm RKD, and the Storehouse opened to the public on 2 December 2000. In 2006-08 a new wing was developed, and Euro 2.5 million was invested in a live technology-driven multi-media installation demonstrating the modern brewing process for Guinness, which was designed by London-based museum design specialist, Event Communications. \n" +
                    "In May 2011, Queen Elizabeth II and Prince Philip visited the Storehouse as part of a state visit to Ireland.\n",
                    "Guinness Storehouse je turistička atrakcija u pivari St. James's Gate u Dablinu, Irska. Od otvaranja 2000. godine, primio je preko dvadeset miliona posetilaca.\n" +
                            "Skladište se prostire na sedam spratova koji okružuju stakleni atrijum u obliku pinte Guinnessa. U prizemlju se nalaze četiri sastojka piva (voda, ječam, hmelj i kvasac) i osnivač pivare Artur Ginis. Drugi spratovi predstavljaju istoriju Ginisovog reklamiranja i uključuju interaktivnu izložbu o odgovornom konzumiranju alkohola. Na sedmom spratu se nalazi Graviti Bar sa pogledom na Dablin i gde posetioci mogu popiti kriglu Ginisovog pića koja je uključena u cenu ulaznice.\n" +
                            "Zgrada u kojoj se nalazi Skladište izgrađena je 1902. godine kao fermentator za pivaru St. James's Gate (u varivo se dodaje kvasac). Dizajniran je u stilu Arhitektonske škole u Čikagu i bio je prva višespratna zgrada sa čeličnim okvirom koja je izgrađena u Irskoj. Zgrada je neprekidno korišćena kao postrojenje za fermentaciju Pivare sve do njenog zatvaranja 1988. godine, kada je završeno novo postrojenje za fermentaciju u blizini reke Lifi.\n" +
                            "Godine 1997. odlučeno je da se zgrada pretvori u Guinness Storehouse, zamenivši Guinness Hop Store kao centar za posetioce Pivare. Redizajn zgrade je preduzela britanska dizajnerska firma Imagination u saradnji sa arhitektonskom firmom RKD sa sedištem u Dablinu, a Storehouse je otvoren za javnost 2. decembra 2000. U 2006-08 je izgrađeno novo krilo, a Euro 2,5 miliona je uloženo u multimedijalnu instalaciju vođenu tehnologijom uživo koja demonstrira moderan proces pivarstva za Guinness, koji je dizajnirao specijalista za dizajn muzeja iz Londona, Event Communications.\n" +
                            "U maju 2011. kraljica Elizabeta II i princ Filip posetili su Skladište u okviru državne posete Irskoj.\n",
                    false, "", "", "https://bankbrokers.us/wp-content/uploads/2021/09/guinness-storehouse-case-study.png",
                    "https://media-exp1.licdn.com/dms/image/C4E1BAQF3Y8Evlt-TrA/company-background_10000/0?e=2159024400&v=beta&t=2Ueh1TK4XJU4lBqRttfizvcv2UbrHrpv-p7Nw_ESd7c",
                    "https://cdn.getyourguide.com/img/location/56b20b53c1aae.jpeg/99.jpg");
            Sight sight6=new Sight("Prsten Kerija", "The Ring of Kerry", "The Ring of Kerry is a 179-kilometre-long (111-mile) circular tourist route in County Kerry, south-western Ireland. Clockwise from Killarney it follows the N71 to Kenmare, then the N70 around the Iveragh Peninsula to Killorglin – passing through Sneem, Waterville, Cahersiveen, and Glenbeigh – before returning to Killarney via the N72.\n" +
                    "Popular points include Muckross House (near Killarney), Staigue stone fort and Derrynane House, home of Daniel O'Connell. Just south of Killarney, Ross Castle, Lough Leane, and Ladies View (a panoramic viewpoint), all located within Killarney National Park, are major attractions located along the Ring. A more complete list of major attractions along the Ring of Kerry includes: Gap of Dunloe, Bog Village, Dunloe Ogham Stones, Kerry Woollen Mills, Rossbeigh Beach, Cahersiveen Heritage Centre, Derrynane House, Skellig Experience, Staigue Fort, Kenmare Lace, Moll's Gap, Ballymalis Castle, Ladies View, Torc Waterfall, Muckross House, The Blue Pool, Ross Castle, Ogham Stones, St Mary’s Cathedral, Muckross Abbey, Franciscan Friary, Kellegy Church, O’Connell Memorial Church, Sneem Church and Cemetery, Skellig Michael (off the coast), Beehive Cells and the Stone Pillars marking an important grave.\n",
                    "Prsten Kerija je 179 kilometara duga (111 milja) kružna turistička ruta u okrugu Keri, jugozapadna Irska. U smeru kazaljke na satu od Kilarnija prati N71 do Kenmera, zatim N70 oko poluostrva Ivera do Kilorglina – prolazeći kroz Snim, Votervil, Kahersiveen i Glenbi – pre nego što se vrati u Kilarni preko N72.\n" +
                            "Popularne tačke su kuća Mukros (blizu Kilarnija), kamena tvrđava Stejg i kuća Derinan, dom Danijela O'Konela. Južno od Kilarnija, Ross Castle, Lough Leane i Ladies Viev (panoramski vidikovac), svi koji se nalaze u Nacionalnom parku Killarnei, glavne su atrakcije koje se nalaze duž prstena. Potpuna lista glavnih atrakcija duž Prstena Keri uključuje: Gap of Dunloe, Bog Village, Dunloe Ogham Stones, Kerri Voolen Mills, Rossbeigh Beach, Cahersiveen Heritage Centre, Derrinane House, Skellig Ekperience, Staigue Fort, Kenmare Lace, Moll's Gap , Dvorac Balimalis, Ladies Viev, Torc vodopad, Muckross House, Plavi bazen, Ross Castle, Ogham Stones, Katedrala Svete Marije, Muckross Abbei, Franjevački samostan, Kellegi crkva, O'Connell Memorijal crkva, Sneem crkva i groblje, Skellig Michael ( kod obale), ćelije košnica i kameni stubovi koji obeležavaju važan grob.\n",
                    false, "", "", "https://s27363.pcdn.co/wp-content/uploads/2020/05/Ring-of-Kerry-Ireland.jpg.optimal.jpg",
                    "https://cdn-assets.alltrails.com/static-map/production/at-map/38041278/trail-ireland-county-kerry-ring-of-kerry-scenic-drive-at-map-38041278-1652399743-414x200-2.png",
                    "https://www.divergenttravelers.com/wp-content/uploads/Ultimate-Ring-of-Kerry-Itinerary.jpg");
            daos.insertSight(sight1);
            daos.insertSight(sight2);
            daos.insertSight(sight3);
            daos.insertSight(sight4);
            daos.insertSight(sight5);
            daos.insertSight(sight6);
            myAppDatabase.close();
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                //DaoCity daoc= myAppDatabase.getDaoCity();
                //System.out.println("gradovi: "+ daoc.getCities().get(8));
            }
        }
    }


}
