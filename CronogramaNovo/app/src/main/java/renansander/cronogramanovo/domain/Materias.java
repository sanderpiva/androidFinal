package renansander.cronogramanovo.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Materias implements Serializable {
    private String id;
    private String description;
    private String week_day;
    private String room;
    private String hour;
    private Boolean active;

    private Date dateChanged;


    public Materias(String id, String desc, String room,
                    String week_day, String hour, Boolean active){

        this.id = id;
        description = desc;
        this.active = active;
        this.hour = hour;
        this.week_day = week_day;
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private String getActiveString(){

        String result = active ? "Ativa" : "Realizada";
        return result;
    }

    public String getWeek_day() {
        return week_day;
    }

    public void setWeek_day(String week_day) {
        this.week_day = week_day;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        String formatted =
                String.format("%s -%s -%s -%s -%s", this.getDescription(),
                        this.getRoom(), this.getWeek_day(), this.getHour(), this.getActiveString());
        return formatted;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged() {
        this.dateChanged = Calendar.getInstance().getTime();
    }

}
