require u-boot-mkimage-naii.inc

SRCBRANCH = "75g5-x2"
KERNEL_SRC ?= "git://github.com/jeremymc526/BouncyCastle.git;protocol=git"
SRC_URI += "${KERNEL_SRC};branch=${SRCBRANCH} "

S = "${WORKDIR}/uboot-naii"

