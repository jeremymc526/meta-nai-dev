#################################################################
##### COMMON Stuff for all boards
#################################################################

DESCRIPTION = "U-boot bootloader for NAI model 75g5-x2"
HOMEPAGE = "http://www.naii.com/"
SECTION = "bootloaders"
LICENSE = "GPLv2"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
DEPENDS_append = " u-boot-tools-native"
PROVIDES = "virtual/bootloader u-boot-nai"

inherit deploy

S = "${WORKDIR}/git"


python () {
		bb.debug(1, "JRM JRM !!! ")
}

