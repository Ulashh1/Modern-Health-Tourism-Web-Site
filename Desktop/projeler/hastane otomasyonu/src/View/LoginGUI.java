package View;

import Model.Admin;
import Model.Doctor;
import Model.Hasta;
import dbhelper.DBConnection;
import dbhelper.Helper;
import dbhelper.Language;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import com.formdev.flatlaf.FlatLightLaf;

public class LoginGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel w_panel;
    private JTextField fld_hastaLogin;
    private JPasswordField fld_hastaSifre;
    private JTextField fld_doctorTC;
    private JPasswordField fld_doctorPass;
    private DBConnection conn = new DBConnection();
    private JToggleButton btnShowHastaPassword;
    private JToggleButton btnShowDoctorPassword;

    // Renkler
    private Color primaryColor   = new Color(6, 95, 70);      // Koyu yeşil
    private Color secondaryColor = new Color(240, 253, 244); // Açık yeşil
    private Color grayTextColor  = new Color(75, 85, 99);

    // Dil seçimi için bayrak butonları
    private JButton btnTurkishFlag;
    private JButton btnUKFlag;

    // Dil desteği için değişkenler
    private JLabel lblHastaneAdi;
    private JLabel lblMainTitle;
    private JLabel lblSubTitle;
    private JLabel lblWelcome;
    private JTabbedPane tabbedPane;

    // Dil çevirileri
    private final String[][] translations = {
        // Türkçe [0], İngilizce [1]
        {"Agas KHİG", "Agas PHIG"},
        {"Sağlınız,\nGörevimiz", "Your Health,\nOur Mission"},
        {"Her ihtiyaca cevap veriyoruz", "We respond to every need"},
        {"Hoşgeldiniz", "Welcome"},
        {"Hasta Girişi", "Patient Login"},
        {"Doktor Girişi", "Doctor Login"}
    };

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());

            // Varsayılan font ve ARC değerleri
            UIManager.put("defaultFont", new Font("Inter", Font.PLAIN, 14));
            UIManager.put("Button.arc", 999);
            UIManager.put("Component.arc", 999);
            UIManager.put("TextComponent.arc", 999);

        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                LoginGUI frame = new LoginGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginGUI() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Kaydedilmiş dil tercihini yükle
        Language.loadLanguagePreference();
        
        setResizable(false);
        setTitle(Language.get("app_title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);

        // Ana panel (arka planı açık yeşil)
        w_panel = new JPanel();
        w_panel.setBackground(secondaryColor);
        w_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        w_panel.setLayout(null);
        setContentPane(w_panel);

        // Sol panel (koyu yeşil)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(primaryColor);
        leftPanel.setBounds(0, 0, 500, 600);
        leftPanel.setLayout(null);
        w_panel.add(leftPanel);

        // Dil seçimi butonu
        JButton btnLanguage = new JButton();
        btnLanguage.setBounds(400, 30, 40, 30);
        btnLanguage.setBorderPainted(false);
        btnLanguage.setContentAreaFilled(false);
        btnLanguage.setFocusPainted(false);
        btnLanguage.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Popup menü
        JPopupMenu languageMenu = new JPopupMenu();
        languageMenu.setBorder(BorderFactory.createEmptyBorder());
        languageMenu.setBackground(Color.WHITE);
        
        // Menü öğeleri
        JMenuItem turkishItem = new JMenuItem("Türkçe");
        turkishItem.setFont(new Font("Inter", Font.PLAIN, 14));
        try {
            ImageIcon trIcon = new ImageIcon(getClass().getResource("/Images/TrBayrak.png"));
            Image trImg = trIcon.getImage().getScaledInstance(20, 15, Image.SCALE_SMOOTH);
            turkishItem.setIcon(new ImageIcon(trImg));
        } catch (Exception e) {
            System.out.println("Türk bayrağı yüklenemedi: " + e.getMessage());
        }
        turkishItem.setIconTextGap(10);
        
        JMenuItem englishItem = new JMenuItem("English");
        englishItem.setFont(new Font("Inter", Font.PLAIN, 14));
        try {
            ImageIcon ukIcon = new ImageIcon(getClass().getResource("/Images/UkBayrak.png"));
            Image ukImg = ukIcon.getImage().getScaledInstance(20, 15, Image.SCALE_SMOOTH);
            englishItem.setIcon(new ImageIcon(ukImg));
        } catch (Exception e) {
            System.out.println("İngiliz bayrağı yüklenemedi: " + e.getMessage());
        }
        englishItem.setIconTextGap(10);
        
        // Menü öğelerinin görünümünü özelleştir
        turkishItem.setBackground(Color.WHITE);
        turkishItem.setOpaque(true);
        englishItem.setBackground(Color.WHITE);
        englishItem.setOpaque(true);
        
        // Menü öğelerini ekle
        languageMenu.add(turkishItem);
        languageMenu.add(englishItem);
        
        // Başlangıçta aktif dile göre butonu ayarla
        try {
            ImageIcon currentFlag = new ImageIcon(getClass().getResource(
                Language.isEnglish() ? "/Images/UkBayrak.png" : "/Images/TrBayrak.png"));
            Image img = currentFlag.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH);
            btnLanguage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Bayrak ikonu yüklenemedi: " + e.getMessage());
        }
        
        // Tıklama olayları
        turkishItem.addActionListener(e -> {
            Language.setEnglish(false);
            updateLanguage();
            try {
                ImageIcon flag = new ImageIcon(getClass().getResource("/Images/TrBayrak.png"));
                Image img = flag.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH);
                btnLanguage.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println("Bayrak ikonu yüklenemedi: " + ex.getMessage());
            }
        });
        
        englishItem.addActionListener(e -> {
            Language.setEnglish(true);
            updateLanguage();
            try {
                ImageIcon flag = new ImageIcon(getClass().getResource("/Images/UkBayrak.png"));
                Image img = flag.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH);
                btnLanguage.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println("Bayrak ikonu yüklenemedi: " + ex.getMessage());
            }
        });
        
        // Buton tıklama olayı
        btnLanguage.addActionListener(e -> {
            languageMenu.show(btnLanguage, 0, btnLanguage.getHeight());
        });
        
        leftPanel.add(btnLanguage);

        // Logo
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/TrLogo.png"));
            Image resizedImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(resizedImage));
            logoLabel.setBounds(50, 30, 80, 80);
            leftPanel.add(logoLabel);
        } catch (Exception e) {
            System.out.println("Logo yüklenemedi: " + e.getMessage());
        }

        // Hastane adı
        lblHastaneAdi = new JLabel("Agas KHİG");
        lblHastaneAdi.setFont(new Font("Inter", Font.BOLD, 24));
        lblHastaneAdi.setForeground(Color.WHITE);
        lblHastaneAdi.setBounds(140, 50, 300, 30);
        leftPanel.add(lblHastaneAdi);

        // Büyük başlık
        lblMainTitle = new JLabel("<html>Sağlınız,<br>Görevimiz</html>");
        lblMainTitle.setFont(new Font("Inter", Font.BOLD, 48));
        lblMainTitle.setForeground(Color.WHITE);
        lblMainTitle.setBounds(50, 150, 400, 120);
        leftPanel.add(lblMainTitle);

        // Alt başlık
        lblSubTitle = new JLabel("Her ihtiyaca cevap veriyoruz");
        lblSubTitle.setFont(new Font("Inter", Font.PLAIN, 20));
        lblSubTitle.setForeground(new Color(255, 255, 255, 220));
        lblSubTitle.setBounds(50, 280, 400, 30);
        leftPanel.add(lblSubTitle);
        
        JButton btnAdminLogin_2 = new JButton("");
        btnAdminLogin_2.setFocusPainted(false);
        btnAdminLogin_2.setContentAreaFilled(false);
        btnAdminLogin_2.setBorderPainted(false);
        btnAdminLogin_2.setBackground(new Color(240, 253, 244));
        btnAdminLogin_2.setBounds(0, 0, 85, 21);
        leftPanel.add(btnAdminLogin_2);

        // Sağ panel (arka planı açık yeşil)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(secondaryColor);
        rightPanel.setBounds(500, 0, 500, 600);
        rightPanel.setLayout(null);
        w_panel.add(rightPanel);

        // Hoşgeldiniz
        lblWelcome = new JLabel("Hoşgeldiniz");
        lblWelcome.setFont(new Font("Inter", Font.BOLD, 32));
        lblWelcome.setForeground(primaryColor);
        lblWelcome.setBounds(50, 50, 400, 40);
        rightPanel.add(lblWelcome);

        // Sekme yapısı
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(50, 100, 400, 450);
        tabbedPane.setFont(new Font("Inter", Font.BOLD, 14));
        // Sekmenin de arka planını açık yeşil yapmak isterseniz:
        tabbedPane.setBackground(secondaryColor);
        tabbedPane.setForeground(primaryColor);
        rightPanel.add(tabbedPane);

        // Hasta Girişi sekmesi
        tabbedPane.addTab("Hasta Girişi", createHastaLoginPanel());

        // Doktor Girişi sekmesii
        tabbedPane.addTab("Doktor Girişi", createDoctorLoginPanel());

        // Yönetici girişi (gizli buton)
        JButton btnAdminLogin = new JButton("");
        btnAdminLogin.setBackground(secondaryColor);
        btnAdminLogin.setBounds(405, 0, 85, 21);
        btnAdminLogin.setFocusPainted(false);
        btnAdminLogin.setBorderPainted(false);
        btnAdminLogin.setContentAreaFilled(false);
        btnAdminLogin.addActionListener(e -> {
            try {
                LoginAGUI loginAGUI = new LoginAGUI();
                loginAGUI.setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        rightPanel.add(btnAdminLogin);
    }

    /**
     * Hasta Girişi Paneli (arka plan artık açık yeşil)
     */
    private JPanel createHastaLoginPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(secondaryColor); // beyaz yerine açık yeşil
        panel.setLayout(null);

        JLabel lblLogin = new JLabel("T.C. No veya E-mail");
        lblLogin.setFont(new Font("Inter", Font.PLAIN, 14));
        lblLogin.setForeground(grayTextColor);
        lblLogin.setBounds(30, 30, 340, 25);
        panel.add(lblLogin);

        fld_hastaLogin = new JTextField();
        fld_hastaLogin.setBounds(30, 60, 340, 45);
        fld_hastaLogin.setFont(new Font("Inter", Font.PLAIN, 14));
        fld_hastaLogin.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(fld_hastaLogin);

        JLabel lblSifre = new JLabel("Şifre");
        lblSifre.setFont(new Font("Inter", Font.PLAIN, 14));
        lblSifre.setForeground(grayTextColor);
        lblSifre.setBounds(30, 120, 340, 25);
        panel.add(lblSifre);

        fld_hastaSifre = new JPasswordField();
        fld_hastaSifre.setBounds(30, 150, 300, 45);
        fld_hastaSifre.setFont(new Font("Inter", Font.PLAIN, 14));
        fld_hastaSifre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(fld_hastaSifre);

        // Şifre göster/gizle butonu
        btnShowHastaPassword = new JToggleButton("O");
        btnShowHastaPassword.setBounds(335, 150, 35, 45);
        btnShowHastaPassword.setBackground(Color.WHITE);
        btnShowHastaPassword.setBorderPainted(true);
        btnShowHastaPassword.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true));
        btnShowHastaPassword.setFocusPainted(false);
        btnShowHastaPassword.setFont(new Font("Inter", Font.BOLD, 14));
        btnShowHastaPassword.setForeground(grayTextColor);
        btnShowHastaPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnShowHastaPassword.addActionListener(e -> {
            if (btnShowHastaPassword.isSelected()) {
                fld_hastaSifre.setEchoChar((char) 0);
                btnShowHastaPassword.setText("X");
            } else {
                fld_hastaSifre.setEchoChar('•');
                btnShowHastaPassword.setText("O");
            }
        });
        panel.add(btnShowHastaPassword);

        JButton btnGiris = new JButton("Giriş Yap");
        btnGiris.setFont(new Font("Inter", Font.BOLD, 14));
        btnGiris.setBounds(30, 220, 340, 45);
        btnGiris.setBackground(primaryColor);
        btnGiris.setForeground(Color.WHITE);
        btnGiris.setBorder(BorderFactory.createEmptyBorder());
        btnGiris.setFocusPainted(false);
        btnGiris.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGiris.addActionListener(e -> hastaLogin());
        panel.add(btnGiris);

        JButton btnKayit = new JButton("Yeni Kayıt Oluştur");
        btnKayit.setFont(new Font("Inter", Font.BOLD, 14));
        btnKayit.setBounds(30, 280, 340, 45);
        // Arka planı beyaz yerine açık yeşil isterseniz:
        btnKayit.setBackground(secondaryColor);
        btnKayit.setForeground(primaryColor);
        btnKayit.setBorder(BorderFactory.createLineBorder(primaryColor, 2, true));
        btnKayit.setFocusPainted(false);
        btnKayit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKayit.addActionListener(e -> {
            RegisterGUI register = new RegisterGUI();
            register.setVisible(true);
            dispose();
        });
        panel.add(btnKayit);

        JButton btnForgotPassword = new JButton("Şifremi Unuttum");
        btnForgotPassword.setFont(new Font("Inter", Font.PLAIN, 14));
        btnForgotPassword.setBounds(30, 340, 340, 30);
        btnForgotPassword.setBackground(secondaryColor);
        btnForgotPassword.setForeground(primaryColor);
        btnForgotPassword.setBorder(null);
        btnForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnForgotPassword.addActionListener(e -> {
        	dispose(); 
            ForgotPasswordGUI forgotPassword = new ForgotPasswordGUI();
            forgotPassword.setVisible(true);
        });
        panel.add(btnForgotPassword);

        return panel;
    }

    /**
     * Doktor Girişi Paneli (arka plan artık açık yeşil)
     */
    private JPanel createDoctorLoginPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(secondaryColor); // beyaz yerine açık yeşil
        panel.setLayout(null);

        JLabel lblTC = new JLabel("T.C. Numarası");
        lblTC.setFont(new Font("Inter", Font.PLAIN, 14));
        lblTC.setForeground(grayTextColor);
        lblTC.setBounds(30, 30, 340, 25);
        panel.add(lblTC);

        fld_doctorTC = new JTextField();
        fld_doctorTC.setBounds(30, 60, 340, 45);
        fld_doctorTC.setFont(new Font("Inter", Font.PLAIN, 14));
        fld_doctorTC.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        fld_doctorTC.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Sadece rakam ve en fazla 11 hane
                if (!Character.isDigit(c) || fld_doctorTC.getText().length() >= 11) {
                    e.consume();
                }
            }
        });
        panel.add(fld_doctorTC);

        JLabel lblSifre = new JLabel("Şifre");
        lblSifre.setFont(new Font("Inter", Font.PLAIN, 14));
        lblSifre.setForeground(grayTextColor);
        lblSifre.setBounds(30, 120, 340, 25);
        panel.add(lblSifre);

        fld_doctorPass = new JPasswordField();
        fld_doctorPass.setBounds(30, 150, 300, 45);
        fld_doctorPass.setFont(new Font("Inter", Font.PLAIN, 14));
        fld_doctorPass.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(fld_doctorPass);

        // Şifre göster/gizle butonu (Doktor)
        btnShowDoctorPassword = new JToggleButton("O");
        btnShowDoctorPassword.setBounds(335, 150, 35, 45);
        btnShowDoctorPassword.setBackground(Color.WHITE);
        btnShowDoctorPassword.setBorderPainted(true);
        btnShowDoctorPassword.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true));
        btnShowDoctorPassword.setFocusPainted(false);
        btnShowDoctorPassword.setFont(new Font("Inter", Font.BOLD, 14));
        btnShowDoctorPassword.setForeground(grayTextColor);
        btnShowDoctorPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnShowDoctorPassword.addActionListener(e -> {
            if (btnShowDoctorPassword.isSelected()) {
                fld_doctorPass.setEchoChar((char) 0);
                btnShowDoctorPassword.setText("X");
            } else {
                fld_doctorPass.setEchoChar('•');
                btnShowDoctorPassword.setText("O");
            }
        });
        panel.add(btnShowDoctorPassword);

        JButton btnGiris = new JButton("Giriş Yap");
        btnGiris.setFont(new Font("Inter", Font.BOLD, 14));
        btnGiris.setBounds(30, 220, 340, 45);
        btnGiris.setBackground(primaryColor);
        btnGiris.setForeground(Color.WHITE);
        btnGiris.setBorder(BorderFactory.createEmptyBorder());
        btnGiris.setFocusPainted(false);
        btnGiris.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGiris.addActionListener(e -> doctorLogin());
        panel.add(btnGiris);

        return panel;
    }

    /**
     * Hasta Girişi
     */
    private void hastaLogin() {
        if (fld_hastaLogin.getText().length() == 0 || fld_hastaSifre.getPassword().length == 0) {
            Helper.showMsg("fill");
            return;
        }

        try {
            Connection con = conn.connDb();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM user WHERE type='hasta' AND (tcno='" +
                fld_hastaLogin.getText() + "' OR email='" + fld_hastaLogin.getText() + "')");

            if (!rs.next()) {
                Helper.showMsg("Böyle bir kullanıcı bulunamadı!");
                return;
            }

            if (!String.valueOf(fld_hastaSifre.getPassword()).equals(rs.getString("password"))) {
                Helper.showMsg("Şifre yanlış!");
                return;
            }

            // Bilgileri al ve Hasta nesnesi oluştur
            Hasta hasta = new Hasta();
            hasta.setId(rs.getInt("id"));
            hasta.setPassword(rs.getString("password"));
            hasta.setTcno(rs.getString("tcno"));
            hasta.setName(rs.getString("name"));
            hasta.setType(rs.getString("type"));

            HomepageGUI hGUI = new HomepageGUI(hasta);
            hGUI.setVisible(true);
            dispose();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Doktor Girişi
     */
    private void doctorLogin() {
        if (fld_doctorTC.getText().length() == 0 || fld_doctorPass.getPassword().length == 0) {
            Helper.showMsg("fill");
            return;
        }

        if (fld_doctorTC.getText().length() != 11) {
            Helper.showMsg(Language.get("invalid_tc"));
            return;
        }

        try (Connection con = conn.connDb();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM user WHERE tcno='" + fld_doctorTC.getText() + 
                "' AND (type='doktor' OR type='bashekim')")) {

            if (!rs.next()) {
                Helper.showMsg(Language.get("doctor_not_found"));
                return;
            }

            if (!String.valueOf(fld_doctorPass.getPassword()).equals(rs.getString("password"))) {
                Helper.showMsg(Language.get("wrong_password"));
                return;
            }

            // başhekim ise
            if ("bashekim".equals(rs.getString("type"))) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setPassword(rs.getString("password"));
                admin.setTcno(rs.getString("tcno"));
                admin.setName(rs.getString("name"));
                admin.setType(rs.getString("type"));

                AdminGUI aGUI = new AdminGUI(admin);
                aGUI.setVisible(true);
                dispose();

            } else if ("doktor".equals(rs.getString("type"))) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setPassword(rs.getString("password"));
                doctor.setTcno(rs.getString("tcno"));
                doctor.setName(rs.getString("name"));
                doctor.setType(rs.getString("type"));

                DoctorGUI dGUI = new DoctorGUI(doctor);
                dGUI.setVisible(true);
                dispose();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    // Dil değiştirme fonksiyonu
    private void updateLanguage() {
        lblHastaneAdi.setText(Language.get("hospital_name"));
        lblMainTitle.setText("<html>" + Language.get("slogan").replace("\n", "<br>") + "</html>");
        lblSubTitle.setText(Language.get("sub_slogan"));
        lblWelcome.setText(Language.get("welcome"));
        tabbedPane.setTitleAt(0, Language.get("patient_login"));
        tabbedPane.setTitleAt(1, Language.get("doctor_login"));
        
        // Hasta girişi panelini güncelle
        JPanel hastaPanel = (JPanel) tabbedPane.getComponentAt(0);
        for (Component c : hastaPanel.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                if (label.getText().contains("T.C. No veya E-mail")) {
                    label.setText(Language.get("tc_or_email"));
                } else if (label.getText().equals("Şifre")) {
                    label.setText(Language.get("password"));
                }
            } else if (c instanceof JButton) {
                JButton button = (JButton) c;
                if (button.getText().equals("Giriş Yap")) {
                    button.setText(Language.get("login_button"));
                } else if (button.getText().equals("Yeni Kayıt Oluştur")) {
                    button.setText(Language.get("register_button"));
                } else if (button.getText().equals("Şifremi Unuttum")) {
                    button.setText(Language.get("forgot_password"));
                }
            }
        }
        
        // Doktor girişi panelini güncelle
        JPanel doctorPanel = (JPanel) tabbedPane.getComponentAt(1);
        for (Component c : doctorPanel.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                if (label.getText().equals("T.C. Numarası")) {
                    label.setText(Language.get("tc_or_email"));
                } else if (label.getText().equals("Şifre")) {
                    label.setText(Language.get("password"));
                }
            } else if (c instanceof JButton) {
                JButton button = (JButton) c;
                if (button.getText().equals("Giriş Yap")) {
                    button.setText(Language.get("login_button"));
                }
            }
        }
    }

    public static void setLanguage(boolean english) {
        Language.setEnglish(english);
    }

    public static boolean isEnglish() {
        return Language.isEnglish();
    }

    // Dil butonunu güncelleme metodu
    private void updateLanguageButton(JButton button, boolean isEnglish) {
        try {
            String imagePath = isEnglish ? "/Images/UkBayrak.png" : "/Images/TrBayrak.png";
            ImageIcon flag = new ImageIcon(getClass().getResource(imagePath));
            
            // Görüntüyü BufferedImage'a dönüştür
            java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(
                flag.getIconWidth(),
                flag.getIconHeight(),
                java.awt.image.BufferedImage.TYPE_INT_ARGB
            );
            java.awt.Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                                java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                                java.awt.RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.drawImage(flag.getImage(), 0, 0, null);
            g2d.dispose();
            
            // Yüksek kaliteli ölçeklendirme
            double scale = Math.min(40.0 / flag.getIconWidth(), 30.0 / flag.getIconHeight());
            int width = (int) (flag.getIconWidth() * scale);
            int height = (int) (flag.getIconHeight() * scale);
            
            java.awt.image.BufferedImage scaled = new java.awt.image.BufferedImage(width, height, bufferedImage.getType());
            java.awt.Graphics2D g2dScaled = scaled.createGraphics();
            g2dScaled.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                                     java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2dScaled.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                                     java.awt.RenderingHints.VALUE_RENDER_QUALITY);
            g2dScaled.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                                     java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2dScaled.drawImage(bufferedImage, 0, 0, width, height, null);
            g2dScaled.dispose();
            
            button.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.out.println("Bayrak ikonu yüklenemedi: " + e.getMessage());
        }
    }
}