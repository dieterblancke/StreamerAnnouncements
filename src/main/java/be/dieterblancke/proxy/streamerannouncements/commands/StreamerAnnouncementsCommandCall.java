package be.dieterblancke.proxy.streamerannouncements.commands;

import be.dieterblancke.bungeeutilisalsx.common.BuX;
import be.dieterblancke.bungeeutilisalsx.common.api.command.CommandCall;
import be.dieterblancke.bungeeutilisalsx.common.api.language.LanguageConfig;
import be.dieterblancke.bungeeutilisalsx.common.api.user.interfaces.User;
import be.dieterblancke.proxy.streamerannouncements.Bootstrap;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StreamerAnnouncementsCommandCall implements CommandCall
{

    private final Bootstrap bootstrap;

    @Override
    public void onExecute( User user, List<String> args, List<String> params )
    {
        LanguageConfig languageConfig = BuX.getApi().getLanguageManager().getLanguageConfiguration( bootstrap.getDescription().getName(), user );

        if ( args.size() == 1 )
        {
            if ( args.get( 0 ).equalsIgnoreCase( "reload" ) || args.get( 0 ).equalsIgnoreCase( "rl" ) )
            {
                bootstrap.reload();
                languageConfig.sendLangMessage( user, "commands.streamerannouncements.reloaded" );
                return;
            }
        }

        languageConfig.sendLangMessage( user, "commands.streamerannouncements.help" );
    }

    @Override
    public String getDescription()
    {
        return "Admin command for stream announcements.";
    }

    @Override
    public String getUsage()
    {
        return "/streamerannouncements reload";
    }
}
