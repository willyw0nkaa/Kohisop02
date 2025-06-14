import java.util.*;

class Dapur {
    private PriorityQueue<ItemPesanan> antrianMakanan;
    private Stack<ItemPesanan> antrianMinuman;
    
    public Dapur() {
        antrianMakanan = new PriorityQueue<>((a, b) -> 
            Double.compare(b.getMenu().getHarga(), a.getMenu().getHarga()));
        
        antrianMinuman = new Stack<>();
    }
    
    public void tambahPesanan(ArrayList<ItemPesanan> daftarPesanan) {
        for (ItemPesanan item : daftarPesanan) {
            if (item.getMenu() instanceof Makanan) {
                antrianMakanan.offer(item);
            } else if (item.getMenu() instanceof Minuman) {
                antrianMinuman.push(item);
            }
        }
    }
    
    public ArrayList<ItemPesanan> getAntrianMakanan() {
        return new ArrayList<>(antrianMakanan);
    }
    
    public ArrayList<ItemPesanan> getAntrianMinuman() {
        ArrayList<ItemPesanan> daftarMinuman = new ArrayList<>();
        Stack<ItemPesanan> tempStack = new Stack<>();
        
        while (!antrianMinuman.isEmpty()) {
            tempStack.push(antrianMinuman.pop());
        }

        while (!tempStack.isEmpty()) {
            ItemPesanan item = tempStack.pop();
            daftarMinuman.add(item);
            antrianMinuman.push(item);
        }
        
        return daftarMinuman;
    }
    
    public void prosesPesanan() {
        System.out.println("\n=== PROSES DAPUR ===");
        System.out.println("Tim Makanan:");
        ArrayList<ItemPesanan> makananList = getAntrianMakanan();
        if (makananList.isEmpty()) {
            System.out.println("Tidak ada pesanan makanan.");
        } else {
            int no = 1;
            for (ItemPesanan item : makananList) {
                System.out.printf("%d. %s - %dx - Rp%.0f%n", 
                    no++, item.getMenu().getNama(), 
                    item.getKuantitas(), item.getMenu().getHarga());
            }
        }
        
        System.out.println("\nTim Minuman:");
        ArrayList<ItemPesanan> minumanList = getAntrianMinuman();
        if (minumanList.isEmpty()) {
            System.out.println("Tidak ada pesanan minuman.");
        } else {
            int no = 1;
            for (ItemPesanan item : minumanList) {
                System.out.printf("%d. %s - %dx - Rp%.0f%n", 
                    no++, item.getMenu().getNama(), 
                    item.getKuantitas(), item.getMenu().getHarga());
            }
        }
    }
    
    public void kosongkanAntrian() {
        antrianMakanan.clear();
        antrianMinuman.clear();
    }
}