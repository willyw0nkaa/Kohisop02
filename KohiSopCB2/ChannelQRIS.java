class ChannelQRIS implements ChannelPembayaran {
    @Override
    public double getDiskon() { return 0.05; }
    
    @Override
    public double getBiayaAdmin() { return 0.0; }
    
    @Override
    public String getNama() { return "QRIS"; }
    
    @Override
    public boolean perluSaldo() { return true; }
}