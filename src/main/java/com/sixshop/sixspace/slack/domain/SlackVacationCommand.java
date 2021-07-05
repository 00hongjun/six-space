package com.sixshop.sixspace.slack.domain;

import com.sixshop.sixspace.exception.IllegalFormatSlackCommandException;
import com.sixshop.sixspace.vacation.domain.VacationLocalDateTime;
import java.time.LocalTime;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SlackVacationCommand {

    private static final Pattern START_TIME_PATTERN = Pattern.compile("^([9]|[0][9]|[1][0-2]|[1][4-7]):([0-5][0-9])$");

    private final TwoDays twoDay;

    private final VacationLocalDateTime startTime;

    private final int useHour;

    public SlackVacationCommand(final String message) {
        validFormat(message);

        final String[] splits = message.split(" ");
        this.twoDay = TwoDays.findByText(splits[0]);
        this.startTime = convertVacationStartTime(splits[1]);
        this.useHour = convertUseHour(splits[2]);
    }

    private void validFormat(final String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalFormatSlackCommandException("잘못된 휴가 커맨드 형식 입니다.");
        }

        final String[] splits = message.split(" ");
        if (splits.length != 3) {
            throw new IllegalFormatSlackCommandException("잘못된 휴가 커맨드 형식 입니다.");
        }
    }

    private VacationLocalDateTime convertVacationStartTime(final String time) {
        if (!isTime(time)) {
            throw new IllegalFormatSlackCommandException("휴가 커맨드에서 시작 시간이 잘못되었습니다.");
        }

        final String[] splits = time.split(":");
        final int hour = Integer.parseInt(splits[0]);
        final int minute = Integer.parseInt(splits[1]);
        return VacationLocalDateTime.of(twoDay.toLocalDate(), LocalTime.of(hour, minute));
    }

    private boolean isTime(final String time) {
        return START_TIME_PATTERN.matcher(time).find();
    }

    private int convertUseHour(final String hour) {
        final int useHour = Integer.parseInt(hour);
        if (useHour <= 0) {
            throw new IllegalFormatSlackCommandException("휴가 커맨드에서 사용 시간이 잘못되었습니다.");
        }
        return useHour;
    }
}
