#
# This file is the nfs-srv-init recipe.
#

SUMMARY = "Simple nfs-srv-init application"
SECTION = "PETALINUX/apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://nfs-srv-init \
	"

S = "${WORKDIR}"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit update-rc.d

INITSCRIPT_NAME = "nfs-srv-init"

## started at runlevel 1 Single-user mode, 3 Full (text based) multi-user mode and 5 Full (GUI based) multi-user mode.
###/etc/rc1.d/S99nfs-srv-init  -> ../init.d/nfs-srv-init
###/etc/rc3.d/S99nfs-srv-init  -> ../init.d/nfs-srv-init
###/etc/rc5.d/S99nfs-srv-init  -> ../init.d/nfs-srv-init
INITSCRIPT_PARAMS = "start 99 1 3 5 ."

do_install() {
#     install -d ${D}/${bindir}
#     install -m 0755 ${S}/nfs-srv-init ${D}/${bindir}
      install -d ${D}/${sysconfdir}/init.d
      install -m 0755 ${S}/nfs-srv-init ${D}/${sysconfdir}/init.d/nfs-srv-init
}
FILES_${PN} += "${sysconfdir}/*"
