package com.wavjaby.discord.object.message.embed;

import com.wavjaby.discord.object.guild.Member;
import com.wavjaby.json.JsonBuilder;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Embed {
    private String title;
    private String type;
    private String description;
    private String url;
    private OffsetDateTime timestamp;
    private Integer color;
    private EmbedFooter footer;
    private EmbedImage image;
    private EmbedThumbnail thumbnail;
    private EmbedVideo video;
    private EmbedProvider provider;
    private EmbedAuthor author;
    private List<EmbedField> fields;

    public Embed() {
    }

    public Embed(JsonObject embedData) {
        title = embedData.getString("title");
        type = embedData.getString("type");
        description = embedData.getString("description");
        url = embedData.getString("url");
        if (embedData.containsKey("premium_since"))
            timestamp = OffsetDateTime.parse(embedData.getString("timestamp"));
        if (embedData.containsKey("color"))
            color = embedData.getInt("color");
        if (embedData.containsKey("footer"))
            footer = new EmbedFooter(embedData.getJson("footer"));
        if (embedData.containsKey("image"))
            image = new EmbedImage(embedData.getJson("image"));
        if (embedData.containsKey("thumbnail"))
            thumbnail = new EmbedThumbnail(embedData.getJson("thumbnail"));
        if (embedData.containsKey("video"))
            video = new EmbedVideo(embedData.getJson("video"));
        if (embedData.containsKey("provider"))
            provider = new EmbedProvider(embedData.getJson("provider"));
        if (embedData.containsKey("author"))
            author = new EmbedAuthor(embedData.getJson("author"));

        if (embedData.containsKey("fields")) {
            fields = new ArrayList<>();
            for (Object i : embedData.getArray("fields"))
                fields.add(new EmbedField((JsonObject) i));
        }
    }

    Embed(String title, String type, String description, String url, OffsetDateTime timestamp, int color,
            EmbedFooter footer, EmbedImage image, EmbedThumbnail thumbnail,
            EmbedVideo video, EmbedProvider provider, EmbedAuthor author, List<EmbedField> fields) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.url = url;
        this.timestamp = timestamp;
        this.color = color;

        this.footer = footer;
        this.image = image;
        this.thumbnail = thumbnail;
        this.video = video;
        this.provider = provider;
        this.author = author;
        this.fields = fields;
    }

    public Embed(String title, String description, int color, EmbedFooter footer, EmbedAuthor author) {
        this.title = title;
        this.type = EmbedType.RICH;
        this.description = description;
        this.timestamp = OffsetDateTime.now();
        this.color = color;

        this.footer = footer;
        this.author = author;
    }

    public Embed(String title, String description) {
        this.title = title;
        this.type = EmbedType.RICH;
        this.description = description;
        this.timestamp = OffsetDateTime.now();
    }

    public void addField(EmbedField field) {
        if (fields == null)
            fields = new ArrayList<>();
        fields.add(field);
    }

    public void addField(String title, String content) {
        if (fields == null)
            fields = new ArrayList<>();
        fields.add(new EmbedField(title, content));
    }

    /**
     * setter
     */
    public void setAuthor(Member member) {
        this.author = new EmbedAuthor(member.getNick(), member.getUser().getAvatar());
    }

    public void setImage(EmbedImage image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description.replace("\n", "\\n");
    }

    public void setFooter(EmbedFooter footer) {
        this.footer = footer;
    }

    public void setFooter(String name, String icon) {
        this.footer = new EmbedFooter(name, icon);
    }

    public void setFooter(String footerText) {
        this.footer = new EmbedFooter(footerText);
    }

    public void setAuthor(EmbedAuthor author) {
        this.author = author;
    }

    public void setAuthor(String name, String icon) {
        this.author = new EmbedAuthor(name, icon);
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (title != null)
            builder.append("title", title);
        if (type != null)
            builder.append("type", type);
        if (description != null)
            builder.append("description", description);
        if (url != null)
            builder.append("url", url);
        if (timestamp != null)
            builder.append("timestamp", timestamp.toString());
        if (color != null)
            builder.append("color", color);
        if (footer != null)
            builder.append("footer", footer.toString(), true);
        if (image != null)
            builder.append("image", image.toString(), true);
        if (video != null)
            builder.append("video", video.toString(), true);
        if (provider != null)
            builder.append("provider", provider.toString(), true);
        if (author != null)
            builder.append("author", author.toString(), true);
        if (fields != null)
            builder.append("fields", fields.toString(), true);
        return builder.toString();
    }

    /**
     * setter
     */
    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public int getColor() {
        return color;
    }

    public EmbedFooter getFooter() {
        return footer;
    }

    public EmbedImage getImage() {
        return image;
    }

    public EmbedThumbnail getThumbnail() {
        return thumbnail;
    }

    public EmbedVideo getVideo() {
        return video;
    }

    public EmbedProvider getProvider() {
        return provider;
    }

    public EmbedAuthor getAuthor() {
        return author;
    }

    public List<EmbedField> getFields() {
        return fields;
    }
}
