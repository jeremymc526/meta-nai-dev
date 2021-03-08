DESCRIPTION = "U-boot bootloader for Naii socfpga"
HOMEPAGE = "http://www.naii.com/"
SECTION = "bootloaders"
PROVIDES += " virtual/bootloader u-boot"
PROVIDES += " u-boot-naii"

inherit uboot-config deploy
require recipes-bsp/u-boot/u-boot.inc

PARALLEL_MAKE=""
PACKAGES=""

##
#SRC_URI = "git://github.com/redrocketcomputing/u-boot-socfpga.git;protocol=git;branch=socfpga_v${PV}"
#SRCREV_pn-${PN} = "${AUTOREV}"
##
#SRCREV = "${AUTOREV}" ## this builds the latest version or you use HASH for specific version
SRCREV = "586d7124ab6d66b21e2dbc9db07d97dd7c0e7621"
SRCBRANCH = "75g5-x2"
KERNEL_SRC ?= "git://github.com/jeremymc526/BouncyCastle.git;protocol=git"
SRC_URI += "${KERNEL_SRC};branch=${SRCBRANCH} "

SRC_URI_append = "\
        file://fragment.cfg \
        file://75g5-x2_defconfig.cfg \
        "

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${S}/Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}/git"

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"


# GCC 4.5.1 builds unusable binaries using -Os, remove it from OPTFLAGS
#EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${CC}" V=1'

python () {
	if not d.getVar("UBOOT_MACHINE", True):
		PN = d.getVar("PN", True)
		FILE = os.path.basename(d.getVar("FILE", True))
		bb.debug(1, "To build %s, see %s for instructions on \
			     setting up your machine config" % (PN, FILE))
		raise bb.parse.SkipPackage("because UBOOT_MACHINE is not set")
}

UBOOT_ELF ?= "u-boot-${MACHINE}-${PV}-${PR}"
UBOOT_IMAGE ?= "u-boot-${MACHINE}-${PV}-${PR}.bin"
UBOOT_IMAGE_UIMAGE ?= "u-boot-${MACHINE}-${PV}-${PR}.img"
UBOOT_IMAGE_SPL ?= "u-boot-spl-${MACHINE}-${PV}-${PR}.bin"
UBOOT_ELF_SPL ?= "u-boot-spl-${MACHINE}-${PV}-${PR}"
UBOOT_BINARY ?= "u-boot.bin"
UBOOT_SYMLINK_ELF ?= "u-boot-${MACHINE}"
UBOOT_SYMLINK ?= "u-boot-${MACHINE}.bin"
UBOOT_SYMLINK_UIMAGE ?= "u-boot-${MACHINE}.img"
UBOOT_SYMLINK_SPL ?= "u-boot-spl-${MACHINE}.bin"
UBOOT_SYMLINK_ELF_SPL ?= "u-boot-spl-${MACHINE}"
UBOOT_MAKE_TARGET ?= "all"
INC_PR = "r0"


do_install () {
    install -d ${D}/boot
    install ${WORKDIR}/build/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}
#JRM Do we need u-boot-spl ???
    bbdebug 1 "NOTE, SKIPPING u-boot-spl install as I dont know if we need it or what it is "
#install ${WORKDIR}/spl/u-boot-spl ${D}/boot/${UBOOT_ELF_SPL}
    bbdebug 1 " Running workdir/u-boot -> UBOOT_ELF"
    install ${WORKDIR}/build/u-boot ${D}/boot/${UBOOT_ELF}
    bbdebug 1 " Linking UBOOT_IMAGE to UBOOT bin "
    ln -sf ${UBOOT_IMAGE} ${D}/boot/${UBOOT_BINARY}

#JRM do this if you need to stop a bbrecipe 
#raise "STopping do_install"
}

FILES_${PN} = "/boot"

do_deploy () {
	install ${WORKDIR}/build/u-boot.bin ${DEPLOYDIR}/${UBOOT_IMAGE}
	#install ${WORKDIR}/build/u-boot.img ${DEPLOYDIR}/${UBOOT_IMAGE_UIMAGE}
#JRM Do we need u-boot SPL ?
	bbdebug 1 "NOTE, SKIPPING u-boot-spl install as I dont know if we need it or what it is "
	#install ${WORKDIR}/spl/u-boot-spl.bin ${DEPLOYDIR}/boot/${UBOOT_IMAGE_SPL}
	#install ${WORKDIR}/spl/u-boot-spl ${DEPLOYDIR}/${UBOOT_ELF_SPL}
	install ${WORKDIR}/build/u-boot ${DEPLOYDIR}/${UBOOT_ELF}

#JRM where do we want to put all this stuff ???
	#mkdir -p ${DEPLOYDIR}
	#cd ${DEPLOYDIR}
	#rm -f ${UBOOT_SYMLINK}
	#ln -sf ${UBOOT_IMAGE} ${UBOOT_SYMLINK}
	#rm -f ${UBOOT_SYMLINK_UIMAGE}
	#ln -sf ${UBOOT_IMAGE_UIMAGE} ${UBOOT_SYMLINK_UIMAGE}
}
addtask deploy before do_build after do_compile


do_configure_prepend_75g5_x2() {
    cp -f ${WORKDIR}/75g5-x2_defconfig ${S}/configs
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/${UBOOT_ENV}.txt

    if [ -f "${WORKDIR}/${UBOOT_ENV}.txt" ]; then
        mkimage -O linux -T script -C none -n "U-Boot boot script" \
            -d ${WORKDIR}/${UBOOT_ENV}.txt ${WORKDIR}/boot.scr.uimg
    fi
}

do_configure_append() {
        bbdebug 3 "Running do config APPEND()! "
        ###this is a better way to do it.  But really Yocto should be managing all this.
        ###${S}/scripts/kconfig/merge_config.sh -m -O ${WORKDIR}/build ${WORKDIR}/build/.config ${WORKDIR}/*.cfg
        ###This method works just fine!
        bbdebug 1 "Applying user specified configs"
        cat ${WORKDIR}/*.cfg >> ${B}/.config
}

do_deploy_append_75g5_x2() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOY_DIR_IMAGE}
    fi
}

FILES_${PN}-env += "/boot/boot.scr.uimg"
                                                       

