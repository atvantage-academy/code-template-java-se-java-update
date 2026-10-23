module bestellwesen.csv {
    requires bestellwesen.modell;

    provides bestellwesen.export.Exportformat with bestellwesen.csv.CsvFormat;
}
