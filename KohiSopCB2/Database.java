import java.util.*;

class Database {
    private static Database instance;
    private ArrayList<Menu> daftarMenu;
    private ArrayList<Member> daftarMember;
    
    private Database() {
        daftarMenu = new ArrayList<>();
        daftarMember = new ArrayList<>();
        inisialisasiMenu();
        inisialisasiMember();
    }
    
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    
    private void inisialisasiMenu() {
        // Minuman
        daftarMenu.add(new Minuman("C1", "Ice Mocha Latte", 35));
        daftarMenu.add(new Minuman("C2", "Coconut Coffee", 55));
        daftarMenu.add(new Minuman("C3", "Caffe Americano", 37));
        daftarMenu.add(new Minuman("C4", "Caffe Mocha", 55));
        daftarMenu.add(new Minuman("C5", "Caramel Macchiato", 59));
        daftarMenu.add(new Minuman("C6", "Vanilla Latte", 55));
        daftarMenu.add(new Minuman("M1", "Zenn Matcha", 100));
        daftarMenu.add(new Minuman("M2", "Hazelnut Frappe", 50));
        daftarMenu.add(new Minuman("M3", "Choco Milkshake", 53));
        daftarMenu.add(new Minuman("M4", "Red Velvet", 55));
        
        // Makanan
        daftarMenu.add(new Makanan("F1", "Oxtail Soup", 250));
        daftarMenu.add(new Makanan("F2", "Beef Brisket", 357));
        daftarMenu.add(new Makanan("F3", "Tomyum", 170));
        daftarMenu.add(new Makanan("F4", "Chicken Basil", 124));
        daftarMenu.add(new Makanan("P1", "Risotto", 160));
        daftarMenu.add(new Makanan("P2", "Aglio Olio", 180));
        daftarMenu.add(new Makanan("P3", "Fettucine A La Panna", 200));
        daftarMenu.add(new Makanan("P4", "Lasagna", 215));
    }
    
    private void inisialisasiMember() {
        // berikut adalah member yang sudah terdaftar namanya
        daftarMember.add(new Member("A12B34", "Naila Rania Shofa", 50));
        daftarMember.add(new Member("C56D78", "Ahmad Rafi", 25));
        daftarMember.add(new Member("E90F12", "Nismanda", 75));
    }
    
    public ArrayList<Menu> getDaftarMenu() {
        return new ArrayList<>(daftarMenu);
    }
    
    public ArrayList<Member> getDaftarMember() {
        return new ArrayList<>(daftarMember);
    }
    
    public Menu cariMenuByKode(String kode) {
        for (Menu menu : daftarMenu) {
            if (menu.getKode().equalsIgnoreCase(kode)) {
                return menu;
            }
        }
        return null;
    }
    
    public Member cariMemberByNama(String nama) {
        for (Member member : daftarMember) {
            if (member.getNamaMember().equalsIgnoreCase(nama)) {
                return member;
            }
        }
        return null;
    }
    
    public void tambahMember(Member member) {
        daftarMember.add(member);
    }
    
    public ArrayList<Menu> getMenuTerurut() {
        ArrayList<Menu> menuTerurut = new ArrayList<>(daftarMenu);
        
        // pengurutan berdasarkan kategori dan kode
        menuTerurut.sort((m1, m2) -> {
            int kategori = m1.getKategori().compareTo(m2.getKategori());
            if (kategori != 0) return kategori;
            return m1.getKode().compareTo(m2.getKode());
        });
        
        return menuTerurut;
    }
}