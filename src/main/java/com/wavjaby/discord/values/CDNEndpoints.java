package com.wavjaby.discord.values;

public class CDNEndpoints {
//Custom Emoji              emojis/emoji_id.png	                                                        PNG, JPEG, WebP, GIF
//Guild Icon                icons/guild_id/guild_icon.png *	                                            PNG, JPEG, WebP, GIF
//Guild Splash              splashes/guild_id/guild_splash.png	                                        PNG, JPEG, WebP
//Guild Discovery Splash    discovery-splashes/guild_id/guild_discovery_splash.png	                    PNG, JPEG, WebP
//Guild Banner	            banners/guild_id/guild_banner.png	                                        PNG, JPEG, WebP
//Default User Avatar       embed/avatars/user_discriminator.png ** ***		                            PNG
//User Avatar	            avatars/user_id/user_avatar.png *		                                    PNG, JPEG, WebP, GIF
//Application Icon	        app-icons/application_id/icon.png		                                    PNG, JPEG, WebP
//Application Cover	        app-icons/application_id/cover_image.png	                                PNG, JPEG, WebP
//Application Asset	        app-assets/application_id/asset_id.png	                                    PNG, JPEG, WebP
//Achievement Icon	        app-assets/application_id/achievements/achievement_id/icons/icon_hash.png	PNG, JPEG, WebP
//Team Icon	                team-icons/team_id/team_icon.png	                                        PNG, JPEG, WebP

//?size=desired_size Image size can be any power of two between 16 and 4096.


    private final static String ImageBaseUrl = "https://cdn.discordapp.com/";

    public static String getUserAvatar(String userID, String userAvatar) {
        return ImageBaseUrl + "avatars/" + userID + "/" + userAvatar + ".png";
    }
}
