package be.dieterblancke.proxy.streamerannouncements;

import be.dieterblancke.bungeeutilisalsx.common.BuX;
import be.dieterblancke.bungeeutilisalsx.common.api.command.Command;
import be.dieterblancke.bungeeutilisalsx.common.api.command.CommandBuilder;
import be.dieterblancke.bungeeutilisalsx.common.api.language.Language;
import be.dieterblancke.configuration.api.FileStorageType;
import be.dieterblancke.configuration.api.IConfiguration;
import be.dieterblancke.proxy.streamerannouncements.commands.StreamerAnnouncementsCommandCall;
import be.dieterblancke.proxy.streamerannouncements.commands.StreamCommandCall;
import be.dieterblancke.proxy.streamerannouncements.commands.VideoCommandCall;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bootstrap extends Plugin
{

    private final List<Command> commands = new ArrayList<>();
    private IConfiguration commandsConfiguration;

    @Override
    public void onEnable()
    {
        File commandsFile = new File( this.getDataFolder(), "commands.yml" );

        if ( !commandsFile.exists() )
        {
            IConfiguration.createDefaultFile( getResourceAsStream( "configurations/commands.yml" ), commandsFile );
        }

        this.commandsConfiguration = IConfiguration.loadYamlConfiguration( commandsFile );
        this.loadCommands();

        BuX.getApi().getLanguageManager().addPlugin(
                this.getDescription().getName(),
                new File( getDataFolder(), "languages" ),
                FileStorageType.YAML
        );
        BuX.getApi().getLanguageManager().loadLanguages( this.getClass(), this.getDescription().getName() );
    }

    @Override
    public void onDisable()
    {

    }

    public void reload()
    {
        for ( Command command : this.commands )
        {
            command.unload();
        }
        this.commands.clear();
        this.loadCommands();

        for ( Language language : BuX.getApi().getLanguageManager().getLanguages() )
        {
            BuX.getApi().getLanguageManager().reloadConfig( this.getDescription().getName(), language );
        }
    }

    private void loadCommands()
    {
        CommandBuilder.builder()
                .name( "streamerannouncements" )
                .fromSection( commandsConfiguration.getSection( "commands.streamerannouncements" ) )
                .executable( new StreamerAnnouncementsCommandCall( this ) )
                .build( this::registerCommand );

        CommandBuilder.builder()
                .name( "stream" )
                .fromSection( commandsConfiguration.getSection( "commands.stream" ) )
                .executable( new StreamCommandCall( this ) )
                .build( this::registerCommand );

        CommandBuilder.builder()
                .name( "video" )
                .fromSection( commandsConfiguration.getSection( "commands.video" ) )
                .executable( new VideoCommandCall( this ) )
                .build( this::registerCommand );
    }

    private void registerCommand(Command command) {
        this.commands.add( command );
        command.register();
    }
}
