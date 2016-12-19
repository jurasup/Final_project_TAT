#!/bin/bash

apache_config_file="/etc/apache2/envvars"
apache_vhost_file="/etc/apache2/sites-available/vagrant_vhost.conf"
php_config_file="/etc/php5/apache2/php.ini"
xdebug_config_file="/etc/php5/mods-available/xdebug.ini"
mysql_config_file="/etc/mysql/my.cnf"
default_apache_index="/var/www/html/index.html"
wordpress_username="wordpress_user"
wordpress_password="password"
wordpress_database="wordpress"
wordpress_debug="true"

# This function is called at the very bottom of the file
main() {
	update_go

	if [[ -e /var/lock/vagrant-provision ]]; then
	    cat 1>&2 << EOD
################################################################################
# To re-run full provisioning, delete /var/lock/vagrant-provision and run
#
#    $ vagrant provision
#
# From the host machine
################################################################################
EOD
	    exit
	fi

	network_go
	tools_go
	apache_go
	php_go
	mysql_go

    download_wordpress_tarball
    extract_wordpress_to_var_www_html_directory
    create_database_in_mysql
		import_wordpress_database
    configure_wordpress_mysql_credentials

	touch /var/lock/vagrant-provision
}

update_go() {
	# Update the server
	apt-get update
	apt-get -y upgrade
}

network_go() {
	IPADDR=$(/sbin/ifconfig eth0 | awk '/inet / { print $2 }' | sed 's/addr://')
	sed -i "s/^${IPADDR}.*//" /etc/hosts
	echo ${IPADDR} ubuntu.localhost >> /etc/hosts			# Just to quiet down some error messages
}

tools_go() {
	# Install basic tools
	apt-get -y install build-essential binutils-doc git
}

apache_go() {
	# Install Apache
	apt-get -y install apache2

	sed -i "s/^\(.*\)www-data/\1vagrant/g" ${apache_config_file}
	chown -R vagrant:vagrant /var/log/apache2

	cat << EOF > ${apache_vhost_file}
<VirtualHost *:80>
        ServerAdmin webmaster@localhost
        DocumentRoot /var/www/html
        LogLevel debug

        ErrorLog /var/log/apache2/error.log
        CustomLog /var/log/apache2/access.log combined

        <Directory /var/www/html>
            AllowOverride All
            Require all granted
        </Directory>
</VirtualHost>
EOF

	a2dissite 000-default
	a2ensite vagrant_vhost

	a2enmod rewrite

	service apache2 reload
	update-rc.d apache2 enable
}

php_go() {
	apt-get -y install php5 php5-curl php5-mysql php5-sqlite php5-xdebug

	sed -i "s/display_startup_errors = Off/display_startup_errors = On/g" ${php_config_file}
	sed -i "s/display_errors = Off/display_errors = On/g" ${php_config_file}

	cat << EOF > ${xdebug_config_file}
zend_extension=xdebug.so
xdebug.remote_enable=1
xdebug.remote_connect_back=1
xdebug.remote_port=9000
xdebug.remote_host=10.0.2.2
EOF
	service apache2 reload
}

mysql_go() {
	# Install MySQL
	echo "mysql-server mysql-server/root_password password root" | debconf-set-selections
	echo "mysql-server mysql-server/root_password_again password root" | debconf-set-selections
	apt-get -y install mysql-client mysql-server

	sed -i "s/bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" ${mysql_config_file}

	# Allow root access from any host
	echo "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION" | mysql -u root --password=root
	echo "GRANT PROXY ON ''@'' TO 'root'@'%' WITH GRANT OPTION" | mysql -u root --password=root

	service mysql restart
	update-rc.d apache2 enable
}

download_wordpress_tarball() {
    wget --quiet -c https://wordpress.org/latest.tar.gz -O /tmp/wordpress-latest.tar.gz
}

extract_wordpress_to_var_www_html_directory() {
    rm -f /var/www/html/index.html
    tar --directory=/var/www/html --extract --strip-components=1 -z -f /tmp/wordpress-latest.tar.gz
}

create_database_in_mysql() {
    mysql -u root --password=root <<EOF
CREATE DATABASE IF NOT EXISTS ${wordpress_database};
GRANT ALL PRIVILEGES ON ${wordpress_database}.* TO "${wordpress_username}"@"localhost" IDENTIFIED BY "${wordpress_password}";
FLUSH PRIVILEGES;
EOF
}

configure_wordpress_mysql_credentials() {
    sed -e "s/define('DB_NAME'.*/define('DB_NAME', '${wordpress_database}');/g" \
        -e "s/define('DB_USER'.*/define('DB_USER', '${wordpress_username}');/g" \
        -e "s/define('DB_PASSWORD'.*/define('DB_PASSWORD', '${wordpress_password}');/g" \
        -e "s/define('WP_DEBUG'.*/define('WP_DEBUG', ${wordpress_debug});/g" \
        /var/www/html/wp-config-sample.php > /var/www/html/wp-config.php
}

import_wordpress_database() {
		mysql -uroot -proot wordpress < /vagrant/database.sql;
}

main
exit 0