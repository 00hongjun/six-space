package com.sixshop.sixspace.slack.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Attachment {

    private static final Attachment DEFAULT_FORMAT = new Attachment("핫식스", "그 누구도 나를 막을수 없으센 (그누막)", "#36a64f", "https://naver.com");

    private String title;
    private String text;
    private String color;
    @JsonProperty("title_link")
    private String titleLink;

    public Attachment(final String title, final String text, final String color, final String titleLink) {
        this.title = title;
        this.text = text;
        this.color = color;
        this.titleLink = titleLink;
    }

    public static Attachment getDefault() {
        return DEFAULT_FORMAT;
    }
}
