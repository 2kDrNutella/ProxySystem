package de.drnutella.proxycore.objects;

public class TimeObject {

    long days;
    long hours;
    long minutes;
    long seconds;

    public TimeObject(long days, long hours, long minutes, long seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public String toTimeString(){
        final StringBuilder stringBuilder = new StringBuilder();
        boolean allNull = true;

        if(days != 0){
            stringBuilder.append(days + " Tage ");
            allNull = false;
        }

        if(hours != 0){
            stringBuilder.append(hours + " Stunden ");
            allNull = false;
        }

        if(minutes != 0){
            stringBuilder.append(minutes + " Minuten ");
            allNull = false;
        }

        if(seconds != 0){
            stringBuilder.append(seconds + " Sekunden");
            allNull = false;
        }

        if(allNull){
            stringBuilder.append("Keine Spielzeit gespeichert (Spieler ist zum ersten mal Online)!");
        }

        return stringBuilder.toString();
    }
}
