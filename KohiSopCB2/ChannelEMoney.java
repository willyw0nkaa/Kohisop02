class ChannelEMoney implements ChannelPembayaran {
    @Override
    public double getDiskon() { return 0.07; }
    
    @Override
    public double getBiayaAdmin() { return 20.0; }
    
    @Override
    public String getNama() { return "eMoney"; }
    
    @Override
    public boolean perluSaldo() { return true; }
}