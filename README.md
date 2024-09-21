# RandomTrainSimWorld
Random route/locomotive/weather chooser for Train Sim World.
Containing every routes/locomotives/weathers of latest Train Sim World 3.

## Requirement
This program is created in OpenJDK 18, OpenJFX 18 and Gradle.

## Install
### For Windows
For Windows, I'm providing installer called ```RandomTrainSimWorld.msi```.
1. Download ```RandomTrainSimWorld.msi``` from release.
2. Run it.
3. Create shortcut to ```C:\Program Files\RandomTrainSimWorld\RandomTrainSimWorld.exe```
4. Run shortcut
5. Finish!

### For the other OS
For the other OS, we do not provide executable binary.
Please download the source code and build with Gradle Wrapper:
```
git clone https://github.com/FreddyYJ/RandomTrainSimWorld.git
cd RandomTrainSimWorld
./gradlew jpackage
```
After running these commands, an executable binary will be stored in `build/jpackage/RandomTrainSimWorld`.
An installer will be stored in `build/jpackage`.

## Docs
Documentation: https://freddyyj.github.io/RandomTrainSimWorld/

## Changelog
v1.1.0
* Update to Train Sim World 5
* Add three new routes
* Add new locomotives
* Update some locmotive lists in some routes

v1.0.12
* Add new locomotives 'DB BR 101 Expert' and 'DB BR 286.1 Bpmmbdzf' in suitable routes

v1.0.11
* Add new locomotive 'ScR Class 158' in Edinburgh Glasgow and Cathcart Circle Line

v1.0.10
* Add new locomotive 'ScR Class 380' in Cathcart Circle Line
* Update locomotive lists in West Somerset Railway, Great Western Express(GWR), Cathcart Circle Line, Midland Main Line, Glossop Line, Blackpool Branches, London Overground and fife Circle Line

v1.0.9
* Add new locomotive 'DB BR 218' and 'DB BR 463.0 Bnrdzf Diesel' at available routes

v1.0.8
* Add new route 'LIRR Commuter'
* Add new locomotive 'M9 LIRR' in LIRR Commuter
* Add locomotives M3 LIRR and M7 LIRR in LIRR Commuter

v1.0.7
* Add new route 'Semmeringbahn'
* Add new locomotive 'ÖBB 1116 Taurus' in Semmeringbahn
* Add suitable locomotives to Semmeringbahn
* Add new locomotive 'ÖBB 1116 Taurus' in Bahnstrecke Salzburg-Rosenheim

v1.0.6
* Add new route 'Fife Circle Line'
* Add new locomotive 'SCR Class 170' in Fife Circle Line
* Add locomotive 'SCR Class 385' in Fife Circle Line

v1.0.5
* Add new route 'London Overground'
* Add new locomotive 'TFL Class 710/2 in London Overground
* Add locomotives in London Overground

v1.0.4
* Add new route 'BahnStrecke Salzburg-Rosenheim'
* Add new locomotive 'DB BR 111' in available routes
* Fix some locomotives

v1.0.3
* Add new route 'Berninalinie'
* Add new locomotive 'RhB Allegra ABe 8/12' in Berninalinie

v1.0.2
* Add new route 'Maintalbahn'
* Add locomotives for Maintalbahn

v1.0.0
* Update Gradle Wrapper to 8.3
* Update JDK and JavaFX version to 18
* Update to Train Sim World 4
* Add new routes 'S-Bahn Vorarlberg', 'Antelope Valley Line' and 'East Coast Main Line'
* Add new locomotives 'ÖBB 4024 S-Bahn' and 'Railpool BR 193 Vectron' to S-Bahn Vorarlberg
* Add new locomotives 'Metrolink F125' and 'Metrolink Rotem Bi-Level Cab Car' to Antelope Valley Line
* Add new locomotives 'LNER Class 801' and 'LNER Class A3 Flying Scotsman' to East Coast Main Line
* Add new locomotive 'Railpool BR 193 Vectron' to such German routes
* Remove 'DB BR 204' from Nahverkehr Dresden

v0.7.11
* Add new locomotive 'NS ES44AC Heritage' in Horseshoe Curve

v0.7.10
* Add new locomotive 'TL Class 700/0' in Southeastern High Speed

v0.7.9
* Add new route 'Glossop Line'
* Add new locomotive 'NT Class 323' in Glossop Line
* Add appropriate locomotives in Glossop Line
* Add 'GRN Class 33' in Southeastern High Speed

