# RandomTrainSimWorld2
A new random Train Sim World 2 picker based on RandomTSW.

This Program is based on RandomTrainSimWorld, a random picker for Train Sim World 2020 and 2. This program only include TSW2, not 2020.

## Requirement
This program is created in OpenJDK 15.0.2, OpenJFX 15.0.1 and Gradle.

## Install
1. Download ```RandomTrainSimWorld2.zip``` or ```RandomTrainSimWorld2.tar.gz``` from release.
2. unzip or untar it.
3. go in directory/folder called RandomTrainSimWorld2.
4. Run ```RandomTrainSimWorld2.exe``` for Windows or ```RandomTrainSimWorld2``` for Linux.
5. Finish!
6. (Optional) If you want to install in local bin(```C:\Program Files``` in Windows, ```/opt/``` in Linux), run ```RandomTrainSimWorld2-<version>.exe/.msi``` or ```RandomTrainSimWorld2-<version>-<architecture>.rpm/.deb```. It doesn't make link, so for comfortable usage, create link to it.

## Changelog
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
