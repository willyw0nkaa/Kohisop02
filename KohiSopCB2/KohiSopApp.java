import java.util.*;

public class KohiSopApp {
    private static Dapur dapur = new Dapur();
    private static ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Memulai sistem KohiSop...\n");
        
        for (int i = 1; i <= 3; i++) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("\t\t\tPELANGGAN KE - " + i);
            System.out.println("=".repeat(70));
            
            Transaksi transaksi = new Transaksi();
            transaksi.mulaiTransaksi();
            
            if (!transaksi.getDaftarPesanan().isEmpty()) {
                daftarTransaksi.add(transaksi);
                dapur.tambahPesanan(transaksi.getDaftarPesanan());
            }
            
            if (i < 3) {
                System.out.println("\nTekan Enter untuk melanjutkan ke pelanggan berikutnya...");
                scanner.nextLine();
            }
        }
        
        if (!daftarTransaksi.isEmpty()) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("\t\t\tPESANAN DIPROSES DI DAPUR");
            System.out.println("=".repeat(70));
            
            dapur.prosesPesanan();
            
            tampilkanRingkasanTransaksi();
        } else {
            System.out.println("\nTidak ada transaksi yang berhasil diselesaikan.");
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("            TERIMA KASIH TELAH MENGGUNAKAN SISTEM KOHISOP");
        System.out.println("=".repeat(70));
    }
    
    private static void tampilkanRingkasanTransaksi() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                        RINGKASAN TRANSAKSI HARI INI");
        System.out.println("=".repeat(70));
        
        double totalPendapatan = 0;
        int totalPelanggan = daftarTransaksi.size();
        int totalItemTerjual = 0;
        
        for (int i = 0; i < daftarTransaksi.size(); i++) {
            Transaksi transaksi = daftarTransaksi.get(i);
            Member member = transaksi.getMember();
            
            System.out.printf("Transaksi %d - %s%s%n", 
                i + 1, 
                member.getNamaMember(),
                member.punyaKarakterA() ? " (Member Premium)" : "");
            
            double totalTransaksi = 0;
            int jumlahItem = 0;
            
            for (ItemPesanan item : transaksi.getDaftarPesanan()) {
                totalTransaksi += item.getTotalDenganPajak();
                jumlahItem += item.getKuantitas();
            }
            
            System.out.printf("  - Total: Rp %.0f%n", totalTransaksi);
            System.out.printf("  - Item: %d%n", jumlahItem);
            
            totalPendapatan += totalTransaksi;
            totalItemTerjual += jumlahItem;
        }
        
        System.out.println("-".repeat(70));
        System.out.printf("TOTAL PELANGGAN DILAYANI: %d%n", totalPelanggan);
        System.out.printf("TOTAL ITEM TERJUAL: %d%n", totalItemTerjual);
        System.out.printf("TOTAL PENDAPATAN: Rp %.0f%n", totalPendapatan);
        System.out.println("=".repeat(70));
        
        tampilkanStatistikMenu();
    }
    
    private static void tampilkanStatistikMenu() {
        System.out.println("\n===================== STATISTIK MENU TERPOPULER ======================");
        
        Map<String, Integer> hitungMenu = new HashMap<>();
        Map<String, Double> pendapatanMenu = new HashMap<>();
        
        for (Transaksi transaksi : daftarTransaksi) {
            for (ItemPesanan item : transaksi.getDaftarPesanan()) {
                String namaMenu = item.getMenu().getNama();
                int kuantitas = item.getKuantitas();
                double pendapatan = item.getTotalDenganPajak();
                
                hitungMenu.put(namaMenu, hitungMenu.getOrDefault(namaMenu, 0) + kuantitas);
                pendapatanMenu.put(namaMenu, pendapatanMenu.getOrDefault(namaMenu, 0.0) + pendapatan);
            }
        }
        
        List<Map.Entry<String, Integer>> menuTerpopuler = new ArrayList<>(hitungMenu.entrySet());
        menuTerpopuler.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        System.out.println("Menu Terlaris:");
        System.out.printf("%-35s %10s %15s%n", "Nama Menu", "Terjual", "Pendapatan");
        System.out.println("-".repeat(70));
        
        int peringkat = 1;
        for (Map.Entry<String, Integer> entry : menuTerpopuler) {
            if (peringkat <= 5) { // Tampilkan top 5
                String namaMenu = entry.getKey();
                int terjual = entry.getValue();
                double pendapatan = pendapatanMenu.get(namaMenu);
                
                System.out.printf("%-35s %10d %15.0f%n", namaMenu, terjual, pendapatan);
                peringkat++;
            }
        }
        
        System.out.println("-".repeat(70));
    }
}