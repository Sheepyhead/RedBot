/**
 * Created by kairy on 4/28/2017.
 */
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;

public class MessageHandler {


    @EventSubscriber
    public void OnMesageEvent(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException{
        IMessage message = event.getMessage();
        if(message.getContent().startsWith("!modulemessage")){
            sendMessage("Message send! Module is working.", event);
        }
    }

    public void sendMessage(String message, MessageReceivedEvent event) throws DiscordException, MissingPermissionsException{
        new MessageBuilder(Bot.client).appendContent(message).withChannel(event.getMessage().getChannel()).build();
    }

}