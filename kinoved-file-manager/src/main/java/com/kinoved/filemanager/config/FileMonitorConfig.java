package com.kinoved.filemanager.config;

import com.kinoved.filemanager.config.props.AppProps;
import com.kinoved.filemanager.config.props.AppSettings;
import com.kinoved.filemanager.handlers.FileListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class FileMonitorConfig {

    @Autowired
    private AppSettings appSettings;

    @Bean
    public FileAlterationObserver fileAlterationObserver(FileListener fileListener) {
        var observer = new FileAlterationObserver(appSettings.getSourceDirectory());
        observer.addListener(fileListener);
        return observer;
    }

    @Bean
    public FileAlterationMonitor fileAlterationMonitor(FileAlterationObserver observer) {
        var monitor = new FileAlterationMonitor(appSettings.getSourceDirectoryScanInterval());
        monitor.addObserver(observer);
        return monitor;
    }
}