v0.7.8
* Add new route 'Peak Forest Railway'
* Add locomotives 'LMS Jubilee Class' and 'LMS Stanier 8F Class' in Peak Forest Railway
* Add new locomotive 'LMS Fowler 4F Class' in Peak Forest Railway

v0.7.7
* Add new route 'Midland Main Line'
* Add new locomotives 'EMT Class 158/0' and 'EMT Class 125(43) HST' in Midland Main Line
* Add locomotive 'LMS Jubilee Class', 'EPX Class 37/7', 'BR Class 45/1 BLU' and 'EWS Class 66' in Midland Main Line
* Add locomotive 'LMS Jubilee Class' and 'BR Class 37/5 RSR' in Edinburgh Glasgow
* Add locomotive 'EPX Class 37/7' and 'BR Class 47/4 BLU' in Southeastern High Speed

v0.7.6
* Add new route 'Niddertalbahn'
* Add new locomotive 'DB BR 628.2' and 'DBB BR 365' in Niddertalbahn
* Add new locomotibe 'DB BR 628.2' in Linke Rheinstrecke

v0.7.5
* Add new route 'Linke Rheinstrecke'
* Add new locomotive 'DB BR 103' in Linke Rheinstrecke and Bahnstrecke Bremen-Oldenburg
* Add locomotives 'DB BR 110', 'DB BR 463.0 Bnrdzf' and 'DB BR 401 ICE 1' in Linke Rheinstrecke

v0.7.4
* Add new locomotive 'LMS Jubilee Class' in West Cornwall Local

v0.7.3
* Add new route 'Northeast Corridor'
* Add new locomotives 'ALP-46', 'Acela Express', 'Multi-Level Commuter Cab Car' and 'Amfleet Cab Car' in Northeast Corridor
* Add a locomotive 'ACS-64' in Northeast Corridor
* Add a new locomotive 'UP SD70ACe Heritage' in Sherman Hill
* Add a new locomotive 'Acela Express' in Boston Sprinter
* Change config file name to `randomtrainsimworld.json`

v0.7.2
* Add new route 'Edinburgh Glasgow'
* Add new locomotive 'ScR Class 385' in Edinburgh Glasgow
* Add locomotives 'ScR Class 314' and 'EWS Class 66' in Edinburgh Glasgow

v0.7.1
* Add new route 'BahnStrecke Bremen-Oldenburg'
* Add new locomotives 'Press BR 155', 'DB BR 110' and 'DB BR 463.0 Bnrdzf' in BahnStrecke Bremen-Oldenburg
* Add locomotives 'DB BR 110', 'DB BR 425', 'DB BR 423' and 'DB BR 101' in Schnellfahrstrecke Köln
* Add locomotive 'DB BR 110' in Haupstrecke Rhein-Ruhr, Main-Spessart Bahn, Hauptstrecke München-Augsburg, Rhein-Ruhr Osten and Nahverkehr Dreseden

v0.7.0
* Add new route 'Birmingham Cross-City'
* Add new locomotive 'WMR Class 323' to Birmingham Cross-City
* Add locomotives 'GWR Class 125(43) HST', 'EWS Class 66' and 'BR Class 37/5 RF' to Birmingham Cross-City
* Update Java version to 18
* Update JavaFX version to 18

v0.6.2
* Add new locomotive 'F7 Santa Fe' to Cajon Pass
* Add new locomotive 'Dispolok ES-64 U2' to Schnellfahrstrecke Kassel
* Add new locomotive 'EWS Class 66' to Southeastern High Speed

v0.6.1
* Add locomotive 'RhB GE 4/4 II Anniv.' to Arosa Linie

v0.6.0
* Update to Train Sim World 3
* Add new routes 'Cajon Pass' and 'Schnellfahrstrecke Kassel', and their locomotives
* Update locomotive lists for each routes
* Add new weather 'dynamic'
* Remove 2 from project name, to support later series

v0.5.13
* Add new route 'Isle of Wight 2022'
* Add new locomotive 'BR Class 484'

v0.5.12
* Add new route 'Spirit of Steam'
* Add new locomotive 'LMS Jubilee Class' and 'LMS Stanier 8F Class'

v0.5.11
* Add new route 'Horseshoe Curve'
* Add new locomotive 'NS ES44AC' and 'NS GP38-2'

