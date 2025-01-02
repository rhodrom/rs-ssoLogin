
#!/bin/bash

source ~/.bash_profile

CURRENT_DIR=$(cd "$(dirname "$0")"; pwd)
cd $CURRENT_DIR

chmod 755 common.sh jq
source ./common.sh

BASE_DIR=$UYUN_ROOT_DIR/uyun/rs-ssoLogin

APP_NAME=rs-ssoLogin

MODULE_NAME=rs-ssoLogin-main

mkdir $BASE_DIR

echo $BASE_DIR

usage() {
    echo "usage:"
    echo "    sh install.sh optstring parameters"
    echo "    sh install.sh [options] [--] optstring parameters"
    echo "    sh install.sh [options] -o|--options optstring [options] [--] parameters"
    echo ""
    echo "Options:"
    echo "    --security-level                   security level(medium | high)"
    echo "    --disconf-host                     disconf ip list, e.g: 10.1.241.2"
    echo "    --disconf-port                     disconf port, default: 8081"
    echo "    --disconf-user                     disconf user, default: admin"
    echo "    --disconf-passwd                   disconf passwd, default: admin"
    echo "    --running-user                     run user, default: uyun"
    echo "    --mail-serverhost                  mail server host, eg: uyum.mail.cn"
    echo "    --mail-serverport                  mail server port, default: 25"
    echo "    --mail-fromaddress                 mail from address, eg: rs-ssoLogin@uyun.cn"
    echo "    --mail-username                    mail username, eg: rs-ssoLogin"
    echo "    --mail-passwd                      mail passwd"
    echo "    -l, --local-ip                     local node ip, eg: 10.1.241.3"
    echo "    -h, --help                         help"
}

ARGS=`getopt -o h:: --long security-level:,config-files:,disconf-host:,disconf-port:,disconf-user:,disconf-passwd:,running-user:,mail-serverhost:,mail-serverport:,mail-fromaddress:,mail-username:,mail-passwd:,local-ip:,remote-ips:,install-role:,help:: -n 'install.sh' -- "$@"`

if [ $? != 0 ]; then
	usage
	exit 1
fi

eval set -- "$ARGS"

while true; do
	case "$1" in
    --config-files)
      CONFIG_FILES=$2
      shift 2
      ;;
        -l|--local-ip)
            LOCAL_IP=$2
            shift 2
            ;;
        --remote-ips)
            REMOTE_IPS=$2
            shift 2
            ;;
        --install-role)
            INSTALL_ROLE=$2
            shift 2
            ;;
		-h|--help)
			usage
			exit 1
			;;
		--)
			break
			;;
		*)
			echo "Invalid parameter";
			exit 1
			;;
	esac
done

update_chmod(){
  if [ ! -d $BASE_DIR ]; then
    mkdir $BASE_DIR
    chown -R $RUNNING_USER $BASE_DIR
    chmod_dir $BASE_DIR
  fi
}
install_rs-ssoLogin() {

    INSTALL_DIR=$BASE_DIR/main

    echo "rs-ssoLogin install_upload....."
    CONFIG_BASE_FILE=$INSTALL_DIR/config/bootstrap.yml
    CONFIG_FILE_PROD=$INSTALL_DIR/config/${MODULE_NAME}.yml

    sed -i "s#environment:.*#environment: pro#g" $CONFIG_BASE_FILE

    sed -i "s#http://.*:7546#$CONFIG_SERVICE_URL#g" $CONFIG_BASE_FILE
    upload_file $APP_NAME $CONFIG_FILE_PROD PRO
    echo "****config_service_url****"
    echo $CONFIG_SERVICE_URL

    echo "rs-ssoLogin install_upload success"

    echo "rs-ssoLogin file static success"
    sed -i "s#/opt#$UYUN_ROOT_DIR#g" $CONFIG_BASE_FILE

    echo "Prepare to install rs-ssoLogin....."
    if [ -n "$(ps aux | grep 'rs-ssoLogin' | grep -v grep)" ]; then
        pkill -9 -f 'rs-ssoLogin'
    fi


    echo "rs-ssoLogin starting....."
    echo ""$INSTALL_DIR"/bin/*"
    ls "$INSTALL_DIR"
    "$INSTALL_DIR"/bin/manage.sh start
}


update_chmod
install_rs-ssoLogin


