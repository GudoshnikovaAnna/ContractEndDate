import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TestForCalculationContractEndDate {

    List<Vertrag> vertragTestList = new ArrayList<Vertrag>();


    public List<Kondition> getListKonditionFirstValidArtUsed() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC03",
                new VertragsLaufZeit(9, "MON"),
                new VerlaengerungsFrist(1, "JHR")
        );
        Kondition kondition2 = new Kondition("ZK02",
                new VertragsLaufZeit(15, "TAG"),
                new VerlaengerungsFrist(9, "MON")
        );

        Kondition kondition3 = new Kondition("ZC02",
                new VertragsLaufZeit(9, "MON"),
                new VerlaengerungsFrist(1, "JHR")
        );

        konditions.add(kondition1);
        konditions.add(kondition2);
        konditions.add(kondition3);
        return konditions;
    }

    public List<Kondition> getListKonditionNoValid() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("TK04",
                new VertragsLaufZeit(15, "TAG"),
                new VerlaengerungsFrist(9, "MON")
        );
        Kondition kondition2 = new Kondition("ZC03",
                new VertragsLaufZeit(9, "MON"),
                new VerlaengerungsFrist(1, "JHR")
        );

        Kondition kondition3 = new Kondition("02K0",
                new VertragsLaufZeit(9, "MON"),
                new VerlaengerungsFrist(1, "JHR")
        );

        konditions.add(kondition1);
        konditions.add(kondition2);
        konditions.add(kondition3);
        return konditions;
    }

    public List<Kondition> getListKonditionNoValidInEinheit() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC02",
                new VertragsLaufZeit(15, "HGF"),
                new VerlaengerungsFrist(9, "HFR")
        );
        konditions.add(kondition1);
        return konditions;
    }

    public List<Kondition> getListKonditionNoValidInAnzahl() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC02",
                new VertragsLaufZeit(-16, "MON"),
                new VerlaengerungsFrist(-5, "JHR")
        );
        konditions.add(kondition1);
        return konditions;
    }

    public List<Kondition> getListKonditionZroStandartAnzahl() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC02",
                new VertragsLaufZeit(0, "MON"),
                new VerlaengerungsFrist(5, "JHR")
        );
        konditions.add(kondition1);
        return konditions;
    }


    public List<Kondition> getListKonditionZeroNextAnzahl() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC02",
                new VertragsLaufZeit(2, "MON"),
                new VerlaengerungsFrist(0, "JHR")
        );
        konditions.add(kondition1);
        return konditions;
    }


    public Vertrag getObject(List<Kondition> konditions, GregorianCalendar startContractDate) {
        return new Vertrag(
                new Veertragsgegenstand(
                        konditions,
                        new IstTermin(startContractDate)
                ));
    }

    CalculateContractEndDate calculateContractEndDate = new CalculateContractEndDate();
    @Test
    public void checkAddOneNextPeriodTime() throws Exception{
        GregorianCalendar currentDate =new GregorianCalendar();
        GregorianCalendar startConstractDate = new GregorianCalendar(2019, Calendar.JANUARY,1);
        Assert.assertEquals(calculateContractEndDate.mappingDataAndOutputDate(getObject(getListKonditionFirstValidArtUsed(), startConstractDate),currentDate), "16.10.2019", "Basic test has fallen");
    }
    @Test
    public void checkAddOneStandartPeriodTime() throws Exception {
        GregorianCalendar currentDate =new GregorianCalendar();
        GregorianCalendar startConstractDate = new GregorianCalendar(2019, Calendar.FEBRUARY,20);
        Assert.assertEquals(calculateContractEndDate.mappingDataAndOutputDate(getObject(getListKonditionFirstValidArtUsed(), startConstractDate),currentDate), "07.03.2019", "Basic test has fallen");
    }

    @Test
    public void checkAddMoreNextPeriodTime() throws Exception {
        GregorianCalendar currentDate =new GregorianCalendar();
        GregorianCalendar startConstractDate = new GregorianCalendar(2018, Calendar.FEBRUARY,20);
        Assert.assertEquals(calculateContractEndDate.mappingDataAndOutputDate(getObject(getListKonditionFirstValidArtUsed(), startConstractDate),currentDate), "07.09.2019", "Basic test has fallen");
    }

    @Test
    public void checkZeroStandartTime() throws Exception {
        GregorianCalendar currentDate =new GregorianCalendar();
        GregorianCalendar startConstractDate = new GregorianCalendar(2018, Calendar.FEBRUARY,20);
        Assert.assertEquals(calculateContractEndDate.mappingDataAndOutputDate(getObject(getListKonditionZroStandartAnzahl(), startConstractDate),currentDate), "20.02.2023", "Basic test has fallen");
    }

    @Test(expectedExceptions = Exception.class)
    public void checkZeroNextTimePeriod() throws Exception {
        GregorianCalendar currentDate =new GregorianCalendar();
        GregorianCalendar startConstractDate = new GregorianCalendar(2018, Calendar.FEBRUARY,20);
        calculateContractEndDate.mappingDataAndOutputDate(getObject(getListKonditionZeroNextAnzahl(), startConstractDate),currentDate);
    }

    @Test(expectedExceptions = Exception.class)
    public void checkKondition02InTheBeginning() throws Exception {
        GregorianCalendar currentDate =new GregorianCalendar();
        GregorianCalendar startConstractDate = new GregorianCalendar(2018, Calendar.FEBRUARY,20);
        calculateContractEndDate.mappingDataAndOutputDate(getObject(getListKonditionNoValid(),startConstractDate),currentDate);
    }

    @Test(expectedExceptions = Exception.class)
    public void checkKonditionInvalidEnhieit() throws Exception {
        GregorianCalendar currentDate =new GregorianCalendar();
        GregorianCalendar startConstractDate = new GregorianCalendar(2018, Calendar.FEBRUARY,20);
        calculateContractEndDate.mappingDataAndOutputDate(getObject(getListKonditionNoValidInEinheit(),startConstractDate),currentDate);
    }

    @Test(expectedExceptions = Exception.class)
    public void checkKonditionInvalidNegativeAnzahl() throws Exception {
        GregorianCalendar currentDate =new GregorianCalendar();
        GregorianCalendar startConstractDate = new GregorianCalendar(2018, Calendar.FEBRUARY,20);
        calculateContractEndDate.mappingDataAndOutputDate(getObject(getListKonditionNoValidInAnzahl(),startConstractDate),currentDate);
    }



}
