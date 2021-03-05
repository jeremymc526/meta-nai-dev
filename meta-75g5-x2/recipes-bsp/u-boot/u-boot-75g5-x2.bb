require u-boot-nai.inc



# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

SRC_URI_append_75g5-x2 = "\
	file://${UBOOT_ENV}.txt \
	file://75g5-x2_defconfig \
	"

do_configure_prepend_75g5_x2() {
    cp -f ${WORKDIR}/75g5-x2_defconfig ${S}/configs
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/${UBOOT_ENV}.txt

    if [ -f "${WORKDIR}/${UBOOT_ENV}.txt" ]; then
        mkimage -O linux -T script -C none -n "U-Boot boot script" \
            -d ${WORKDIR}/${UBOOT_ENV}.txt ${WORKDIR}/boot.scr.uimg
    fi
}

do_deploy_append_75g5_x2() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOY_DIR_IMAGE}
    fi
}

FILES_${PN}-env += "/boot/boot.scr.uimg"
