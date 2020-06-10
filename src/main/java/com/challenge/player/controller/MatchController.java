package com.challenge.player.controller;

import com.challenge.player.controller.resource.StartMatchRequest;
import com.challenge.player.model.Match;
import com.challenge.player.model.Match.MatchBuilder;
import com.challenge.player.model.Move;
import com.challenge.player.model.Player;
import com.challenge.player.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchService service;
    private final Player selfPlayer;

    @PostMapping("/play")
    public Match play(@RequestBody final Match match) {
        final Move move = match.getMove().nextMove();
        final MatchBuilder builder = match.builderForTheSameMatch()
                .move(move);
        if (move.isFinished()) {
            builder.winner(selfPlayer);
        }
        return builder.build();
    }

    @PostMapping("/start")
    public Match start(@RequestBody final StartMatchRequest request) {
        final Match match = Match.builder()
                .move(Move.builder()
                        .value(request.getValue())
                        .build())
                .opponent(request.getOpponent())
                .challenger(selfPlayer)
                .build();

        log.info("challengerId={}, opponentId={}, value={}, msg={}",
                match.getChallenger().getId(), match.getOpponent().getId(), match.getMove().getValue(), "Starting a new match");

        service.checkAvailability(match.getOpponent());
        log.info("msg={}", "Opponent is available, the new match is beginning!");
        service.playNewMatch(match);
        return match;
    }
}
