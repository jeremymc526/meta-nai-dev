#
# This file is the mbcore recipe.
#

SUMMARY = "Simple mbcore application"
SECTION = "PETALINUX/apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://naiEtherConfigCmndListener      \
           file://naiMBEEPROMUtil                 \
           file://naiModEEPROMUtil                \
           file://naiPlatformOpStartup            \
           file://runConfigModeTCP1Offbrd         \
           file://runConfigModeTCP1Onbrd          \
           file://runConfigModeTCP2Offbrd         \
           file://runConfigModeTCP2Onbrd          \
           file://runOpModeTCP1                   \
           file://runOpModeTCP2                   \
           file://runOpModeUDP1                   \
           file://runOpModeUDP2                   \
           file://runmbexecbk                     \
        "

S = "${WORKDIR}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit update-rc.d

INITSCRIPT_NAME = "runmbexecbk"

## started at runlevel 5 Full (GUI based) multi-user mode.
##/etc/rc5.d/S90runmbexecbk  -> ../init.d/runmbexecbk
INITSCRIPT_PARAMS = "start 90 5 ."

do_install() {
             install -d ${D}/${base_sbindir}
             install -m 0755 ${S}/naiEtherConfigCmndListener          ${D}/${base_sbindir}
             install -m 0755 ${S}/naiMBEEPROMUtil                     ${D}/${base_sbindir}
             install -m 0755 ${S}/naiModEEPROMUtil                    ${D}/${base_sbindir}
             install -m 0755 ${S}/naiPlatformOpStartup                ${D}/${base_sbindir}
             install -m 0755 ${S}/runConfigModeTCP1Offbrd             ${D}/${base_sbindir}
             install -m 0755 ${S}/runConfigModeTCP1Onbrd              ${D}/${base_sbindir}
             install -m 0755 ${S}/runConfigModeTCP2Offbrd             ${D}/${base_sbindir}
             install -m 0755 ${S}/runConfigModeTCP2Onbrd              ${D}/${base_sbindir}
             install -m 0755 ${S}/runOpModeTCP1                       ${D}/${base_sbindir}
             install -m 0755 ${S}/runOpModeTCP2                       ${D}/${base_sbindir}
             install -m 0755 ${S}/runOpModeUDP1                       ${D}/${base_sbindir}
             install -m 0755 ${S}/runOpModeUDP2                       ${D}/${base_sbindir}

             install -d ${D}/${sysconfdir}/init.d
             install -m 0755 ${S}/runmbexecbk ${D}/${sysconfdir}/init.d/runmbexecbk
}
FILES_${PN} += "${sysconfdir}/*"
