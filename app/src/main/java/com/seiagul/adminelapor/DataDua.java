package com.seiagul.adminelapor;

public class DataDua {
    private String id, usernameList, emailList;

    public DataDua() {
    }

    public DataDua(String id, String usernameList, String emailList){
        this.id = id;
        this.usernameList = usernameList;
        this.emailList = emailList;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(String pelaporList) {
        this.usernameList = pelaporList;
    }

    public String getEmailList() {
        return emailList;
    }

    public void setEmailList(String emailList) {
        this.emailList = emailList;
    }

}
