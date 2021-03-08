
IMAGE_LINGUAS = " "

IMAGE_ROOTFS_SIZE = "8192"

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = ""

IMAGE_DEVICE_TABLES += "  "

# remove not needed ipkg informations
ROOTFS_POSTPROCESS_COMMAND += " "

# Set root password to blank
zap_root_password () {
        sed -i 's%^root:[^:]*:%root::%' ${IMAGE_ROOTFS}/etc/passwd 
}

