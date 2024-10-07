package com.kinoved.filemanager.utils.impl;

import com.kinoved.filemanager.utils.FileSystemUtility;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class FileSystemUtilityImpl implements FileSystemUtility {

    public boolean isFileLocked(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public int getFileSizeInMb(File file) {
        return (int) (file.length() / 1024 / 1024);
    }

    public File getTargetFolder(String targetDirectory, String folderName) throws IOException {
        File[] folders = new File(targetDirectory).listFiles(File::isDirectory);
        if (folders != null) {
            for (File folder : folders) {
                if (folder.getName().equalsIgnoreCase(folderName)) {
                    return folder;
                }
            }
        }
        File newFolder = new File(targetDirectory, folderName);
        FileUtils.forceMkdir(newFolder);
        return newFolder;
    }

}
