package com.kinoved.filemanager.handlers.impl;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;
import com.kinoved.filemanager.config.props.AppSettings;
import com.kinoved.filemanager.fabrics.MovieFileInfoFabric;
import com.kinoved.filemanager.handlers.FileTaskHandler;
import com.kinoved.filemanager.handlers.NotificationSender;
import com.kinoved.filemanager.utils.FileSystemUtility;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FileTaskHandlerImpl implements FileTaskHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FileTaskHandlerImpl.class);

    private final AppSettings appSettings;

    private final MovieFileInfoFabric movieFileInfoFabric;

    private final FileSystemUtility fileSystemUtility;

    private final NotificationSender notificationSender;

    @Override
    public void readFileInfoAndSendToKafka(File file) {
        MovieFileInfoDto movieFileInfoDto = movieFileInfoFabric.create(file);
        notificationSender.sendNotification(movieFileInfoDto);
    }

    @Override
    public void moveFileToGenreDirectory(MovieFileMoveTask movieFileMoveTask) {
        String fileName = movieFileMoveTask.getFileName();
        String targetFolderName = determineTargetFolderName(movieFileMoveTask);

        File targetFolder;
        try {
            targetFolder = createTargetFolder(targetFolderName);
        } catch (IOException e) {
            notifyFolderCreationFailure(movieFileMoveTask, targetFolderName, e);
            return;
        }

        File sourceFile = new File(appSettings.getSourceDirectory(), fileName);
        File targetFile = new File(targetFolder, fileName);

        try {
            moveFileAndNotifySuccess(sourceFile, targetFile, movieFileMoveTask);
        } catch (IOException e) {
            notifyFileMoveFailure(movieFileMoveTask, targetFile, e);
        }
    }

    private String determineTargetFolderName(MovieFileMoveTask movieFileMoveTask) {
        return movieFileMoveTask.getGenre().isBlank()
                ? appSettings.getUndefinedGenreFolder()
                : movieFileMoveTask.getGenre();
    }

    private File createTargetFolder(String targetFolderName) throws IOException {
        return fileSystemUtility.getTargetFolder(appSettings.getTargetDirectory(), targetFolderName);
    }

    private void notifyFolderCreationFailure(MovieFileMoveTask movieFileMoveTask,
                                             String targetFolderName, IOException ex) {
        LOG.error("Не удалось создать папку для жанра {}", targetFolderName, ex);

        notificationSender.sendTaskResultNotification(movieFileMoveTask, false,
                String.format("Не удалось создать папку для жанра \"{}\"\n", targetFolderName)
                        + ex.getMessage());
    }

    private void moveFileAndNotifySuccess(File sourceFile, File targetFile,
                                          MovieFileMoveTask movieFileMoveTask) throws IOException {
        FileUtils.moveFile(sourceFile, targetFile);

        LOG.info("Файл \"{}\" перемещен в папку \"{}\"",
                sourceFile.getName(),
                targetFile.getAbsolutePath());

        notificationSender.sendTaskResultNotification(movieFileMoveTask, true,
                String.format("Файл \"%s\" перемещен в папку \"%s\"",
                        sourceFile.getName(),
                        targetFile.getParentFile().getName()));
    }

    private void notifyFileMoveFailure(MovieFileMoveTask movieFileMoveTask, File targetFile, IOException ex) {
        LOG.error("Ошибка при перемещении файла \"{}\" в папку \"{}\"",
                targetFile.getName(),
                targetFile.getAbsolutePath(),
                ex);

        notificationSender.sendTaskResultNotification(movieFileMoveTask, false,
                String.format("Ошибка при перемещении файла \"%s\" в папку \"%s\"\n",
                        targetFile.getName(), targetFile.getParent())
                        + ex.getMessage());
    }


//    @Override
//    public void moveFileToGenreDirectory(MovieFileMoveTask movieFileMoveTask) {
//        String fileName = movieFileMoveTask.getFileName();
//        String targetFolderName = movieFileMoveTask.getGenre().isBlank()
//                ? appSettings.getUndefinedGenreFolder()
//                : movieFileMoveTask.getGenre();
//
//        File targetFolder;
//        try {
//            targetFolder = fileSystemUtility.getTargetFolder(appSettings.getTargetDirectory(), targetFolderName);
//        } catch (IOException e) {
//            LOG.error("Не удалось создать папку для жанра {}", targetFolderName, e);
//            MovieFileMoveResult movieFileMoveResult = taskResultFabric.create(movieFileMoveTask, false,
//                    "Не удалось создать папку для жанра\n" + e.getMessage());
//            NotifyKafkaMessage notifyMessage = notifyKafkaMessageFabric.createMessage(movieFileMoveResult);
//            messageSender.send(notifyMessage);
//            return;
//        }
//
//        File sourceFile = new File(appSettings.getSourceDirectory(), fileName);
//        File targetFile = new File(targetFolder, fileName);
//
//        try {
//            FileUtils.moveFile(sourceFile, targetFile);
//            LOG.info("Файл \"{}\" перемещен в папку \"{}\"", fileName, targetFile.getAbsolutePath());
//            MovieFileMoveResult movieFileMoveResult = taskResultFabric.create(movieFileMoveTask, true,
//                    String.format("Файл \"%s\" перемещен в папку \"%s\"", fileName, targetFolderName));
//            NotifyKafkaMessage notifyMessage = notifyKafkaMessageFabric.createMessage(movieFileMoveResult);
//            messageSender.send(notifyMessage);
//        } catch (IOException e) {
//            LOG.error("Ошибка при перемещении файла \"{}\" в папку \"{}\"", fileName, targetFile.getAbsolutePath(), e);
//            MovieFileMoveResult movieFileMoveResult = taskResultFabric.create(movieFileMoveTask, false,
//                    String.format("Ошибка при перемещении файла \"%s\" в папку \"%s\"\n",
//                            fileName, targetFolderName)
//                            + e.getMessage());
//            NotifyKafkaMessage notifyMessage = notifyKafkaMessageFabric.createMessage(movieFileMoveResult);
//            messageSender.send(notifyMessage);
//        }

}
