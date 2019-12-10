package fr.cookiedev.jlangton.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LangtonStepTest {

	@Mock
	LangtonMap langtonMap;

	LangtonAnt langtonAnt;

	@BeforeEach
	public void initAnt() {
		langtonAnt = new LangtonAnt(langtonMap);
	}

	@Test
	public void givenFalseSquare_whenNextStep_thenSetTrueAndTurnLeft() {
		// GIVEN
		final long fromPos = 0;
		final long toPos = 1;
		when(langtonMap.get(fromPos)).thenReturn(false);
		when(langtonMap.toLeft(fromPos)).thenReturn(toPos);

		// WHEN
		final long actualToPos = langtonAnt.move(fromPos);

		// THEN
		verify(langtonMap).set(fromPos, true);
		verify(langtonMap).toLeft(fromPos);
		assertThat(actualToPos).isEqualTo(toPos);
	}

	@Test
	public void givenTrueSquare_whenNextStep_thenSetFalseAndTurnRight() {
		// GIVEN
		final long fromPos = 0;
		final long toPos = 1;
		when(langtonMap.get(fromPos)).thenReturn(true);
		when(langtonMap.toRight(fromPos)).thenReturn(toPos);

		// WHEN
		final long actualToPos = langtonAnt.move(fromPos);

		// THEN
		verify(langtonMap).set(fromPos, false);
		verify(langtonMap).toRight(fromPos);
		assertThat(actualToPos).isEqualTo(toPos);
	}

	@Test
	public void whenFourWhiteMoves_thenCallToLeftFourTimes() {
		// GIVEN
		final long pos1 = 0;
		final long pos2 = 1;
		final long pos3 = 2;
		final long pos4 = 3;

		when(langtonMap.get(ArgumentMatchers.any(Long.class))).thenReturn(false);
		when(langtonMap.toLeft(pos1)).thenReturn(pos2);
		when(langtonMap.toLeft(pos2)).thenReturn(pos3);
		when(langtonMap.toLeft(pos3)).thenReturn(pos4);
		when(langtonMap.toLeft(pos4)).thenReturn(pos1);

		// WHEN
		final long actualToPos = langtonAnt.move(pos1, 4);

		// THEN
		verify(langtonMap).toLeft(pos1);
		verify(langtonMap).toLeft(pos2);
		verify(langtonMap).toLeft(pos3);
		verify(langtonMap).toLeft(pos4);
		assertThat(actualToPos).isEqualTo(pos1);
	}
}
