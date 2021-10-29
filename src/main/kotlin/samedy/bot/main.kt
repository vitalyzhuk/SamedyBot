package samedy.bot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN_V2
import com.github.kotlintelegrambot.entities.ReplyKeyboardRemove
import com.github.kotlintelegrambot.entities.TelegramFile.ByUrl
import com.github.kotlintelegrambot.entities.dice.DiceEmoji
import com.github.kotlintelegrambot.entities.inlinequeryresults.InlineQueryResult
import com.github.kotlintelegrambot.entities.inlinequeryresults.InputMessageContent
import com.github.kotlintelegrambot.entities.inputmedia.InputMediaPhoto
import com.github.kotlintelegrambot.entities.inputmedia.MediaGroup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import com.github.kotlintelegrambot.network.fold

fun main() {
    val supportedCommands = listOf("/ping", "/marko")

    val bot = bot {
        token = System.getenv("SAMEDY_TOKEN") ?: ""
        timeout = 30

        dispatch {

            command("ping") {
                bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "pong!")
            }

            command("marko") {
                bot.sendMessage(chatId = ChatId.fromId(message.chat.id), text = "polo!")
            }

            message(!Filter.Command) {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = """
                        Sorry, but now I dont know what is "${message.text}"
                         I know only these commands: $supportedCommands
                    """
                )
            }

            message(Filter.Command) {
                if (message.text !in supportedCommands) {
                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = """
                            Sorry, but now I dont know command "${message.text}"
                             I know only these commands: $supportedCommands
                        """
                    )
                }
            }

            telegramError {
                println(error.getErrorMessage())
            }
        }
    }

    bot.startPolling()
}
