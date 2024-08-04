package com.kinoved.telegrambot.converters;

import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieDtoV14;
import com.kinoved.common.kinopoisk.dtos.movies.details.PersonInMovieDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.ItemNameDto;
import com.kinoved.telegrambot.config.AppProps;
import com.kinoved.telegrambot.dtos.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieDataConverterImpl implements MovieDataConverter {

    private final AppProps appProps;

    @Override
    public String convertToShortOverview(MovieDto movieDto) {

        String titleLine = getTitleLine(
                movieDto.getName(),
                movieDto.getAlternativeName());
        String yearAndGenresLine = getYearAndGenresLine(
                movieDto.getYear(),
                movieDto.getGenres());
        String countryAndDurationLine = getCountryAndDurationLine(
                movieDto.getCountries(),
                movieDto.getMovieLength(),
                movieDto.getAgeRating());
        String ratingsLine = getRatingsLine(
                movieDto.getRating().getKp(),
                movieDto.getRating().getImdb());
        String kinopoiskRef = getKinopoiskRef(
                movieDto.getExternalId().getKpDev());

        return String.join("\n",
                titleLine,
                yearAndGenresLine,
                countryAndDurationLine,
                ratingsLine,
                kinopoiskRef);
    }

    @Override
    public String convertToFullOverview(MovieDto movieDto) {
        String shortOverview = convertToShortOverview(movieDto);

        String description = getDescription(movieDto.getDescription());

        String personsGroupedByProfessions = getPersonsGroupedByProfessions(movieDto);

        String fullOverview = String.join("\n",
                shortOverview,
                description,
                personsGroupedByProfessions);

        return truncateStringToLimitByLines(fullOverview, appProps.getCaptionSymbolsLimit());
    }

    @Override
    public String convertToIdentificationOverview(SearchMovieDtoV14 movieDto) {

        String titleLine = getTitleLine(
                movieDto.getName(),
                movieDto.getAlternativeName());
        String yearAndGenresLine = getYearAndGenresLine(
                movieDto.getYear(),
                movieDto.getGenres().stream().map(ItemNameDto::getName).toList());
        String countryAndDurationLine = getCountryAndDurationLine(
                movieDto.getCountries().stream().map(ItemNameDto::getName).toList(),
                movieDto.getMovieLength(),
                movieDto.getAgeRating());
        String ratingsLine = getRatingsLine(
                movieDto.getRating().getKp(),
                movieDto.getRating().getImdb());
        String descriptionLine = getDescription(movieDto.getDescription());
        String kinopoiskRef = getKinopoiskRef(
                movieDto.getId());

        return String.join("\n",
                titleLine,
                yearAndGenresLine,
                countryAndDurationLine,
                ratingsLine,
                descriptionLine,
                kinopoiskRef);
    }

    private String getTitleLine(String name, String englishName) {
        return String.format("\uD83C\uDFAC %s (%s)", name, englishName);
    }

    private String getYearAndGenresLine(int year, List<String> genres) {
        return String.format("%d, %s", year, String.join(", ", genres));
    }

    private String getCountryAndDurationLine(List<String> countries, Integer movieLength, Integer ageRating) {
        return String.format("%s, %dч %d мин. %s",
                String.join(", ", countries),
                movieLength / 60,
                movieLength % 60,
                ageRating != null ? ageRating + "+" : "");
    }

    private String getRatingsLine(Double kp, Double imdb) {
        return String.format("⭐ %.2f КП  (IMDb: %.1f)",
                kp, imdb);
    }

    private String getKinopoiskRef(Long movieId) {
        return String.format("https://www.kinopoisk.ru/film/%s/", movieId);
    }

    private String getDescription(String description) {
        return String.format("\n%s\n", description);
    }

    private String getPersonsGroupedByProfessions(MovieDto movieDto) {
        if (movieDto.getPersons() == null) {
            return "Нет данных по актерам";
        }

        StringBuilder personsStringBuilder = new StringBuilder("");

        Map<String, List<PersonInMovieDto>> personsByProfession = movieDto.getPersons().stream()
                .filter(person -> "актеры".equalsIgnoreCase(person.getProfession())
                                || "режиссеры".equalsIgnoreCase(person.getProfession())
//                            || "продюсеры".equalsIgnoreCase(person.getProfession())
//                            || "операторы".equalsIgnoreCase(person.getProfession())
//                            || "композиторы".equalsIgnoreCase(person.getProfession())
                )
                .collect(Collectors.groupingBy(PersonInMovieDto::getProfession));

        // Формирование строки для персон с ограничением по кол-ву
        personsByProfession.forEach((profession, persons) -> {
            personsStringBuilder.append(profession).append("\n");
            persons.stream()
                    .filter(person -> person.getName() != null)
                    .limit(appProps.getMaxDisplayedPersonsPerProfession())
                    .forEach(person -> personsStringBuilder
                            .append(String.format("  - %s\n", person.getName())));
        });
        return personsStringBuilder.toString();
    }

    private String truncateStringToLimitByLines(String string, int limit) {
        //todo возможен рефакторинг. возможно перенести в утилитарный класс так как может понадобиться в других классах
        //Ограничение на выводимый caption в телеграмме: по умолчанию 1024 символов
        if (string.length() <= limit) {
            return string;
        }

        String[] lines = string.split("\n");
        StringBuilder truncatedString = new StringBuilder();
        for (String line : lines) {
            if (truncatedString.length() + line.length() + 1 > limit) {
                break;
            }
            truncatedString.append(line).append("\n");
        }
        return truncatedString.toString().trim();
    }
}
