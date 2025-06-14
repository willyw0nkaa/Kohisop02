class Minuman extends Menu {
    public Minuman(String kode, String nama, double harga) {
        super(kode, nama, harga);
    }
    
    @Override
    public double hitungPajak(boolean isMemberA) {
        if (isMemberA) return 0.0;
        if (harga < 50) return 0.0;
        if (harga >= 50 && harga <= 55) return 0.08;
        return 0.11;
    }
    
    @Override
    public String getKategori() {
        return "Minuman";
    }
}

