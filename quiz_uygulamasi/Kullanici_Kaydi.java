package git_quiz.quiz_uygulamasi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Kullanici_Kaydi {
    public static void main(String[] args) {
        // Veritabanı bağlantı bilgileri
        String url = "jdbc:postgresql://localhost:5432/QA_Team_04";
        String user = "postgres";
        String password = "227672";
        boolean kayitBasariliMi = false;
        int denemeHakki = 3;

        Scanner input = new Scanner(System.in);
        System.out.print("___QuizApp Uygulamasına Hoşgeldiniz___\n\nLütfen bilgilerinizi giriniz.\n");

        while (denemeHakki > 0 && !kayitBasariliMi) {
            System.out.print("\tKullanıcı Adı: ");
            String kullaniciAdi = input.nextLine();
            System.out.print("\tŞifre: ");
            String sifre = input.nextLine();

            try {
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement st = con.prepareStatement("INSERT INTO kullanicilar VALUES (?, ?) ON CONFLICT DO NOTHING;");
                st.setString(1, kullaniciAdi);
                st.setString(2, sifre);

                int guncellenenSatirSayisi = st.executeUpdate();
                if (guncellenenSatirSayisi > 0) {
                    System.out.println("Kayıt başarılı!");
                    kayitBasariliMi = true;
                } else {
                    denemeHakki--;
                    System.out.println("Bu kullanıcı adı zaten kullanılıyor. Lütfen başka bir kullanıcı adı deneyin. Kalan deneme hakkınız "+denemeHakki);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (!kayitBasariliMi){
            System.out.println("\nKayıt deneme hakkınız bitti. Lütfen daha sonra tekrar deneyin.");
        }
    }
}
