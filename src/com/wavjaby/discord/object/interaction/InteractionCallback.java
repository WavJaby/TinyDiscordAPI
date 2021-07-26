package com.wavjaby.discord.object.interaction;

import com.wavjaby.discord.object.message.AllowedMention;
import com.wavjaby.discord.object.message.component.Component;
import com.wavjaby.discord.object.message.embed.Embed;
import com.wavjaby.json.JsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class InteractionCallback {
    private Boolean tts;
    private String content;
    private List<Embed> embeds;
    private AllowedMention allowed_mentions;
    private Integer flags;
    private List<Component> components;

    public InteractionCallback() {
    }

    public InteractionCallback(String content, List<Embed> embeds, List<Component> components) {
        this.content = content;
        this.embeds = embeds;
        this.components = components;
    }

    public Component addActionRow() {
        if (components == null)
            components = new ArrayList<>();
        Component component = new Component();
        components.add(component);
        return component;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addEmbed(Embed embed) {
        if (embeds == null)
            embeds = new ArrayList<>();
        embeds.add(embed);
    }

    public Embed addEmbed() {
        if (embeds == null)
            embeds = new ArrayList<>();
        Embed embed = new Embed();
        embeds.add(embed);
        return embed;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();
        if (content != null)
            builder.append("content", content);
        if (embeds != null)
            builder.append("embeds", embeds.toString(), true);
        if (flags != null)
            builder.append("flags", flags);
        if (components != null)
            builder.append("components", components.toString(), true);
        return builder.toString();
    }

    public void addFlag(int flag) {
        if (this.flags == null)
            this.flags = 0;
        this.flags |= flag;
    }

    public void removeFlag(int flag) {
        if (this.flags == null)
            this.flags = 0;
        if ((this.flags & flag) == flag)
            this.flags -= flag;
    }
}
