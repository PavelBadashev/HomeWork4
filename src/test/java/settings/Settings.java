package settings;

import java.util.Map;

public class Settings implements iSettings{
    @Override
    public Map<String, String> getSettings() {
        Settings settings = new Settings();
        Map<String, String> config = settings.getSettings();

        String url = config.get("URL");
        return Map.of();
    }
}
