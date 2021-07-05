package com.sixshop.sixspace.exception;

public class NotEnoughVacationException extends RuntimeException {

    private static final String message = "남은 휴가가 부족하여 휴가 등록이 실패되었어요 ㅠㅠ 다시 한번 확인 부탁드릴께요 :sob:";

    public NotEnoughVacationException() {
        this(message);
    }

    public NotEnoughVacationException(final String message) {
        super(message);
    }
}
