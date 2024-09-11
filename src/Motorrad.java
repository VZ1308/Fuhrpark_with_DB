public class Motorrad extends Fahrzeug {
    private int hubraum;

    public Motorrad(String marke, String modell, int baujahr, int kilometerstand, int hubraum) {
        super(marke, modell, baujahr, kilometerstand);
        this.hubraum = hubraum;
    }

    @Override
    public String getTableName() {
        return "Motorrad";
    }

    @Override
    public String getInsertSQL() {
        return "INSERT INTO Fahrzeug (marke, modell, baujahr, kilometerstand) VALUES ('" +
                getMarke() + "', '" + getModell() + "', " + getBaujahr() + ", " + getKilometerstand() + ")";
    }

    public String getInsertSpecificSQL(int id) {
        return "INSERT INTO Motorrad (id, marke, modell, baujahr, kilometerstand, hubraum) VALUES (" +
                id + ", '" + getMarke() + "', '" + getModell() + "', " + getBaujahr() + ", " + getKilometerstand() + ", " + hubraum + ")";
    }
    @Override
    public void anzeigen() {
        System.out.println("Typ: Motorrad, Marke: " + getMarke() +
                ", Modell: " + getModell() +
                ", Baujahr: " + getBaujahr() +
                ", Kilometerstand: " + getKilometerstand() +
                ", Hubraum: " + hubraum + " ccm");
    }
}
