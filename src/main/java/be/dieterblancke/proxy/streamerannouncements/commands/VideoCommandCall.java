package be.dieterblancke.proxy.streamerannouncements.commands;

import be.dieterblancke.bungeeutilisalsx.common.BuX;
import be.dieterblancke.bungeeutilisalsx.common.api.command.CommandCall;
import be.dieterblancke.bungeeutilisalsx.common.api.job.jobs.ExternalPluginBroadcastLanguageMessageJob;
import be.dieterblancke.bungeeutilisalsx.common.api.language.LanguageConfig;
import be.dieterblancke.bungeeutilisalsx.common.api.user.interfaces.User;
import be.dieterblancke.bungeeutilisalsx.common.api.utils.placeholders.MessagePlaceholders;
import be.dieterblancke.proxy.streamerannouncements.Bootstrap;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class VideoCommandCall implements CommandCall
{

    private final Bootstrap bootstrap;

    @Override
    public void onExecute( User user, List<String> args, List<String> params )
    {
        if ( args.size() != 1 )
        {
            LanguageConfig languageConfig = BuX.getApi().getLanguageManager().getLanguageConfiguration( bootstrap.getDescription().getName(), user );
            languageConfig.sendLangMessage( user, "commands.video.usage" );
            return;
        }

        BuX.getInstance().getJobManager().executeJob( new ExternalPluginBroadcastLanguageMessageJob(
                bootstrap.getDescription().getName(),
                "commands.video.broadcast",
                "",
                MessagePlaceholders.create()
                        .append( "user", user.getName() )
                        .append( "video-url", args.get( 0 ) )
        ) );
    }

    @Override
    public String getDescription()
    {
        return "Announces if someone's video went live.";
    }

    @Override
    public String getUsage()
    {
        return "/video (video url)";
    }
}
