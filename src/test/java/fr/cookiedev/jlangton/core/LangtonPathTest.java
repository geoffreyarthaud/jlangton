package fr.cookiedev.jlangton.core;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LangtonPathTest {
	@Mock
	LangtonMap langtonMap;

	@Mock
	LangtonPath langtonPath;

	LangtonAnt langtonAnt;

	@BeforeEach
	public void initAnt() {
		langtonAnt = new LangtonAnt(langtonMap, langtonPath);
	}

	@Test
	public void whenToLeft_ThenAddFalseToPath() {
		// GIVEN
		when(langtonMap.get(0)).thenReturn(false);

		// WHEN
		langtonAnt.move(0);

		// THEN
		verify(langtonPath, times(1)).add(false);
	}

	@Test
	public void whenToRight_ThenAddTrueToPath() {
		// GIVEN
		when(langtonMap.get(0)).thenReturn(true);

		// WHEN
		langtonAnt.move(0);

		// THEN
		verify(langtonPath, times(1)).add(true);
	}
}
