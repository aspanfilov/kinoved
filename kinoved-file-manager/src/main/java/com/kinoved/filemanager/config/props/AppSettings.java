package com.kinoved.filemanager.config.props;

public interface AppSettings {
    String getSourceDirectory();

    String getTargetDirectory();

    int getSourceDirectoryScanInterval();

    String getUndefinedGenreFolder();
}
