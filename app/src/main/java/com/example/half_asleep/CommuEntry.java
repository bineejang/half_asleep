package com.example.half_asleep;

public class CommuEntry {
    private String post_id;
    private String username;
    private String postDate;
    private String profileImage;
    private String postImage;
    private String content;
    public CommuEntry(String diaryId, String postDate, String profileImage,String username,String postImage,String content) {
        this.post_id = diaryId;
        this.username = username;
        this.postDate = postDate;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.content = content;
    }

    public String getPost_id() {
        return post_id;
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
    public String getpostImage() {
        return postImage;
    }
    public String getcontent() {
        return content;
    }
}
