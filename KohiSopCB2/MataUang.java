interface MataUang {
    double konversiDariIDR(double nilaiIDR);
    String getNama();
    String getSimbol();
}

class IDR implements MataUang {
    @Override
    public double konversiDariIDR(double nilaiIDR) { return nilaiIDR; }
    
    @Override
    public String getNama() { return "IDR"; }
    
    @Override
    public String getSimbol() { return "Rp"; }
}

class USD implements MataUang {
    @Override
    public double konversiDariIDR(double nilaiIDR) { return nilaiIDR / 15.0; }
    
    @Override
    public String getNama() { return "USD"; }
    
    @Override
    public String getSimbol() { return "$"; }
}

class JPY implements MataUang {
    @Override
    public double konversiDariIDR(double nilaiIDR) { return nilaiIDR * 10.0; }
    
    @Override
    public String getNama() { return "JPY"; }
    
    @Override
    public String getSimbol() { return "¥"; }
}

class MYR implements MataUang {
    @Override
    public double konversiDariIDR(double nilaiIDR) { return nilaiIDR / 4.0; }
    
    @Override
    public String getNama() { return "MYR"; }
    
    @Override
    public String getSimbol() { return "RM"; }
}
class EUR implements MataUang {
    @Override
    public double konversiDariIDR(double nilaiIDR) { return nilaiIDR / 14.0; }
    
    @Override
    public String getNama() { return "EUR"; }
    
    @Override
    public String getSimbol() { return "€"; }
}