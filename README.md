# RandomTrainSimWorld2
A new random Train Sim World 2 picker based on RandomTSW.

This Program is based on RandomTrainSimWorld, a random picker for Train Sim World 2020 and 2. This program only include TSW2, not 2020.

## Requirement
This program is created in OpenJDK 16, OpenJFX 16 and Gradle.

## Install
### For Windows
For Windows, I'm providing installer called ```RandomTrainSimWorld2.msi```.
1. Download ```RandomTrainSimWorld2.msi``` from release.
2. Run it.
3. Create shortcut to ```C:\Program Files\RandomTrainSimWorld2\RandomTrainSimWorld2.exe```
4. Run shortcut
5. Finish!

### For Linux
For Linux, I'm providing runtime image. So, just run it in terminal.
1. Download ```RandomTrainSimWorld2``` from release.
2. Open terminal, and go to downloaded directory.
3. Run ```./RandomTrainSimWorld2```.
4. Finish!

## Docs
Documentation: https://freddyyj.github.io/RandomTrainSimWorld2/

## Note for New Journey Expansion
### Sand Patch Grade
New Journey Expansion DLC adds SD40 CSX as variation of SD40. But for convenience at selecting journey, it is only seperated at Sand Patch Grade.
### Schnellfahrstrecke Köln
New Journey Expantion DLC adds DB BR 423 at Schnellfahrstrecke Köln, added at RandomTSW2 as normally.
### London Underground Bakerloo
New Journey Expantion DLC adds 1972 Mk.II Tube Stock-AL as variation of 1972 Mk.II Tube Stock. But for convenience at selecting journey, it is seperated.
In game, original name of 1972 Mk.II Tube Stock-AL is 1972 Mk.II Tube Stock-LT.

## Changelog
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
