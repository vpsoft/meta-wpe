SUMMARY = "WPE Framework common plugins"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39fb5e7bc6aded7b6d2a5f5aa553425f"
PR = "r1"

require include/wpeframework-plugins.inc

SRC_URI = "git://github.com/rdkcentral/ThunderNanoServices.git;protocol=git;branch=master \
           file://index.html \
           file://osmc-devinput-remote.json \
           "
SRCREV = "751a7581214a641533ae6cbcb7c5b3ec5a96d7ac"
# ----------------------------------------------------------------------------

# More complicated plugins are moved seperate includes

include include/cobalt.inc
include include/compositor.inc
include include/dialserver.inc
include include/firmwarecontrol.inc
include include/ioconnector.inc
include include/ocdm.inc
include include/power.inc
include include/remotecontrol.inc
include include/snapshot.inc
include include/spark.inc
include include/streamer.inc
include include/webkitbrowser.inc
include include/webpa.inc

# ----------------------------------------------------------------------------

WPEFRAMEWORK_LOCATIONSYNC_URI ?= "http://jsonip.metrological.com/?maf=true"
PLUGIN_WEBSERVER_PORT ?= "8080"
PLUGIN_WEBSERVER_PATH ?= "/var/www/"

# ----------------------------------------------------------------------------

PACKAGECONFIG ?= " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'bluetooth',           'bluetoothcontrol', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'bluetooth',           'bluetoothremote', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opencdm',              'opencdmi', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'clearkey',             'opencdmi_ck', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'compositor',           'compositor', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready',            'opencdmi_pr', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready_nexus',      'opencdmi_prnx', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready_nexus_svp',  'opencdmi_prnx_svp', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready_vg',         'opencdmi_vgrdm', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'security',             'securityagent', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd',              'systemdconnector', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine',             'opencdmi_wv', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'thunder',              'network', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi',                'network wifi', '', d)} \
    ${@bb.utils.contains('STREAMER_DISTRO_PACKAGE_AVAILABLE', 'True', 'streamer', '', d)} \
    apps deviceinfo dhcpserver dialserver dictionary ioconnector locationsync monitor remote remote-devinput systemcommands timesync tracing ux virtualinput webkitbrowser webserver \
"

PACKAGECONFIG_append_rpi = " cobalt"
PACKAGECONFIG_append_brcm = " cobalt"

PACKAGECONFIG_append_rpi = " displayinfo"
PACKAGECONFIG_append_brcm = " displayinfo"

PACKAGECONFIG_append_rpi = " snapshot"
PACKAGECONFIG_append_brcm = " snapshot"

PACKAGECONFIG_append_brcm = " volumecontrol"
PACKAGECONFIG[bluetoothcontrol] = "-DPLUGIN_BLUETOOTH=ON -DPLUGIN_BLUETOOTH_AUTOSTART=true,-DPLUGIN_BLUETOOTH=OFF,,bluez5"
PACKAGECONFIG[bluetoothremote]  = "-DPLUGIN_BLUETOOTHREMOTECONTROL=ON -DPLUGIN_BLUETOOTHREMOTECONTROL_AUTOSTART=true,-DPLUGIN_BLUETOOTHREMOTECONTROL=OFF,"

PACKAGECONFIG[deviceinfo]     = "-DPLUGIN_DEVICEINFO=ON,-DPLUGIN_DEVICEINFO=OFF,"
PACKAGECONFIG[dhcpserver]     = "-DPLUGIN_DHCPSERVER=ON,-DPLUGIN_DHCPSERVER=OFF,"
PACKAGECONFIG[dictionary]     = "-DPLUGIN_DICTIONARY=ON,-DPLUGIN_DICTIONARY=OFF,"
PACKAGECONFIG[displayinfo]    = "-DPLUGIN_DISPLAYINFO=ON,-DPLUGIN_DISPLAYINFO=OFF,"
PACKAGECONFIG[dsgcc_client]   = "-DPLUGIN_DSGCCCLIENT=ON,,broadcom-refsw"
PACKAGECONFIG[dsresolution]   = "-DPLUGIN_DSRESOLUTION=ON,,devicesettings"
PACKAGECONFIG[filetransfer]   = "-DPLUGIN_FILETRANSFER=ON,-DPLUGIN_FILETRANSFER=OFF,"
PACKAGECONFIG[locationsync]   = "-DPLUGIN_LOCATIONSYNC=ON \
                                 -DPLUGIN_LOCATIONSYNC_URI=${WPEFRAMEWORK_LOCATIONSYNC_URI} \
                                ,-DPLUGIN_LOCATIONSYNC=OFF,"
