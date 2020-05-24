package com.company.IO.file;

import com.company.model.Offer;
import com.company.model.Person;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerTest {
    private FileManager manager = new FileManager();

    @Test
    void should_import_form_file_and_return_list_of_people() {
        List<Person> people = manager.importPeople("src/main/java/com/company/peoples");
        String actual = people.stream()
                .map(person -> format("%s %s", person.getName(), person.getSurname()))
                .collect(Collectors.joining("\n"));
        String expected = "Enzo Daniels\n"+
                "Alejandra Poole\n"+
                "Leah Diaz\n"+
                "Julianne Klein\n"+
                "Dayton Wolfe\n"+
                "Ashtyn Bird\n"+
                "Dillan Russo\n"+
                "Shane Fields\n"+
                "Ishaan Schwartz\n"+
                "Hayley Sanchez\n"+
                "Miles Shah\n"+
                "Brice Walsh\n"+
                "Matthias Andrade\n"+
                "Colby Newman\n"+
                "Rocco Bender\n"+
                "Vivian Garrett\n"+
                "Maci Duran\n"+
                "Peter Stevenson\n"+
                "Beckett Cain\n"+
                "Keith Riddle\n"+
                "Melissa Klein\n"+
                "Jamir Higgins\n"+
                "Trenton Anthony\n"+
                "Saul Glenn\n"+
                "Savion Whitney\n"+
                "Gael Chambers\n"+
                "Alexandra Rowe\n"+
                "Miranda Sanders\n"+
                "Braxton Chaney\n"+
                "Kadyn Keith\n"+
                "Selena Thomas\n"+
                "Jamir Reeves\n"+
                "Armando Powers\n"+
                "Clinton Blanchard\n"+
                "Annabella Ewing\n"+
                "Rigoberto Flowers\n"+
                "Fiona Flynn\n"+
                "Pierre Mathis\n"+
                "Soren Rosales\n"+
                "Natasha Boyd";
        assertEquals(40, people.size());
        assertEquals(expected, actual);
    }

    @Test
    void should_import_form_file_and_return_list_of_offers() {
        List<Offer> offers = manager.importOffers("src/main/java/com/company/offers");
        String actual = offers.stream()
                .map(Offer::toString)
                .collect(Collectors.joining("\n"));
        String expected = "\"pearl necklace\" USD: 290.44; PLN: 1043.65; GBP: 181.62; EUR: 285.34;\n" +
                "\"ladle\" GBP: 48.23; PLN: 92.22; EUR: 9.37;\n" +
                "\"rubber duck\" PLN: 75.71; USD: 82.57;\n" +
                "\"pair of socks\" PLN: 10.34;\n" +
                "\"keyboard\" USD: 5.56;\n" +
                "\"carrot\" USD: 0.44; PLN: 2.65; GBP: 0.10; EUR: 0.34;\n" +
                "\"card\" PLN: 60.22; EUR: 9.37;\n" +
                "\"zipper\" USD: 82.57; PLN: 2.65; EUR: 0.34;\n" +
                "\"towel\" EUR: 20.00;\n" +
                "\"cellphone\" GBP: 48.23; PLN: 92.22; EUR: 9.37;\n" +
                "\"football\" EUR: 0.34; PLN: 2.65;\n" +
                "\"pine cone\" USD: 2.00;\n" +
                "\"clay pot\" GBP: 4.00;\n" +
                "\"panda\" USD: 25.00;\n" +
                "\"hanger\" PLN: 75.71; USD: 82.57;\n" +
                "\"box of markers\" GBP: 1.78;\n" +
                "\"shoes\" GBP: 110.00;\n" +
                "\"purse/bag\" PLN: 75.71; USD: 82.57;\n" +
                "\"camera\" USD: 0.44; PLN: 2.65; GBP: 0.10; EUR: 0.34;\n" +
                "\"tomato\" PLN: 6.45;\n" +
                "\"zebra\" GBP: 48.23; PLN: 92.22; EUR: 9.37;\n" +
                "\"leg warmers\" USD: 0.44; PLN: 2.65; GBP: 0.10; EUR: 0.34;\n" +
                "\"candle\" GBP: 48.23; PLN: 92.22; EUR: 9.37;\n" +
                "\"scallop shell\" USD: 0.44; PLN: 2.65; GBP: 0.10; EUR: 0.34;\n" +
                "\"hair clip\" GBP: 4.23; PLN: 12.22; EUR: 3.37;\n" +
                "\"monitor\" EUR: 252.34; PLN: 944.65;\n" +
                "\"hair pin\" EUR: 0.34; PLN: 2.65;\n" +
                "\"can of beans\" PLN: 75.71; USD: 82.57;\n" +
                "\"sketch pad\" PLN: 6.45;\n" +
                "\"radio\" USD: 40.44; PLN: 183.65; GBP: 61.62; EUR: 72.34;";
        assertEquals(30, offers.size());
        assertEquals(expected, actual);
    }
}