package org.example.backend.utils;

public class RedisKeyUtils {

    public static final String MapUserLiked = "MapUserLiked";
    public static final String MapUserLikesCount = "MapUserLikesCount";

    public static String getLikedKey(int postId, int giveUserId) {
        StringBuilder builder = new StringBuilder();
        builder.append(postId);
        builder.append("::");
        builder.append(giveUserId);
        return builder.toString();
    }
}
