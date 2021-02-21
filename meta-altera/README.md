meta-altera
===========

OpenEmbedded / Yocto Layer for Altera SOC FPGA.  This is subtree split from the poky-socfpga repository 
from http://git.rocketboards.org/poky-socfpga.git

How was split was performed
===========================
    git clone git://github.com/redrocketcomputing/poky-socfpga
    git clone git@github:redrocketcomputing/meta-alter
    cd ./poky-socfpga
    git subtree split -P meta-altera -b meta-altera
    cd ../meta-altera
    git pull ../poky-socfpga meta-altera
    git push
 
