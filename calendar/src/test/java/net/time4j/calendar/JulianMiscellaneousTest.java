package net.time4j.calendar;

import net.time4j.Month;
import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.format.DisplayMode;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.history.HistoricEra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class JulianMiscellaneousTest {

    @Test
    public void julianCalendarProperties() {
        JulianCalendar date = JulianCalendar.of(HistoricEra.AD, 1752, Month.FEBRUARY, 29);
        assertThat(
            date.getDayOfMonth(),
            is(29));
        assertThat(
            date.getMonth(),
            is(Month.FEBRUARY));
        assertThat(
            date.lengthOfMonth(),
            is(29));
        assertThat(
            date.atTime(12, 0).toDate(),
            is(date));
        assertThat(
            date.lengthOfYear(),
            is(366)
        );
    }

    @Test
    public void julianCalendarBetween() {
        JulianCalendar start = JulianCalendar.of(HistoricEra.AD, 1752, Month.FEBRUARY, 28);
        JulianCalendar end = JulianCalendar.of(HistoricEra.AD, 1752, Month.APRIL, 27);
        assertThat(JulianCalendar.Unit.MONTHS.between(start, end), is(1));
        assertThat(JulianCalendar.Unit.DAYS.between(start, end), is(59));
        end = end.plus(1, JulianCalendar.Unit.YEARS);
        assertThat(JulianCalendar.Unit.YEARS.between(start, end), is(1));
    }

    @Test
    public void formatJulianCalendar() {
        ChronoFormatter<JulianCalendar> f =
            ChronoFormatter.ofStyle(DisplayMode.FULL, Locale.GERMAN, JulianCalendar.axis());
        assertThat(
            f.format(JulianCalendar.of(HistoricEra.AD, 1752, 9, 14)),
            is("Montag, 14. September 1752 n. Chr.")
        );
    }

    @Test
    public void defaultFirstDayOfWeek() {
        assertThat(JulianCalendar.DAY_OF_WEEK.getDefaultMinimum(), is(Weekday.SUNDAY));
    }

    @Test
    public void isValidIfWeekdayOutOfRange() {
        JulianCalendar min = JulianCalendar.axis().getMinimum(); // wednesday
        JulianCalendar max = JulianCalendar.axis().getMaximum(); // sunday

        assertThat(min.isValid(JulianCalendar.DAY_OF_WEEK, Weekday.TUESDAY), is(false));
        assertThat(min.isValid(JulianCalendar.DAY_OF_WEEK, Weekday.WEDNESDAY), is(true));
        assertThat(max.isValid(JulianCalendar.DAY_OF_WEEK, Weekday.MONDAY), is(false));
        assertThat(max.isValid(JulianCalendar.DAY_OF_WEEK, Weekday.SUNDAY), is(true));

        assertThat(min.getMinimum(JulianCalendar.DAY_OF_WEEK), is(Weekday.WEDNESDAY));
        assertThat(min.getMaximum(JulianCalendar.DAY_OF_WEEK), is(Weekday.SATURDAY));
        assertThat(max.getMinimum(JulianCalendar.DAY_OF_WEEK), is(Weekday.SUNDAY));
        assertThat(max.getMaximum(JulianCalendar.DAY_OF_WEEK), is(Weekday.SUNDAY));

        StdCalendarElement<Weekday, JulianCalendar> elementUS =
            CommonElements.localDayOfWeek(JulianCalendar.axis(), Weekmodel.of(Locale.US));
        assertThat(min.getMinimum(elementUS), is(Weekday.WEDNESDAY));
        assertThat(min.getMaximum(elementUS), is(Weekday.SATURDAY));
        assertThat(max.getMinimum(elementUS), is(Weekday.SUNDAY));
        assertThat(max.getMaximum(elementUS), is(Weekday.SUNDAY));

        StdCalendarElement<Weekday, JulianCalendar> elementISO =
            CommonElements.localDayOfWeek(JulianCalendar.axis(), Weekmodel.ISO);
        assertThat(min.getMinimum(elementISO), is(Weekday.WEDNESDAY));
        assertThat(min.getMaximum(elementISO), is(Weekday.SUNDAY));
        assertThat(max.getMinimum(elementISO), is(Weekday.MONDAY));
        assertThat(max.getMaximum(elementISO), is(Weekday.SUNDAY));
    }

}