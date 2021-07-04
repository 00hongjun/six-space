package com.sixshop.sixspace.slack.repository.dto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import lombok.Getter;

@Getter
public class SlackMessage {

    private final String text;

    private final List<Attachment> attachments;

    private SlackMessage(final String text) {
        this(text, List.of(Attachment.getDefault()));
    }

    private SlackMessage(final String text, final List<Attachment> attachments) {
        this.text = text;
        this.attachments = attachments;
    }

    public static SlackMessage fail(final String slackId) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("<@").append(slackId).append(">")
                      .append("님! 남은 휴가가 부족하여 휴가 등록이 실패되었어요 ㅠㅠ 다시 한번 확인 부탁드릴께요 :sob:")
        ;
        return new SlackMessage(messageBuilder.toString());
    }

    public static SlackMessage success(final String slackId, final LocalDateTime startTime, final int useHour) {
        final DayOfWeek dayOfWeek = startTime.getDayOfWeek();
        final String displayName = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("<@").append(slackId).append(">").append("님! ")
                      .append(startTime.getMonth().getValue() + "/" + startTime.getDayOfMonth() +" ")
                      .append("(").append(displayName).append(")")
                      .append(" ").append(startTime.getHour()).append("시").append(getMinuteMessage(startTime.getMinute())).append("부터")
                      .append(" ").append(useHour).append("시간 휴가 사용합니당")
                      .append(" ").append(":surfer:")
        ;

        return new SlackMessage(messageBuilder.toString());
    }


    private static String getMinuteMessage(final int minute) {
        if (minute == 0) {
            return "";
        }
        return " " + minute + "분";
    }
}