v0.5.10
* Add new route 'S-Bahn Zentralschweiz'
* Add new locomotive 'SBB RABe 513' in S-Bahn Zentralschweiz

v0.5.9
* Add Harlem Line
* Add new locomotives 'M3A MTA' and 'M7A MTA' in Harlem Line

v0.5.8
* Add New Journey Expansion DLC
* Add new locomotive 'SD40 CSX' at Sand Patch Grade
* Add locomotive 'DB BR 423' and 'DB BR 425' at Schnellfahrstrecke Köln
* Add new locomotive '1972 Mk.II Tube Stock-AL' at London Underground Bakerloo

v0.5.7
* Remove 'DB BR 767.2 DABpbzfa' from german routes(duplicate with DB BR 766.2)
* Add 'MRCE ES-64 U2' and 'DB BR 155' in Nahverkehr Dresden
* Add new route 'Tharandter Rampe'
* Add new locomotives 'DB BR 612' and 'RP BR 185.6' in Tharandter Rampe
* Add locomotives 'DB BR 143, 766.2, 363, 112.1, 187, 185.2, 155', 'MRCE BR 185.5', 'MRCE ES-64 U2', 'DB G6' and 'S-Bahn BR 1442 "Talent 2"' in Tharandter Rampe

v0.5.6
* Add new locomotive 'DB G6' in Rhein-Ruhr Osten

v0.5.5
* Add new route 'Sherman Hill'
* Add new locomotive 'UP SD70ACe' in Sherman Hill
* Add locomotive 'UP SD40-2' in Sherman Hill and Sand Patch Grade

v0.5.4
* Add new route 'West Cornwall Local'
* Add new locomotive 'BR Class 150' and 'BR Class 37/5' in West Cornwall Local
* Add BR Class 40, 45/1, 47/4, 08, 101 in West Cornwall Local
* Add BR Class 40, 37/5, 101, 31/1, 45/1 in West Somerset Railway

v0.5.3
* Add new locomotive 'SN Class 313/4' in East Coastway and London Commuter

v0.5.2
* Update default JSON encoding to UTF-16

v0.5.1
* Add new locomotive '1938 Heritage Tube Stock' in London Bakerloo Line
* Add new locomotive 'DB BR 187' in all German Routes

v0.5.0
* Add new Route 'London Commuter'
* Add new Locomotive 'GX Class 387/2' in London Commuter
* Add locomotives 'SN Class 377/4', 'SEB Class 375/9' and 'EWS Class 66' in London Commuter
* Fix non-ASCII characters in routes and locomotives
* Update dependencies to up-to-date

v0.4.4
* Fix unit tests fail

v0.4.3
* Remove locomotive 'DB BR 155' from Nahverkehr Dresden

v0.4.2
* Add new route 'Nahverkehr Dreseden'
* Add appropriate locomotives in Nahverkehr Dresden
* Update dependencies to latest version

v0.4.1
* Add new route 'Northeast Corridor'
* Add new locomotive 'ACS-64', 'MBTA F40PH-3C' and 'MBTA CTC-3'

v0.4.0
* Update JDK and OpenJFX version to 16, Gradle version to 7.1.1
* Update dependencies and plugins version to latest
* Change internal String data to object(class)
* Fix and improve JUnit tests

v0.3.2
* Add new route 'Cane Creek'
* Add new locomotives 'UP SD40-2' and 'UP AC4400CW' in Cane Creek

v0.3.1
* Add new route 'Cathcart Circle Line'
* Add new locomotives 'ScR Class 314' in Cathcart Circle Line
* Add locomotives 'BR Class 37/5 RF', 'BR Class 31/1 BLU', 'BR Class 47/4 BLU', 'BR Class 45/1 BLU' in Cathcart Circle Line
* Change locomotives 'RF/BLU Class XX' to 'BR Class XX RF/BLU' in Southeastern High Speed

