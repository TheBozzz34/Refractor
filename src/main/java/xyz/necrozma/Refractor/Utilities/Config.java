package xyz.necrozma.Refractor.Utilities;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.route.Route;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import xyz.necrozma.Refractor.Refractor;

import java.io.File;
import java.io.IOException;

public class Config {
    private static Config instance;
    private YamlDocument config;

    private Config() throws IOException {
        config = YamlDocument.create(
                new File(Refractor.getPlugin(Refractor.class).getDataFolder(), "config.yml"),
                Refractor.getPlugin(Refractor.class).getResource("config.yml"),
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build()
        );
    }

    public static Config getInstance() {
        if (instance == null) {
            try {
                instance = new Config();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public String getString(Route route) {
        return config.getString(route);
    }

    public Boolean getBoolean(Route route) {
        return config.getBoolean(route);
    }
}

