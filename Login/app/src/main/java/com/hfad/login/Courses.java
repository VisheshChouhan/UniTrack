package com.hfad.login;

import java.io.Serializable;
import java.util.ArrayList;

public class Courses implements Serializable
{
    String passwd, courseId, enterStageTwo, exitStageTwo, totalClassesTaken;

    MaxMarks maxMarks;

    public Courses() {
    }

    public Courses(String courseid) {


        this.passwd = "helloooooooooooooooooooooooooooooooooooooo786348273";
        this.courseId = courseid;
        enterStageTwo= "0";
        exitStageTwo = "0";
        totalClassesTaken = "0";

    }



    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getEnterStageTwo() {
        return enterStageTwo;
    }

    public void setEnterStageTwo(String enterStageTwo) {
        this.enterStageTwo = enterStageTwo;
    }

    public String getExitStageTwo() {
        return exitStageTwo;
    }

    public void setExitStageTwo(String exitStageTwo) {
        this.exitStageTwo = exitStageTwo;
    }

    public String getTotalClassesTaken() {
        return totalClassesTaken;
    }

    public void setTotalClassesTaken(String totalClassesTaken) {
        this.totalClassesTaken = totalClassesTaken;
    }

}
