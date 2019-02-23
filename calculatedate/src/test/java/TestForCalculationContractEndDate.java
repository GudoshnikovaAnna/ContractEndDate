import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TestForCalculationContractEndDate {

    List<Vertrag> vertragTestList = new ArrayList<Vertrag>();




    public Vertrag getObject() {
        Kondition kondition1 = new Kondition("ZK02",
                new VertragsLaufZeit(15, "TAG"),
                new VerlaengerungsFrist(9, "MON")
        );
        Kondition kondition2 = new Kondition("ZC03",
                new VertragsLaufZeit(9, "MON"),
                new VerlaengerungsFrist(1, "JHR")
        );

        Kondition kondition3 = new Kondition("ZK02",
                new VertragsLaufZeit(9, "MON"),
                new VerlaengerungsFrist(1, "JHR")
        );


        List<Kondition> konditions = new ArrayList<Kondition>();

        konditions.add(kondition1);
        konditions.add(kondition2);
        konditions.add(kondition3);

        return new Vertrag(
                new Veertragsgegenstand(
                        konditions,
                        new IstTermin(new GregorianCalendar(2019, Calendar.JANUARY, 1))
                ));
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    public void checkoFromTestTask() throws Exception{
        CalculateContractEndDate calculateContractEndDate = new CalculateContractEndDate();
        GregorianCalendar currentDate =new GregorianCalendar();
        Assert.assertEquals(calculateContractEndDate.mappingDataAndOutputDate(getObject(),currentDate), "16.10.2019", "Basic test has fallen");
    }

}
