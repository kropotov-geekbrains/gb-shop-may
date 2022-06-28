package ru.gb.gbshopmay.modelMessage;

import lombok.*;

import java.io.Serializable;

/**
 * @author Artem Kropotov
 * created at 08.06.2022
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePricedMessage implements Serializable {

    static final long serialVersionUID = 6076956653392542387L;

    private String message;

    @Override
    public String toString() {
        return "ChangePricedMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
