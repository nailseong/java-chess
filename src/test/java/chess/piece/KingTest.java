package chess.piece;

import static chess.piece.Team.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import chess.position.Position;

public class KingTest {
	@DisplayName("체스말의 팀과 종류에 따라 심볼이 반환된다.")
	@ParameterizedTest
	@CsvSource(value = {"BLACK,K", "WHITE,k"})
	void getSymbolTest(Team team, String expected) {
		Piece piece = new King(team);
		assertThat(piece.getSymbol()).isEqualTo(expected);
	}
}