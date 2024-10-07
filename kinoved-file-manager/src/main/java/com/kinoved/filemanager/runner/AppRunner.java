package com.kinoved.filemanager.runner;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final FileAlterationMonitor monitor;

    @Override
    public void run(String... args) throws Exception {
        monitor.start();

    }
}
