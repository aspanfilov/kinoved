package com.kinoved.telegrambot.senders;

public interface ErrorMessageSender {
    void send(Exception ex);
}
