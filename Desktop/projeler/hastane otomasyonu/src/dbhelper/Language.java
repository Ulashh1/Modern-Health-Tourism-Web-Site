package dbhelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Language {
    private static boolean isEnglish = false;
    private static final Map<String, String[]> translations = new HashMap<>();
    private static final String CONFIG_FILE = "config.properties";
    
    static {
        // Login ekranı çevirileri
        translations.put("hospital_name", new String[]{"Agas KHİG", "Agas KHIG"});
        translations.put("slogan", new String[]{"Sağlınız,\nGörevimiz", "Your Health,\nOur Mission"});
        translations.put("sub_slogan", new String[]{"Her ihtiyaca cevap veriyoruz", "We respond to every need"});
        translations.put("welcome", new String[]{"Hoşgeldiniz", "Welcome"});
        translations.put("patient_login", new String[]{"Hasta Girişi", "Patient Login"});
        translations.put("doctor_login", new String[]{"Doktor Girişi", "Doctor Login"});
        translations.put("admin_login", new String[]{"Yönetici Girişi", "Admin Login"});
        translations.put("app_title", new String[]{"Hastane Otomasyonu", "Hospital Automation"});
        
        // Giriş formu çevirileri
        translations.put("tc_or_email", new String[]{"T.C. No veya E-mail", "ID No or Email"});
        translations.put("tc_number", new String[]{"T.C. Numarası", "ID Number"});
        translations.put("password", new String[]{"Şifre", "Password"});
        translations.put("login_button", new String[]{"Giriş Yap", "Login"});
        translations.put("register_button", new String[]{"Yeni Kayıt Oluştur", "Create New Account"});
        translations.put("forgot_password", new String[]{"Şifremi Unuttum", "Forgot Password"});
        
        // Ana sayfa çevirileri
        translations.put("homepage", new String[]{"Ana Sayfa", "Homepage"});
        translations.put("make_appointment", new String[]{"Randevu Al", "Make Appointment"});
        translations.put("my_appointments", new String[]{"Randevularım", "My Appointments"});
        translations.put("profile", new String[]{"Profil", "Profile"});
        translations.put("logout", new String[]{"Çıkış", "Logout"});
        translations.put("blog", new String[]{"Blog", "Blog"});
        translations.put("welcome_message", new String[]{"Hoşgeldiniz, %s %s", "Welcome, %s %s"});
        translations.put("mr", new String[]{"Bey", "Mr."});
        translations.put("mrs", new String[]{"Hanım", "Mrs."});
        
        // Sağlık kartları çevirileri
        translations.put("healthy_life", new String[]{"Sağlıklı Yaşam", "Healthy Life"});
        translations.put("healthy_life_content", new String[]{
            "Dengeli beslenme ve düzenli egzersiz sağlıklı bir yaşamın temelidir. Günde en az 30 dakika egzersiz yapmayı hedefleyin.",
            "Balanced nutrition and regular exercise are the foundation of a healthy life. Aim for at least 30 minutes of exercise daily."
        });
        translations.put("stress_management", new String[]{"Stres Yönetimi", "Stress Management"});
        translations.put("stress_management_content", new String[]{
            "Meditasyon ve nefes egzersizleri ile stresi kontrol altında tutun. Düzenli molalar verin ve hobiler edinin.",
            "Control stress with meditation and breathing exercises. Take regular breaks and develop hobbies."
        });
        
        // Nöbetçi eczane çevirileri
        translations.put("pharmacy_title", new String[]{"Nöbetçi Eczaneler", "Duty Pharmacies"});
        translations.put("city", new String[]{"İl", "City"});
        translations.put("district", new String[]{"İlçe", "District"});
        translations.put("search_pharmacy", new String[]{"Eczane Ara", "Search Pharmacy"});
        translations.put("address", new String[]{"Adres", "Address"});
        translations.put("phone", new String[]{"Telefon", "Phone"});
        translations.put("region", new String[]{"Bölge", "Region"});
        translations.put("previous", new String[]{"Geri", "Previous"});
        translations.put("next", new String[]{"İleri", "Next"});
        translations.put("loading", new String[]{"Yükleniyor...", "Loading..."});
        translations.put("coming_soon", new String[]{"Yakında gelecek", "Coming Soon"});
        translations.put("no_pharmacy", new String[]{"Bu bölgede nöbetçi eczane bulunamadı.", "No duty pharmacy found in this area."});
        translations.put("please_select", new String[]{"Lütfen il ve ilçe seçin", "Please select city and district"});
        
        // Hata mesajları
        translations.put("error", new String[]{"Hata", "Error"});
        translations.put("fill_fields", new String[]{"Lütfen tüm alanları doldurun", "Please fill all fields"});
        translations.put("wrong_password", new String[]{"Şifre yanlış!", "Wrong password!"});
        translations.put("user_not_found", new String[]{"Kullanıcı bulunamadı!", "User not found!"});
        translations.put("doctor_not_found", new String[]{"TC kimlik numarası ile kayıtlı doktor bulunamadı!", "No doctor found with this ID number!"});
        translations.put("invalid_tc", new String[]{"Lütfen 11 haneli TC kimlik numaranızı giriniz!", "Please enter your 11-digit ID number!"});
        translations.put("logout_confirm", new String[]{"Çıkış yapmak istediğinizden emin misiniz?", "Are you sure you want to logout?"});
        translations.put("yes", new String[]{"Evet", "Yes"});
        translations.put("no", new String[]{"Hayır", "No"});
        translations.put("success", new String[]{"Başarılı", "Success"});
        translations.put("warning", new String[]{"Uyarı", "Warning"});
        translations.put("info", new String[]{"Bilgi", "Information"});
        
        // Profil ve ayarlar
        translations.put("settings", new String[]{"Ayarlar", "Settings"});
        translations.put("edit_profile", new String[]{"Profili Düzenle", "Edit Profile"});
        translations.put("save", new String[]{"Kaydet", "Save"});
        translations.put("cancel", new String[]{"İptal", "Cancel"});
        translations.put("name", new String[]{"Ad Soyad", "Full Name"});
        translations.put("email", new String[]{"E-posta", "Email"});
        translations.put("gender", new String[]{"Cinsiyet", "Gender"});
        translations.put("male", new String[]{"Erkek", "Male"});
        translations.put("female", new String[]{"Kadın", "Female"});
        translations.put("birth_date", new String[]{"Doğum Tarihi", "Birth Date"});
        translations.put("phone", new String[]{"Telefon", "Phone"});
        
        // Başlangıçta dil tercihini yükle
        loadLanguagePreference();
    }
    
    public static String get(String key) {
        String[] values = translations.get(key);
        if (values == null) {
            System.err.println("Translation not found for key: " + key);
            return key;
        }
        return values[isEnglish ? 1 : 0];
    }
    
    public static String get(String key, Object... args) {
        String translation = get(key);
        return String.format(translation, args);
    }
    
    public static void setEnglish(boolean english) {
        isEnglish = english;
        // Dil tercihini kaydet
        saveLanguagePreference(english);
    }
    
    public static boolean isEnglish() {
        return isEnglish;
    }
    
    public static void addTranslation(String key, String turkish, String english) {
        translations.put(key, new String[]{turkish, english});
    }
    
    // Dil tercihini kaydetme ve yükleme metodları
    private static void saveLanguagePreference(boolean english) {
        Properties props = new Properties();
        props.setProperty("language", english ? "en" : "tr");
        
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Language Preference");
        } catch (Exception e) {
            System.err.println("Dil tercihi kaydedilemedi: " + e.getMessage());
        }
    }
    
    public static void loadLanguagePreference() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            isEnglish = false; // Varsayılan olarak Türkçe
            return;
        }
        
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(configFile)) {
            props.load(in);
            String lang = props.getProperty("language", "tr");
            isEnglish = "en".equals(lang);
        } catch (Exception e) {
            System.err.println("Dil tercihi yüklenemedi: " + e.getMessage());
            isEnglish = false; // Hata durumunda Türkçe
        }
    }
} 