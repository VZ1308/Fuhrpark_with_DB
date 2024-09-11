import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreation {

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        // Tabelle für allgemeine Fahrzeugdaten erstellen
        String createFahrzeugTableSQL = "CREATE TABLE IF NOT EXISTS Fahrzeug (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "marke VARCHAR(50), " +
                "modell VARCHAR(50), " +
                "baujahr INT, " +
                "kilometerstand INT)";
        stmt.executeUpdate(createFahrzeugTableSQL);

        // Tabellen für spezifische Fahrzeugtypen erstellen
        createSpecificTable(stmt, "Fahrrad", "hatGepaecktraeger BOOLEAN");
        createSpecificTable(stmt, "Motorrad", "hubraum INT");
        createSpecificTable(stmt, "PKW", "anzahlTueren INT");
        createSpecificTable(stmt, "LKW", "ladegewicht INT");

        // Tabellen für Mitarbeiter und Nutzung erstellen
        createMitarbeiterTable(stmt);
        createNutzungTable(stmt);

        System.out.println("Tabellen erfolgreich erstellt.");
    }

    private static void createSpecificTable(Statement stmt, String tableName, String additionalColumns) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT PRIMARY KEY, " +
                "marke VARCHAR(50), " +
                "modell VARCHAR(50), " +
                "baujahr INT, " +
                "kilometerstand INT, " +
                additionalColumns + ", " +
                "FOREIGN KEY (id) REFERENCES Fahrzeug(id))";
        stmt.executeUpdate(createTableSQL);
    }

    private static void createMitarbeiterTable(Statement stmt) throws SQLException {
        String createMitarbeiterSQL = "CREATE TABLE IF NOT EXISTS Mitarbeiter (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "vorname VARCHAR(50), " +
                "nachname VARCHAR(50), " +
                "geburtsdatum DATE)";
        stmt.executeUpdate(createMitarbeiterSQL);
    }

    private static void createNutzungTable(Statement stmt) throws SQLException {
        String createNutzungSQL = "CREATE TABLE IF NOT EXISTS Nutzung (" +
                "fahrzeug_id INT, " +
                "mitarbeiter_id INT, " +
                "PRIMARY KEY(fahrzeug_id, mitarbeiter_id), " +
                "FOREIGN KEY (fahrzeug_id) REFERENCES Fahrzeug(id), " +
                "FOREIGN KEY (mitarbeiter_id) REFERENCES Mitarbeiter(id))";
        stmt.executeUpdate(createNutzungSQL);
    }
}
