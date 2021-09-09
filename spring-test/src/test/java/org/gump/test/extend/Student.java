package org.gump.test.extend;

public class Student extends People {
    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public void work() {
        super.work();
    }
}