v0.3.0
* Add new route 'Hauptstrecke Hamburg-Lübeck'
* Add new locomotives 'DB BR 112.1' and 'MRCE ES-64 U2' in Hauptstrecke Hamburg-Lübeck
* Add locomotives 'DB BR 185.2', 'MRCE BR 185.5', 'DB BR 146.2', 'DB BR 766.2 DBpbzfa', 'DB BR 767.2 DABpbzfa', 'DB BR 101', 'DB BR 182', and 'DB BR 155' in Hauptstrecke Hamburg-Lübeck
* Add locomotive 'MRCE ES-64 U2', 'DB BR 767.2 DABpbzfa' and 'DB BR 112.1' in Hauptstrecke München-Augsburg
* Add locomotive 'DB BR 767.2 DABpbzfa', 'DB BR 112.1', 'DB BR 422' and 'MRCE BR 185.5' in Schnellfahrstrecke Köln
* Add locomotive 'DB BR 767.2 DABpbzfa' and 'DB BR 112.1' in Hauptstrecke Rhein-Ruhr, Main-Spessart Bahn and Ruhr-Sieg Nord
* Add locomotive 'MRCE ES-64 U2' and 'DB BR 112.1' in Hauptstrecke Rhein-Ruhr
* Divide 'Rapid Transit' to 'Rapid Transit (2017)' and 'Rapid Transit (2021)'
* Add locomotives 'DB BR 767.2 DABpbzfa', 'DB BR 766.2 DBpbzfa', 'S-Bahn BR 1442 "Talent 2"' and 'DB BR 182' in Rapid Transit (2017)
* Add locomotives 'DB BR 767.2 DABpbzfa', 'DB BR 766.2 DBpbzfa', 'S-Bahn BR 1442 "Talent 2"', 'DB BR 143' and 'DB BR 182' in Rapid Transit (2021)
* Change runtime files in release
* Add JUnit test for some basic classes
* Fix no vertical scroll in locomotive box

v0.2.7
* Add new locomotive 'DB BR 101' in Haupstrecke Rhein-Ruhr

v0.2.6
* Add new route 'Clinchfield Railroad'
* Add new locomotives 'SD40 CRR' and 'F7 CRR' in Clinchfield Railroad
* Add new locomotive 'SD40 CRR' in Sand Patch Grade
* Update License to MIT License

v0.2.5
* Add new route 'Arosa Linie'
* Add new locomotive 'RhB GE 4/4 II' in Arosa Linie

v0.2.4
* Add new route 'Great Western Express (BR)'
* Add new locomotive 'BR Class 08', 'BR Class 101', 'BR Class 31/1', 'BR Class 47/4', 'BR Class 45/1' and 'BR Class 52 "Western"'
* Change route name 'Great Western Express' to 'Great Western Express (GWR)'
* Remove ```"``` from front and end of locomotives
* Upgrade JUnit version to 5.7.1
* Add JUnit test for LocomotiveReader

v0.2.3
* Add new locomotive 'SEW Class 465/9' in Southeastern High Speed

v0.2.2
* Fix issue when running program after closing program without saving change
* Fix issue in saving and loading locomotive and weather

v0.2.1: Minor issue in saving and loading route
* Fix issue in saving and loading route from savefile
* Update information popup page

v0.2.0: Runtime image & Southeastern High Speed
* Create Java Runtime, so don't have to run command to run program
* Add new route 'Southeastern High Speed'
* Add new locomotive 'SEB Class 375/9' and 'SEB Class 395 'Javelin'' in Southeastern High Speed
* Add lots of UK locomotives in Southeastern High Speed, it includes this locomotives in timetable
* Improve and add exceptions, improved exception throws and catch

v0.1.5: Small Issue Fix at closing
* Fix issue when closing proram without savefile

v0.1.4: LGV Méditerranée
* Add new route 'LGV Méditerranée'
* Add new locomotive 'TGV Duplex 200 CM' in LGV Méditerranée

v0.1.3: Minor Locomotive Issue
* Remove locomotive 'DB BR 106 ICE 3M' from Hauptstrecke Rhein-Ruhr

v0.1.2: Heavy Freight DLC & C40-8W
* Add new locomotive 'C40-8W CSX' in Sand Patch Grade
* Add new locomotives 'BR Class 08 BLU' and 'BR Class 40 BLU' in Northern-Trans Pennine
* Add new locomotives 'BR Class 37/5 RF', 'BR Class 31/1 BLU', 'BR Class 47/4 BLU', 'BR Class 45/1 BLU' and 'BR Class 40 BLU' in East Coastway

v0.1.1: Major issue fix
* Fixed issue when autosave config at closing after save file load

v0.1.0: Change from RandomTrainSimWorld
* Change Project Maven to Gradle.
* Remove all routes and locomotives in fxml, move to json.
