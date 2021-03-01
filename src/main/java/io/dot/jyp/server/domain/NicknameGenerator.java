package io.dot.jyp.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Random;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameGenerator {
    private List<String> first;
    private List<String> second;

    private NicknameGenerator(List<String> first, List<String> second) {
        this.first = first;
        this.second = second;
    }

    public static NicknameGenerator of(List<String> first, List<String> second) {
        return new NicknameGenerator(
                first,
                second
        );
    }

    public static int generateRandomValue(int length) {
        return new Random().nextInt(length);
    }

    public String makeNickname() {
        return this.first.get(generateRandomValue(first.size())) + " " + this.second.get(generateRandomValue(second.size()));
    }
}
