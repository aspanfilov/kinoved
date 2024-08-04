package com.kinoved.filemanager.utils;

import java.io.File;
import java.io.IOException;

public interface FileSystemUtility {
    boolean isFileLocked(File file);

    int getFileSizeInMb(File file);

    File getTargetFolder(String targetDirectory, String folderName) throws IOException;
}
