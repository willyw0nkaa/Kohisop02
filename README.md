# Kafe KohiSop

Sistem Point of Sale (POS) berbasis Java yang komprehensif untuk manajemen kafe dengan sistem member reward, dukungan multi mata uang, dan pemrosesan pesanan dapur.

## 🚀 Fitur Utama

### Fungsionalitas Inti
- **Manajemen Menu**: Katalog makanan dan minuman terorganisir dengan harga
- **Sistem Member**: Registrasi pelanggan dengan poin loyalitas dan benefit premium
- **Dukungan Multi Mata Uang**: IDR, USD, JPY, MYR, EUR dengan konversi real-time
- **Channel Pembayaran**: Tunai, QRIS, dan eMoney dengan struktur diskon berbeda
- **Manajemen Dapur**: Antrian terpisah untuk persiapan makanan dan minuman
- **Pemrosesan Transaksi**: Alur kerja lengkap dari pesanan hingga pembayaran

### Fitur Lanjutan
- **Harga Dinamis**: Perhitungan pajak berdasarkan status member dan harga item
- **Sistem Poin**: Dapatkan dan tukarkan poin loyalitas (2 poin = 1 IDR)
- **Member Premium**: Poin ganda untuk member dengan kode mengandung 'A'
- **Batas Pesanan**: Kuantitas maksimum per jenis item (Makanan: 2, Minuman: 3)
- **Analitik Penjualan**: Ringkasan transaksi harian dan statistik popularitas menu

## 📋 Persyaratan Sistem

- **Java**: JDK 8 atau lebih tinggi
- **Memori**: Minimum 512MB RAM
- **Penyimpanan**: 50MB ruang kosong

## 🛠️ Instalasi & Pengaturan

1. **Clone atau Download** file proyek
2. **Kompilasi** semua file Java:
   ```bash
   javac *.java
   ```
3. **Jalankan** aplikasi:
   ```bash
   java KohiSopApp
   ```

## 📖 Panduan Penggunaan

### Memulai Sistem
Aplikasi secara otomatis memproses 3 pelanggan berurutan. Setiap pelanggan melalui:

1. **Registrasi/Login Pelanggan**
   - Masukkan nama pelanggan
   - Sistem mengecek keanggotaan yang ada
   - Pelanggan baru otomatis mendapat kode member

2. **Pemesanan Menu**
   - Jelajahi menu berkategori (Makanan/Minuman)
   - Tambahkan item dengan kuantitas
   - Maksimal 5 item berbeda per kategori
   - Ketik 'CC' untuk membatalkan pesanan

3. **Pemrosesan Pembayaran**
   - Pilih channel pembayaran (Tunai/QRIS/eMoney)
   - Pilih mata uang (IDR/USD/JPY/MYR/EUR)
   - Lihat struk detail dengan pajak dan diskon

4. **Pemrosesan Dapur**
   - Pesanan makanan: Antrian prioritas (harga tertinggi pertama)
   - Pesanan minuman: Pemrosesan berbasis stack FIFO

### Kategori Menu

#### Item Makanan
- **Soup**: Oxtail Soup, Tomyum
- **Hidangan Utama**: Beef Brisket, Chicken Basil
- **Pasta**: Risotto, Aglio Olio, Fettucine A La Panna, Lasagna

#### Minuman
- **Kopi**: Ice Mocha Latte, Coconut Coffee, Caffe Americano, Caffe Mocha, Caramel Macchiato, Vanilla Latte
- **Minuman Spesial**: Zenn Matcha, Hazelnut Frappe, Choco Milkshake, Red Velvet

### Channel Pembayaran

| Channel | Diskon | Biaya Admin | Persyaratan |
|---------|--------|-------------|-------------|
| Tunai   | 0%     | 0 IDR       | Tidak ada   |
| QRIS    | 5%     | 0 IDR       | Saldo cukup |
| eMoney  | 7%     | 20 IDR      | Saldo cukup |

### Kurs Mata Uang
- **USD**: 1 USD = 15 IDR
- **JPY**: 10 JPY = 1 IDR  
- **MYR**: 1 MYR = 4 IDR
- **EUR**: 1 EUR = 14 IDR

### Class Utama

#### Class Inti
- `KohiSopApp`: Controller aplikasi utama
- `Transaksi`: Logika pemrosesan transaksi
- `Database`: Manajemen data terpusat (Singleton)

#### Sistem Menu
- `Menu`: Class abstract dasar untuk semua item menu
- `Makanan`: Item makanan dengan aturan pajak spesifik
- `Minuman`: Item minuman dengan perhitungan pajak berbeda

#### Sistem Member
- `Member`: Data pelanggan dan manajemen poin loyalitas
- `ItemPesanan`: Item pesanan individual dengan perhitungan pajak

#### Sistem Pembayaran
- `ChannelPembayaran`: Interface metode pembayaran
- `ChannelTunai`, `ChannelQRIS`, `ChannelEMoney`: Implementasi pembayaran
- `MataUang`: Interface konversi mata uang dengan implementasi

#### Operasi Dapur
- `Dapur`: Manajemen antrian dapur untuk persiapan makanan dan minuman

#### Sistem Tampilan
- `Format`: Semua metode formatting dan tampilan UI

## 💡 Logika Bisnis

### Perhitungan Pajak
**Item Makanan:**
- Member premium (kode mengandung 'A'): 0% pajak
- Harga > 50 IDR: 8% pajak
- Harga ≤ 50 IDR: 11% pajak

**Minuman:**
- Member premium: 0% pajak
- Harga < 50 IDR: 0% pajak
- Harga 50-55 IDR: 8% pajak
- Harga > 55 IDR: 11% pajak

### Sistem Poin Loyalitas
- **Mendapatkan**: 1 poin per 10 IDR yang dibelanjakan (sebelum pajak)
- **Bonus Premium**: 2x poin untuk member dengan 'A' di kode
- **Penukaran**: 2 poin = 1 IDR diskon (hanya transaksi IDR)
- **Batas Penggunaan**: Maksimal 50% dari total tagihan dapat dibayar dengan poin

### Manajemen Antrian Dapur
- **Antrian Makanan**: Priority queue berdasarkan harga (tertinggi pertama)
- **Antrian Minuman**: Pemrosesan berbasis stack LIFO
- **Tampilan**: Menampilkan urutan persiapan untuk staf dapur

## 📊 Contoh Output

```
======================================================================
                        SELAMAT DATANG DI
                            KOHISOP
======================================================================

Masukkan nama Anda: John Doe
Selamat! Anda telah menjadi member dengan kode: A1B2C3

======================================================================
                           DAFTAR MENU
======================================================================
MAKANAN:
----------------------------------------------------------------------
Kode   Nama Menu                           Harga (Rp)
----------------------------------------------------------------------
F1     Oxtail Soup                              250
F2     Beef Brisket                             357
...

TOTAL TAGIHAN: Rp 450.00
Poin Diperoleh: 18 (digandakan karena kode member mengandung 'A')
```

