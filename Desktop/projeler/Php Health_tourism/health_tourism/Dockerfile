FROM php:8.0-apache

# PHP eklentilerini yükle
RUN docker-php-ext-install pdo pdo_mysql

# Apache yapılandırması
RUN a2enmod rewrite

# Çalışma dizinini ayarla
WORKDIR /var/www/html

# Projeyi kopyala
COPY . /var/www/html/

# Dosya izinlerini ayarla
RUN chown -R www-data:www-data /var/www/html

# Port 80'i aç
EXPOSE 80

# Apache başlat
CMD ["apache2-foreground"]