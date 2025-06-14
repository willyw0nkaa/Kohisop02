import java.util.*;

class Format {
    
    public static void tampilkanHeader() {
        System.out.println("=".repeat(70));
        System.out.println("\t\t\tSELAMAT DATANG DI");
        System.out.println("\t\t\t    KOHISOP");
        System.out.println("=".repeat(70));
    }
    
    public static void tampilkanDaftarMenu(ArrayList<Menu> daftarMenu) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("\t\t\t   DAFTAR MENU");
        System.out.println("=".repeat(70));

        Map<String, List<Menu>> menuByKategori = new HashMap<>();
        for (Menu menu : daftarMenu) {
            menuByKategori.computeIfAbsent(menu.getKategori(), k -> new ArrayList<>()).add(menu);
        }

        if (menuByKategori.containsKey("Makanan")) {
            System.out.println("MAKANAN:");
            System.out.println("-".repeat(70));
            System.out.printf("%-6s %-35s %20s%n", "Kode", "Nama Menu", "Harga (Rp)");
            System.out.println("-".repeat(70));
            
            List<Menu> makanan = menuByKategori.get("Makanan");
            makanan.sort(Comparator.comparing(Menu::getKode));
            
            for (Menu menu : makanan) {
                System.out.printf("%-6s %-35s %15.0f%n", 
                    menu.getKode(), menu.getNama(), menu.getHarga());
            }
        }
        
        System.out.println();

