package me.stevenlol.simplesmp.utilities;

public class TimeFormatFromSeconds {

    public static String time(int seconds) {

        String day = "";
        String hour = "";
        String min = "";
        String sec = "";

        int days = seconds / (60 * 60 * 24);
        if (days == 1) {
            day = "1 day";
        } else if (days > 1) {
            day = days + " days";
        }
        seconds -= days * (60 * 60 * 24);

        int hours = seconds / (60 * 60);
        if (hours == 1) {
            hour = "1 hour";
        } else if (hours > 1) {
            hour = hours + " hours";
        }
        seconds -= hours * (60 * 60);

        int mins = seconds / 60;
        if (mins == 1) {
            min = "1 minute";
        } else if (mins > 1) {
            min = mins + " minutes";
        }
        seconds -= mins * 60;

        if (seconds == 1) {
            sec = "1 second";
        } else if (seconds > 1) {
            sec = seconds + " seconds";
        }

        String fin = day + " " + hour + " " + min + " " + sec;

        if (hour.equals("")) {
            fin = day + " " + min + " " + sec;
            if (min.equals("")) {
                fin = day + " " + sec;
            }
        } else if (min.equals("")) {
            fin = day + " " + hour + " " + sec;
        }

        return fin.trim();

    }

}
