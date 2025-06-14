import java.util.Random;

class Member {
    private String kodeMember;
    private String namaMember;
    private int poin;
    
    public Member(String nama) {
        this.kodeMember = generateKodeMember();
        this.namaMember = nama;
        this.poin = 0;
    }
    
    public Member(String kode, String nama, int poin) {
        this.kodeMember = kode;
        this.namaMember = nama;
        this.poin = poin;
    }
    
    private String generateKodeMember() {
        String karakter = "ABCDEF0123456789";
        Random random = new Random();
        StringBuilder kode = new StringBuilder();
        
        for (int i = 0; i < 6; i++) {
            kode.append(karakter.charAt(random.nextInt(karakter.length())));
        }
        
        return kode.toString();
    }
    
    public String getKodeMember() { return kodeMember; }
    public String getNamaMember() { return namaMember; }
    public int getPoin() { return poin; }
    
    public void tambahPoin(int poinBaru) {
        this.poin += poinBaru;
    }
    
    public void kurangiPoin(int poinDipakai) {
        this.poin = Math.max(0, this.poin - poinDipakai);
    }
    
    public boolean punyaKarakterA() {
        return kodeMember.contains("A");
    }
}