        if (menuByKategori.containsKey("Minuman")) {
            System.out.println("MINUMAN:");
            System.out.println("-".repeat(70));
            System.out.printf("%-6s %-35s %20s%n", "Kode", "Nama Menu", "Harga (Rp)");
            System.out.println("-".repeat(70));
            
            List<Menu> minuman = menuByKategori.get("Minuman");
            minuman.sort(Comparator.comparing(Menu::getKode));
            
            for (Menu menu : minuman) {
                System.out.printf("%-6s %-35s %15.0f%n", 
                    menu.getKode(), menu.getNama(), menu.getHarga());
            }
        }
        System.out.println("=".repeat(70));
        System.out.println("Ketik 'CC' untuk membatalkan pesanan");
    }
    
    public static void tampilkanPesananSementara(ArrayList<ItemPesanan> daftarPesanan) {
        if (daftarPesanan.isEmpty()) {
            System.out.println("\nBelum ada pesanan.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("\t\t\t  PESANAN ANDA");
        System.out.println("=".repeat(70));

        Map<String, List<ItemPesanan>> pesananByKategori = new HashMap<>();
        for (ItemPesanan item : daftarPesanan) {
            String kategori = item.getMenu().getKategori();
            pesananByKategori.computeIfAbsent(kategori, k -> new ArrayList<>()).add(item);
        }

        if (pesananByKategori.containsKey("Makanan")) {
            System.out.println("MAKANAN:");
            System.out.println("-".repeat(70));
            System.out.printf("%-6s %-30s %10s %10s%n", "Kode", "Nama", "Harga", "Kuantitas");
            System.out.println("-".repeat(70));
            
            List<ItemPesanan> makanan = pesananByKategori.get("Makanan");
            makanan.sort((a, b) -> Double.compare(a.getMenu().getHarga(), b.getMenu().getHarga()));
            
            for (ItemPesanan item : makanan) {
                System.out.printf("%-6s %-30s %10.0f %10d%n",
                    item.getMenu().getKode(), item.getMenu().getNama(),
                    item.getMenu().getHarga(), item.getKuantitas());
            }
        }
        
        System.out.println();

        if (pesananByKategori.containsKey("Minuman")) {
            System.out.println("MINUMAN:");
            System.out.println("-".repeat(70));
            System.out.printf("%-6s %-30s %10s %10s%n", "Kode", "Nama", "Harga", "Kuantitas");
            System.out.println("-".repeat(70));
            
            List<ItemPesanan> minuman = pesananByKategori.get("Minuman");
            minuman.sort((a, b) -> Double.compare(a.getMenu().getHarga(), b.getMenu().getHarga()));
            
            for (ItemPesanan item : minuman) {
                System.out.printf("%-6s %-30s %10.0f %10d%n",
                    item.getMenu().getKode(), item.getMenu().getNama(),
                    item.getMenu().getHarga(), item.getKuantitas());
            }
        }
        
        System.out.println("=".repeat(70));
    }
    
    public static void tampilkanKuitansi(ArrayList<ItemPesanan> daftarPesanan, Member member, 
                                       ChannelPembayaran channel, MataUang mataUang) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("\t\t\tKUITANSI PEMBAYARAN");
        System.out.println("\t\t\t     KOHISOP");
        System.out.println("=".repeat(70));
        
        if (member != null) {
            System.out.printf("Member: %s (%s)%n", member.getNamaMember(), member.getKodeMember());
            System.out.printf("Poin Sebelum Transaksi: %d%n", member.getPoin());
        } else {
            System.out.println("Member: Bukan Member");
        }
        
        System.out.println("-".repeat(70));

        Map<String, List<ItemPesanan>> pesananByKategori = new HashMap<>();
        for (ItemPesanan item : daftarPesanan) {
            String kategori = item.getMenu().getKategori();
            pesananByKategori.computeIfAbsent(kategori, k -> new ArrayList<>()).add(item);
        }
        
        double totalSemuaTanpaPajak = 0;
        double totalSemuaDenganPajak = 0;

        if (pesananByKategori.containsKey("Makanan")) {
            System.out.println("MAKANAN:");
            System.out.println("-".repeat(70));
            System.out.printf("%-6s %-25s %8s %5s %10s %10s%n", 
                "Kode", "Nama", "Harga", "Qty", "Total", "Pajak");
            System.out.println("-".repeat(70));
            
            double totalMakananTanpaPajak = 0;
            double totalMakananDenganPajak = 0;
            
            for (ItemPesanan item : pesananByKategori.get("Makanan")) {
                System.out.printf("%-6s %-25s %8.0f %5d %10.0f %10.0f%n",
                    item.getMenu().getKode(), item.getMenu().getNama(),
                    item.getMenu().getHarga(), item.getKuantitas(),
                    item.getTotalHarga(), item.getPajak());
                
                totalMakananTanpaPajak += item.getTotalHarga();
                totalMakananDenganPajak += item.getTotalDenganPajak();
            }
            
            System.out.println("-".repeat(70));
            System.out.printf("Subtotal Makanan (tanpa pajak) : %31.0f%n", totalMakananTanpaPajak);
            System.out.printf("Subtotal Makanan (dengan pajak): %31.0f%n", totalMakananDenganPajak);
            
            totalSemuaTanpaPajak += totalMakananTanpaPajak;
            totalSemuaDenganPajak += totalMakananDenganPajak;
        }
        
        System.out.println();

        if (pesananByKategori.containsKey("Minuman")) {
            System.out.println("MINUMAN:");
            System.out.println("-".repeat(70));
            System.out.printf("%-6s %-25s %8s %5s %10s %10s%n", 
                "Kode", "Nama", "Harga", "Qty", "Total", "Pajak");
            System.out.println("-".repeat(70));
            
            double totalMinumanTanpaPajak = 0;
            double totalMinumanDenganPajak = 0;
            
            for (ItemPesanan item : pesananByKategori.get("Minuman")) {
                System.out.printf("%-6s %-25s %8.0f %5d %10.0f %10.0f%n",
                    item.getMenu().getKode(), item.getMenu().getNama(),
                    item.getMenu().getHarga(), item.getKuantitas(),
                    item.getTotalHarga(), item.getPajak());
                
                totalMinumanTanpaPajak += item.getTotalHarga();
                totalMinumanDenganPajak += item.getTotalDenganPajak();
            }
            
            System.out.println("-".repeat(70));
            System.out.printf("Subtotal Minuman (tanpa pajak) : %31.0f%n", totalMinumanTanpaPajak);
            System.out.printf("Subtotal Minuman (dengan pajak): %31.0f%n", totalMinumanDenganPajak);
            
            totalSemuaTanpaPajak += totalMinumanTanpaPajak;
            totalSemuaDenganPajak += totalMinumanDenganPajak;
        }
        
        System.out.println("=".repeat(70));
        System.out.printf("TOTAL KESELURUHAN (tanpa pajak) : %30.0f IDR%n", totalSemuaTanpaPajak);
        System.out.printf("TOTAL KESELURUHAN (dengan pajak): %30.0f IDR%n", totalSemuaDenganPajak);
        
        // diskon dan biaya admin
        double diskon = totalSemuaDenganPajak * channel.getDiskon();
        double biayaAdmin = channel.getBiayaAdmin();
        double totalSetelahDiskon = totalSemuaDenganPajak - diskon + biayaAdmin;
        
        System.out.println("-".repeat(70));
        System.out.printf("Channel Pembayaran: %s%n", channel.getNama());
        System.out.printf("Diskon (%.0f%%): %50.0f IDR%n", channel.getDiskon() * 100, diskon);
        if (biayaAdmin > 0) {
            System.out.printf("Biaya Admin: %50.0f IDR%n", biayaAdmin);
        }
        
        int poinDigunakan = 0;
        if (member != null && mataUang instanceof IDR) {
            int maxPoinBisaDigunakan = (int) Math.min(member.getPoin(), totalSetelahDiskon / 2);
            poinDigunakan = maxPoinBisaDigunakan;
            totalSetelahDiskon -= (poinDigunakan * 2);
            System.out.printf("Poin Digunakan: %d (= %.0f IDR)%n", poinDigunakan, poinDigunakan * 2.0);
        }
        
        System.out.println("-".repeat(70));
        

        double totalDalamMataUangTerpilih = mataUang.konversiDariIDR(totalSetelahDiskon);
        System.out.printf("TOTAL TAGIHAN: %s %.2f%n", 
            mataUang.getSimbol(), totalDalamMataUangTerpilih);

        if (member != null) {
            int poinBaru = (int) (totalSemuaTanpaPajak / 10);
            if (member.punyaKarakterA()) {
                poinBaru *= 2;
            }
            int poinAkhir = member.getPoin() - poinDigunakan + poinBaru;
            
            System.out.println("-".repeat(70));
            System.out.printf("Poin Diperoleh: %d%s%n", poinBaru, 
                member.punyaKarakterA() ? " (digandakan karena kode member mengandung 'A')" : "");
            System.out.printf("Poin Setelah Transaksi: %d%n", poinAkhir);
        }
        
        System.out.println("=".repeat(70));
        System.out.println("\t\t  TERIMA KASIH DAN SILAKAN DATANG KEMBALI");
        System.out.println("=".repeat(70));
    }
    
    public static void tampilkanPilihanChannel() {
    System.out.println("\n===================== PILIHAN CHANNEL PEMBAYARAN =====================");
    System.out.printf("%-3s %-20s %-30s\n", "No", "Channel", "Keterangan");
    System.out.println("----------------------------------------------------------------------");
    System.out.printf("%-3s %-20s %-30s\n", "1", "Tunai", "Tanpa diskon");
    System.out.printf("%-3s %-20s %-30s\n", "2", "QRIS", "Diskon 5%");
    System.out.printf("%-3s %-20s %-30s\n", "3", "eMoney", "Diskon 7%, Biaya admin 20 IDR");
    System.out.print("Pilih channel pembayaran (1-3): ");
}

    
    public static void tampilkanPilihanMataUang() {
    System.out.println("\n========================= PILIHAN MATA UANG ==========================");
    System.out.printf("%-3s %-10s %-30s\n", "No", "Kode", "Keterangan");
    System.out.println("----------------------------------------------------------------------");
    System.out.printf("%-3s %-10s %-30s\n", "1", "IDR", "Rupiah");
    System.out.printf("%-3s %-10s %-30s\n", "2", "USD", "Dollar US\t(1 USD = 15 IDR)");
    System.out.printf("%-3s %-10s %-30s\n", "3", "JPY", "Yen\t\t(10 JPY = 1 IDR)");
    System.out.printf("%-3s %-10s %-30s\n", "4", "MYR", "Ringgit Malaysia\t(1 MYR = 4 IDR)");
    System.out.printf("%-3s %-10s %-30s\n", "5", "EUR", "Euro\t\t(1 EUR = 14 IDR)");
    System.out.print("Pilih mata uang pembayaran (1-5): ");
}

    
    public static void tampilkanGaris() {
        System.out.println("-".repeat(60));
    }
    
    public static void clearScreen() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
}