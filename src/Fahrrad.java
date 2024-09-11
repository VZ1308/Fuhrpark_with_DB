public class Fahrrad extends Fahrzeug {
    private boolean hatGepaecktraeger;

    public Fahrrad(String marke, String modell, int baujahr, int kilometerstand, boolean hatGepaecktraeger) {
        super(marke, modell, baujahr, kilometerstand);
        this.hatGepaecktraeger = hatGepaecktraeger;
    }

    @Override
    public String getTableName() {
        return "Fahrrad";
    }

    @Override
    public String getInsertSQL() {
        return "INSERT INTO Fahrzeug (marke, modell, baujahr, kilometerstand) VALUES ('" +
                getMarke() + "', '" + getModell() + "', " + getBaujahr() + ", " + getKilometerstand() + ")";
    }

    @Override
    public String getInsertSpecificSQL(int id) {
        return "INSERT INTO Fahrrad (id, marke, modell, baujahr, kilometerstand, hatGepaecktraeger) VALUES (" +
                id + ", '" + getMarke() + "', '" + getModell() + "', " + getBaujahr() + ", " + getKilometerstand() + ", " + hatGepaecktraeger + ")";
    }

    @Override
    public void anzeigen() {
        System.out.println("Typ: Fahrrad, Marke: " + getMarke() +
                ", Modell: " + getModell() +
                ", Baujahr: " + getBaujahr() +
                ", Kilometerstand: " + getKilometerstand() +
                ", Gepäckträger: " + hatGepaecktraeger);
    }
}
