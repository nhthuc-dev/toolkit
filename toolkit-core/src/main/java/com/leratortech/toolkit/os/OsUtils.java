package com.leratortech.toolkit.os;

public final class OsUtils {


    private static final OperatingSystem OS = detectOperatingSystem();

    public static boolean isWindows() {
        return OS == OperatingSystem.WINDOWS;
    }

    public static boolean isMac() {
        return OS == OperatingSystem.MAC;
    }

    public static boolean isLinux() {
        return OS == OperatingSystem.LINUX;
    }

    public static boolean isFreeBsd() {
        return OS == OperatingSystem.FREEBSD;
    }

    public static boolean isSolaris() {
        return OS == OperatingSystem.SOLARIS;
    }

    public static boolean isUnix() {
        return OS == OperatingSystem.LINUX || OS == OperatingSystem.FREEBSD
                || OS == OperatingSystem.MAC || OS == OperatingSystem.SOLARIS;
    }

    private OsUtils() {
        //empty
    }

    public static OperatingSystem getOs() {
        return OS;
    }

    private static OperatingSystem detectOperatingSystem() {
        var name = System.getProperty("os.name").toLowerCase();
        if (name.contains("win")) {
            return OperatingSystem.WINDOWS;
        } else if (name.contains("mac")) {
            return OperatingSystem.MAC;
        } else if (name.contains("linux")) {
            return OperatingSystem.LINUX;
        } else if (name.contains("free")) {
            return OperatingSystem.FREEBSD;
        } else if (name.contains("sunos")) {
            return OperatingSystem.SOLARIS;
        } else {
            return null;
        }
    }


}
