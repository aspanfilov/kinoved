package com.kinoved.telegrambot.bot;

import com.kinoved.telegrambot.config.TelegramBotProps;
import com.kinoved.telegrambot.handlers.CallbackHandler;
import com.kinoved.telegrambot.handlers.CommandHandler;
import com.kinoved.telegrambot.handlers.GenreHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.function.Predicate;

import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class KinovedTelegramBot extends AbilityBot {

    private final TelegramBotProps botProps;

    private final CommandHandler commandHandler;

    private final CallbackHandler callbackHandler;

    private final GenreHandler genreHandler;

    public KinovedTelegramBot(TelegramClient telegramClient,
                              TelegramBotProps botProps,
                              CommandHandler commandHandler,
                              CallbackHandler callbackHandler,
                              GenreHandler genreHandler) {
        super(telegramClient, botProps.getUsername());
        this.botProps = botProps;
        this.commandHandler = commandHandler;
        this.callbackHandler = callbackHandler;
        this.genreHandler = genreHandler;
    }

    @Override
    public long creatorId() {
        return botProps.getCreatorId();
    }

    public Reply replyToCallback() {
        return Reply.of(
                (bot, upd) -> callbackHandler.replyToCallback(
                        getChatId(upd),
                        upd.getCallbackQuery()),
                isBotCreator(),
                Flag.CALLBACK_QUERY);
    }

    public Reply replyToCommand() {
        return Reply.of(
                (bot, upd) -> commandHandler.replyToCommand(
                        getChatId(upd),
                        upd.getMessage()),
                isBotCreator(),
                Flag.TEXT,
                upd -> upd.getMessage().isCommand());
    }

    public Reply replyToGenre() {
        return Reply.of(
                (bot, upd) -> genreHandler.replyToCommand(
                        getChatId(upd),
                        upd.getMessage().getFrom().getId(),
                        upd.getMessage().getText()),
                isBotCreator(),
                Flag.TEXT,
                upd -> !upd.getMessage().isCommand());
    }

    private Predicate<Update> isBotCreator() {
        return upd -> getChatId(upd).equals(creatorId());
    }

}
