package fr.cookiedev.jlangton;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LangtonStepTest {

	@Mock
	LangtonMap langtonMap;

	LangtonAnt langtonAnt;

	@BeforeEach
	public void initAnt() {
		this.langtonAnt = new LangtonAnt(this.langtonMap);
	}

	@Test
	public void givenFalseSquare_whenNextStep_thenSetTrueAndTurnLeft() {
		// GIVEN
		final long fromPos = 0;
		final long toPos = 1;
		when(this.langtonMap.get(fromPos)).thenReturn(false);
		when(this.langtonMap.toLeft(fromPos)).thenReturn(toPos);

		// WHEN
		final long actualToPos = this.langtonAnt.move(fromPos);

		// THEN
		verify(this.langtonMap).set(fromPos, true);
		verify(this.langtonMap).toLeft(fromPos);
		assertThat(actualToPos).isEqualTo(toPos);
	}

	@Test
	public void givenTrueSquare_whenNextStep_thenSetFalseAndTurnRight() {
		// GIVEN
		final long fromPos = 0;
		final long toPos = 1;
		when(this.langtonMap.get(fromPos)).thenReturn(true);
		when(this.langtonMap.toRight(fromPos)).thenReturn(toPos);

		// WHEN
		final long actualToPos = this.langtonAnt.move(fromPos);

		// THEN
		verify(this.langtonMap).set(fromPos, false);
		verify(this.langtonMap).toRight(fromPos);
		assertThat(actualToPos).isEqualTo(toPos);
	}
}
