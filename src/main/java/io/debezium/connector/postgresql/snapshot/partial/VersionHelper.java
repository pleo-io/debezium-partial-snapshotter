package io.debezium.connector.postgresql.snapshot.partial;

import io.debezium.connector.postgresql.Module;

public class VersionHelper {

    public static String MIN_VERSION = "1.3.0.Final";

    public static boolean isCurrentVersionCompatibleWithPlugin() {
        String version = Module.version();
        // Always include SNAPSHOT versions
        return version.endsWith("SNAPSHOT") || isSupportedVersion(version);
    }

    private static boolean isSupportedVersion(String version) {
        String[] explodedVersion = version.split("\\.");
        int major = Integer.parseInt(explodedVersion[0]);
        int minor = Integer.parseInt(explodedVersion[1]);

        if (major >= 1 && minor >= 3) {
            // 1.3.0.CR1 was the first release that supported Snapshotter#shouldStreamEventsStartingFromSnapshot
            // Note: We should phase out CR1 references in favor of only supporting Final
            // 1.4.0.Final will support the new close handler.
            if (major == 1 && minor == 3 && explodedVersion.length >= 4 &&
                ! explodedVersion[3].equals("Final") && ! explodedVersion[3].equals("CR1")) {
                return false;
            }
            return true;
        }
        return false;
    }
}
