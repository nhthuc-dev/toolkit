package com.leratortech.toolkit.os;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OsUtilsTest {

    @Test
    void testGetOs() {
        OperatingSystem os = OsUtils.getOs();
        assertNotNull(os, "Detected OS should not be null");
        System.out.println("OS: " + os);
    }

    @Test
    void testIsWindows() {
        if (OsUtils.getOs() == OperatingSystem.WINDOWS) {
            assertTrue(OsUtils.isWindows());
        } else {
            assertFalse(OsUtils.isWindows());
        }
    }

    @Test
    void testIsMac() {
        if (OsUtils.getOs() == OperatingSystem.MAC) {
            assertTrue(OsUtils.isMac());
        } else {
            assertFalse(OsUtils.isMac());
        }
    }

    @Test
    void testIsLinux() {
        if (OsUtils.getOs() == OperatingSystem.LINUX) {
            assertTrue(OsUtils.isLinux());
        } else {
            assertFalse(OsUtils.isLinux());
        }
    }

    @Test
    void testIsFreeBsd() {
        if (OsUtils.getOs() == OperatingSystem.FREEBSD) {
            assertTrue(OsUtils.isFreeBsd());
        } else {
            assertFalse(OsUtils.isFreeBsd());
        }
    }

    @Test
    void testIsSolaris() {
        if (OsUtils.getOs() == OperatingSystem.SOLARIS) {
            assertTrue(OsUtils.isSolaris());
        } else {
            assertFalse(OsUtils.isSolaris());
        }
    }

    @Test
    void testIsUnix() {
        OperatingSystem os = OsUtils.getOs();
        if (os == OperatingSystem.LINUX || os == OperatingSystem.MAC ||
                os == OperatingSystem.FREEBSD || os == OperatingSystem.SOLARIS) {
            assertTrue(OsUtils.isUnix());
        } else {
            assertFalse(OsUtils.isUnix());
        }
    }
}
