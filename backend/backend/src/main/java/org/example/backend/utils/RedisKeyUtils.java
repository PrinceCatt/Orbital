package org.example.backend.utils;

public class RedisKeyUtils {

    //the key for two hashmaps for liked and likedCount
    public static final String MapUserLiked = "MapUserLiked";
    public static final String MapUserLikesCount = "MapUserLikesCount";

    //generate key for storing in hashmap
    public static String getLikedKey(int commentId, int giveUserId) {
        StringBuilder builder = new StringBuilder();
        builder.append(commentId);
        builder.append("::");
        builder.append(giveUserId);
        return builder.toString();
    }
}
