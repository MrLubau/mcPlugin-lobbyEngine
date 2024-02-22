package eu.mrlubau.lobbyengine.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class emojis : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        var message = event.message

        message = replaceEmoji(message, ":xd:", "ヱ")
        message = replaceEmoji(message, ":ragey:", "ヲ")
        message = replaceEmoji(message, ":pog:", "ン")
        message = replaceEmoji(message, ":pepehands:", "ヴ")
        message = replaceEmoji(message, ":monkaw:", "ヵ")
        message = replaceEmoji(message, ":sadge:", "ヶ")
        message = replaceEmoji(message, ":pepepausechamp:", "ヸ")
        message = replaceEmoji(message, ":peepolove:", "ヹ")
        message = replaceEmoji(message, ":peeposad:", "ヺ")
        message = replaceEmoji(message, ":pausechamp:", "ヾ")
        message = replaceEmoji(message, ":pagman:", "く")
        message = replaceEmoji(message, ":omegasp:", "ぐ")
        message = replaceEmoji(message, ":omegalul:", "ゥ")
        message = replaceEmoji(message, ":notlikethis:", "ウ")
        message = replaceEmoji(message, ":monkahmm:", "ェ")
        message = replaceEmoji(message, ":kek:", "エ")
        message = replaceEmoji(message, ":huh:", "ォ")
        message = replaceEmoji(message, ":gasp:", "オ")
        message = replaceEmoji(message, ":kappa:", "ゎ")
        message = replaceEmoji(message, ":lul:", "ゲ")
        message = replaceEmoji(message, ":pogchamp:", "を")
        message = replaceEmoji(message, ":kekw:", "っ")
        message = replaceEmoji(message, ":pepelaugh:", "そ")
        message = replaceEmoji(message, ":jebaited:", "に")
        message = replaceEmoji(message, ":weirdchamp:", "め")
        message = replaceEmoji(message, ":monkah:", "タ")
        message = replaceEmoji(message, ":forsencd:", "こ")
        message = replaceEmoji(message, ":residentsleeper:", "ご")
        message = replaceEmoji(message, ":3head:", "か")

        event.message = message
    }

    private fun replaceEmoji(input: String, emojiPattern: String, replacement: String): String {
        return input.replace(emojiPattern, replacement)
    }
}