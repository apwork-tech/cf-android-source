package com.wallet.cryptofuelx.utils.helper

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.loader.content.CursorLoader
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*

/**
 * This is a class that contains utils to work with files
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
class FileUtils private constructor() {

    companion object {
        private val sMimeTypes = HashMap<String, String>()

        private fun init() {
            sMimeTypes["323"] = "text/h323"
            sMimeTypes["3g2"] = "video/3gpp2"
            sMimeTypes["3gp"] = "video/3gpp"
            sMimeTypes["3gp2"] = "video/3gpp2"
            sMimeTypes["3gpp"] = "video/3gpp"
            sMimeTypes["7z"] = "application/x-7z-compressed"
            sMimeTypes["aa"] = "audio/audible"
            sMimeTypes["AAC"] = "audio/aac"
            sMimeTypes["aaf"] = "application/octet-stream"
            sMimeTypes["aax"] = "audio/vnd.audible.aax"
            sMimeTypes["ac3"] = "audio/ac3"
            sMimeTypes["aca"] = "application/octet-stream"
            sMimeTypes["accda"] = "application/msaccess.addin"
            sMimeTypes["accdb"] = "application/msaccess"
            sMimeTypes["accdc"] = "application/msaccess.cab"
            sMimeTypes["accde"] = "application/msaccess"
            sMimeTypes["accdr"] = "application/msaccess.runtime"
            sMimeTypes["accdt"] = "application/msaccess"
            sMimeTypes["accdw"] = "application/msaccess.webapplication"
            sMimeTypes["accft"] = "application/msaccess.ftemplate"
            sMimeTypes["acx"] = "application/internet-property-stream"
            sMimeTypes["AddIn"] = "text/xml"
            sMimeTypes["ade"] = "application/msaccess"
            sMimeTypes["adobebridge"] = "application/x-bridge-url"
            sMimeTypes["adp"] = "application/msaccess"
            sMimeTypes["ADT"] = "audio/vnd.dlna.adts"
            sMimeTypes["ADTS"] = "audio/aac"
            sMimeTypes["afm"] = "application/octet-stream"
            sMimeTypes["ai"] = "application/postscript"
            sMimeTypes["aif"] = "audio/aiff"
            sMimeTypes["aifc"] = "audio/aiff"
            sMimeTypes["aiff"] = "audio/aiff"
            sMimeTypes["air"] = "application/vnd.adobe.air-application-installer-package+zip"
            sMimeTypes["amc"] = "application/mpeg"
            sMimeTypes["anx"] = "application/annodex"
            sMimeTypes["apk"] = "application/vnd.android.package-archive"
            sMimeTypes["application"] = "application/x-ms-application"
            sMimeTypes["art"] = "image/x-jg"
            sMimeTypes["asa"] = "application/xml"
            sMimeTypes["asax"] = "application/xml"
            sMimeTypes["ascx"] = "application/xml"
            sMimeTypes["asd"] = "application/octet-stream"
            sMimeTypes["asf"] = "video/x-ms-asf"
            sMimeTypes["ashx"] = "application/xml"
            sMimeTypes["asi"] = "application/octet-stream"
            sMimeTypes["asm"] = "text/plain"
            sMimeTypes["asmx"] = "application/xml"
            sMimeTypes["aspx"] = "application/xml"
            sMimeTypes["asr"] = "video/x-ms-asf"
            sMimeTypes["asx"] = "video/x-ms-asf"
            sMimeTypes["atom"] = "application/atom+xml"
            sMimeTypes["au"] = "audio/basic"
            sMimeTypes["avi"] = "video/x-msvideo"
            sMimeTypes["axa"] = "audio/annodex"
            sMimeTypes["axs"] = "application/olescript"
            sMimeTypes["axv"] = "video/annodex"
            sMimeTypes["bas"] = "text/plain"
            sMimeTypes["bcpio"] = "application/x-bcpio"
            sMimeTypes["bin"] = "application/octet-stream"
            sMimeTypes["bmp"] = "image/bmp"
            sMimeTypes["c"] = "text/plain"
            sMimeTypes["cab"] = "application/octet-stream"
            sMimeTypes["caf"] = "audio/x-caf"
            sMimeTypes["calx"] = "application/vnd.ms-office.calx"
            sMimeTypes["cat"] = "application/vnd.ms-pki.seccat"
            sMimeTypes["cc"] = "text/plain"
            sMimeTypes["cd"] = "text/plain"
            sMimeTypes["cdda"] = "audio/aiff"
            sMimeTypes["cdf"] = "application/x-cdf"
            sMimeTypes["cer"] = "application/x-x509-ca-cert"
            sMimeTypes["cfg"] = "text/plain"
            sMimeTypes["chm"] = "application/octet-stream"
            sMimeTypes["class"] = "application/x-java-applet"
            sMimeTypes["clp"] = "application/x-msclip"
            sMimeTypes["cmd"] = "text/plain"
            sMimeTypes["cmx"] = "image/x-cmx"
            sMimeTypes["cnf"] = "text/plain"
            sMimeTypes["cod"] = "image/cis-cod"
            sMimeTypes["config"] = "application/xml"
            sMimeTypes["contact"] = "text/x-ms-contact"
            sMimeTypes["coverage"] = "application/xml"
            sMimeTypes["cpio"] = "application/x-cpio"
            sMimeTypes["cpp"] = "text/plain"
            sMimeTypes["crd"] = "application/x-mscardfile"
            sMimeTypes["crl"] = "application/pkix-crl"
            sMimeTypes["crt"] = "application/x-x509-ca-cert"
            sMimeTypes["cs"] = "text/plain"
            sMimeTypes["csdproj"] = "text/plain"
            sMimeTypes["csh"] = "application/x-csh"
            sMimeTypes["csproj"] = "text/plain"
            sMimeTypes["css"] = "text/css"
            sMimeTypes["csv"] = "text/csv"
            sMimeTypes["cur"] = "application/octet-stream"
            sMimeTypes["cxx"] = "text/plain"
            sMimeTypes["dat"] = "application/octet-stream"
            sMimeTypes["datasource"] = "application/xml"
            sMimeTypes["dbproj"] = "text/plain"
            sMimeTypes["dcr"] = "application/x-director"
            sMimeTypes["def"] = "text/plain"
            sMimeTypes["deploy"] = "application/octet-stream"
            sMimeTypes["der"] = "application/x-x509-ca-cert"
            sMimeTypes["dgml"] = "application/xml"
            sMimeTypes["dib"] = "image/bmp"
            sMimeTypes["dif"] = "video/x-dv"
            sMimeTypes["dir"] = "application/x-director"
            sMimeTypes["disco"] = "text/xml"
            sMimeTypes["divx"] = "video/divx"
            sMimeTypes["dll"] = "application/x-msdownload"
            sMimeTypes["dll.config"] = "text/xml"
            sMimeTypes["dlm"] = "text/dlm"
            sMimeTypes["doc"] = "application/msword"
            sMimeTypes["docm"] = "application/vnd.ms-word.document.macroEnabled.12"
            sMimeTypes["docx"] = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            sMimeTypes["dot"] = "application/msword"
            sMimeTypes["dotm"] = "application/vnd.ms-word.template.macroEnabled.12"
            sMimeTypes["dotx"] = "application/vnd.openxmlformats-officedocument.wordprocessingml.template"
            sMimeTypes["dsp"] = "application/octet-stream"
            sMimeTypes["dsw"] = "text/plain"
            sMimeTypes["dtd"] = "text/xml"
            sMimeTypes["dtsConfig"] = "text/xml"
            sMimeTypes["dv"] = "video/x-dv"
            sMimeTypes["dvi"] = "application/x-dvi"
            sMimeTypes["dwf"] = "drawing/x-dwf"
            sMimeTypes["dwp"] = "application/octet-stream"
            sMimeTypes["dxr"] = "application/x-director"
            sMimeTypes["eml"] = "message/rfc822"
            sMimeTypes["emz"] = "application/octet-stream"
            sMimeTypes["eot"] = "application/vnd.ms-fontobject"
            sMimeTypes["eps"] = "application/postscript"
            sMimeTypes["etl"] = "application/etl"
            sMimeTypes["etx"] = "text/x-setext"
            sMimeTypes["evy"] = "application/envoy"
            sMimeTypes["exe"] = "application/octet-stream"
            sMimeTypes["exe.config"] = "text/xml"
            sMimeTypes["fdf"] = "application/vnd.fdf"
            sMimeTypes["fif"] = "application/fractals"
            sMimeTypes["filters"] = "application/xml"
            sMimeTypes["fla"] = "application/octet-stream"
            sMimeTypes["flac"] = "audio/flac"
            sMimeTypes["flr"] = "x-world/x-vrml"
            sMimeTypes["flv"] = "video/x-flv"
            sMimeTypes["fsscript"] = "application/fsharp-script"
            sMimeTypes["fsx"] = "application/fsharp-script"
            sMimeTypes["generictest"] = "application/xml"
            sMimeTypes["gif"] = "image/gif"
            sMimeTypes["group"] = "text/x-ms-group"
            sMimeTypes["gsm"] = "audio/x-gsm"
            sMimeTypes["gtar"] = "application/x-gtar"
            sMimeTypes["gz"] = "application/x-gzip"
            sMimeTypes["h"] = "text/plain"
            sMimeTypes["hdf"] = "application/x-hdf"
            sMimeTypes["hdml"] = "text/x-hdml"
            sMimeTypes["hhc"] = "application/x-oleobject"
            sMimeTypes["hhk"] = "application/octet-stream"
            sMimeTypes["hhp"] = "application/octet-stream"
            sMimeTypes["hlp"] = "application/winhlp"
            sMimeTypes["hpp"] = "text/plain"
            sMimeTypes["hqx"] = "application/mac-binhex40"
            sMimeTypes["hta"] = "application/hta"
            sMimeTypes["htc"] = "text/x-component"
            sMimeTypes["htm"] = "text/html"
            sMimeTypes["html"] = "text/html"
            sMimeTypes["htt"] = "text/webviewhtml"
            sMimeTypes["hxa"] = "application/xml"
            sMimeTypes["hxc"] = "application/xml"
            sMimeTypes["hxd"] = "application/octet-stream"
            sMimeTypes["hxe"] = "application/xml"
            sMimeTypes["hxf"] = "application/xml"
            sMimeTypes["hxh"] = "application/octet-stream"
            sMimeTypes["hxi"] = "application/octet-stream"
            sMimeTypes["hxk"] = "application/xml"
            sMimeTypes["hxq"] = "application/octet-stream"
            sMimeTypes["hxr"] = "application/octet-stream"
            sMimeTypes["hxs"] = "application/octet-stream"
            sMimeTypes["hxt"] = "text/html"
            sMimeTypes["hxv"] = "application/xml"
            sMimeTypes["hxw"] = "application/octet-stream"
            sMimeTypes["hxx"] = "text/plain"
            sMimeTypes["i"] = "text/plain"
            sMimeTypes["ico"] = "image/x-icon"
            sMimeTypes["ics"] = "application/octet-stream"
            sMimeTypes["idl"] = "text/plain"
            sMimeTypes["ief"] = "image/ief"
            sMimeTypes["iii"] = "application/x-iphone"
            sMimeTypes["inc"] = "text/plain"
            sMimeTypes["inf"] = "application/octet-stream"
            sMimeTypes["ini"] = "text/plain"
            sMimeTypes["inl"] = "text/plain"
            sMimeTypes["ins"] = "application/x-internet-signup"
            sMimeTypes["ipa"] = "application/x-itunes-ipa"
            sMimeTypes["ipg"] = "application/x-itunes-ipg"
            sMimeTypes["ipproj"] = "text/plain"
            sMimeTypes["ipsw"] = "application/x-itunes-ipsw"
            sMimeTypes["iqy"] = "text/x-ms-iqy"
            sMimeTypes["isp"] = "application/x-internet-signup"
            sMimeTypes["ite"] = "application/x-itunes-ite"
            sMimeTypes["itlp"] = "application/x-itunes-itlp"
            sMimeTypes["itms"] = "application/x-itunes-itms"
            sMimeTypes["itpc"] = "application/x-itunes-itpc"
            sMimeTypes["IVF"] = "video/x-ivf"
            sMimeTypes["jar"] = "application/java-archive"
            sMimeTypes["java"] = "application/octet-stream"
            sMimeTypes["jck"] = "application/liquidmotion"
            sMimeTypes["jcz"] = "application/liquidmotion"
            sMimeTypes["jfif"] = "image/pjpeg"
            sMimeTypes["jnlp"] = "application/x-java-jnlp-file"
            sMimeTypes["jpb"] = "application/octet-stream"
            sMimeTypes["jpe"] = "image/jpeg"
            sMimeTypes["jpeg"] = "image/jpeg"
            sMimeTypes["jpg"] = "image/jpeg"
            sMimeTypes["js"] = "application/javascript"
            sMimeTypes["json"] = "application/json"
            sMimeTypes["jsx"] = "text/jscript"
            sMimeTypes["jsxbin"] = "text/plain"
            sMimeTypes["latex"] = "application/x-latex"
            sMimeTypes["library-ms"] = "application/windows-library+xml"
            sMimeTypes["lit"] = "application/x-ms-reader"
            sMimeTypes["loadtest"] = "application/xml"
            sMimeTypes["lpk"] = "application/octet-stream"
            sMimeTypes["lsf"] = "video/x-la-asf"
            sMimeTypes["lst"] = "text/plain"
            sMimeTypes["lsx"] = "video/x-la-asf"
            sMimeTypes["lzh"] = "application/octet-stream"
            sMimeTypes["m13"] = "application/x-msmediaview"
            sMimeTypes["m14"] = "application/x-msmediaview"
            sMimeTypes["m1v"] = "video/mpeg"
            sMimeTypes["m2t"] = "video/vnd.dlna.mpeg-tts"
            sMimeTypes["m2ts"] = "video/vnd.dlna.mpeg-tts"
            sMimeTypes["m2v"] = "video/mpeg"
            sMimeTypes["m3u"] = "audio/x-mpegurl"
            sMimeTypes["m3u8"] = "audio/x-mpegurl"
            sMimeTypes["m4a"] = "audio/m4a"
            sMimeTypes["m4b"] = "audio/m4b"
            sMimeTypes["m4p"] = "audio/m4p"
            sMimeTypes["m4r"] = "audio/x-m4r"
            sMimeTypes["m4v"] = "video/x-m4v"
            sMimeTypes["mac"] = "image/x-macpaint"
            sMimeTypes["mak"] = "text/plain"
            sMimeTypes["man"] = "application/x-troff-man"
            sMimeTypes["manifest"] = "application/x-ms-manifest"
            sMimeTypes["map"] = "text/plain"
            sMimeTypes["master"] = "application/xml"
            sMimeTypes["mda"] = "application/msaccess"
            sMimeTypes["mdb"] = "application/x-msaccess"
            sMimeTypes["mde"] = "application/msaccess"
            sMimeTypes["mdp"] = "application/octet-stream"
            sMimeTypes["me"] = "application/x-troff-me"
            sMimeTypes["mfp"] = "application/x-shockwave-flash"
            sMimeTypes["mht"] = "message/rfc822"
            sMimeTypes["mhtml"] = "message/rfc822"
            sMimeTypes["mid"] = "audio/mid"
            sMimeTypes["midi"] = "audio/mid"
            sMimeTypes["mix"] = "application/octet-stream"
            sMimeTypes["mk"] = "text/plain"
            sMimeTypes["mmf"] = "application/x-smaf"
            sMimeTypes["mno"] = "text/xml"
            sMimeTypes["mny"] = "application/x-msmoney"
            sMimeTypes["mod"] = "video/mpeg"
            sMimeTypes["mov"] = "video/quicktime"
            sMimeTypes["movie"] = "video/x-sgi-movie"
            sMimeTypes["mp2"] = "video/mpeg"
            sMimeTypes["mp2v"] = "video/mpeg"
            sMimeTypes["mp3"] = "audio/mpeg"
            sMimeTypes["mp4"] = "video/mp4"
            sMimeTypes["mp4v"] = "video/mp4"
            sMimeTypes["mpa"] = "video/mpeg"
            sMimeTypes["mpe"] = "video/mpeg"
            sMimeTypes["mpeg"] = "video/mpeg"
            sMimeTypes["mpf"] = "application/vnd.ms-mediapackage"
            sMimeTypes["mpg"] = "video/mpeg"
            sMimeTypes["mpp"] = "application/vnd.ms-project"
            sMimeTypes["mpv2"] = "video/mpeg"
            sMimeTypes["mqv"] = "video/quicktime"
            sMimeTypes["ms"] = "application/x-troff-ms"
            sMimeTypes["msi"] = "application/octet-stream"
            sMimeTypes["mso"] = "application/octet-stream"
            sMimeTypes["mts"] = "video/vnd.dlna.mpeg-tts"
            sMimeTypes["mtx"] = "application/xml"
            sMimeTypes["mvb"] = "application/x-msmediaview"
            sMimeTypes["mvc"] = "application/x-miva-compiled"
            sMimeTypes["mxp"] = "application/x-mmxp"
            sMimeTypes["nc"] = "application/x-netcdf"
            sMimeTypes["nsc"] = "video/x-ms-asf"
            sMimeTypes["nws"] = "message/rfc822"
            sMimeTypes["ocx"] = "application/octet-stream"
            sMimeTypes["oda"] = "application/oda"
            sMimeTypes["odb"] = "application/vnd.oasis.opendocument.database"
            sMimeTypes["odc"] = "application/vnd.oasis.opendocument.chart"
            sMimeTypes["odf"] = "application/vnd.oasis.opendocument.formula"
            sMimeTypes["odg"] = "application/vnd.oasis.opendocument.graphics"
            sMimeTypes["odh"] = "text/plain"
            sMimeTypes["odi"] = "application/vnd.oasis.opendocument.image"
            sMimeTypes["odl"] = "text/plain"
            sMimeTypes["odm"] = "application/vnd.oasis.opendocument.text-master"
            sMimeTypes["odp"] = "application/vnd.oasis.opendocument.presentation"
            sMimeTypes["ods"] = "application/vnd.oasis.opendocument.spreadsheet"
            sMimeTypes["odt"] = "application/vnd.oasis.opendocument.text"
            sMimeTypes["oga"] = "audio/ogg"
            sMimeTypes["ogg"] = "audio/ogg"
            sMimeTypes["ogv"] = "video/ogg"
            sMimeTypes["ogx"] = "application/ogg"
            sMimeTypes["one"] = "application/onenote"
            sMimeTypes["onea"] = "application/onenote"
            sMimeTypes["onepkg"] = "application/onenote"
            sMimeTypes["onetmp"] = "application/onenote"
            sMimeTypes["onetoc"] = "application/onenote"
            sMimeTypes["onetoc2"] = "application/onenote"
            sMimeTypes["opus"] = "audio/ogg"
            sMimeTypes["orderedtest"] = "application/xml"
            sMimeTypes["osdx"] = "application/opensearchdescription+xml"
            sMimeTypes["otf"] = "application/font-sfnt"
            sMimeTypes["otg"] = "application/vnd.oasis.opendocument.graphics-template"
            sMimeTypes["oth"] = "application/vnd.oasis.opendocument.text-web"
            sMimeTypes["otp"] = "application/vnd.oasis.opendocument.presentation-template"
            sMimeTypes["ots"] = "application/vnd.oasis.opendocument.spreadsheet-template"
            sMimeTypes["ott"] = "application/vnd.oasis.opendocument.text-template"
            sMimeTypes["oxt"] = "application/vnd.openofficeorg.extension"
            sMimeTypes["p10"] = "application/pkcs10"
            sMimeTypes["p12"] = "application/x-pkcs12"
            sMimeTypes["p7b"] = "application/x-pkcs7-certificates"
            sMimeTypes["p7c"] = "application/pkcs7-mime"
            sMimeTypes["p7m"] = "application/pkcs7-mime"
            sMimeTypes["p7r"] = "application/x-pkcs7-certreqresp"
            sMimeTypes["p7s"] = "application/pkcs7-signature"
            sMimeTypes["pbm"] = "image/x-portable-bitmap"
            sMimeTypes["pcast"] = "application/x-podcast"
            sMimeTypes["pct"] = "image/pict"
            sMimeTypes["pcx"] = "application/octet-stream"
            sMimeTypes["pcz"] = "application/octet-stream"
            sMimeTypes["pdf"] = "application/pdf"
            sMimeTypes["pfb"] = "application/octet-stream"
            sMimeTypes["pfm"] = "application/octet-stream"
            sMimeTypes["pfx"] = "application/x-pkcs12"
            sMimeTypes["pgm"] = "image/x-portable-graymap"
            sMimeTypes["pic"] = "image/pict"
            sMimeTypes["pict"] = "image/pict"
            sMimeTypes["pkgdef"] = "text/plain"
            sMimeTypes["pkgundef"] = "text/plain"
            sMimeTypes["pko"] = "application/vnd.ms-pki.pko"
            sMimeTypes["pls"] = "audio/scpls"
            sMimeTypes["pma"] = "application/x-perfmon"
            sMimeTypes["pmc"] = "application/x-perfmon"
            sMimeTypes["pml"] = "application/x-perfmon"
            sMimeTypes["pmr"] = "application/x-perfmon"
            sMimeTypes["pmw"] = "application/x-perfmon"
            sMimeTypes["png"] = "image/png"
            sMimeTypes["pnm"] = "image/x-portable-anymap"
            sMimeTypes["pnt"] = "image/x-macpaint"
            sMimeTypes["pntg"] = "image/x-macpaint"
            sMimeTypes["pnz"] = "image/png"
            sMimeTypes["pot"] = "application/vnd.ms-powerpoint"
            sMimeTypes["potm"] = "application/vnd.ms-powerpoint.template.macroEnabled.12"
            sMimeTypes["potx"] = "application/vnd.openxmlformats-officedocument.presentationml.template"
            sMimeTypes["ppa"] = "application/vnd.ms-powerpoint"
            sMimeTypes["ppam"] = "application/vnd.ms-powerpoint.addin.macroEnabled.12"
            sMimeTypes["ppm"] = "image/x-portable-pixmap"
            sMimeTypes["pps"] = "application/vnd.ms-powerpoint"
            sMimeTypes["ppsm"] = "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"
            sMimeTypes["ppsx"] = "application/vnd.openxmlformats-officedocument.presentationml.slideshow"
            sMimeTypes["ppt"] = "application/vnd.ms-powerpoint"
            sMimeTypes["pptm"] = "application/vnd.ms-powerpoint.presentation.macroEnabled.12"
            sMimeTypes["pptx"] = "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            sMimeTypes["prf"] = "application/pics-rules"
            sMimeTypes["prm"] = "application/octet-stream"
            sMimeTypes["prx"] = "application/octet-stream"
            sMimeTypes["ps"] = "application/postscript"
            sMimeTypes["psc1"] = "application/PowerShell"
            sMimeTypes["psd"] = "application/octet-stream"
            sMimeTypes["psess"] = "application/xml"
            sMimeTypes["psm"] = "application/octet-stream"
            sMimeTypes["psp"] = "application/octet-stream"
            sMimeTypes["pub"] = "application/x-mspublisher"
            sMimeTypes["pwz"] = "application/vnd.ms-powerpoint"
            sMimeTypes["qht"] = "text/x-html-insertion"
            sMimeTypes["qhtm"] = "text/x-html-insertion"
            sMimeTypes["qt"] = "video/quicktime"
            sMimeTypes["qti"] = "image/x-quicktime"
            sMimeTypes["qtif"] = "image/x-quicktime"
            sMimeTypes["qtl"] = "application/x-quicktimeplayer"
            sMimeTypes["qxd"] = "application/octet-stream"
            sMimeTypes["ra"] = "audio/x-pn-realaudio"
            sMimeTypes["ram"] = "audio/x-pn-realaudio"
            sMimeTypes["rar"] = "application/x-rar-compressed"
            sMimeTypes["ras"] = "image/x-cmu-raster"
            sMimeTypes["rat"] = "application/rat-file"
            sMimeTypes["rc"] = "text/plain"
            sMimeTypes["rc2"] = "text/plain"
            sMimeTypes["rct"] = "text/plain"
            sMimeTypes["rdlc"] = "application/xml"
            sMimeTypes["reg"] = "text/plain"
            sMimeTypes["resx"] = "application/xml"
            sMimeTypes["rf"] = "image/vnd.rn-realflash"
            sMimeTypes["rgb"] = "image/x-rgb"
            sMimeTypes["rgs"] = "text/plain"
            sMimeTypes["rm"] = "application/vnd.rn-realmedia"
            sMimeTypes["rmi"] = "audio/mid"
            sMimeTypes["rmp"] = "application/vnd.rn-rn_music_package"
            sMimeTypes["roff"] = "application/x-troff"
            sMimeTypes["rpm"] = "audio/x-pn-realaudio-plugin"
            sMimeTypes["rqy"] = "text/x-ms-rqy"
            sMimeTypes["rtf"] = "application/rtf"
            sMimeTypes["rtx"] = "text/richtext"
            sMimeTypes["ruleset"] = "application/xml"
            sMimeTypes["s"] = "text/plain"
            sMimeTypes["safariextz"] = "application/x-safari-safariextz"
            sMimeTypes["scd"] = "application/x-msschedule"
            sMimeTypes["scr"] = "text/plain"
            sMimeTypes["sct"] = "text/scriptlet"
            sMimeTypes["sd2"] = "audio/x-sd2"
            sMimeTypes["sdp"] = "application/sdp"
            sMimeTypes["sea"] = "application/octet-stream"
            sMimeTypes["searchConnector-ms"] = "application/windows-search-connector+xml"
            sMimeTypes["setpay"] = "application/set-payment-initiation"
            sMimeTypes["setreg"] = "application/set-registration-initiation"
            sMimeTypes["settings"] = "application/xml"
            sMimeTypes["sgimb"] = "application/x-sgimb"
            sMimeTypes["sgml"] = "text/sgml"
            sMimeTypes["sh"] = "application/x-sh"
            sMimeTypes["shar"] = "application/x-shar"
            sMimeTypes["shtml"] = "text/html"
            sMimeTypes["sit"] = "application/x-stuffit"
            sMimeTypes["sitemap"] = "application/xml"
            sMimeTypes["skin"] = "application/xml"
            sMimeTypes["sldm"] = "application/vnd.ms-powerpoint.slide.macroEnabled.12"
            sMimeTypes["sldx"] = "application/vnd.openxmlformats-officedocument.presentationml.slide"
            sMimeTypes["slk"] = "application/vnd.ms-excel"
            sMimeTypes["sln"] = "text/plain"
            sMimeTypes["slupkg-ms"] = "application/x-ms-license"
            sMimeTypes["smd"] = "audio/x-smd"
            sMimeTypes["smi"] = "application/octet-stream"
            sMimeTypes["smx"] = "audio/x-smd"
            sMimeTypes["smz"] = "audio/x-smd"
            sMimeTypes["snd"] = "audio/basic"
            sMimeTypes["snippet"] = "application/xml"
            sMimeTypes["snp"] = "application/octet-stream"
            sMimeTypes["sol"] = "text/plain"
            sMimeTypes["sor"] = "text/plain"
            sMimeTypes["spc"] = "application/x-pkcs7-certificates"
            sMimeTypes["spl"] = "application/futuresplash"
            sMimeTypes["spx"] = "audio/ogg"
            sMimeTypes["src"] = "application/x-wais-source"
            sMimeTypes["srf"] = "text/plain"
            sMimeTypes["SSISDeploymentManifest"] = "text/xml"
            sMimeTypes["ssm"] = "application/streamingmedia"
            sMimeTypes["sst"] = "application/vnd.ms-pki.certstore"
            sMimeTypes["stl"] = "application/vnd.ms-pki.stl"
            sMimeTypes["sv4cpio"] = "application/x-sv4cpio"
            sMimeTypes["sv4crc"] = "application/x-sv4crc"
            sMimeTypes["svc"] = "application/xml"
            sMimeTypes["svg"] = "image/svg+xml"
            sMimeTypes["swf"] = "application/x-shockwave-flash"
            sMimeTypes["t"] = "application/x-troff"
            sMimeTypes["tar"] = "application/x-tar"
            sMimeTypes["tcl"] = "application/x-tcl"
            sMimeTypes["testrunconfig"] = "application/xml"
            sMimeTypes["testsettings"] = "application/xml"
            sMimeTypes["tex"] = "application/x-tex"
            sMimeTypes["texi"] = "application/x-texinfo"
            sMimeTypes["texinfo"] = "application/x-texinfo"
            sMimeTypes["tgz"] = "application/x-compressed"
            sMimeTypes["thmx"] = "application/vnd.ms-officetheme"
            sMimeTypes["thn"] = "application/octet-stream"
            sMimeTypes["tif"] = "image/tiff"
            sMimeTypes["tiff"] = "image/tiff"
            sMimeTypes["tlh"] = "text/plain"
            sMimeTypes["tli"] = "text/plain"
            sMimeTypes["toc"] = "application/octet-stream"
            sMimeTypes["tr"] = "application/x-troff"
            sMimeTypes["trm"] = "application/x-msterminal"
            sMimeTypes["trx"] = "application/xml"
            sMimeTypes["ts"] = "video/vnd.dlna.mpeg-tts"
            sMimeTypes["tsv"] = "text/tab-separated-values"
            sMimeTypes["ttf"] = "application/font-sfnt"
            sMimeTypes["tts"] = "video/vnd.dlna.mpeg-tts"
            sMimeTypes["txt"] = "text/plain"
            sMimeTypes["u32"] = "application/octet-stream"
            sMimeTypes["uls"] = "text/iuls"
            sMimeTypes["user"] = "text/plain"
            sMimeTypes["ustar"] = "application/x-ustar"
            sMimeTypes["vb"] = "text/plain"
            sMimeTypes["vbdproj"] = "text/plain"
            sMimeTypes["vbk"] = "video/mpeg"
            sMimeTypes["vbproj"] = "text/plain"
            sMimeTypes["vbs"] = "text/vbscript"
            sMimeTypes["vcf"] = "text/x-vcard"
            sMimeTypes["vcproj"] = "application/xml"
            sMimeTypes["vcs"] = "text/plain"
            sMimeTypes["vcxproj"] = "application/xml"
            sMimeTypes["vddproj"] = "text/plain"
            sMimeTypes["vdp"] = "text/plain"
            sMimeTypes["vdproj"] = "text/plain"
            sMimeTypes["vdx"] = "application/vnd.ms-visio.viewer"
            sMimeTypes["vml"] = "text/xml"
            sMimeTypes["vscontent"] = "application/xml"
            sMimeTypes["vsct"] = "text/xml"
            sMimeTypes["vsd"] = "application/vnd.visio"
            sMimeTypes["vsi"] = "application/ms-vsi"
            sMimeTypes["vsix"] = "application/vsix"
            sMimeTypes["vsixlangpack"] = "text/xml"
            sMimeTypes["vsixmanifest"] = "text/xml"
            sMimeTypes["vsmdi"] = "application/xml"
            sMimeTypes["vspscc"] = "text/plain"
            sMimeTypes["vss"] = "application/vnd.visio"
            sMimeTypes["vsscc"] = "text/plain"
            sMimeTypes["vssettings"] = "text/xml"
            sMimeTypes["vssscc"] = "text/plain"
            sMimeTypes["vst"] = "application/vnd.visio"
            sMimeTypes["vstemplate"] = "text/xml"
            sMimeTypes["vsto"] = "application/x-ms-vsto"
            sMimeTypes["vsw"] = "application/vnd.visio"
            sMimeTypes["vsx"] = "application/vnd.visio"
            sMimeTypes["vtx"] = "application/vnd.visio"
            sMimeTypes["wav"] = "audio/wav"
            sMimeTypes["wave"] = "audio/wav"
            sMimeTypes["wax"] = "audio/x-ms-wax"
            sMimeTypes["wbk"] = "application/msword"
            sMimeTypes["wbmp"] = "image/vnd.wap.wbmp"
            sMimeTypes["wcm"] = "application/vnd.ms-works"
            sMimeTypes["wdb"] = "application/vnd.ms-works"
            sMimeTypes["wdp"] = "image/vnd.ms-photo"
            sMimeTypes["webarchive"] = "application/x-safari-webarchive"
            sMimeTypes["webm"] = "video/webm"
            sMimeTypes["webp"] = "image/webp"
            sMimeTypes["webtest"] = "application/xml"
            sMimeTypes["wiq"] = "application/xml"
            sMimeTypes["wiz"] = "application/msword"
            sMimeTypes["wks"] = "application/vnd.ms-works"
            sMimeTypes["WLMP"] = "application/wlmoviemaker"
            sMimeTypes["wlpginstall"] = "application/x-wlpg-detect"
            sMimeTypes["wlpginstall3"] = "application/x-wlpg3-detect"
            sMimeTypes["wm"] = "video/x-ms-wm"
            sMimeTypes["wma"] = "audio/x-ms-wma"
            sMimeTypes["wmd"] = "application/x-ms-wmd"
            sMimeTypes["wmf"] = "application/x-msmetafile"
            sMimeTypes["wml"] = "text/vnd.wap.wml"
            sMimeTypes["wmlc"] = "application/vnd.wap.wmlc"
            sMimeTypes["wmls"] = "text/vnd.wap.wmlscript"
            sMimeTypes["wmlsc"] = "application/vnd.wap.wmlscriptc"
            sMimeTypes["wmp"] = "video/x-ms-wmp"
            sMimeTypes["wmv"] = "video/x-ms-wmv"
            sMimeTypes["wmx"] = "video/x-ms-wmx"
            sMimeTypes["wmz"] = "application/x-ms-wmz"
            sMimeTypes["woff"] = "application/font-woff"
            sMimeTypes["wpl"] = "application/vnd.ms-wpl"
            sMimeTypes["wps"] = "application/vnd.ms-works"
            sMimeTypes["wri"] = "application/x-mswrite"
            sMimeTypes["wrl"] = "x-world/x-vrml"
            sMimeTypes["wrz"] = "x-world/x-vrml"
            sMimeTypes["wsc"] = "text/scriptlet"
            sMimeTypes["wsdl"] = "text/xml"
            sMimeTypes["wvx"] = "video/x-ms-wvx"
            sMimeTypes["x"] = "application/directx"
            sMimeTypes["xaf"] = "x-world/x-vrml"
            sMimeTypes["xaml"] = "application/xaml+xml"
            sMimeTypes["xap"] = "application/x-silverlight-app"
            sMimeTypes["xbap"] = "application/x-ms-xbap"
            sMimeTypes["xbm"] = "image/x-xbitmap"
            sMimeTypes["xdr"] = "text/plain"
            sMimeTypes["xht"] = "application/xhtml+xml"
            sMimeTypes["xhtml"] = "application/xhtml+xml"
            sMimeTypes["xla"] = "application/vnd.ms-excel"
            sMimeTypes["xlam"] = "application/vnd.ms-excel.addin.macroEnabled.12"
            sMimeTypes["xlc"] = "application/vnd.ms-excel"
            sMimeTypes["xld"] = "application/vnd.ms-excel"
            sMimeTypes["xlk"] = "application/vnd.ms-excel"
            sMimeTypes["xll"] = "application/vnd.ms-excel"
            sMimeTypes["xlm"] = "application/vnd.ms-excel"
            sMimeTypes["xls"] = "application/vnd.ms-excel"
            sMimeTypes["xlsb"] = "application/vnd.ms-excel.sheet.binary.macroEnabled.12"
            sMimeTypes["xlsm"] = "application/vnd.ms-excel.sheet.macroEnabled.12"
            sMimeTypes["xlsx"] = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            sMimeTypes["xlt"] = "application/vnd.ms-excel"
            sMimeTypes["xltm"] = "application/vnd.ms-excel.template.macroEnabled.12"
            sMimeTypes["xltx"] = "application/vnd.openxmlformats-officedocument.spreadsheetml.template"
            sMimeTypes["xlw"] = "application/vnd.ms-excel"
            sMimeTypes["xml"] = "text/xml"
            sMimeTypes["xmta"] = "application/xml"
            sMimeTypes["xof"] = "x-world/x-vrml"
            sMimeTypes["XOML"] = "text/plain"
            sMimeTypes["xpm"] = "image/x-xpixmap"
            sMimeTypes["xps"] = "application/vnd.ms-xpsdocument"
            sMimeTypes["xrm-ms"] = "text/xml"
            sMimeTypes["xsc"] = "application/xml"
            sMimeTypes["xsd"] = "text/xml"
            sMimeTypes["xsf"] = "text/xml"
            sMimeTypes["xsl"] = "text/xml"
            sMimeTypes["xslt"] = "text/xml"
            sMimeTypes["xsn"] = "application/octet-stream"
            sMimeTypes["xss"] = "application/xml"
            sMimeTypes["xspf"] = "application/xspf+xml"
            sMimeTypes["xtp"] = "application/octet-stream"
            sMimeTypes["xwd"] = "image/x-xwindowdump"
            sMimeTypes["z"] = "application/x-compress"
            sMimeTypes["zip"] = "application/zip"
        }

        /**
         * This method fetches the type of a file
         *
         * @param fileName name of the file
         * @return the file type
         * */
        fun getFileType(fileName: String): String {
            return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
        }

        /**
         * This method returns the mime type of a file
         *
         * @param fileType type of the file
         * @return file mime type
         * */
        fun getFileMimeType(fileType: String): String? {
            if (sMimeTypes.size == 0) init()
            return sMimeTypes[fileType]
        }

        /**
         * This method provides a file to save captured image using camera of the device
         *
         * @param context context
         * @return [File] an empty file
         * */
        fun getEmptyFileForSavingCapturedImage(context: Context): File? {
            return getEmptyFile(context, Constants.File.PREFIX_IMAGE,
                    TimeUtils.currentTime().toString(), Constants.File.SUFFIX_IMAGE,
                    Environment.DIRECTORY_PICTURES)
        }

        /**
         * This method provides a file to save cropped image
         *
         * @param context context
         * @return [File] an empty file
         * */
        fun getEmptyFileForSavingCroppedImage(context: Context): File? {
            return getEmptyFile(context, Constants.File.PREFIX_CROPPED_IMAGE,
                    TimeUtils.currentTime().toString(), Constants.File.SUFFIX_IMAGE,
                    Environment.DIRECTORY_PICTURES)
        }

        private fun getEmptyFile(context: Context, fileNamePrefix: String,
                                 fileNameBody: String, fileNameSuffix: String,
                                 directoryType: String): File? {

            val fileName = fileNamePrefix + fileNameBody + fileNameSuffix
            val storageDirectory = context.getExternalFilesDir(directoryType)
            val file = File(storageDirectory, fileName)

            try {
                if (!file.exists()) {
                    file.createNewFile()
                }
            } catch (e: IOException) {
                Timber.e(e)
                return null
            }

            if (file.parentFile == null) {
                file.mkdirs()
            } else {
                file.parentFile.mkdirs()
            }

            return file
        }

        /**
         * This method gets path from image uri
         *
         * @param context UI context
         * @param uri current image uri
         * @return [String] image path
         * */
        fun getImagePathFromUri(context: Context, uri: Uri): String? {
            val data = arrayOf(MediaStore.Images.Media.DATA)
            val loader = CursorLoader(context, uri, data, null, null, null)
            val cursor = loader.loadInBackground() ?: return null

            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
    }

}
