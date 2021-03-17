
DESCRIPTION = "The set of packages for development and testing provided by NAI industries"

LICENSE = "MIT"

NAII_IMAGE_INSTALL ?= "util-linux kernel-modules netbase init-ifupdown busybox base-passwd base-files sysvinit initscripts e2fsprogs e2fsprogs-mke2fs e2fsprogs-resize2fs mtd-utils gdb gdbserver bash strace openssh openssl elfutils sysfsutils usbutils dtc gawk ethtool grep iputils make pciutils sed setserial wget autoconf diffutils perl minicom valgrind i2c-tools lttng-tools iptables oprofile openssh-sftp-server memtool nfs-utils-client nfs-utils eglibc-dev libgcc-dev canutils udev udev-extraconf packagegroup-core-boot packagegroup-core-ssh-dropbear tcf-agent nfs-srv-init nfs-srv-setup bridge-utils"
IMAGE_INSTALL ?= "${NAII_IMAGE_INSTALL}"

inherit core-image

# naii-image.inc must be included after inherit core-image to override functionality

include naii-image.inc

