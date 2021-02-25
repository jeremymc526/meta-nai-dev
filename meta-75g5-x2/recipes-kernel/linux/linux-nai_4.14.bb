FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

SECTION = "kernel"
DESCRIPTION = "NAI Linux kernel"
SUMMARY = "Linux kernel for NAI Industries"

### XXX - TODO  Deal with LICENSE stuff later
LICENSE = "GPLv2"
#LICENSE = "commercial"


#SRCREV = "${AUTOREV}" ## this builds the latest version or you use HASH for specific version
SRCREV = "735eea7cc7d985c96a5900146eabcc34336e0674"
SRCBRANCH = "75g5-x2"
KERNEL_SRC ?= "git://github.com/jeremymc526/PublicPlayZone.git;protocol=git"
SRC_URI += "${KERNEL_SRC};branch=${SRCBRANCH} "

S = "${WORKDIR}/git"

SRC_URI_append = "\
	file://defconfig.cfg \
	file://fragment.cfg \
	"

#PROVIDES += " linux virtual/kernel"
#This is a regex of machines
#COMPATIBLE_MACHINE_75g5-x2 = "(zc702-zynq7|75g5-x2)"
COMPATIBLE_MACHINE_75g5-x2 = "(75g5-x2)"

SRCREV_machine = "${SRCREV}"


COMPATIBLE_MACHINE_75g5-x2 = "(75g5-x2)"

SRCREV_machine = "${SRCREV}"

##############
### THIS SUCKS!
### Kernel config fragments dont seem to work for me.   
### Not sure what the heck is going on, but here is another way to
###  get kernel config fragments to apply 
#############

do_configure_append() {
	bbdebug 3 "Running do config APPEND()! "
	#this is a better way to do it.  But really Yocto should be managing all this. 
	#${S}/scripts/kconfig/merge_config.sh -m -O ${WORKDIR}/build ${WORKDIR}/build/.config ${WORKDIR}/*.cfg

	#This method works just fine!
	bbdebug 1 "Applying user specified configs"
	cat ${WORKDIR}/*.cfg >> ${B}/.config
}

