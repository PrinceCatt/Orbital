package org.example.backend.utils;

public class RedisKeyUtils {

    public static final String MapUserLiked = "MapUserLiked";
    public static final String MapUserLikesCount = "MapUserLikesCount";

    public static String getLikedKey(int commentId, int giveUserId) {
        StringBuilder builder = new StringBuilder();
        builder.append(commentId);
        builder.append("::");
        builder.append(giveUserId);
        return builder.toString();
    }
}
