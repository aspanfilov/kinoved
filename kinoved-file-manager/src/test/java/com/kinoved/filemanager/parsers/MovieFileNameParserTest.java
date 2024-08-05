package com.kinoved.filemanager.parsers;

import com.kinoved.filemanager.dtos.MovieFileNameDto;
import com.kinoved.filemanager.parsers.impl.MovieFileNameParserImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс MovieFileNameParser должен: ")
public class MovieFileNameParserTest {

    @Autowired
    private final MovieFileNameParserImpl parser = new MovieFileNameParserImpl();

    private static Stream<Arguments> provideMovieFileNames() {
        return Stream.of(
                Arguments.of("About.My.Father.2023.1080p.AMZN", "About My Father", "2023", "1080p"),
                Arguments.of("About.My.Father.2023.BDRip.1080p.pk", "About My Father", "2023", "1080p"),
                Arguments.of("Aftersun.2022.1080p.BDRip.1080p.pk", "Aftersun", "2022", "1080p"),
                Arguments.of("Air (2023) WEB-DL.1080p", "Air", "2023", "1080p"),
                Arguments.of("Cobweb.2023.2160p.WEB-DL.HDR.H.265.s", "Cobweb", "2023", "2160p"),
                Arguments.of("Corner.Office.2023.1080p.AMZN_от New-Team_JNS82", "Corner Office", "2023", "1080p"),
                Arguments.of("God Is a Bullet (2023).UnCut.WEB-DL.1080p", "God Is a Bullet", "2023", "1080p"),
                Arguments.of("Guardians of the Galaxy Vol. 3 (2023) HDR.4K.DV.WEB-DL.2160p.IMAX", "Guardians of the Galaxy Vol  3", "2023", "2160p"),
                Arguments.of("Guardians.of.the.Galaxy.Vol.3.2023.2160p.AMZN.WEB-DL.HDR.H.265.Master5", "Guardians of the Galaxy Vol 3", "2023", "2160p"),
                Arguments.of("Guy.Ritchies.The.Covenant.2023.1080p.BluRay.x264-HiDt_EniaHD", "Guy Ritchies The Covenant", "2023", "1080p"),
                Arguments.of("Istoriya o nas", "Istoriya o nas", null, null),
                Arguments.of("Italian Best Shorts 6 Дом, милый дом (2023) WEB-DL 1080p NNMClub", "Italian Best Shorts 6 Дом, милый дом", "2023", "1080p"),
                Arguments.of("Little.Bone.Lodge.2023.MVO.AMZN.WEB-DLRip.720p.x264.seleZen", "Little Bone Lodge", "2023", "720p"),
                Arguments.of("No.Hard.Feelings.2023.1080p.AMZN.WEB-DL.H.264-EniaHD", "No Hard Feelings", "2023", "1080p"),
                Arguments.of("Novaya.realnost.2022.WEB-DL.1080p.ELEKTRI4KA.UNIONGANG", "Novaya realnost", "2022", "1080p"),
                Arguments.of("Po.muzhski.2022.WEB-DL.1080p.ELEKTRI4KA.UNIONGANG", "Po muzhski", "2022", "1080p"),
                Arguments.of("Pryamoy.efir.2022.WEB-DL.1080p.ELEKTRI4KA.UNIONGANG", "Pryamoy efir", "2022", "1080p"),
                Arguments.of("Sisu.2022.WEB-DL.2160p.HDR.Bobropandava", "Sisu", "2022", "2160p"),
                Arguments.of("The.Portable.Door.2023.AMZN.WEB-DL.1080p.seleZen", "The Portable Door", "2023", "1080p"),
                Arguments.of("Yura.dvornik.2023.WEB-DL.1080p.ELEKTRI4KA.UNIONGANG", "Yura dvornik", "2023", "1080p"),
                Arguments.of("Нефариус.2023.BDRip 720p msltel", "Нефариус", "2023", "720p"),
                Arguments.of("Переводчик. 2023 (HEVC.SDR.WEB-DL 2160p)", "Переводчик", "2023", "2160p")
        );
    }

    @DisplayName("корректно парсить наименования файлов фильмов")
    @ParameterizedTest
    @MethodSource("provideMovieFileNames")
    void testParseFileName(String fileName, String expectedTitle, String expectedYear, String expectedResolution) {
        MovieFileNameDto result = parser.parseFileName(fileName);

        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(result.getYear()).isEqualTo(expectedYear);
        assertThat(result.getResolution()).isEqualTo(expectedResolution);
    }

}
