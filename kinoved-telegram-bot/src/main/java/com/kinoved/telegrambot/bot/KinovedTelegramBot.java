package com.kinoved.telegrambot.bot;

import com.kinoved.telegrambot.config.TelegramBotProps;
import com.kinoved.telegrambot.handlers.CallbackHandler;
import com.kinoved.telegrambot.handlers.CommandHandler;
import com.kinoved.telegrambot.handlers.impl.CallbackHandlerImpl;
import com.kinoved.telegrambot.handlers.impl.CommandHandlerImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.function.Predicate;

import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

//todo вывод фильмов по жанрам, отсортированным по рейтингу или по дате загрузки
// сделать возможность выбора

//todo сделать кнопку скриншотов из фильма.
// При нажатии делается запрос к KinovedCoreClient на загрузку скриншотов.
// Они должны где-то сохраниться,
// после чего они отправляются в виде SendGroupMedia в качестве ответа на текущее сообщение.
// Таким образом после просмотра изображений можно будет обратно вернуться к текущему сообщению

@Component
public class KinovedTelegramBot extends AbilityBot {

    private final TelegramBotProps botProps;

    private final CommandHandler commandHandler;

    private final CallbackHandler callbackHandler;

    public KinovedTelegramBot(TelegramClient telegramClient,
                              TelegramBotProps botProps,
                              CommandHandlerImpl commandHandler,
                              CallbackHandlerImpl callbackHandler) {
        super(telegramClient, botProps.getUsername());
        this.botProps = botProps;
        this.commandHandler = commandHandler;
        this.callbackHandler = callbackHandler;
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
                        upd.getMessage().getText()),
                isBotCreator(),
                Flag.TEXT,
                upd -> upd.getMessage().isCommand());
    }

    private Predicate<Update> isBotCreator() {
        return upd -> getChatId(upd).equals(creatorId());
    }

}
