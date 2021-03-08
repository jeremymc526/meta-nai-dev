FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
DESCRIPTION = "U-boot bootloader mkimage tool"
HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
LICENSE = "GPLv2+"
SECTION = "bootloader"
PROVIDES+=" u-boot-mkimage"
PR = "r0"

SRCREV = "586d7124ab6d66b21e2dbc9db07d97dd7c0e7621"
SRCBRANCH = "75g5-x2"
KERNEL_SRC ?= "git://github.com/jeremymc526/BouncyCastle.git;protocol=git"
SRC_URI += "${KERNEL_SRC};branch=${SRCBRANCH} "

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${S}/Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}/git"

BBCLASSEXTEND = "native nativesdk"

EXTRA_OEMAKE = 'HOSTCC="${CC}" HOSTLD="${LD}" HOSTLDFLAGS="${LDFLAGS}" HOSTSTRIP=true'

do_compile () {
  oe_runmake tools
}

do_install () {
  install -d ${D}${bindir}
  install -m 0755 tools/mkimage ${D}${bindir}/uboot-mkimage
  ln -sf uboot-mkimage ${D}${bindir}/mkimage
}

