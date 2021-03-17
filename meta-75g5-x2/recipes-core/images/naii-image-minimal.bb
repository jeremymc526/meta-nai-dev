
DESCRIPTION = "The set of packages for development and testing provided by NAII"

LICENSE = "MIT"

NAII_MINIMAL_IMAGE_INSTALL ?= "util-linux kernel-modules busybox base-passwd base-files busybox sysvinit initscripts"
IMAGE_INSTALL ?= "${NAII_MINIMAL_IMAGE_INSTALL}"

inherit core-image

# naii-image.inc must be included after inherit core-image to override functionality
include naii-image.inc

