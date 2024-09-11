import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class MenuHandling {

    public static void startMenu(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            while (true) {
                System.out.println("\n--- Fahrzeugverwaltung ---");
                System.out.println("1. Fahrzeug hinzufügen");
                System.out.println("2. Alle Fahrzeuge anzeigen");
                System.out.println("3. Mitarbeiter hinzufügen");
                System.out.println("4. Fahrzeug einem Mitarbeiter zuweisen");
                System.out.println("5. Beenden");
                System.out.print("Wählen Sie eine Option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Zeilenumbruch nach der Eingabe überspringen

                switch (choice) {
                    case 1:
                        addFahrzeug(scanner, stmt);
                        break;

                    case 2:
                        showFahrzeuge(stmt);
                        break;

                    case 3:
                        addMitarbeiter(scanner, stmt);
                        break;

                    case 4:
                        assignFahrzeugToMitarbeiter(scanner, stmt);
                        break;

                    case 5:
                        System.out.println("Programm wird beendet.");
                        return;

                    default:
                        System.out.println("Ungültige Option.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            try {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("Fehler beim Schließen des Statements: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private static void addFahrzeug(Scanner scanner, Statement stmt) throws SQLException {
        Fahrzeug fahrzeug = getFahrzeug(scanner);
        if (fahrzeug != null) {
            String insertSQL = fahrzeug.getInsertSQL();
            stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                String specificSQL = fahrzeug.getInsertSpecificSQL(id);
                stmt.executeUpdate(specificSQL);
                System.out.println("Fahrzeug erfolgreich hinzugefügt!");
            }
        }
    }

    // Tabelle Fahrzeug wird mit anderen Tabellen über die id verbunden
    private static void showFahrzeuge(Statement stmt) throws SQLException {
        String query = "SELECT Fahrzeug.id, Fahrzeug.marke, Fahrzeug.modell, Fahrzeug.baujahr, Fahrzeug.kilometerstand, " +
                "Fahrrad.hatGepaecktraeger, Motorrad.hubraum, PKW.anzahlTueren, LKW.ladegewicht " +
                "FROM Fahrzeug " +
                "LEFT JOIN Fahrrad ON Fahrzeug.id = Fahrrad.id " +
                "LEFT JOIN Motorrad ON Fahrzeug.id = Motorrad.id " +
                "LEFT JOIN PKW ON Fahrzeug.id = PKW.id " +
                "LEFT JOIN LKW ON Fahrzeug.id = LKW.id";
        ResultSet rs = stmt.executeQuery(query);

        // Extrahieren und Anzeigen der Daten
        while (rs.next()) {
            int id = rs.getInt("id");
            String fahrzeugMarke = rs.getString("marke");
            String fahrzeugModell = rs.getString("modell");
            int fahrzeugBaujahr = rs.getInt("baujahr");
            int fahrzeugKilometerstand = rs.getInt("kilometerstand");

            System.out.println("ID: " + id + ", Marke: " + fahrzeugMarke + ", Modell: " + fahrzeugModell +
                    ", Baujahr: " + fahrzeugBaujahr + ", Kilometerstand: " + fahrzeugKilometerstand);

            // Überprüfen des Fahrzeugtyps und Anzeigen von Details
            if (rs.getBoolean("hatGepaecktraeger")) {
                System.out.println("Typ: Fahrrad, Gepäckträger: " + rs.getBoolean("hatGepaecktraeger"));
            } else if (rs.getInt("hubraum") > 0) {
                System.out.println("Typ: Motorrad, Hubraum: " + rs.getInt("hubraum") + " ccm");
            } else if (rs.getInt("anzahlTueren") > 0) {
                System.out.println("Typ: PKW, Anzahl der Türen: " + rs.getInt("anzahlTueren"));
            } else if (rs.getInt("ladegewicht") > 0) {
                System.out.println("Typ: LKW, Ladegewicht: " + rs.getInt("ladegewicht") + " kg");
            }
            System.out.println("-----------------------------");
        }
    }

    private static void addMitarbeiter(Scanner scanner, Statement stmt) throws SQLException {
        System.out.print("Vorname des Mitarbeiters: ");
        String vorname = scanner.nextLine();
        System.out.print("Nachname des Mitarbeiters: ");
        String nachname = scanner.nextLine();
        System.out.print("Geburtsdatum (YYYY-MM-DD): ");
        String geburtsdatum = scanner.nextLine();

        String insertSQL = "INSERT INTO Mitarbeiter (vorname, nachname, geburtsdatum) VALUES ('" +
                vorname + "', '" + nachname + "', '" + geburtsdatum + "')";
        stmt.executeUpdate(insertSQL);
        System.out.println("Mitarbeiter erfolgreich hinzugefügt!");
    }

    private static void assignFahrzeugToMitarbeiter(Scanner scanner, Statement stmt) throws SQLException {
        System.out.print("ID des Fahrzeugs: ");
        int fahrzeugId = scanner.nextInt();
        System.out.print("ID des Mitarbeiters: ");
        int mitarbeiterId = scanner.nextInt();

        String insertSQL = "INSERT INTO Nutzung (fahrzeug_id, mitarbeiter_id) VALUES (" +
                fahrzeugId + ", " + mitarbeiterId + ")";
        stmt.executeUpdate(insertSQL);
        System.out.println("Fahrzeug erfolgreich dem Mitarbeiter zugewiesen!");
    }

    private static Fahrzeug getFahrzeug(Scanner scanner) {
        System.out.println("Fahrzeugtypen: ");
        System.out.println("1. Fahrrad");
        System.out.println("2. Motorrad");
        System.out.println("3. PKW");
        System.out.println("4. LKW");
        System.out.print("Wählen Sie einen Typ: ");
        int typ = scanner.nextInt();
        scanner.nextLine(); // Zeilenumbruch nach der Eingabe überspringen

        System.out.print("Marke des Fahrzeugs: ");
        String marke = scanner.nextLine();
        System.out.print("Modell des Fahrzeugs: ");
        String modell = scanner.nextLine();
        System.out.print("Baujahr des Fahrzeugs: ");
        int baujahr = scanner.nextInt();
        System.out.print("Kilometerstand des Fahrzeugs: ");
        int kilometerstand = scanner.nextInt();


        switch (typ) {
            case 1:
                System.out.print("Hat das Fahrrad einen Gepäckträger? (true/false): ");
                boolean hatGepaecktraeger = scanner.nextBoolean();
                return new Fahrrad(marke, modell, baujahr, kilometerstand, hatGepaecktraeger);

            case 2:
                System.out.print("Hubraum des Motorrads: ");
                int hubraum = scanner.nextInt();
                return new Motorrad(marke, modell, baujahr, kilometerstand, hubraum);

            case 3:
                System.out.print("Anzahl der Türen des PKW: ");
                int anzahlTueren = scanner.nextInt();
                return new PKW(marke, modell, baujahr, kilometerstand, anzahlTueren);

            case 4:
                System.out.print("Ladegewicht des LKW in kg: ");
                int ladegewicht = scanner.nextInt();
                return new LKW(marke, modell, baujahr, kilometerstand, ladegewicht);

            default:
                System.out.println("Ungültiger Typ.");
                return null;
        }
    }
}
