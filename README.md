# PR Software Engineering WS 22 Team 2

## Digital Twin of a Smart Room
Die Applikation “Smart Room” soll als digitaler Zwilling eines Raumes dienen, dessen Raumausstattung, wie etwa Türen, Fenster, Beleuchtung und Ventilatoren, manuell lenkbar ist. Zu dieser Raumausstattung gehören Sensoren, die, die Anzahl der Personen in einem Raum sowie den CO2-Wert als auch die Raumtemperatur erkennen können. Neben den manuellen Funktionen, wie etwa die Funktion, Türen zu öffnen und zu schließen, soll die Applikation auch sicherstellen, dass beim Auftreten gewisser Bedingungen, wie etwa die Anwesenheit mehr als 0 Personen in einem Raum, die Raumausstattung automatisch dementsprechend reagiert. Zudem dient die Applikation als Anzeige und graphische Visualisierung gewisser Daten, wie etwa vorhandene Räume, Raumausstattung, Temperaturen, CO2-Werte und Anzahl der Personen. Die Informationen über die Räume werden entweder manuell angelegt oder mittels einer .CSV-Datei importiert, die dann in eine Datenbank verwandelt wird, auf die die Applikation Zugriff hat.
### Team Members

| Student ID | First Name | Last Name     | E-Mail                       | github user |
|------------|------------|---------------|------------------------------|-------------|
| 01607879   | Nuray      | Seker         | nurayseker_54@hotmail.com    | nvrxy       |
| 11714476   | Elma       | Buljina       | buljina.elma.98@gmail.com    | e1maa       |
| 11904290   | Abir       | Sikder        | sikder.abir@gmail.com        | realabir    |
| 11713187   | Stefan     | Pilgerstorfer | stefan.pilgerstorfer@gmx.net | buegi       |

### Project Documentation
Please use this link to jump directly to the complete [Project Documentaton](/documentation/Project Documentation.docx)


### GUI Overview (Mock-Up)
![Mockup Sample](/documentation/mockup/figma_ui_mockup_sample.png)


For the clickable prototype, click on the following link:
https://www.figma.com/file/Ae03yyrc6oA21e3DZcrQh2/Smart-Room?node-id=0%3A1

### Installation
The Project documentation includes Installation Instructions. For faster finding, we list the basic Installation steps here.

- Clone this repository
- Perform a maven install of the parent project
- If you are developer you can for example use IntelliJ or Eclipse to run the apps in the modules:
    - Server (server/src/main/java/at/jku/DigitalTwinServer)
    - Client (client/src/main/java/at/jku/DigitalTwinApp)
    - Simulator (simulator/src/main/java/at/jku/Simulator)
    - Hasher (hasher/src/main/java/at/jku/HasherApp)
- Always start the server first (Backend)
- If you just want to run the generated jar files, which are located in the target folders of the corresponding modules, please use a command like this:
  ![Powershell](/documentation/jar_run/powershell.png)
For other modules just change every occurence of server in the command to client or simulator or hasher
It for sure depends on the location of the cloned repository and the installed/used java version.

### UML Diagram for Server Module
![UML Diagram](/documentation/diagrams/Server-UML.png)

### UML Diagram for Client Module
![UML Diagram](/documentation/diagrams/Client-UML.png)

### UML Diagram for Simulator Module
![UML Diagram](/documentation/diagrams/Simulator-UML.png)