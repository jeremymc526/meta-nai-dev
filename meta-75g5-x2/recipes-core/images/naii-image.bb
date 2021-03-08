
DESCRIPTION = "The set of packages for development and testing provided by NAI industries"

LICENSE = "MIT"

NAII_IMAGE_INSTALL ?= "util-linux initramfs kernel-modules netbase init-ifupdown busybox base-passwd base-files tinylogin sysvinit initscripts e2fsprogse2fsprogs-mke2fs e2fsprogs-resize2fs mtd-utils gdb gdbserver bash strace openssh openssl elfutils sysfsutils usbutils dtc gawk ethtool grep iputils make pciutils portmap sed setserial wget autoconf diffutils perl minicom valgrind i2c-tools lttng-tools iptables oprofile gator openssh-sftp-server memtool nfs-utils-client nfs-utils eglibc-dev libgcc-dev canutils udev udev-extraconf packagegroup-core-boot packagegroup-core-ssh-dropbear tcf-agent nfs-srv-init nfs-srv-setup bridge-utils"
IMAGE_INSTALL ?= "${NAII_IMAGE_INSTALL}"

inherit core-image

# naii-image.inc must be included after inherit core-image to override functionality

include naii-image.inc

