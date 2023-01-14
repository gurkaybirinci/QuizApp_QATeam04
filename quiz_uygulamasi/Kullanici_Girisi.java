package git_quiz.quiz_uygulamasi;

import java.sql.*;
import java.util.Scanner;

public class Kullanici_Girisi {
    public static void main(String[] args) {
        // Veritabanı bağlantı bilgileri
        String url = "jdbc:postgresql://localhost:5432/QA_Team_04";
        String user = "postgres";
        String password = "227672";
        boolean girisBasariliMi = false;
        int denemeHakki = 3;

        Scanner input = new Scanner(System.in);

        while (denemeHakki > 0 && !girisBasariliMi) {
            // Kullanıcıdan giriş bilgilerini alalım
            System.out.print("Kullanıcı Adı: ");
            String kullaniciAdi = input.nextLine();
            System.out.print("Şifre: ");
            String sifre = input.nextLine();

            // Veritabanına bağlanma
            Connection con = null;
            try {
                con = DriverManager.getConnection(url, user, password);
                // Kullanıcı giriş bilgilerini almak için gerekli SQL sorusunu oluşturduk
                PreparedStatement st = con.prepareStatement("select * from kullanicilar where kullaniciadi = ? and sifre = ?");
                st.setString(1, kullaniciAdi);
                st.setString(2, sifre);

                // Sorgumuzu çalıştıralım
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    System.out.println("Giriş başarılı!");
                    girisBasariliMi = true;
                } else {
                    denemeHakki--;
                    System.out.println("Giriş başarısız. Kalan deneme hakkınız: "+denemeHakki);
                }

                // Veritabanı bağlantısını kapatalım
                con.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
