DESCRIPTION = "Recipe to copy externally built HDF to deploy"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
#see sources/core/../meta-xilinx-tools/recipes-bsp/hdf/external-hdf.bb 
LICENSE = "CLOSED"

PROVIDES = " virtual/hdf"
inherit deploy

##Do we want these stored in a local git repo ?
#HDF_BASE_75g5-x2 ?= "file://"
#HDF_PATH_75g5-x2 ?= "HDF_X2_75G5_E_00_00005.00001_20200501_143115.hdf"

###NOTE the rest is a clone of meta-xilinx-tools/recipes-bsp/hdf/external-hdf.bb

SRCREV = "2c8179dcf05d2706515a2a2319cfbc0cdfdfa9e3"
HDF_BASE ?= "git://"
HDF_PATH ??= "github.com/jeremymc526/naii-hdf-files.git;branch=main;protocol=git"
HDF_NAME = "HDF_X2_75G5_E_00_00005.00001_20200501_143115.hdf"

S = "${WORKDIR}/git"

#Set HDF_EXT to "dsa" if you want to use a dsa file instead of hdf.
HDF_EXT ?= "hdf"

SRC_URI = "${HDF_BASE}${HDF_PATH}"

PACKAGE_ARCH ?= "${MACHINE_ARCH}"


#SRCREV ?= "35fc620e3cd7dc06cddd610e61a738c68412ae67"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

python do_check() {
    ext=d.getVar('HDF_EXT',True)
    if(ext == 'hdf'):
         bb.warn("Only XSA format is supported in Vivado tool starting from 2019.2 release, thus we need to stay <= 2019.1")
}

do_install() {
    install -d ${D}/opt/xilinx/hw-design
    if [ "${HDF_BASE}" = "git://" ]; then
         install -m 0644 ${S}/${HDF_NAME} ${D}/opt/xilinx/hw-design/design.xsa
    else
         install -m 0644 ${WORKDIR}/${HDF_PATH} ${D}/opt/xilinx/hw-design/design.xsa
    fi
}



do_deploy() {
    install -d ${DEPLOYDIR}
    if [ "${HDF_BASE}" = "git://" ]; then
        install -m 0644 ${WORKDIR}/git/${HDF_NAME} ${DEPLOYDIR}/Xilinx-${MACHINE}.${HDF_EXT}
    else
        install -m 0644 ${WORKDIR}/${HDF_PATH} ${DEPLOYDIR}/Xilinx-${MACHINE}.${HDF_EXT}
    fi
}

addtask do_check before do_deploy
addtask do_deploy after do_install
FILES_${PN}= "/opt/xilinx/hw-design/design.xsa"
SYSROOT_DIRS += "/opt"
