# otusnet
Курсовой проект по Highload Architect
Запуск mysql в docker 
docker run --env=MYSQL_ROOT_PASSWORD=root \
   --env=PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin \
   --env=GOSU_VERSION=1.14 \
   --env=MYSQL_MAJOR=8.0 \
   --env=MYSQL_VERSION=8.0.31-1.el8 \
   --env=MYSQL_SHELL_VERSION=8.0.31-1.el8 \
   --volume=/var/lib/mysql 
   -p 3306:3306 \
   --restart=no \
   --runtime=runc \
   -d mysql:latest
