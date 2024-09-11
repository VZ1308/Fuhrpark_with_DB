public class PKW extends Fahrzeug {
    private int anzahlTueren;

    public PKW(String marke, String modell, int baujahr, int kilometerstand, int anzahlTueren) {
        super(marke, modell, baujahr, kilometerstand);
        this.anzahlTueren = anzahlTueren;
    }

    @Override
    public String getTableName() {
        return "PKW";
    }

    public String getInsertSQL() {
        return "INSERT INTO Fahrzeug (marke, modell, baujahr, kilometerstand) VALUES ('" +
                getMarke() + "', '" + getModell() + "', " + getBaujahr() + ", " + getKilometerstand() + ")";
    }

    public String getInsertSpecificSQL(int id) {
        return "INSERT INTO PKW (id, marke, modell, baujahr, kilometerstand, anzahlTueren) VALUES (" +
                id + ", '" + getMarke() + "', '" + getModell() + "', " + getBaujahr() + ", " + getKilometerstand() + ", " + anzahlTueren + ")";
    }

    public void anzeigen() {
        System.out.println("Typ: PKW, Marke: " + getMarke() +
                ", Modell: " + getModell() +
                ", Baujahr: " + getBaujahr() +
                ", Kilometerstand: " + getKilometerstand() +
                ", Anzahl der Türen: " + anzahlTueren);
    }
}
