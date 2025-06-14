class ChannelTunai implements ChannelPembayaran {
    @Override
    public double getDiskon() { return 0.0; }
    
    @Override
    public double getBiayaAdmin() { return 0.0; }
    
    @Override
    public String getNama() { return "Tunai"; }
    
    @Override
    public boolean perluSaldo() { return false; }
}