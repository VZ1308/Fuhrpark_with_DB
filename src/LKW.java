public class LKW extends Fahrzeug {
    private int ladegewicht;

    public LKW(String marke, String modell, int baujahr, int kilometerstand, int ladegewicht) {
        super(marke, modell, baujahr, kilometerstand);
        this.ladegewicht = ladegewicht;
    }

    @Override
    public String getTableName() {
        return "LKW";
    }

    public String getInsertSQL() {
        return "INSERT INTO Fahrzeug (marke, modell, baujahr, kilometerstand) VALUES ('" +
                getMarke() + "', '" + getModell() + "', " + getBaujahr() + ", " + getKilometerstand() + ")";
    }

    public String getInsertSpecificSQL(int id) {
        return "INSERT INTO LKW (id, marke, modell, baujahr, kilometerstand, ladegewicht) VALUES (" +
                id + ", '" + getMarke() + "', '" + getModell() + "', " + getBaujahr() + ", " + getKilometerstand() + ", " + ladegewicht + ")";
    }

    public void anzeigen() {
        System.out.println("Typ: LKW, Marke: " + getMarke() +
                ", Modell: " + getModell() +
                ", Baujahr: " + getBaujahr() +
                ", Kilometerstand: " + getKilometerstand() +
                ", Ladegewicht: " + ladegewicht + " kg");
    }
}