PACKAGECONFIG[network]        = "-DPLUGIN_NETWORKCONTROL=ON,-DPLUGIN_NETWORKCONTROL=OFF,"
PACKAGECONFIG[monitor]        = "-DPLUGIN_MONITOR=ON \
                                 -DPLUGIN_WEBKITBROWSER_MEMORYLIMIT=614400 \
                                 -DPLUGIN_YOUTUBE_MEMORYLIMIT=614400 \
                                 -DPLUGIN_NETFLIX_MEMORYLIMIT=307200 \
                                ,-DPLUGIN_MONITOR=OFF,"
PACKAGECONFIG[packager]         = "-DPLUGIN_PACKAGER=ON, -DPLUGIN_PACKAGER=OFF,,opkg"
PACKAGECONFIG[securityagent]    = "-DPLUGIN_SECURITYAGENT=ON,-DPLUGIN_SECURITYAGENT=OFF,"
PACKAGECONFIG[systemcommands]   = "-DPLUGIN_SYSTEMCOMMANDS=ON,-DPLUGIN_SYSTEMCOMMANDS=OFF,"
PACKAGECONFIG[systemdconnector] = "-DPLUGIN_SYSTEMDCONNECTOR=ON,-DPLUGIN_SYSTEMDCONNECTOR=OFF,"
PACKAGECONFIG[timesync]       = "-DPLUGIN_TIMESYNC=ON,-DPLUGIN_TIMESYNC=OFF,"
PACKAGECONFIG[tracing]        = "-DPLUGIN_TRACECONTROL=ON,-DPLUGIN_TRACECONTROL=OFF,"
PACKAGECONFIG[virtualinput]   = "-DPLUGIN_COMPOSITOR_VIRTUALINPUT=ON,-DPLUGIN_COMPOSITOR_VIRTUALINPUT=OFF,"
PACKAGECONFIG[volumecontrol]  = "-DPLUGIN_VOLUMECONTROL=ON,-DPLUGIN_VOLUMECONTROL=OFF,"
PACKAGECONFIG[webproxy]       = "-DPLUGIN_WEBPROXY=ON,-DPLUGIN_WEBPROXY=OFF,"
PACKAGECONFIG[webserver]      = "-DPLUGIN_WEBSERVER=ON \
                                 -DPLUGIN_WEBSERVER_PORT="${PLUGIN_WEBSERVER_PORT}" \
                                 -DPLUGIN_WEBSERVER_PATH="${PLUGIN_WEBSERVER_PATH}" \
                                 ,-DPLUGIN_WEBSERVER=OFF,"
PACKAGECONFIG[webshell]       = "-DPLUGIN_WEBSHELL=ON,-DPLUGIN_WEBSHELL=OFF,"

WPE_WIFICONTROL_DEP          ?= ""
WPE_WIFICONTROL_DEP_rpi      ?= "linux-firmware-bcm43430"
PACKAGECONFIG[wifi]           = "-DPLUGIN_WIFICONTROL=ON,-DPLUGIN_WIFICONTROL=OFF,,wpa-supplicant ${WPE_WIFICONTROL_DEP}"
PACKAGECONFIG[wifi_rdkhal]    = "-DPLUGIN_USE_RDK_HAL_WIFI=ON,-DPLUGIN_USE_RDK_HAL_WIFI=OFF,,wifi-hal"

# ----------------------------------------------------------------------------

EXTRA_OECMAKE += " \
    -DBUILD_REFERENCE=${SRCREV} \
    -DBUILD_SHARED_LIBS=ON \
"

# ----------------------------------------------------------------------------

do_install_append() {
    if ${@bb.utils.contains("PACKAGECONFIG", "webserver", "true", "false", d)}
    then
      if ${@bb.utils.contains("PACKAGECONFIG", "webkitbrowser", "true", "false", d)}
      then
          install -d ${D}/var/www
          install -m 0755 ${WORKDIR}/index.html ${D}/var/www/
      fi
      install -d ${D}${PLUGIN_WEBSERVER_PATH}
    fi
}

# ----------------------------------------------------------------------------

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/wpeframework/plugins/*.so ${libdir}/*.so ${datadir}/WPEFramework/* /var/www/index.html"
FILES_${PN} += "${includedir}/WPEFramework/*"
FILES_${PN}-dev += "${libdir}/cmake/*"

INSANE_SKIP_${PN} += "libdir staticdev dev-so"
INSANE_SKIP_${PN}-dbg += "libdir"
