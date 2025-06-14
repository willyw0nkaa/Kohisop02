abstract class Menu {
    protected String kode;
    protected String nama;
    protected double harga;
    
    public Menu(String kode, String nama, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }
    
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    
    public abstract double hitungPajak(boolean isMemberA);
    public abstract String getKategori();
}
