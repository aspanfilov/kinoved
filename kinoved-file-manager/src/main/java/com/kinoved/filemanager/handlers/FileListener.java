package com.kinoved.filemanager.handlers;

import com.kinoved.filemanager.utils.impl.FileSystemUtilityImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class FileListener extends FileAlterationListenerAdaptor {

    private final FileSystemUtilityImpl fileSystemUtility;

    private final FileTaskHandler fileTaskHandler;

    @Override
    public void onFileCreate(File file) {
        System.out.println("File created: " + file.getAbsolutePath());

        if (fileSystemUtility.isFileLocked(file)) {
            System.out.println("File is locked: " + file.getAbsolutePath());
            return;
        }
        fileTaskHandler.readFileInfoAndSendToKafka(file);
    }

    @Override
    public void onFileChange(File file) {
        System.out.println("File changed: " + file.getAbsolutePath());
        fileTaskHandler.readFileInfoAndSendToKafka(file);
    }
}
