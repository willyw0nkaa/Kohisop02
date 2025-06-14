import java.util.*;

public class Transaksi {

    private ArrayList<ItemPesanan> daftarPesanan;
    private Member member;
    private ChannelPembayaran channelPembayaran;
    private MataUang mataUang;
    private Scanner scanner;

    public Transaksi() {
        this.daftarPesanan = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void mulaiTransaksi() {
        Format.tampilkanHeader();

        System.out.print("Masukkan nama Anda: ");
        String namaPelanggan = scanner.nextLine().trim();

        Database db = Database.getInstance();
        member = db.cariMemberByNama(namaPelanggan);

        if (member == null) {
            member = new Member(namaPelanggan);
            db.tambahMember(member);
            System.out.println("Selamat! Anda telah menjadi member dengan kode: " + member.getKodeMember());
        } else {
            System.out.println("Selamat datang kembali, " + member.getNamaMember() + "!");
            System.out.println("Poin Anda saat ini: " + member.getPoin());
        }

        if (prosesPemesanan()) {
            channelPembayaran = pilihChannelPembayaran();
            mataUang = pilihMataUang();
            hitungTotalDanTampilkanKuitansi();
        } else {
            System.out.println("Transaksi dibatalkan. Terima kasih!");
        }
    }

    private boolean prosesPemesanan() {
        Database db = Database.getInstance();
        ArrayList<Menu> daftarMenu = db.getMenuTerurut();

        Map<String, Integer> hitungKategori = new HashMap<>();
        hitungKategori.put("Makanan", 0);
        hitungKategori.put("Minuman", 0);

        while (true) {
            Format.tampilkanDaftarMenu(daftarMenu);
            Format.tampilkanPesananSementara(daftarPesanan);

            System.out.print("\nMasukkan kode menu (atau 'CC' untuk batal): ");
            String inputKode = scanner.nextLine().trim().toUpperCase();

            if (inputKode.equals("CC")) {
                System.out.println("Pesanan dibatalkan.");
                return false;
            }

            Menu menuDipilih = db.cariMenuByKode(inputKode);
            if (menuDipilih == null) {
                System.out.println("Kode menu tidak valid!");
                continue;
            }

            String kategori = menuDipilih.getKategori();
            if (hitungKategori.get(kategori) >= 5) {
                System.out.println("Maksimal 5 jenis " + kategori.toLowerCase() + " per pesanan!");
                continue;
            }

            boolean sudahAda = daftarPesanan.stream()
                .anyMatch(item -> item.getMenu().getKode().equals(inputKode));
            if (sudahAda) {
                System.out.println("Menu sudah ada dalam pesanan!");
                continue;
            }

            int kuantitas = inputKuantitas(menuDipilih);
            if (kuantitas == -1) return false;
            if (kuantitas == 0) continue;

            boolean isMemberA = member != null && member.punyaKarakterA();
            daftarPesanan.add(new ItemPesanan(menuDipilih, kuantitas, isMemberA));
            hitungKategori.put(kategori, hitungKategori.get(kategori) + 1);

            System.out.println("Berhasil menambahkan: " + menuDipilih.getNama() + " x" + kuantitas);

            System.out.print("Apakah Anda sudah selesai memesan? (y/n): ");
            String selesai = scanner.nextLine().trim().toLowerCase();
            if (selesai.equals("y") || selesai.equals("yes")) {
                Format.tampilkanPesananSementara(daftarPesanan);
                break;
            }
        }

        return !daftarPesanan.isEmpty();
    }

    private int inputKuantitas(Menu menu) {
        int maxKuantitas = (menu instanceof Makanan) ? 2 : 3;

        while (true) {
            System.out.printf("Masukkan jumlah %s (1-%d, '0' atau 'S' skip, 'CC' batal): ",
                    menu.getNama(), maxKuantitas);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("CC")) return -1;
            if (input.equalsIgnoreCase("S") || input.equals("0")) return 0;
            if (input.isEmpty()) return 1;

            try {
                int qty = Integer.parseInt(input);
                if (qty >= 1 && qty <= maxKuantitas) return qty;
                System.out.println("Kuantitas harus antara 1 dan " + maxKuantitas);
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid!");
            }
        }
    }

    private ChannelPembayaran pilihChannelPembayaran() {
        while (true) {
            Format.tampilkanPilihanChannel();
            String pilihan = scanner.nextLine().trim();

            switch (pilihan) {
                case "1":
                    return new ChannelTunai();
                case "2":
                    if (konfirmasiSaldo("QRIS")) return new ChannelQRIS();
                    break;
                case "3":
                    if (konfirmasiSaldo("eMoney")) return new ChannelEMoney();
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private boolean konfirmasiSaldo(String namaChannel) {
        System.out.print("Apakah saldo " + namaChannel + " Anda mencukupi? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    private MataUang pilihMataUang() {
        while (true) {
            Format.tampilkanPilihanMataUang();
            String pilihan = scanner.nextLine().trim();

            switch (pilihan) {
                case "1": return new IDR();
                case "2": return new USD();
                case "3": return new JPY();
                case "4": return new MYR();
                case "5": return new EUR();
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void hitungTotalDanTampilkanKuitansi() {
        double totalTanpaPajak = 0;
        double totalDenganPajak = 0;

        for (ItemPesanan item : daftarPesanan) {
            totalTanpaPajak += item.getTotalHarga();
            totalDenganPajak += item.getTotalDenganPajak();
        }

        Format.tampilkanKuitansi(daftarPesanan, member, channelPembayaran, mataUang);

        if (member != null) {
            int poinBaru = (int) (totalTanpaPajak / 10);
            if (member.punyaKarakterA()) poinBaru *= 2;

            int poinDigunakan = 0;
            if (mataUang instanceof IDR) {
                double totalSetelahDiskon = totalDenganPajak - (totalDenganPajak * channelPembayaran.getDiskon())
                        + channelPembayaran.getBiayaAdmin();
                poinDigunakan = (int) Math.min(member.getPoin(), totalSetelahDiskon / 2);
                member.kurangiPoin(poinDigunakan);
            }

            member.tambahPoin(poinBaru);
        }
    }

    public ArrayList<ItemPesanan> getDaftarPesanan() {
        return new ArrayList<>(daftarPesanan);
    }

    public Member getMember() {
        return member;
    }
}
