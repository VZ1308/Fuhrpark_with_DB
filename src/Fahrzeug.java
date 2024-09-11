public abstract class Fahrzeug {
    private String marke;
    private String modell;
    private int baujahr;
    private int kilometerstand;

    // Konstruktor
    public Fahrzeug(String marke, String modell, int baujahr, int kilometerstand) {
        this.marke = marke;
        this.modell = modell;
        this.baujahr = baujahr;
        this.kilometerstand = kilometerstand;
    }

    public String getMarke() {
        return marke;
    }

    public String getModell() {
        return modell;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public int getKilometerstand() {
        return kilometerstand;
    }

    // Diese Methode gibt den Namen der spezifischen Tabelle in der Datenbank zurück
    public abstract String getTableName();

    // Abstrakte Methode, die den SQL-Befehl für das Einfügen eines neuen Fahrzeugs in die allgemeine 'Fahrzeug'-Tabelle zurückgeben soll
    public abstract String getInsertSQL();

    // Abstrakte Methode, die den SQL-Befehl zum Einfügen eines spezifischen
// Fahrzeugtyps in die entsprechende spezifische Tabelle (z.B. Fahrrad oder PKW) zurückgibt.
// Die 'id' bezieht sich auf die ID, die in der Haupttabelle 'Fahrzeug' generiert wurde.
    public abstract String getInsertSpecificSQL(int id);

// Diese Methode definiert, wie die Informationen des Fahrzeugs ausgegeben werden sollen.
    public abstract void anzeigen();
}
