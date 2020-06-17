package com.challenge.player.controller;

import com.challenge.player.PlayerApplication;
import com.challenge.player.controller.resource.StartMatchRequest;
import com.challenge.player.model.Match;
import com.challenge.player.model.Move;
import com.challenge.player.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest(
        webEnvironment = MOCK,
        classes = PlayerApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class MatchControllerIntegrationTest {

    private static final String MATCH_API_PATH = "/match";
    private static final String PLAY_API_PATH = MATCH_API_PATH + "/play";
    private static final String START_API_PATH = MATCH_API_PATH + "/start";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private Player selfPlayer;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        setupStub();
    }

    @Test
    public void play_WithSuccess_ShouldReturnNewMatchWithNextMove() throws Exception {
        final Match match = buildMatch(90);
        mvc.perform(post(PLAY_API_PATH)
                .content(objectMapper.writeValueAsBytes(match))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("move.value", is(30)))
                .andExpect(jsonPath("move.owner.address", is(match.getMove().getOwner().getAddress())))
                .andExpect(jsonPath("move.endGameMove", is(match.getMove().isEndGameMove())))
                .andExpect(jsonPath("challenger.address", is(match.getChallenger().getAddress())))
                .andExpect(jsonPath("opponent.address", is(match.getOpponent().getAddress())))
                .andExpect(jsonPath("gameEnded", is(match.isGameEnded())));
    }

    @Test
    public void play_WithSuccess_ShouldReturnFinalMatchWithLastMove() throws Exception {
        final Match match = buildMatch(3);
        mvc.perform(post(PLAY_API_PATH)
                .content(objectMapper.writeValueAsBytes(match))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("move.value", is(1)))
                .andExpect(jsonPath("move.owner.address", is(match.getMove().getOwner().getAddress())))
                .andExpect(jsonPath("move.endGameMove", is(Boolean.TRUE)))
                .andExpect(jsonPath("challenger.address", is(match.getChallenger().getAddress())))
                .andExpect(jsonPath("opponent.address", is(match.getOpponent().getAddress())))
                .andExpect(jsonPath("gameEnded", is(Boolean.TRUE)));
    }

    @Test
    public void start_WithOpponentAvailable_ShouldReturnNewMatchCreated() throws Exception {
        final StartMatchRequest request = new StartMatchRequest(Player.builder().address("http://localhost:8081").build(), 56);
        final Match match = buildMatch(56);

        mvc.perform(post(START_API_PATH)
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("move.value", is(match.getMove().getValue())))
                .andExpect(jsonPath("move.owner.address", is(match.getMove().getOwner().getAddress())))
                .andExpect(jsonPath("move.endGameMove", is(Boolean.TRUE)))
                .andExpect(jsonPath("challenger.address", is(match.getChallenger().getAddress())))
                .andExpect(jsonPath("opponent.address", is(match.getOpponent().getAddress())))
                .andExpect(jsonPath("gameEnded", is(match.isGameEnded())));
    }

    private void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/actuator/health"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(OK.value())
                        .withBody("{\"status\":\"UP\"}")));
    }

    private Match buildMatch(final Integer value) {
        final Move move = Move.builder().owner(selfPlayer).value(value).build();
        final Player player = Player.builder().address("").build();
        return Match.builder().id(randomUUID().toString()).challenger(selfPlayer).opponent(player).move(move).build();
    }
}
