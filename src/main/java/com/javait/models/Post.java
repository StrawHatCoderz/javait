package com.javait.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document
public final class Post {
  @Id
  private final int postId;

  private final int authorId;
  private final String title;
  private final String content;
  private final Date postedOn;
  private final Likes likes;

  public Post(int postId, int authorId, String title,
              String content, Date postedOn,
              Likes likes) {
    this.postId = postId;
    this.authorId = authorId;
    this.title = title;
    this.content = content;
    this.postedOn = postedOn;
    this.likes = likes;
  }

  public int postId() {
    return postId;
  }

  public int authorId() {
    return authorId;
  }

  public String title() {
    return title;
  }

  public String content() {
    return content;
  }

  public Date postedOn() {
    return postedOn;
  }

  public Likes likes() {
    return likes;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Post) obj;
    return this.postId == that.postId &&
            this.authorId == that.authorId &&
            Objects.equals(this.title, that.title) &&
            Objects.equals(this.content, that.content) &&
            Objects.equals(this.postedOn, that.postedOn) &&
            Objects.equals(this.likes, that.likes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(postId, authorId, title, content, postedOn, likes);
  }

  @Override
  public String toString() {
    return "Post[" +
            "postId=" + postId + ", " +
            "authorId=" + authorId + ", " +
            "title=" + title + ", " +
            "content=" + content + ", " +
            "postedOn=" + postedOn + ", " +
            "likes=" + likes + ']';
  }

}
