import io.javalin.Javalin;

import java.text.SimpleDateFormat;
import java.util.*;


public class CalculateContractEndDate {
    public CalculateContractEndDate() {
    }

    /*public static void main(String[] args) throws Exception{
        //Javalin javalinapp = Javalin.create().start(7000);
        //javalinapp.get("/", ctx -> ctx.result("Hello"));

        GregorianCalendar currentDate =new GregorianCalendar();

        mappingDataAndOutputDate(getObject(),currentDate);
    }*/

    public GregorianCalendar calculateEndDate(GregorianCalendar startDate, GregorianCalendar currentDate, int standartPeriodTime, String typeOfStandartPeriodTime, int nextPeriodTime, String typerOfNextPeriodTime)  throws Exception {

    if (typeOfStandartPeriodTime.equals("JHR") && standartPeriodTime >= 0 && nextPeriodTime > 0) {
        startDate.add(Calendar.YEAR, standartPeriodTime);
        if (currentDate.before(startDate)) {
            return startDate;
        } else {
            if (typerOfNextPeriodTime.equals("JHR")) {
                startDate.add(Calendar.YEAR, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.YEAR, nextPeriodTime);
                }
                return startDate;
            } else if (typerOfNextPeriodTime.equals("MON")) {
                startDate.add(Calendar.MONTH, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.MONTH, nextPeriodTime);
                }
                return startDate;
            } else if (typerOfNextPeriodTime.equals("TAG")) {
                startDate.add(Calendar.DAY_OF_MONTH, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.DAY_OF_MONTH, nextPeriodTime);
                }
                return startDate;
            }
        }
    } else if (typeOfStandartPeriodTime.equals("MON") && standartPeriodTime >= 0 && nextPeriodTime > 0) {
        startDate.add(Calendar.MONTH, standartPeriodTime);
        if (currentDate.before(startDate)) {
            return startDate;
        } else {
            if (typerOfNextPeriodTime.equals("JHR")) {
                startDate.add(Calendar.YEAR, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.YEAR, nextPeriodTime);
                }
                return startDate;
            } else if (typerOfNextPeriodTime.equals("MON")) {
                startDate.add(Calendar.MONTH, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.MONTH, nextPeriodTime);
                }
                return startDate;
            } else if (typerOfNextPeriodTime.equals("TAG")) {
                startDate.add(Calendar.DAY_OF_MONTH, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.DAY_OF_MONTH, nextPeriodTime);
                }
                return startDate;
            }
        }
    } else if (typeOfStandartPeriodTime.equals("TAG") && standartPeriodTime >= 0 && nextPeriodTime > 0) {
        startDate.add(Calendar.DAY_OF_MONTH, standartPeriodTime);
        if (currentDate.before(startDate)) {
            return startDate;
        } else {
            if (typerOfNextPeriodTime.equals("JHR")) {
                startDate.add(Calendar.YEAR, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.YEAR, nextPeriodTime);
                }
                return startDate;
            } else if (typerOfNextPeriodTime.equals("MON")) {
                startDate.add(Calendar.MONTH, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.MONTH, nextPeriodTime);
                }
                return startDate;
            } else if (typerOfNextPeriodTime.equals("TAG")) {
                startDate.add(Calendar.DAY_OF_MONTH, nextPeriodTime);
                while (currentDate.after(startDate)) {
                    startDate.add(Calendar.DAY_OF_MONTH, nextPeriodTime);
                }
                return startDate;
            }
        }
    }
    else throw new Exception("Not valid data fields for calculating Contract End Date");
    return null;
    }




    public String mappingDataAndOutputDate(Vertrag vertrag, GregorianCalendar currentDate) throws Exception{
        Date resultDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Kondition k : vertrag.veertragsgegenstand.konditionList) {
            if (k.art.startsWith("Z") && k.art.endsWith("02")) {
                resultDate = calculateEndDate(vertrag.veertragsgegenstand.istTermin.vertragsBeginnTerminIst, currentDate, k.vertragsLaufZeit.anzahl,k.vertragsLaufZeit.einheit, k.verlaengerungsFrist.anzahl, k.verlaengerungsFrist.einheit).getTime();
                break;
            }
        }
        if (resultDate != null)return simpleDateFormat.format(resultDate);
        else throw new Exception("In Vertag there is no konditions with 02");
    }
}


