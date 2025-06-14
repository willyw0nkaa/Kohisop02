
class ItemPesanan {
    private Menu menu;
    private int kuantitas;
    private boolean isMemberA;

    public ItemPesanan(Menu menu, int kuantitas, boolean isMemberA) {
        this.menu = menu;
        this.kuantitas = kuantitas;
        this.isMemberA = isMemberA;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public double getTotalHarga() {
        return menu.getHarga() * kuantitas;
    }

    public double getPajak() {
        return getTotalHarga() * menu.hitungPajak(isMemberA);
    }

    public double getTotalDenganPajak() {
        return getTotalHarga() + getPajak();
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }
}
