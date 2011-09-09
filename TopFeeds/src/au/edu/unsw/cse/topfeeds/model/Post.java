package au.edu.unsw.cse.topfeeds.model;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Post {
	private int id;
	private String postId;
	private int ownerId;
	private int senderId;
	private String message;
	private String url;
	private int comments;
	private int likes;
	private String type;
	private double score;
	private Date createdTime;
	private Date lastUpdated;

	public Post() {
	}

	public Post(int id, String postId, int ownerId, int senderId,
			String message, String url, int comments, int likes, String type,
			double score, Date createdTime, Date lastUpdated) {
		super();
		this.id = id;
		this.postId = postId;
		this.ownerId = ownerId;
		this.senderId = senderId;
		this.message = message;
		this.url = url;
		this.comments = comments;
		this.likes = likes;
		this.type = type;
		this.score = score;
		this.createdTime = createdTime;
		this.lastUpdated = lastUpdated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
