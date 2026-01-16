
import java.util.Scanner;

public class shopping {

    static String[] kodeMenu = {"E1", "E2", "E3", "E4", "E5", "E6", "E7", "B1", "B2", "B3"};
    static String[] namaMenu = {
        "Caffe Latte", "Cappuccino", "Caffe Mocha", "Caramel Macchiato",
        "Caffe Americano", "Asian Dolce Latte", "Double Shots Iced Shaken Espresso",
        "Freshly Brewed Coffee", "Vanilla Sweet Cream Cold Brew", "Cold Brew"
    };
    static int[] harga = {46, 46, 55, 59, 37, 55, 50, 23, 50, 44};
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        tampilkanMenu();

        String[] pesanan = new String[5];
        int[] jumlahPesanan = new int[5];
        int[] hargaPesanan = new int[5];
        double[] pajakPesanan = new double[5];
        int totalPesanan = 0;

        totalPesanan = prosesPesanan(pesanan, jumlahPesanan, hargaPesanan, pajakPesanan);

        double totalHarga = tampilkanRingkasan(pesanan, jumlahPesanan, hargaPesanan, pajakPesanan, totalPesanan);

        pilihMataUang(totalHarga);
    }

    public static void tampilkanMenu() {
        System.out.println("Selamat datang di KohiSop!");
        System.out.println("\nDaftar Menu:");
        System.out.println("Kode\tMenu\t\t\t\t\tHarga ");
        System.out.println("=======================================================");
        for (int i = 0; i < kodeMenu.length; i++) {
            System.out.printf("%s\t%-40s%dK\n", kodeMenu[i], namaMenu[i], harga[i]);
        }
        System.out.println("=======================================================");
        System.out.println("Petunjuk:");
        System.out.println("- Masukkan kode menu (E1-E7, B1-B3)");
        System.out.println("- Ketik 's' untuk selesai");
        System.out.println("- Maksimal 3 porsi per menu");
        System.out.println("- Maksimal 5 jenis pesanan\n");
    }

    public static int prosesPesanan(String[] pesanan, int[] jumlahPesanan, int[] hargaPesanan, double[] pajakPesanan) {
        int totalPesanan = 0;
        while (totalPesanan < 5) {
            System.out.print("\nMasukkan kode menu (atau 's' untuk mengakhiri): ");
            String kode = input.nextLine().toUpperCase();

            if (kode.equals("s")) {
                break;
            }

            int indexMenu = cariMenu(kode);
            if (indexMenu == -1) {
                // System.out.println("Error: Kode menu tidak ditemukan!");
                break;
            }

            int jumlah = mintaJumlahPesanan();
            if (jumlah > 0) {
                pesanan[totalPesanan] = namaMenu[indexMenu];
                jumlahPesanan[totalPesanan] = jumlah;
                hargaPesanan[totalPesanan] = harga[indexMenu];
                pajakPesanan[totalPesanan] = hitungPajak(harga[indexMenu]);
                totalPesanan++;
            }
        }
        return totalPesanan;
    }

    public static int cariMenu(String kode) {
        for (int i = 0; i < kodeMenu.length; i++) {
            if (kodeMenu[i].equals(kode)) {
                return i;
            }
        }
        return -1;
    }

    public static int mintaJumlahPesanan() {
        int jumlah = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Masukkan jumlah pesanan (1-3, '0' atau 's' untuk skip): ");
            String jumlahInput = input.nextLine();

            if (jumlahInput.equalsIgnoreCase("s") || jumlahInput.equals("0")) {
                return 0;
            }

            if (isNumeric(jumlahInput)) {
                jumlah = Integer.parseInt(jumlahInput);
                if (jumlah >= 1 && jumlah <= 3) {
                    validInput = true;
                } else {
                    System.out.println("Error: Jumlah pesanan harus antara 1-3!");
                }
            } else {
                System.out.println("Error: Masukkan angka yang valid!");
            }
        }
        return jumlah;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static double hitungPajak(int harga) {
        if (harga >= 50 && harga <= 55) {
            return 0.08;
        } else if (harga > 55) {
            return 0.11;
        } else {
            return 0;
        }
    }

    public static double tampilkanRingkasan(String[] pesanan, int[] jumlahPesanan, int[] hargaPesanan, double[] pajakPesanan, int totalPesanan) {
        double totalHarga = 0;
        double totalPajak = 0;

        System.out.println("\nRingkasan Pesanan:");
        System.out.println("=======================================================");
        for (int i = 0; i < totalPesanan; i++) {
            double hargaItem = hargaPesanan[i] * jumlahPesanan[i];
            double pajakItem = hargaItem * pajakPesanan[i];

            System.out.printf("%d. %-30s x%d\n", (i + 1), pesanan[i], jumlahPesanan[i]);
            System.out.printf("   Harga: Rp %,.0f\n", hargaItem * 1000);
            System.out.printf("   Pajak: Rp %,.0f\n", pajakItem * 1000);

            totalHarga += hargaItem;
            totalPajak += pajakItem;
        }

        double grandTotal = (totalHarga + totalPajak) * 1000;

        System.out.println("=======================================================");
        System.out.printf("Total Harga: Rp %,.0f\n", totalHarga * 1000);
        System.out.printf("Total Pajak: Rp %,.0f\n", totalPajak * 1000);
        System.out.printf("Total Pembayaran: Rp %,.0f\n", grandTotal);

        return grandTotal;
    }

    public static void pilihMataUang(double grandTotal) {
        System.out.println("\nPilih mata uang pembayaran:");
        System.out.println("1. IDR (Indonesian Rupiah)");
        System.out.println("2. USD (1 USD = Rp 15,000)");
        System.out.println("3. JPY (10 JPY = Rp 1,000)");
        System.out.println("4. MYR (1 MYR = Rp 4,000)");
        System.out.println("5. EUR (1 EUR = Rp 14,000)");

        double convertedAmount = 0;
        String currencySymbol = "";
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Pilih mata uang (1-5): ");
            String pilihanStr = input.nextLine();

            if (isNumeric(pilihanStr)) {
                int pilihan = Integer.parseInt(pilihanStr);
                switch (pilihan) {
                    case 1:
                        convertedAmount = grandTotal;
                        currencySymbol = "Rp";
                        validInput = true;
                        break;
                    case 2:
                        convertedAmount = grandTotal / 15000;
                        currencySymbol = "USD";
                        validInput = true;
                        break;
                    case 3:
                        convertedAmount = (grandTotal / 1000) * 10;
                        currencySymbol = "JPY";
                        validInput = true;
                        break;
                    case 4:
                        convertedAmount = grandTotal / 4000;
                        currencySymbol = "MYR";
                        validInput = true;
                        break;
                    case 5:
                        convertedAmount = grandTotal / 14000;
                        currencySymbol = "EUR";
                        validInput = true;
                        break;
                    default:
                        System.out.println("Error: Pilihan mata uang tidak valid (1-5)!");
                }
            } else {
                System.out.println("Error: Masukkan angka yang valid!");
            }
        }

        System.out.printf("\nTotal yang harus dibayar: %s %,.2f\n", currencySymbol, convertedAmount);
    }
}
