package com.challenge.player.service.client;

import com.challenge.player.model.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MatchClient {

    public static final String MATCH_PLAY_ENDPOINT = "/match/play";
    private final RestTemplate restTemplate;

    public Optional<Match> playNewMatch(final Match match) {
        final String url = match.getOpponent().getAddress() + MATCH_PLAY_ENDPOINT;
        return Optional.ofNullable(restTemplate.postForObject(url, match, Match.class));
    }
}
