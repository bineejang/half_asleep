package com.example.half_asleep;

public class CommentEntry {
    private String comment_id;
    private String username;
    private String postDate;
    private String profileImage;
    private String pin;
    private String content;

    public CommentEntry(String commentid, String username,String postDate, String profileImage,String pin,String content) {
        this.comment_id = commentid;
        this.username = username;
        this.postDate = postDate;
        this.profileImage = profileImage;
        this.pin = pin;
        this.content = content;
    }

    public String getcomment_id() {
        return comment_id;
    }
    public String getusername() {
        return username;
    }
    public String getpostDate() {
        return postDate;
    }
    public String getprofileImage() {
        return profileImage;
    }
    public String getpin() { return pin; }
    public String getcontent() {
        return content;
    }
}
