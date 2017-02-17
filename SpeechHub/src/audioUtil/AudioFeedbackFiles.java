package audioUtil;


public class AudioFeedbackFiles {
    public final String brightness_bright = "audioFile/brightness_bright.wav";
    public final String brightness_brighter = "audioFile/brightness_brighter.wav";
    public final String brightness_brightest = "audioFile/brightness_brightest.wav";
    public final String brightness_dimmer = "audioFile/brightness_dimmer.wav";
    public final String color_blue = "audioFile/color_blue.wav";
    public final String color_green = "audioFile/color_green.wav";
    public final String color_red = "audioFile/color_red.wav";
    public final String color_purple = "audioFile/color_purple.wav";
    public final String color_yellow = "audioFile/color_yellow.wav";
    public final String color_change_success = "audioFile/color_change_success.wav";
    public final String help_main = "audioFile/help_main.wav";
    public final String help_brightness = "audioFile/help_brightness.wav";
    public final String invalid_input = "audioFile/invalid_input.wav";
    private final String ok = "audioFile/ok.wav";
    private final String sure = "audioFile/sure.wav";

    public String response() {
        int temp =(int)(Math.random()*100);
        if (temp<50) {
            return ok;
        } else {
            return sure;
        }
    }
}
